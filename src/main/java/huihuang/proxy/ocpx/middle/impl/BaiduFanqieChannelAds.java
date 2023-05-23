package huihuang.proxy.ocpx.middle.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.fanqie.FanqieAdsDTO;
import huihuang.proxy.ocpx.ads.fanqie.FanqieParamEnum;
import huihuang.proxy.ocpx.ads.fanqie.FanqieParamField;
import huihuang.proxy.ocpx.ads.fanqie.FanqiePath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IFanqieAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.middle.BaseSupport;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
 * baidu--ltjd
 *
 * @Author: xietao
 * @Date: 2023/5/10 22:28
 */
@Component("bfChannelAds")
public class BaiduFanqieChannelAds extends BaseSupport implements IChannelAds {

    @Autowired
    private IFanqieAdsDao fanqieAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_FANQIE;

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历Fanqie查找baidu对应的宏参数
        Set<FanqieParamEnum> fanqieParamEnums = FanqieParamEnum.baiduFanqieMap.keySet();
        for (FanqieParamEnum fanqie : fanqieParamEnums) {
            BaiduParamEnum baidu = FanqieParamEnum.baiduFanqieMap.get(fanqie);
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
        return serverPath + Constants.ServerPath.BAIDU_FANQIE + Constants.ServerPath.CLICK_REPORT + "?" + macroStr;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        FanqieParamField fanqieParamField = new FanqieParamField();

        Set<Map.Entry<FanqieParamEnum, BaiduParamEnum>> bfSet = FanqieParamEnum.baiduFanqieMap.entrySet();
        bfSet.stream().filter(bf -> Objects.nonNull(bf.getValue())).forEach(bf -> {
            FanqieParamEnum fanqie = bf.getKey();
            BaiduParamEnum baidu = bf.getValue();
            //fanqie的字段名
            String fanqieField = fanqie.getName();
            String baiduParam = baidu.getParam();
            String[] value = parameterMap.get(baiduParam);
            if (Objects.isNull(value) || value.length == 0) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(fanqieField, fanqieParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(fanqieParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, fanqieParamField);
        return fanqieParamField;
    }

    @Override
    protected void convertParams(Object adsObj) {
        FanqieParamField fanqieParamField = (FanqieParamField) adsObj;
        if (null != fanqieParamField.getCallback_url()) {
            fanqieParamField.setCallback_url(URLEncoder.createQuery().encode(fanqieParamField.getCallback_url(), StandardCharsets.UTF_8));
        }
//        fanqieParamField.setApp_type(osConvertAppType(fanqieParamField.getApp_type()));
        fanqieParamField.setRequest_id(String.valueOf(System.currentTimeMillis()));
        //时间戳，秒
        String ts = Optional.ofNullable(fanqieParamField.getTs()).orElse(String.valueOf(System.currentTimeMillis()));
        fanqieParamField.setTs(String.valueOf(Long.parseLong(ts) / 1000));
        if (null != fanqieParamField.getUa()) {
            fanqieParamField.setUa(URLEncoder.createQuery().encode(fanqieParamField.getUa(), StandardCharsets.UTF_8));
        }
        //签名
        signature(fanqieParamField);
        logger.info("clickReport {} 特殊参数进行转换 convertParams:{}", channelAdsKey, fanqieParamField);
    }


    @Override
    protected Response judgeParams(Object adsObj) {
        FanqieParamField fanqieParamField = (FanqieParamField) adsObj;
        if (Objects.isNull(fanqieParamField.getSignature())) {
            return BasicResult.getFailResponse(FanqieParamEnum.SIGNATURE.getName() + "不能为空");
        }
        if (Objects.isNull(fanqieParamField.getTp_adv_id())) {
            return BasicResult.getFailResponse(FanqieParamEnum.TP_ADV_ID.getName() + "不能为空");
        }
        if (Objects.isNull(fanqieParamField.getAccess_id())) {
            return BasicResult.getFailResponse(FanqieParamEnum.ACCESS_ID.getName() + "不能为空");
        }
        if (Objects.isNull(fanqieParamField.getRequest_id())) {
            return BasicResult.getFailResponse(FanqieParamEnum.REQUEST_ID.getName() + "不能为空");
        }
        //如果ios设备为空，则判断安卓设备
        if (Objects.isNull(fanqieParamField.getIdfa()) && Objects.isNull(fanqieParamField.getIdfa_md5())
                && Objects.isNull(fanqieParamField.getImei()) && Objects.isNull(fanqieParamField.getImei_md5())
                && Objects.isNull(fanqieParamField.getOaid()) && Objects.isNull(fanqieParamField.getOaid_md5())) {
            return BasicResult.getFailResponse("安卓设备：" + FanqieParamEnum.IMEI.getName() + "、" + FanqieParamEnum.OAID.getName()
                    + "、" + FanqieParamEnum.IMEI_MD5.getName() + "、" + FanqieParamEnum.OAID_MD5.getName() + "不能同时为空；"
                    + " ios设备" + FanqieParamEnum.IDFA.getName() + "、" + FanqieParamEnum.IDFA_MD5.getName() + "不能同时为空");
        }
        return BasicResult.getSuccessResponse();
    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        FanqieParamField fanqieParamField = (FanqieParamField) adsObj;
        FanqieAdsDTO fanqieAdsDTO = new FanqieAdsDTO();
        BeanUtil.copyProperties(fanqieParamField, fanqieAdsDTO);
        fanqieAdsDTO.setChannelName(BaiduPath.BAIDU_CHANNEL_NAME);
        fanqieAdsDao.insert(fanqieAdsDTO);
        logger.info("clickReport {} 将原始参数保存数据库，返回数据库对象 saveOriginParamData:{}", channelAdsKey, fanqieAdsDTO);
        return fanqieAdsDTO;
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {
        FanqieParamField fanqieParamField = (FanqieParamField) adsObj;
        FanqieAdsDTO fanqieAdsDTO = (FanqieAdsDTO) adsDtoObj;
        String ocpxUrl = queryServerPath() + Constants.ServerPath.BAIDU_FANQIE + Constants.ServerPath.ADS_CALLBACK + "/" + fanqieAdsDTO.getId() + "?";
        logger.info("clickReport {} 客户回调渠道的url：{}", channelAdsKey, ocpxUrl);
        String encodeUrl = URLEncoder.createQuery().encode(ocpxUrl, StandardCharsets.UTF_8);
//            ocpxUrl = URLEncoder.encode(ocpxUrl, "UTF-8");
        fanqieParamField.setCallback_url(encodeUrl);
        logger.info("clickReport {} 回调参数 replaceCallbackUrl:{}", channelAdsKey, fanqieParamField);
    }

    @Override
    protected String initAdsUrl() {
        return FanqiePath.BASIC_URI;
    }

    @Override
    protected Response reportAds(String adsUrl, Object adsDtoObj) throws Exception {
        logger.info("调用用户侧的地址 {} adsUrl:{}", channelAdsKey, adsUrl);
        HttpResponse response = HttpRequest.get(adsUrl).timeout(20000).header("token", "application/json").execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        FanqieAdsDTO fanqieAdsDTO = (FanqieAdsDTO) adsDtoObj;
        FanqieAdsDTO fanqieAdsVO = new FanqieAdsDTO();
        fanqieAdsVO.setId(fanqieAdsDTO.getId());
        //上报成功
        if (HttpStatus.HTTP_OK == response.getStatus() && Objects.requireNonNull(responseBodyMap).get("code").equals("0")) {
            fanqieAdsVO.setReportStatus(Constants.ReportStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(fanqieAdsVO, fanqieAdsDao);
            logger.info("clickReport {} 上报广告侧接口请求成功:{} 数据:{}", channelAdsKey, response, fanqieAdsVO);
            return BasicResult.getSuccessResponse(fanqieAdsDTO.getId());
        } else {
            fanqieAdsVO.setReportStatus(Constants.ReportStatus.FAIL.getCode() + "--" + JSONObject.toJSONString(responseBodyMap));
            baseServiceInner.updateAdsObject(fanqieAdsVO, fanqieAdsDao);
            logger.error("clickReport {} 上报广告侧接口请求失败:{} 数据:{}", channelAdsKey, response, fanqieAdsVO);
            return BasicResult.getFailResponse("上报广告侧接口请求失败", 0);
        }
    }

    //计算签名
    private void signature(FanqieParamField fanqieParamField) {
        String access_id = fanqieParamField.getAccess_id();
        String ts = fanqieParamField.getTs();
        String src = "access_id=" + access_id + "&ts=" + ts;
        String signatureStr = src + FanqiePath.SECRET;
        String signature = DigestUtil.md5Hex(signatureStr).toLowerCase();
        logger.info("clickReport {} 原始:{}  签名:{}", channelAdsKey, signatureStr, signature);
        fanqieParamField.setSignature(signature);
    }


}
