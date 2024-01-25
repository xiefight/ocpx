package huihuang.proxy.ocpx.middle.baseadsreport;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.huihuang.HuihuangParamEnum;
import huihuang.proxy.ocpx.ads.keep.KeepAdsDTO;
import huihuang.proxy.ocpx.ads.keep.KeepParamField;
import huihuang.proxy.ocpx.ads.keep.KeepPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IKeepAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.middle.BaseSupport;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: xietao
 * @Date: 2023/7/6 16:35
 */
public abstract class KeepReportFactory extends BaseSupport implements IChannelAds {

    @Autowired
    private IKeepAdsDao keepAdsDao;
    @Autowired
    protected BaseServiceInner baseServiceInner;

    protected abstract String channelAdsKey();

    protected abstract String serverPathKey();

    protected abstract String channelName();

    @Override
    protected void convertParams(Object adsObj) {
        KeepParamField keepParamField = (KeepParamField) adsObj;
        if (null != keepParamField.getCallback()) {
            keepParamField.setCallback(URLEncoder.createQuery().encode(keepParamField.getCallback(), StandardCharsets.UTF_8));
        }
        //时间戳，毫秒
//        keepParamField.setTms(String.valueOf(System.currentTimeMillis()));
        if (null != keepParamField.getUa()) {
            keepParamField.setUa(URLEncoder.createQuery().encode(keepParamField.getUa(), StandardCharsets.UTF_8));
        }
        logger.info("clickReport {} 特殊参数进行转换 convertParams:{}", channelAdsKey(), keepParamField);
    }

    @Override
    protected Response judgeParams(Object adsObj) {
        KeepParamField keepParamField = (KeepParamField) adsObj;
        if (Objects.isNull(keepParamField.getCallback())) {
            return BasicResult.getFailResponse(HuihuangParamEnum.CALLBACK_URL.getName() + "不能为空");
        }
//        if (Objects.isNull(keepParamField.getChainCode())) {
//            return BasicResult.getFailResponse(HuihuangParamEnum.CHAIN_CODE.getName() + "不能为空");
//        }
//        if (Objects.isNull(keepParamField.getIdfa())
//                && Objects.isNull(keepParamField.getImeiMd5())
//                && Objects.isNull(keepParamField.getOaid())
//        ) {
//            return BasicResult.getFailResponse("安卓设备：" + HuihuangParamEnum.IMEI_MD5.getName() + "、" + HuihuangParamEnum.OAID.getName() + "不能同时为空；"
//                    + " ios设备" + HuihuangParamEnum.IDFA.getName() + "不能为空");
//        }
        return BasicResult.getSuccessResponse();
    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        KeepParamField keepParamField = (KeepParamField) adsObj;
        KeepAdsDTO keepAdsDTO = new KeepAdsDTO();
        BeanUtil.copyProperties(keepParamField, keepAdsDTO);
        keepAdsDTO.setChannelName(channelName());
        keepAdsDao.insert(keepAdsDTO);
        logger.info("clickReport {} 将原始参数保存数据库，返回数据库对象 saveOriginParamData:{}", channelAdsKey(), keepAdsDTO);
        return keepAdsDTO;
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {
        KeepParamField keepParamField = (KeepParamField) adsObj;
        KeepAdsDTO keepAdsDTO = (KeepAdsDTO) adsDtoObj;
        String ocpxUrl = queryServerPath() + serverPathKey() + Constants.ServerPath.ADS_CALLBACK + "/" + keepAdsDTO.getId() + "?";
        logger.info("clickReport {} 客户回调渠道的url：{}", channelAdsKey(), ocpxUrl);
        String encodeUrl = URLEncoder.createQuery().encode(ocpxUrl, StandardCharsets.UTF_8);
//            ocpxUrl = URLEncoder.encode(ocpxUrl, "UTF-8");
        keepParamField.setCallback(encodeUrl);
        logger.info("clickReport {} 回调参数 replaceCallbackUrl:{}", channelAdsKey(), keepParamField);
    }

    @Override
    protected String initAdsUrl() {
        return KeepPath.BASIC_URI;
    }

    @Override
    protected Response reportAds(String adsUrl, Object adsDtoObj) throws Exception {
        logger.info("调用用户侧的地址 {} adsUrl:{}", channelAdsKey(), adsUrl);
        HttpResponse response = HttpRequest.get(adsUrl).timeout(20000).header("token", "application/json").execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        KeepAdsDTO keepAdsDTO = (KeepAdsDTO) adsDtoObj;
        KeepAdsDTO keepAdsVO = new KeepAdsDTO();
        keepAdsVO.setId(keepAdsDTO.getId());
        //上报成功
        if (HttpStatus.HTTP_OK == response.getStatus() && (Integer) Objects.requireNonNull(responseBodyMap).get("error_code") == 0) {
            keepAdsVO.setReportStatus(Constants.ReportStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(keepAdsVO, keepAdsDao);
            logger.info("clickReport {} 上报广告侧接口请求成功:{} 数据:{}", channelAdsKey(), response, keepAdsVO);
            return BasicResult.getSuccessResponse(keepAdsDTO.getId());
        } else {
            keepAdsVO.setReportStatus(Constants.ReportStatus.FAIL.getCode() + "--" + JSONObject.toJSONString(responseBodyMap));
            baseServiceInner.updateAdsObject(keepAdsVO, keepAdsDao);
            logger.error("clickReport {} 上报广告侧接口请求失败:{} 数据:{}", channelAdsKey(), response, keepAdsVO);
            return BasicResult.getFailResponse("上报广告侧接口请求失败", 0);
        }
    }
}
