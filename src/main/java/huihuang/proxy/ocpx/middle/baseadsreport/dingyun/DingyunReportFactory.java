package huihuang.proxy.ocpx.middle.baseadsreport.dingyun;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.dingyun.DingyunAdsDTO;
import huihuang.proxy.ocpx.ads.dingyun.DingyunParamEnum;
import huihuang.proxy.ocpx.ads.dingyun.DingyunParamField;
import huihuang.proxy.ocpx.ads.dingyun.DingyunPath;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianParamEnum;
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

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-01-04 09:22
 **/
public abstract class DingyunReportFactory extends BaseSupport implements IChannelAds {

    @Autowired
    protected BaseServiceInner baseServiceInner;

    protected abstract String channelAdsKey();

    protected abstract String serverPathKey();

    protected abstract String channelName();

    protected abstract IMarkDao adsDao();

    @Override
    protected void convertParams(Object adsObj) {
        DingyunParamField dingyunParamField = (DingyunParamField) adsObj;
        if (null != dingyunParamField.getCallback()) {
            dingyunParamField.setCallback(URLEncoder.createQuery().encode(dingyunParamField.getCallback(), StandardCharsets.UTF_8));
        }
        //时间戳，毫秒
//        dingyunParamField.setTms(String.valueOf(Sysstem.currentTimeMillis()));
        if (null != dingyunParamField.getUser_agent()) {
            dingyunParamField.setUser_agent(URLEncoder.createQuery().encode(dingyunParamField.getUser_agent(), StandardCharsets.UTF_8));
        }
        /*if (null != dingyunParamField.getOaid() || null != dingyunParamField.getOaidMd5()){
            dingyunParamField.setOs("0");
        }*/
        //不是ios的都默认为安卓
//        if (null != dingyunParamField.getIdfa() || null != dingyunParamField.getIdfaMd5()){
//            dingyunParamField.setOs("1");
//        }else {
//            dingyunParamField.setOs("0");
//        }
        dingyunParamField.setType("2");
        logger.info("clickReport {} 特殊参数进行转换 convertParams:{}", channelAdsKey(), dingyunParamField);
    }

    @Override
    protected Response judgeParams(Object adsObj) {
        DingyunParamField dingyunParamField = (DingyunParamField) adsObj;
        if (Objects.isNull(dingyunParamField.getCallback())) {
            return BasicResult.getFailResponse(DingyunParamEnum.CALLBACK.getName() + "不能为空");
        }
        if (Objects.isNull(dingyunParamField.getAdid())) {
            return BasicResult.getFailResponse(DingyunParamEnum.ADID.getName() + "不能为空");
        }
        /*if (Objects.isNull(dingyunParamField.getApp())) {
            return BasicResult.getFailResponse(HuihuangmingtianParamEnum.APP.getName() + "不能为空");
        }*/
        if (Objects.isNull(dingyunParamField.getIdfa())
                && Objects.isNull(dingyunParamField.getImeiMd5())
                && Objects.isNull(dingyunParamField.getOaid())
        ) {
            return BasicResult.getFailResponse("安卓设备：" + HuihuangmingtianParamEnum.IMEI_MD5.getName() + "、" + HuihuangmingtianParamEnum.OAID.getName() + "不能同时为空；"
                    + " ios设备" + HuihuangmingtianParamEnum.IDFA.getName() + "不能为空");
        }
        return BasicResult.getSuccessResponse();
    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        DingyunParamField dingyunParamField = (DingyunParamField) adsObj;
        DingyunAdsDTO dingyunAdsDTO = new DingyunAdsDTO();
        BeanUtil.copyProperties(dingyunParamField, dingyunAdsDTO);
        dingyunAdsDTO.setChannelName(channelName());
        baseServiceInner.insertAdsObject(dingyunAdsDTO, adsDao());
        logger.info("clickReport {} 将原始参数保存数据库，返回数据库对象 saveOriginParamData:{}", channelAdsKey(), dingyunAdsDTO);
        return dingyunAdsDTO;
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {
        DingyunParamField dingyunParamField = (DingyunParamField) adsObj;
        DingyunAdsDTO dingyunAdsDTO = (DingyunAdsDTO) adsDtoObj;
        String ocpxUrl = queryServerPath() + serverPathKey() + Constants.ServerPath.ADS_CALLBACK + "/" + dingyunAdsDTO.getId() + "?";
        logger.info("clickReport {} 客户回调渠道的url：{}", channelAdsKey(), ocpxUrl);
        String encodeUrl = URLEncoder.createQuery().encode(ocpxUrl, StandardCharsets.UTF_8);
//            ocpxUrl = URLEncoder.encode(ocpxUrl, "UTF-8");
        dingyunParamField.setCallback(encodeUrl);
        logger.info("clickReport {} 回调参数 replaceCallbackUrl:{}", channelAdsKey(), dingyunParamField);
    }

    @Override
    protected String initAdsUrl() {
        return DingyunPath.BASIC_URI;
    }

    @Override
    protected Response reportAds(String adsUrl, Object adsDtoObj) throws Exception {
        logger.info("调用用户侧的地址 {} adsUrl:{}", channelAdsKey(), adsUrl);
        HttpResponse response = HttpRequest.get(adsUrl).timeout(20000).header("token", "application/json").execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        DingyunAdsDTO dingyunAdsDTO = (DingyunAdsDTO) adsDtoObj;
        DingyunAdsDTO dingyunAdsVO = new DingyunAdsDTO();
        dingyunAdsVO.setId(dingyunAdsDTO.getId());
        //上报成功
        if (HttpStatus.HTTP_OK == response.getStatus() && (Integer) Objects.requireNonNull(responseBodyMap).get("code") == 0) {
            dingyunAdsVO.setReportStatus(Constants.ReportStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(dingyunAdsVO, adsDao());
            logger.info("clickReport {} 上报广告侧接口请求成功:{} 数据:{}", channelAdsKey(), response, dingyunAdsVO);
            return BasicResult.getSuccessResponse(dingyunAdsDTO.getId());
        } else {
            dingyunAdsVO.setReportStatus(Constants.ReportStatus.FAIL.getCode() + "--" + JSONObject.toJSONString(responseBodyMap));
            baseServiceInner.updateAdsObject(dingyunAdsVO, adsDao());
            logger.error("clickReport {} 上报广告侧接口请求失败:{} 数据:{}", channelAdsKey(), response, dingyunAdsVO);
            return BasicResult.getFailResponse("上报广告侧接口请求失败", 0);
        }
    }

}
