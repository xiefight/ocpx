package huihuang.proxy.ocpx.middle.baseadsreport.ningzhi;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.dingyun.DingyunParamEnum;
import huihuang.proxy.ocpx.ads.ningzhi.NingzhiAdsDTO;
import huihuang.proxy.ocpx.ads.ningzhi.NingzhiParamField;
import huihuang.proxy.ocpx.ads.ningzhi.NingzhiPath;
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

public abstract class NingzhiReportFactory extends BaseSupport implements IChannelAds {

    @Autowired
    protected BaseServiceInner baseServiceInner;

    protected abstract String channelAdsKey();

    protected abstract String serverPathKey();

    protected abstract String channelName();

    protected abstract IMarkDao adsDao();

    @Override
    protected void convertParams(Object adsObj) {
        NingzhiParamField ningzhiParamField = (NingzhiParamField) adsObj;
        if (null != ningzhiParamField.getCallback()) {
            ningzhiParamField.setCallback(URLEncoder.createQuery().encode(ningzhiParamField.getCallback(), StandardCharsets.UTF_8));
        }
        if (null != ningzhiParamField.getUa()) {
            ningzhiParamField.setUa(URLEncoder.createQuery().encode(ningzhiParamField.getUa(), StandardCharsets.UTF_8));
        }
        logger.info("clickReport {} 特殊参数进行转换 convertParams:{}", channelAdsKey(), ningzhiParamField);
    }

    @Override
    protected Response judgeParams(Object adsObj) {
        NingzhiParamField ningzhiParamField = (NingzhiParamField) adsObj;
        if (Objects.isNull(ningzhiParamField.getCallback())) {
            return BasicResult.getFailResponse(DingyunParamEnum.CALLBACK.getName() + "不能为空");
        }
        return BasicResult.getSuccessResponse();
    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        NingzhiParamField ningzhiParamField = (NingzhiParamField) adsObj;
        NingzhiAdsDTO ningzhiAdsDTO = new NingzhiAdsDTO();
        BeanUtil.copyProperties(ningzhiParamField, ningzhiAdsDTO);
        ningzhiAdsDTO.setChannelName(channelName());
        baseServiceInner.insertAdsObject(ningzhiAdsDTO, adsDao());
        logger.info("clickReport {} 将原始参数保存数据库，返回数据库对象 saveOriginParamData:{}", channelAdsKey(), ningzhiAdsDTO);
        return ningzhiAdsDTO;
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {
        NingzhiParamField ningzhiParamField = (NingzhiParamField) adsObj;
        NingzhiAdsDTO ningzhiAdsDTO = (NingzhiAdsDTO) adsDtoObj;
        String ocpxUrl = queryServerPath() + serverPathKey() + Constants.ServerPath.ADS_CALLBACK + "/" + ningzhiAdsDTO.getId() + "?";
        logger.info("clickReport {} 客户回调渠道的url：{}", channelAdsKey(), ocpxUrl);
        String encodeUrl = URLEncoder.createQuery().encode(ocpxUrl, StandardCharsets.UTF_8);
        ningzhiParamField.setCallback(encodeUrl);
        logger.info("clickReport {} 回调参数 replaceCallbackUrl:{}", channelAdsKey(), ningzhiParamField);
    }

    @Override
    protected String initAdsUrl() {
        return NingzhiPath.BASIC_URI;
    }

    @Override
    protected Response reportAds(String adsUrl, Object adsDtoObj) throws Exception {
        logger.info("调用用户侧的地址 {} adsUrl:{}", channelAdsKey(), adsUrl);
        HttpResponse response = HttpRequest.get(adsUrl).timeout(20000).header("token", "application/json").execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        NingzhiAdsDTO ningzhiAdsDTO = (NingzhiAdsDTO) adsDtoObj;
        NingzhiAdsDTO ningzhiAdsVO = new NingzhiAdsDTO();
        ningzhiAdsVO.setId(ningzhiAdsDTO.getId());
        //上报成功
        if (HttpStatus.HTTP_OK == response.getStatus() && (Integer) Objects.requireNonNull(responseBodyMap).get("code") == 200) {
            ningzhiAdsVO.setReportStatus(Constants.ReportStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(ningzhiAdsVO, adsDao());
            logger.info("clickReport {} 上报广告侧接口请求成功:{} 数据:{}", channelAdsKey(), response, ningzhiAdsVO);
            return BasicResult.getSuccessResponse(ningzhiAdsDTO.getId());
        } else {
            ningzhiAdsVO.setReportStatus(Constants.ReportStatus.FAIL.getCode() + "--" + JSONObject.toJSONString(responseBodyMap));
            baseServiceInner.updateAdsObject(ningzhiAdsVO, adsDao());
            logger.error("clickReport {} 上报广告侧接口请求失败:{} 数据:{}", channelAdsKey(), response, ningzhiAdsVO);
            return BasicResult.getFailResponse("上报广告侧接口请求失败", 0);
        }
    }

}
