package huihuang.proxy.ocpx.middle.baseadsreport.buget;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.bupet.BupetAdsDTO;
import huihuang.proxy.ocpx.ads.bupet.BupetParamField;
import huihuang.proxy.ocpx.ads.bupet.BupetPath;
import huihuang.proxy.ocpx.ads.dingyun.DingyunParamEnum;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.BaseSupport;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

public abstract class BupetReportFactory extends BaseSupport implements IChannelAds {

    @Autowired
    protected BaseServiceInner baseServiceInner;

    protected abstract String channelAdsKey();

    protected abstract String serverPathKey();

    protected abstract String channelName();

    protected abstract IMarkDao adsDao();

    @Override
    protected void convertParams(Object adsObj) {
        BupetParamField bupetParamField = (BupetParamField) adsObj;
        if (null != bupetParamField.getCallback()) {
            bupetParamField.setCallback(URLEncoder.createQuery().encode(bupetParamField.getCallback(), StandardCharsets.UTF_8));
        }
        if (null != bupetParamField.getUserAgent()) {
            bupetParamField.setUserAgent(URLEncoder.createQuery().encode(bupetParamField.getUserAgent(), StandardCharsets.UTF_8));
        }
        logger.info("clickReport {} 特殊参数进行转换 convertParams:{}", channelAdsKey(), bupetParamField);
    }

    @Override
    protected Response judgeParams(Object adsObj) {
        BupetParamField bupetParamField = (BupetParamField) adsObj;
        if (Objects.isNull(bupetParamField.getCallback())) {
            return BasicResult.getFailResponse(DingyunParamEnum.CALLBACK.getName() + "不能为空");
        }
        return BasicResult.getSuccessResponse();
    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        BupetParamField bupetParamField = (BupetParamField) adsObj;
        BupetAdsDTO bupetAdsDTO = new BupetAdsDTO();
        BeanUtil.copyProperties(bupetParamField, bupetAdsDTO);
        bupetAdsDTO.setChannelName(channelName());
        baseServiceInner.insertAdsObject(bupetAdsDTO, adsDao());
        logger.info("clickReport {} 将原始参数保存数据库，返回数据库对象 saveOriginParamData:{}", channelAdsKey(), bupetAdsDTO);
        return bupetAdsDTO;
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {
        BupetParamField bupetParamField = (BupetParamField) adsObj;
        BupetAdsDTO bupetAdsDTO = (BupetAdsDTO) adsDtoObj;
        String ocpxUrl = queryServerPath() + serverPathKey() + Constants.ServerPath.ADS_CALLBACK + "/" + bupetAdsDTO.getId() + "?";
        logger.info("clickReport {} 客户回调渠道的url：{}", channelAdsKey(), ocpxUrl);
        String encodeUrl = URLEncoder.createQuery().encode(ocpxUrl, StandardCharsets.UTF_8);
        bupetParamField.setCallback(encodeUrl);
        logger.info("clickReport {} 回调参数 replaceCallbackUrl:{}", channelAdsKey(), bupetParamField);
    }

    @Override
    protected String initAdsUrl() {
        return BupetPath.BASIC_URI;
    }

    @Override
    protected Response reportAds(String adsUrl, Object adsDtoObj) throws Exception {
        logger.info("调用用户侧的地址 {} adsUrl:{}", channelAdsKey(), adsUrl);
        HttpResponse response = HttpRequest.get(adsUrl).timeout(20000).header("token", "application/json").execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        BupetAdsDTO bupetAdsDTO = (BupetAdsDTO) adsDtoObj;
        BupetAdsDTO bupetAdsVO = new BupetAdsDTO();
        bupetAdsVO.setId(bupetAdsDTO.getId());
        //上报成功
        if (HttpStatus.HTTP_OK == response.getStatus() && (Integer) Objects.requireNonNull(responseBodyMap).get("code") == 0) {
            bupetAdsVO.setReportStatus(Constants.ReportStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(bupetAdsVO, adsDao());
            logger.info("clickReport {} 上报广告侧接口请求成功:{} 数据:{}", channelAdsKey(), response, bupetAdsVO);
            return BasicResult.getSuccessResponse(bupetAdsDTO.getId());
        } else {
            bupetAdsVO.setReportStatus(Constants.ReportStatus.FAIL.getCode() + "--" + JSONObject.toJSONString(responseBodyMap));
            baseServiceInner.updateAdsObject(bupetAdsVO, adsDao());
            logger.error("clickReport {} 上报广告侧接口请求失败:{} 数据:{}", channelAdsKey(), response, bupetAdsVO);
            return BasicResult.getFailResponse("上报广告侧接口请求失败", 0);
        }
    }

}
