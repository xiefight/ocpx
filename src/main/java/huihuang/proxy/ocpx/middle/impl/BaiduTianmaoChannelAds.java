package huihuang.proxy.ocpx.middle.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.litianjingdong.LTJDPath;
import huihuang.proxy.ocpx.ads.tianmao.TianmaoAdsDTO;
import huihuang.proxy.ocpx.ads.tianmao.TianmaoParamEnum;
import huihuang.proxy.ocpx.ads.tianmao.TianmaoParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.ITianmaoAdsDao;
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
 * @Author: xietao
 * @Date: 2023/5/16 20:29
 */
@Component("btChannelAds")
public class BaiduTianmaoChannelAds extends BaseSupport implements IChannelAds {

    @Autowired
    private ITianmaoAdsDao tianmaoAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_TIANMAO;

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历tianmao查找baidu对应的宏参数
        Set<TianmaoParamEnum> tianmaoParamEnums = TianmaoParamEnum.tianmaoBaiduMap.keySet();
        for (TianmaoParamEnum tianmao : tianmaoParamEnums) {
            BaiduParamEnum baidu = TianmaoParamEnum.tianmaoBaiduMap.get(tianmao);
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
        return serverPath + Constants.ServerPath.BAIDU_TIANMAO + Constants.ServerPath.CLICK_REPORT + "?" + macroStr;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        TianmaoParamField tianmaoParamField = new TianmaoParamField();

        Set<Map.Entry<TianmaoParamEnum, BaiduParamEnum>> btSet = TianmaoParamEnum.tianmaoBaiduMap.entrySet();
        btSet.stream().filter(bt -> Objects.nonNull(bt.getValue())).forEach(tm -> {
            TianmaoParamEnum tianmao = tm.getKey();
            BaiduParamEnum baidu = tm.getValue();
            //tianmao的字段名
            String ltjdField = tianmao.getName();
            String baiduParam = baidu.getParam();
            String[] value = parameterMap.get(baiduParam);
            if (Objects.isNull(value) || value.length == 0) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(ltjdField, tianmaoParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(tianmaoParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, tianmaoParamField);
        return tianmaoParamField;
    }

    @Override
    protected void convertParams(Object adsObj) {
        TianmaoParamField tianmaoParamField = (TianmaoParamField) adsObj;
        if (null != tianmaoParamField.getCallback_url()) {
            tianmaoParamField.setCallback_url(URLEncoder.createQuery().encode(tianmaoParamField.getCallback_url(), StandardCharsets.UTF_8));
        }
//        tianmaoParamField.setApp_type(osConvertAppType(tianmaoParamField.getApp_type()));
        tianmaoParamField.setRequest_id(String.valueOf(System.currentTimeMillis()));
        //时间戳，秒
        String ts = Optional.ofNullable(tianmaoParamField.getTs()).orElse(String.valueOf(System.currentTimeMillis()));
        tianmaoParamField.setTs(String.valueOf(Long.parseLong(ts) / 1000));
        if (null != tianmaoParamField.getUa()) {
            tianmaoParamField.setUa(URLEncoder.createQuery().encode(tianmaoParamField.getUa(), StandardCharsets.UTF_8));
        }
        //签名
//        signature(tianmaoParamField);
        logger.info("clickReport {} 特殊参数进行转换 convertParams:{}", channelAdsKey, tianmaoParamField);
    }

    @Override
    protected Response judgeParams(Object adsObj) {
        TianmaoParamField tianmaoParamField = (TianmaoParamField) adsObj;
        if (Objects.isNull(tianmaoParamField.getSignature())) {
            return BasicResult.getFailResponse(TianmaoParamEnum.SIGNATURE.getName() + "不能为空");
        }
        if (Objects.isNull(tianmaoParamField.getTp_adv_id())) {
            return BasicResult.getFailResponse(TianmaoParamEnum.TP_ADV_ID.getName() + "不能为空");
        }
        if (Objects.isNull(tianmaoParamField.getAccess_id())) {
            return BasicResult.getFailResponse(TianmaoParamEnum.ACCESS_ID.getName() + "不能为空");
        }
        if (Objects.isNull(tianmaoParamField.getRequest_id())) {
            return BasicResult.getFailResponse(TianmaoParamEnum.REQUEST_ID.getName() + "不能为空");
        }
        //如果ios设备为空，则判断安卓设备
        if (Objects.isNull(tianmaoParamField.getIdfa()) && Objects.isNull(tianmaoParamField.getIdfa_md5())
                && Objects.isNull(tianmaoParamField.getImei()) && Objects.isNull(tianmaoParamField.getImei_md5())
                && Objects.isNull(tianmaoParamField.getOaid()) && Objects.isNull(tianmaoParamField.getOaid_md5())) {
            return BasicResult.getFailResponse("安卓设备：" + TianmaoParamEnum.IMEI.getName() + "、" + TianmaoParamEnum.OAID.getName()
                    + "、" + TianmaoParamEnum.IMEI_MD5.getName() + "、" + TianmaoParamEnum.OAID_MD5.getName() + "不能同时为空；"
                    + " ios设备" + TianmaoParamEnum.IDFA.getName() + "、" + TianmaoParamEnum.IDFA_MD5.getName() + "不能同时为空");
        }
        return BasicResult.getSuccessResponse();
    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        TianmaoParamField tianmaoParamField = (TianmaoParamField) adsObj;
        TianmaoAdsDTO tianmaoAdsDTO = new TianmaoAdsDTO();
        BeanUtil.copyProperties(tianmaoParamField, tianmaoAdsDTO);
        tianmaoAdsDTO.setChannelName(BaiduPath.BAIDU_CHANNEL_NAME);
        tianmaoAdsDao.insert(tianmaoAdsDTO);
        logger.info("clickReport {} 将原始参数保存数据库，返回数据库对象 saveOriginParamData:{}", channelAdsKey, tianmaoAdsDTO);
        return tianmaoAdsDTO;
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {
        TianmaoParamField tianmaoParamField = (TianmaoParamField) adsObj;
        TianmaoAdsDTO tianmaoAdsDTO = (TianmaoAdsDTO) adsDtoObj;
        String ocpxUrl = queryServerPath() + Constants.ServerPath.BAIDU_TIANMAO + Constants.ServerPath.ADS_CALLBACK + "/" + tianmaoAdsDTO.getId() + "?";
        logger.info("clickReport {} 客户回调渠道的url：{}", channelAdsKey, ocpxUrl);
        String encodeUrl = URLEncoder.createQuery().encode(ocpxUrl, StandardCharsets.UTF_8);
//            ocpxUrl = URLEncoder.encode(ocpxUrl, "UTF-8");
        tianmaoParamField.setCallback_url(encodeUrl);
        logger.info("clickReport {} 回调参数 replaceCallbackUrl:{}", channelAdsKey, tianmaoParamField);
    }

    @Override
    protected String initAdsUrl() {
        return LTJDPath.BASIC_URI;
    }

    @Override
    protected Response reportAds(String adsUrl, Object adsDtoObj) throws Exception {
        logger.info("调用用户侧的地址 {} adsUrl:{}", channelAdsKey, adsUrl);
        HttpResponse response = HttpRequest.get(adsUrl).timeout(20000).header("token", "application/json").execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        TianmaoAdsDTO tianmaoAdsDTO = (TianmaoAdsDTO) adsDtoObj;
        TianmaoAdsDTO tianmaoAdsVO = new TianmaoAdsDTO();
        tianmaoAdsVO.setId(tianmaoAdsDTO.getId());
        //上报成功
        if (HttpStatus.HTTP_OK == response.getStatus() && Objects.requireNonNull(responseBodyMap).get("code").equals("0")) {
            tianmaoAdsVO.setReportStatus(Constants.ReportStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(tianmaoAdsVO, tianmaoAdsDao);
            logger.info("clickReport {} 上报-广告侧接口请求成功:{} 数据:{}", channelAdsKey, response, tianmaoAdsVO);
            return BasicResult.getSuccessResponse(tianmaoAdsDTO.getId());
        } else {
            tianmaoAdsVO.setReportStatus(Constants.ReportStatus.FAIL.getCode() + "--" + JSONObject.toJSONString(responseBodyMap));
            baseServiceInner.updateAdsObject(tianmaoAdsVO, tianmaoAdsDao);
            logger.error("clickReport {} 上报-广告侧接口请求失败:{} 数据:{}", channelAdsKey, response, tianmaoAdsVO);
            return BasicResult.getFailResponse("上报-广告侧接口请求失败", 0);
        }
    }

    //计算签名
    private void signature(TianmaoParamField tianmaoParamField) {
        String access_id = tianmaoParamField.getAccess_id();
        String ts = tianmaoParamField.getTs();
        String src = "access_id=" + access_id + "&ts=" + ts;
        String signatureStr = src + BaiduPath.YOUKU_SECRET;
        String signature = DigestUtil.md5Hex(signatureStr).toLowerCase();
        logger.info("clickReport {} 原始:{}  签名:{}", channelAdsKey, signatureStr, signature);
        tianmaoParamField.setSignature(signature);
    }


}
