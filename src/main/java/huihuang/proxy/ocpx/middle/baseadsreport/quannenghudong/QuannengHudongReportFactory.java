package huihuang.proxy.ocpx.middle.baseadsreport.quannenghudong;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongAdsDTO;
import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongParamEnum;
import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongParamField;
import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongPath;
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
import java.util.Map;
import java.util.Objects;

public abstract class QuannengHudongReportFactory extends BaseSupport implements IChannelAds {

    @Autowired
    protected BaseServiceInner baseServiceInner;

    protected abstract String channelAdsKey();

    protected abstract String serverPathKey();

    protected abstract String channelName();

    protected abstract IMarkDao adsDao();


    @Override
    protected void convertParams(Object adsObj) {
        QuannengHudongParamField quannengHudongParamField = (QuannengHudongParamField) adsObj;
        if (null != quannengHudongParamField.getCallback()) {
            quannengHudongParamField.setCallback(URLEncoder.createQuery().encode(quannengHudongParamField.getCallback(), StandardCharsets.UTF_8));
        }
        //uniqueid
        quannengHudongParamField.setUniqueid(RandomUtil.randomStamp());
//        if (null != quannengHudongParamField.getAdAgent()) {
//            quannengHudongParamField.setAdAgent(URLEncoder.createQuery().encode(quannengHudongParamField.getAdAgent(), StandardCharsets.UTF_8));
//        }
        if (quannengHudongParamField.getIdfa() != null) {
            quannengHudongParamField.setOs("ios");
        } else {
            quannengHudongParamField.setOs("android");
        }
        logger.info("clickReport {} 特殊参数进行转换 convertParams:{}", channelAdsKey(), quannengHudongParamField);
    }


    @Override
    protected Response judgeParams(Object adsObj) {
        QuannengHudongParamField quannengHudongParamField = (QuannengHudongParamField) adsObj;
//        if (Objects.isNull(quannengHudongParamField.getCallback())) {
//            return BasicResult.getFailResponse(QuannengHudongParamEnum.CALLBACK.getName() + "不能为空");
//        }
        if (Objects.isNull(quannengHudongParamField.getUniqueid())) {
            return BasicResult.getFailResponse(QuannengHudongParamEnum.UNIQUE_ID.getName() + "不能为空");
        }
        if (Objects.isNull(quannengHudongParamField.getIdfa())
                && Objects.isNull(quannengHudongParamField.getImei())
                && Objects.isNull(quannengHudongParamField.getOaid())
        ) {
            return BasicResult.getFailResponse("安卓设备：" + QuannengHudongParamEnum.IMEI.getName() + "、" + QuannengHudongParamEnum.OAID.getName() + "不能同时为空；"
                    + " ios设备" + QuannengHudongParamEnum.IDFA.getName() + "不能为空");
        }
        return BasicResult.getSuccessResponse();
    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        QuannengHudongParamField quannengHudongParamField = (QuannengHudongParamField) adsObj;
        QuannengHudongAdsDTO quannengHudongAdsDTO = new QuannengHudongAdsDTO();
        BeanUtil.copyProperties(quannengHudongParamField, quannengHudongAdsDTO);
        quannengHudongAdsDTO.setChannelName(channelName());
        baseServiceInner.insertAdsObject(quannengHudongAdsDTO, adsDao());
        logger.info("clickReport {} 将原始参数保存数据库，返回数据库对象 saveOriginParamData:{}", channelAdsKey(), quannengHudongAdsDTO);
        return quannengHudongAdsDTO;
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {
        QuannengHudongParamField quannengHudongParamField = (QuannengHudongParamField) adsObj;
        QuannengHudongAdsDTO quannengHudongAdsDTO = (QuannengHudongAdsDTO) adsDtoObj;
        String ocpxUrl = queryServerPath() + serverPathKey() + Constants.ServerPath.ADS_CALLBACK + "/" + quannengHudongAdsDTO.getId() + "?";
        logger.info("clickReport {} 客户回调渠道的url：{}", channelAdsKey(), ocpxUrl);
        String encodeUrl = URLEncoder.createQuery().encode(ocpxUrl, StandardCharsets.UTF_8);
//            ocpxUrl = URLEncoder.encode(ocpxUrl, "UTF-8");
        quannengHudongParamField.setCallback(encodeUrl);
        logger.info("clickReport {} 回调参数 replaceCallbackUrl:{}", channelAdsKey(), quannengHudongParamField);
    }

    @Override
    protected String initAdsUrl() {
        return QuannengHudongPath.BASIC_URI;
    }

    @Override
    protected Response reportAds(String adsUrl, Object adsDtoObj) throws Exception {
        logger.info("调用用户侧的地址 {} adsUrl:{}", channelAdsKey(), adsUrl);
        HttpResponse response = HttpRequest.get(adsUrl).timeout(20000).header("token", "application/json").execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        QuannengHudongAdsDTO quannengHudongAdsDTO = (QuannengHudongAdsDTO) adsDtoObj;
        QuannengHudongAdsDTO quannengHudongAdsVO = new QuannengHudongAdsDTO();
        quannengHudongAdsVO.setId(quannengHudongAdsDTO.getId());
        //上报成功
        if (HttpStatus.HTTP_OK == response.getStatus() && Integer.parseInt(String.valueOf(Objects.requireNonNull(responseBodyMap).get("code"))) == 200) {
            quannengHudongAdsVO.setReportStatus(Constants.ReportStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(quannengHudongAdsVO, adsDao());
            logger.info("clickReport {} 上报广告侧接口请求成功:{} 数据:{}", channelAdsKey(), response, quannengHudongAdsVO);
            return BasicResult.getSuccessResponse(quannengHudongAdsDTO.getId());
        } else {
            quannengHudongAdsVO.setReportStatus(Constants.ReportStatus.FAIL.getCode() + "--" + JSONObject.toJSONString(responseBodyMap));
            baseServiceInner.updateAdsObject(quannengHudongAdsVO, adsDao());
            logger.error("clickReport {} 上报广告侧接口请求失败:{} 数据:{}", channelAdsKey(), response, quannengHudongAdsVO);
            return BasicResult.getFailResponse("上报广告侧接口请求失败", 0);
        }
    }
}
