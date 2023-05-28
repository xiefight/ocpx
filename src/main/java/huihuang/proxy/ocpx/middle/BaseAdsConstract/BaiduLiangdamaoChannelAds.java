package huihuang.proxy.ocpx.middle.BaseAdsConstract;

import cn.hutool.core.net.URLEncoder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoAdsDTO;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamEnum;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamField;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoPath;
import huihuang.proxy.ocpx.ads.litianjingdong.LTJDPath;
import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.middle.BaseSupport;
import huihuang.proxy.ocpx.middle.IChannelAds;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * @Description: 上报客户的逻辑再抽象
 * 比如：百度和京东、优酷、番茄的对接，本质上是百度和粮大猫的对接，抽离出百度和粮大猫的公共部分，将京东、优酷、番茄的不同参数传入
 * @Author: xietao
 * @Date: 2023-05-23 17:39
 **/
public abstract class BaiduLiangdamaoChannelAds extends BaseSupport implements IChannelAds {

    protected abstract String channelAdsKey();

    protected abstract String serverPathKey();

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历liangdamao查找baidu对应的宏参数
        Set<LiangdamaoParamEnum> liangdamaoParamEnums = LiangdamaoParamEnum.liangdamaoBaiduMap.keySet();
        for (LiangdamaoParamEnum liangdamao : liangdamaoParamEnums) {
            BaiduParamEnum baidu = LiangdamaoParamEnum.liangdamaoBaiduMap.get(liangdamao);
            if (Objects.isNull(baidu) || StrUtil.isEmpty(baidu.getMacro())) {
                continue;
            }
            macro.append(baidu.getParam()).append("=").append(baidu.getMacro()).append("&");
        }
        String macroStr = macro.toString();
        if (macroStr.endsWith("&")) {
            macroStr = macroStr.substring(0, macroStr.length() - 1);
        }
        //2.config中查找服务地址
        String serverPath = queryServerPath();
        //3.拼接监测地址
        return serverPath + serverPathKey() + Constants.ServerPath.CLICK_REPORT + "?" + macroStr;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        LiangdamaoParamField liangdamaoParamField = new LiangdamaoParamField();

        Set<Map.Entry<LiangdamaoParamEnum, BaiduParamEnum>> blSet = LiangdamaoParamEnum.liangdamaoBaiduMap.entrySet();
        blSet.stream().filter(bl -> Objects.nonNull(bl.getValue())).forEach(bl -> {
            LiangdamaoParamEnum liangdamao = bl.getKey();
            BaiduParamEnum baidu = bl.getValue();
            //liangdamao的字段名
            String liangdamaoField = liangdamao.getName();
            String baiduParam = baidu.getParam();
            String[] value = parameterMap.get(baiduParam);
            if (Objects.isNull(value) || value.length == 0) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(liangdamaoField, liangdamaoParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(liangdamaoParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return liangdamaoParamField;
    }

    @Override
    protected void convertParams(Object adsObj) {
        LiangdamaoParamField liangdamaoParamField = (LiangdamaoParamField) adsObj;
        if (null != liangdamaoParamField.getCallback_url()) {
            liangdamaoParamField.setCallback_url(URLEncoder.createQuery().encode(liangdamaoParamField.getCallback_url(), StandardCharsets.UTF_8));
        }
//        liangdamaoParamField.setApp_type(osConvertAppType(liangdamaoParamField.getApp_type()));
        liangdamaoParamField.setRequest_id(String.valueOf(System.currentTimeMillis()));
        //时间戳，秒
        String ts = Optional.ofNullable(liangdamaoParamField.getTs()).orElse(String.valueOf(System.currentTimeMillis()));
        liangdamaoParamField.setTs(String.valueOf(Long.parseLong(ts) / 1000));
        if (null != liangdamaoParamField.getUa()) {
            liangdamaoParamField.setUa(URLEncoder.createQuery().encode(liangdamaoParamField.getUa(), StandardCharsets.UTF_8));
        }
        //签名
        signature(liangdamaoParamField);
    }

    @Override
    protected Response judgeParams(Object adsObj) {
        LiangdamaoParamField liangdamaoParamField = (LiangdamaoParamField) adsObj;
        if (Objects.isNull(liangdamaoParamField.getSignature())) {
            return BasicResult.getFailResponse(LiangdamaoParamEnum.SIGNATURE.getName() + "不能为空");
        }
        if (Objects.isNull(liangdamaoParamField.getTp_adv_id())) {
            return BasicResult.getFailResponse(LiangdamaoParamEnum.TP_ADV_ID.getName() + "不能为空");
        }
        if (Objects.isNull(liangdamaoParamField.getAccess_id())) {
            return BasicResult.getFailResponse(LiangdamaoParamEnum.ACCESS_ID.getName() + "不能为空");
        }
        if (Objects.isNull(liangdamaoParamField.getRequest_id())) {
            return BasicResult.getFailResponse(LiangdamaoParamEnum.REQUEST_ID.getName() + "不能为空");
        }
        //如果ios设备为空，则判断安卓设备
        if (Objects.isNull(liangdamaoParamField.getIdfa()) && Objects.isNull(liangdamaoParamField.getIdfa_md5())
                && Objects.isNull(liangdamaoParamField.getImei()) && Objects.isNull(liangdamaoParamField.getImei_md5())
                && Objects.isNull(liangdamaoParamField.getOaid()) && Objects.isNull(liangdamaoParamField.getOaid_md5())) {
            return BasicResult.getFailResponse("安卓设备：" + LiangdamaoParamEnum.IMEI.getName() + "、" + LiangdamaoParamEnum.OAID.getName()
                    + "、" + LiangdamaoParamEnum.IMEI_MD5.getName() + "、" + LiangdamaoParamEnum.OAID_MD5.getName() + "不能同时为空；"
                    + " ios设备" + LiangdamaoParamEnum.IDFA.getName() + "、" + LiangdamaoParamEnum.IDFA_MD5.getName() + "不能同时为空");
        }
        return BasicResult.getSuccessResponse();
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {
        LiangdamaoParamField liangdamaoParamField = (LiangdamaoParamField) adsObj;
        LiangdamaoAdsDTO liangdamaoAdsDTO = (LiangdamaoAdsDTO) adsDtoObj;
        String ocpxUrl = queryServerPath() + serverPathKey() + Constants.ServerPath.ADS_CALLBACK + "/" + liangdamaoAdsDTO.getId() + "?";
        logger.info("clickReport {} 客户回调渠道的url：{}", channelAdsKey(), ocpxUrl);
        String encodeUrl = URLEncoder.createQuery().encode(ocpxUrl, StandardCharsets.UTF_8);
//            ocpxUrl = URLEncoder.encode(ocpxUrl, "UTF-8");
        liangdamaoParamField.setCallback_url(encodeUrl);
        logger.info("clickReport {} 回调参数 replaceCallbackUrl:{}", channelAdsKey(), liangdamaoParamField);
    }

    @Override
    protected String initAdsUrl() {
        return LTJDPath.BASIC_URI;
    }

    //计算签名
    private void signature(LiangdamaoParamField liangdamaoParamField) {
        String access_id = liangdamaoParamField.getAccess_id();
        String ts = liangdamaoParamField.getTs();
        String src = "access_id=" + access_id + "&ts=" + ts;
        String signatureStr = src + LiangdamaoPath.SECRET;
        String signature = DigestUtil.md5Hex(signatureStr).toLowerCase();
        logger.info("clickReport {} 原始:{}  签名:{}", channelAdsKey(), signatureStr, signature);
        liangdamaoParamField.setSignature(signature);
    }

}
