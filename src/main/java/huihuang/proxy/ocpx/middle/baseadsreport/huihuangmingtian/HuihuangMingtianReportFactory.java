package huihuang.proxy.ocpx.middle.baseadsreport.huihuangmingtian;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianAdsDTO;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianParamEnum;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianParamField;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianPath;
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

public abstract class HuihuangMingtianReportFactory extends BaseSupport implements IChannelAds {

    @Autowired
    protected BaseServiceInner baseServiceInner;

    protected abstract String channelAdsKey();

    protected abstract String serverPathKey();

    protected abstract String channelName();

    protected abstract IMarkDao adsDao();

    @Override
    protected void convertParams(Object adsObj) {
        HuihuangmingtianParamField huihuangmingtianParamField = (HuihuangmingtianParamField) adsObj;
        if (null != huihuangmingtianParamField.getCallbackUrl()) {
            huihuangmingtianParamField.setCallbackUrl(URLEncoder.createQuery().encode(huihuangmingtianParamField.getCallbackUrl(), StandardCharsets.UTF_8));
        }
        //时间戳，毫秒
        huihuangmingtianParamField.setTms(String.valueOf(System.currentTimeMillis()));
        if (null != huihuangmingtianParamField.getAdAgent()) {
            huihuangmingtianParamField.setAdAgent(URLEncoder.createQuery().encode(huihuangmingtianParamField.getAdAgent(), StandardCharsets.UTF_8));
        }
        /*if (null != huihuangmingtianParamField.getOaid() || null != huihuangmingtianParamField.getOaidMd5()){
            huihuangmingtianParamField.setOs("0");
        }*/
        //不是ios的都默认为安卓
        if (null != huihuangmingtianParamField.getIdfa() || null != huihuangmingtianParamField.getIdfaMd5()){
            huihuangmingtianParamField.setOs("1");
        }else {
            huihuangmingtianParamField.setOs("0");
        }
        logger.info("clickReport {} 特殊参数进行转换 convertParams:{}", channelAdsKey(), huihuangmingtianParamField);
    }

    @Override
    protected Response judgeParams(Object adsObj) {
        HuihuangmingtianParamField huihuangmingtianParamField = (HuihuangmingtianParamField) adsObj;
//        if (Objects.isNull(huihuangmingtianParamField.getCallbackUrl())) {
//            return BasicResult.getFailResponse(HuihuangmingtianParamEnum.CALLBACK_URL.getName() + "不能为空");
//        }
        if (Objects.isNull(huihuangmingtianParamField.getChainCode())) {
            return BasicResult.getFailResponse(HuihuangmingtianParamEnum.CHAIN_CODE.getName() + "不能为空");
        }
        /*if (Objects.isNull(huihuangmingtianParamField.getApp())) {
            return BasicResult.getFailResponse(HuihuangmingtianParamEnum.APP.getName() + "不能为空");
        }*/
        if (Objects.isNull(huihuangmingtianParamField.getIdfa())
                && Objects.isNull(huihuangmingtianParamField.getImeiMd5())
                && Objects.isNull(huihuangmingtianParamField.getOaid())
        ) {
            return BasicResult.getFailResponse("安卓设备：" + HuihuangmingtianParamEnum.IMEI_MD5.getName() + "、" + HuihuangmingtianParamEnum.OAID.getName() + "不能同时为空；"
                    + " ios设备" + HuihuangmingtianParamEnum.IDFA.getName() + "不能为空");
        }
        return BasicResult.getSuccessResponse();
    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        HuihuangmingtianParamField huihuangmingtianParamField = (HuihuangmingtianParamField) adsObj;
        HuihuangmingtianAdsDTO huihuangmingtianAdsDTO = new HuihuangmingtianAdsDTO();
        BeanUtil.copyProperties(huihuangmingtianParamField, huihuangmingtianAdsDTO);
        huihuangmingtianAdsDTO.setChannelName(channelName());
        baseServiceInner.insertAdsObject(huihuangmingtianAdsDTO, adsDao());
        logger.info("clickReport {} 将原始参数保存数据库，返回数据库对象 saveOriginParamData:{}", channelAdsKey(), huihuangmingtianAdsDTO);
        return huihuangmingtianAdsDTO;
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {
        HuihuangmingtianParamField huihuangmingtianParamField = (HuihuangmingtianParamField) adsObj;
        HuihuangmingtianAdsDTO huihuangmingtianAdsDTO = (HuihuangmingtianAdsDTO) adsDtoObj;
        String ocpxUrl = queryServerPath() + serverPathKey() + Constants.ServerPath.ADS_CALLBACK + "/" + huihuangmingtianAdsDTO.getId() + "?";
        logger.info("clickReport {} 客户回调渠道的url：{}", channelAdsKey(), ocpxUrl);
        String encodeUrl = URLEncoder.createQuery().encode(ocpxUrl, StandardCharsets.UTF_8);
//            ocpxUrl = URLEncoder.encode(ocpxUrl, "UTF-8");
        huihuangmingtianParamField.setCallbackUrl(encodeUrl);

        logger.info("clickReport {} 回调参数 replaceCallbackUrl:{}", channelAdsKey(), huihuangmingtianParamField);
    }

    @Override
    protected String initAdsUrl() {
        return HuihuangmingtianPath.BASIC_URI;
    }

    @Override
    protected Response reportAds(String adsUrl, Object adsDtoObj) throws Exception {
        logger.info("调用用户侧的地址 {} adsUrl:{}", channelAdsKey(), adsUrl);
        HttpResponse response = HttpRequest.get(adsUrl).timeout(20000).header("token", "application/json").execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        HuihuangmingtianAdsDTO huihuangAdsDTO = (HuihuangmingtianAdsDTO) adsDtoObj;
        HuihuangmingtianAdsDTO huihuangmingtianAdsVO = new HuihuangmingtianAdsDTO();
        huihuangmingtianAdsVO.setId(huihuangAdsDTO.getId());
        //上报成功
        if (HttpStatus.HTTP_OK == response.getStatus() && Objects.requireNonNull(responseBodyMap).get("code").equals("200")) {
            huihuangmingtianAdsVO.setReportStatus(Constants.ReportStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(huihuangmingtianAdsVO, adsDao());
            logger.info("clickReport {} 上报广告侧接口请求成功:{} 数据:{}", channelAdsKey(), response, huihuangmingtianAdsVO);
            return BasicResult.getSuccessResponse(huihuangAdsDTO.getId());
        } else {
            huihuangmingtianAdsVO.setReportStatus(Constants.ReportStatus.FAIL.getCode() + "--" + JSONObject.toJSONString(responseBodyMap));
            baseServiceInner.updateAdsObject(huihuangmingtianAdsVO, adsDao());
            logger.error("clickReport {} 上报广告侧接口请求失败:{} 数据:{}", channelAdsKey(), response, huihuangmingtianAdsVO);
            return BasicResult.getFailResponse("上报广告侧接口请求失败", 0);
        }
    }
}
