package huihuang.proxy.ocpx.middle.baseadsreport.jiyue;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.jiyue.JiyueAdsDTO;
import huihuang.proxy.ocpx.ads.jiyue.JiyueParamEnum;
import huihuang.proxy.ocpx.ads.jiyue.JiyueParamField;
import huihuang.proxy.ocpx.ads.jiyue.JiyuePath;
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

public abstract class JiyueReportFactory extends BaseSupport implements IChannelAds {

    @Autowired
    protected BaseServiceInner baseServiceInner;

    protected abstract String channelAdsKey();

    protected abstract String serverPathKey();

    protected abstract String channelName();

    protected abstract IMarkDao adsDao();

    @Override
    protected void convertParams(Object adsObj) {
        JiyueParamField jiyueParamField = (JiyueParamField) adsObj;
        if (null != jiyueParamField.getOs()) {
            jiyueParamField.setOs(baseServiceInner.typeConvertOs(jiyueParamField.getOs()));
        }
        if (null != jiyueParamField.getCallback()) {
            jiyueParamField.setCallback(URLEncoder.createQuery().encode(jiyueParamField.getCallback(), StandardCharsets.UTF_8));
        }
        if (null != jiyueParamField.getUa()) {
            jiyueParamField.setUa(URLEncoder.createQuery().encode(jiyueParamField.getUa(), StandardCharsets.UTF_8));
        }
        logger.info("clickReport {} 特殊参数进行转换 convertParams:{}", channelAdsKey(), jiyueParamField);
    }

    @Override
    protected Response judgeParams(Object adsObj) {
        JiyueParamField jiyueParamField = (JiyueParamField) adsObj;
        if (Objects.isNull(jiyueParamField.getCallback())) {
            return BasicResult.getFailResponse(JiyueParamEnum.CALLBACK.getName() + "不能为空");
        }
        return BasicResult.getSuccessResponse();
    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        JiyueParamField jiyueParamField = (JiyueParamField) adsObj;
        JiyueAdsDTO jiyueAdsDTO = new JiyueAdsDTO();
        BeanUtil.copyProperties(jiyueParamField, jiyueAdsDTO);
        jiyueAdsDTO.setChannelName(channelName());
        baseServiceInner.insertAdsObject(jiyueAdsDTO, adsDao());
        logger.info("clickReport {} 将原始参数保存数据库，返回数据库对象 saveOriginParamData:{}", channelAdsKey(), jiyueAdsDTO);
        return jiyueAdsDTO;
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {
        JiyueParamField jiyueParamField = (JiyueParamField) adsObj;
        JiyueAdsDTO jiyueAdsDTO = (JiyueAdsDTO) adsDtoObj;
        String ocpxUrl = queryServerPath() + serverPathKey() + Constants.ServerPath.ADS_CALLBACK + "/" + jiyueAdsDTO.getId() + "?";
        logger.info("clickReport {} 客户回调渠道的url：{}", channelAdsKey(), ocpxUrl);
        String encodeUrl = URLEncoder.createQuery().encode(ocpxUrl, StandardCharsets.UTF_8);
        jiyueParamField.setCallback(encodeUrl);
        logger.info("clickReport {} 回调参数 replaceCallbackUrl:{}", channelAdsKey(), jiyueParamField);
    }

    @Override
    protected String initAdsUrl() {
        return JiyuePath.BASIC_URI;
    }

    @Override
    protected Response reportAds(String adsUrl, Object adsDtoObj) throws Exception {
        logger.info("调用用户侧的地址 {} adsUrl:{}", channelAdsKey(), adsUrl);
        HttpResponse response = HttpRequest.get(adsUrl).timeout(20000).header("token", "application/json").execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        JiyueAdsDTO jiyueAdsDTO = (JiyueAdsDTO) adsDtoObj;
        JiyueAdsDTO jiyueAdsVO = new JiyueAdsDTO();
        jiyueAdsVO.setId(jiyueAdsDTO.getId());
        //上报成功
        if (HttpStatus.HTTP_OK == response.getStatus() && "200".equals(Objects.requireNonNull(responseBodyMap).get("code"))) {
            jiyueAdsVO.setReportStatus(Constants.ReportStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(jiyueAdsVO, adsDao());
            logger.info("clickReport {} 上报广告侧接口请求成功:{} 数据:{}", channelAdsKey(), response, jiyueAdsVO);
            return BasicResult.getSuccessResponse(jiyueAdsDTO.getId());
        } else {
            jiyueAdsVO.setReportStatus(Constants.ReportStatus.FAIL.getCode() + "--" + JSONObject.toJSONString(responseBodyMap));
            baseServiceInner.updateAdsObject(jiyueAdsVO, adsDao());
            logger.error("clickReport {} 上报广告侧接口请求失败:{} 数据:{}", channelAdsKey(), response, jiyueAdsVO);
            return BasicResult.getFailResponse("上报广告侧接口请求失败", 0);
        }
    }

}
