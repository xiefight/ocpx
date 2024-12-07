package huihuang.proxy.ocpx.middle.baseadsreport.weibo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.weibo.WeiboAdsDTO;
import huihuang.proxy.ocpx.ads.weibo.WeiboParamField;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.BaseSupport;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import huihuang.proxy.ocpx.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public abstract class WeiboReportFactory extends BaseSupport implements IChannelAds {

    @Autowired
    protected BaseServiceInner baseServiceInner;

    protected abstract String channelAdsKey();

    protected abstract String serverPathKey();

    protected abstract String channelName();

    protected abstract IMarkDao adsDao();


    @Override
    protected void convertParams(Object adsObj) {
        WeiboParamField weiboParamField = (WeiboParamField) adsObj;
        if (null != weiboParamField.getCb()) {
            weiboParamField.setCb(URLEncoder.createQuery().encode(weiboParamField.getCb(), StandardCharsets.UTF_8));
        }
        //uniqueid
        weiboParamField.setUniq_id(RandomUtil.randomStamp());
        if (weiboParamField.getLanguage() == null) {
            weiboParamField.setLanguage("zh_CN");
        }
        if (weiboParamField.getIdfa_md5() != null) {
            weiboParamField.setOs("1");
        } else {
            weiboParamField.setOs("0");
        }
        //md5转大写
        if (weiboParamField.getOaid_md5() != null) {
            weiboParamField.setOaid_md5(weiboParamField.getOaid_md5().toUpperCase(Locale.ROOT));
        }
        if (weiboParamField.getIdfa_md5() != null) {
            weiboParamField.setIdfa_md5(weiboParamField.getIdfa_md5().toUpperCase(Locale.ROOT));
        }
        if (weiboParamField.getImei_md5() != null) {
            weiboParamField.setImei_md5(weiboParamField.getImei_md5().toUpperCase(Locale.ROOT));
        }
        //监测类型 0：曝光 1：点击（默认）
        if (weiboParamField.getMonitorType() == null) {
            weiboParamField.setMonitorType("1");
        }
        weiboParamField.setTs(String.valueOf((System.currentTimeMillis())));
//        if (weiboParamField.getTs() == null) {
//            weiboParamField.setTs(String.valueOf((System.currentTimeMillis())));
//        }
        logger.info("clickReport {} 特殊参数进行转换 convertParams:{}", channelAdsKey(), weiboParamField);
    }


    @Override
    protected Response judgeParams(Object adsObj) {
        /*QuannengHudongParamField quannengHudongParamField = (QuannengHudongParamField) adsObj;
        if (Objects.isNull(quannengHudongParamField.getCallback())) {
            return BasicResult.getFailResponse(QuannengHudongParamEnum.CALLBACK.getName() + "不能为空");
        }
        if (Objects.isNull(quannengHudongParamField.getUniqueid())) {
            return BasicResult.getFailResponse(QuannengHudongParamEnum.UNIQUE_ID.getName() + "不能为空");
        }
        if (Objects.isNull(quannengHudongParamField.getIdfa())
                && Objects.isNull(quannengHudongParamField.getImei())
                && Objects.isNull(quannengHudongParamField.getOaid())
        ) {
            return BasicResult.getFailResponse("安卓设备：" + QuannengHudongParamEnum.IMEI.getName() + "、" + QuannengHudongParamEnum.OAID.getName() + "不能同时为空；"
                    + " ios设备" + QuannengHudongParamEnum.IDFA.getName() + "不能为空");
        }*/
        return BasicResult.getSuccessResponse();
    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        WeiboParamField weiboParamField = (WeiboParamField) adsObj;
        WeiboAdsDTO weiboAdsDTO = new WeiboAdsDTO();
        try {
            BeanUtil.copyProperties(weiboParamField, weiboAdsDTO);
            weiboAdsDTO.setChannelName(channelName());
            baseServiceInner.insertAdsObject(weiboAdsDTO, adsDao());
            logger.info("clickReport {} 将原始参数保存数据库，返回数据库对象 saveOriginParamData:{}", channelAdsKey(), weiboAdsDTO);
        } catch (Exception e) {
            logger.error("clickReport {} 保存原始参数数据异常:{}", channelAdsKey(), e);
        }
        return weiboAdsDTO;
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {
        WeiboParamField weiboParamField = (WeiboParamField) adsObj;
        WeiboAdsDTO weiboAdsDTO = (WeiboAdsDTO) adsDtoObj;
        String ocpxUrl = queryServerPath() + serverPathKey() + Constants.ServerPath.ADS_CALLBACK + "/" + weiboAdsDTO.getId() + "?";
        logger.info("clickReport {} 客户回调渠道的url：{}", channelAdsKey(), ocpxUrl);
        String encodeUrl = URLEncoder.createQuery().encode(ocpxUrl, StandardCharsets.UTF_8);
//            ocpxUrl = URLEncoder.encode(ocpxUrl, "UTF-8");
        weiboParamField.setCb(encodeUrl);
        logger.info("clickReport {} 回调参数 replaceCallbackUrl:{}", channelAdsKey(), weiboParamField);
    }

    /*@Override
    protected String initAdsUrl() {
        return QuannengHudongPath.BASIC_URI;
    }*/

    @Override
    protected Response reportAds(String adsUrl, Object adsDtoObj) throws Exception {
        logger.info("调用用户侧的地址 {} adsUrl:{}", channelAdsKey(), adsUrl);
        HttpResponse response = HttpRequest.get(adsUrl).timeout(20000).header("token", "application/json").execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        WeiboAdsDTO weiboAdsDTO = (WeiboAdsDTO) adsDtoObj;
        WeiboAdsDTO weiboAdsVO = new WeiboAdsDTO();
        weiboAdsVO.setId(weiboAdsDTO.getId());
        //上报成功
        if (HttpStatus.HTTP_OK == response.getStatus() && Integer.parseInt(String.valueOf(Objects.requireNonNull(responseBodyMap).get("code"))) == 10000) {
            weiboAdsVO.setReportStatus(Constants.ReportStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(weiboAdsVO, adsDao());
            logger.info("clickReport {} 上报广告侧接口请求成功:{} 数据:{}", channelAdsKey(), response, weiboAdsVO);
            return BasicResult.getSuccessResponse(weiboAdsDTO.getId());
        } else {
            weiboAdsVO.setReportStatus(Constants.ReportStatus.FAIL.getCode() + "--" + JSONObject.toJSONString(responseBodyMap));
            baseServiceInner.updateAdsObject(weiboAdsVO, adsDao());
            logger.error("clickReport {} 上报广告侧接口请求失败:{} 数据:{}", channelAdsKey(), response, weiboAdsVO);
            return BasicResult.getFailResponse("上报广告侧接口请求失败", 0);
        }
    }
}
