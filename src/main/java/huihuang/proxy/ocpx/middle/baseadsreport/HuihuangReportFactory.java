package huihuang.proxy.ocpx.middle.baseadsreport;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.huihuang.HuihuangAdsDTO;
import huihuang.proxy.ocpx.ads.huihuang.HuihuangParamEnum;
import huihuang.proxy.ocpx.ads.huihuang.HuihuangParamField;
import huihuang.proxy.ocpx.ads.huihuang.HuihuangPath;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouAdsDTO;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouParamField;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangAdsDao;
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
public abstract class HuihuangReportFactory extends BaseSupport implements IChannelAds {

    @Autowired
    private IHuihuangAdsDao huihuangAdsDao;
    @Autowired
    protected BaseServiceInner baseServiceInner;

    protected abstract String channelAdsKey();

    protected abstract String serverPathKey();

    protected abstract String channelName();

    @Override
    protected void convertParams(Object adsObj) {
        HuihuangParamField huihuangParamField = (HuihuangParamField) adsObj;
        if (null != huihuangParamField.getCallbackUrl()) {
            huihuangParamField.setCallbackUrl(URLEncoder.createQuery().encode(huihuangParamField.getCallbackUrl(), StandardCharsets.UTF_8));
        }
        //时间戳，毫秒
        huihuangParamField.setTms(String.valueOf(System.currentTimeMillis()));
        if (null != huihuangParamField.getAdAgent()) {
            huihuangParamField.setAdAgent(URLEncoder.createQuery().encode(huihuangParamField.getAdAgent(), StandardCharsets.UTF_8));
        }
        logger.info("clickReport {} 特殊参数进行转换 convertParams:{}", channelAdsKey(), huihuangParamField);
    }

    @Override
    protected Response judgeParams(Object adsObj) {
        HuihuangParamField huihuangParamField = (HuihuangParamField) adsObj;
        if (Objects.isNull(huihuangParamField.getCallbackUrl())) {
            return BasicResult.getFailResponse(HuihuangParamEnum.CALLBACK_URL.getName() + "不能为空");
        }
        if (Objects.isNull(huihuangParamField.getChainCode())) {
            return BasicResult.getFailResponse(HuihuangParamEnum.CHAIN_CODE.getName() + "不能为空");
        }
        if (Objects.isNull(huihuangParamField.getIdfa())
                && Objects.isNull(huihuangParamField.getImeiMd5())
                && Objects.isNull(huihuangParamField.getOaid())
        ) {
            return BasicResult.getFailResponse("安卓设备：" + HuihuangParamEnum.IMEI_MD5.getName() + "、" + HuihuangParamEnum.OAID.getName() + "不能同时为空；"
                    + " ios设备" + HuihuangParamEnum.IDFA.getName() + "不能为空");
        }
        return BasicResult.getSuccessResponse();
    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        HuihuangParamField huihuangParamField = (HuihuangParamField) adsObj;
        HuihuangAdsDTO huihuangAdsDTO = new HuihuangAdsDTO();
        BeanUtil.copyProperties(huihuangParamField, huihuangAdsDTO);
        huihuangAdsDTO.setChannelName(channelName());
        huihuangAdsDao.insert(huihuangAdsDTO);
        logger.info("clickReport {} 将原始参数保存数据库，返回数据库对象 saveOriginParamData:{}", channelAdsKey(), huihuangAdsDTO);
        return huihuangAdsDTO;
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {
        HuihuangParamField huihuangParamField = (HuihuangParamField) adsObj;
        HuihuangAdsDTO huihuangAdsDTO = (HuihuangAdsDTO) adsDtoObj;
        String ocpxUrl = queryServerPath() + serverPathKey() + Constants.ServerPath.ADS_CALLBACK + "/" + huihuangAdsDTO.getId() + "?";
        logger.info("clickReport {} 客户回调渠道的url：{}", channelAdsKey(), ocpxUrl);
        String encodeUrl = URLEncoder.createQuery().encode(ocpxUrl, StandardCharsets.UTF_8);
//            ocpxUrl = URLEncoder.encode(ocpxUrl, "UTF-8");
        huihuangParamField.setCallbackUrl(encodeUrl);
        logger.info("clickReport {} 回调参数 replaceCallbackUrl:{}", channelAdsKey(), huihuangParamField);
    }

    @Override
    protected String initAdsUrl() {
        return HuihuangPath.BASIC_URI;
    }

    @Override
    protected Response reportAds(String adsUrl, Object adsDtoObj) throws Exception {
        logger.info("调用用户侧的地址 {} adsUrl:{}", channelAdsKey(), adsUrl);
        HttpResponse response = HttpRequest.get(adsUrl).timeout(20000).header("token", "application/json").execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        HuihuangAdsDTO huihuangAdsDTO = (HuihuangAdsDTO) adsDtoObj;
        HuihuangAdsDTO huihuangAdsVO = new HuihuangAdsDTO();
        huihuangAdsVO.setId(huihuangAdsDTO.getId());
        //上报成功
        if (HttpStatus.HTTP_OK == response.getStatus() && Objects.requireNonNull(responseBodyMap).get("code").equals("1")) {
            huihuangAdsVO.setReportStatus(Constants.ReportStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(huihuangAdsVO, huihuangAdsDao);
            logger.info("clickReport {} 上报广告侧接口请求成功:{} 数据:{}", channelAdsKey(), response, huihuangAdsVO);
            return BasicResult.getSuccessResponse(huihuangAdsDTO.getId());
        } else {
            huihuangAdsVO.setReportStatus(Constants.ReportStatus.FAIL.getCode() + "--" + JSONObject.toJSONString(responseBodyMap));
            baseServiceInner.updateAdsObject(huihuangAdsVO, huihuangAdsDao);
            logger.error("clickReport {} 上报广告侧接口请求失败:{} 数据:{}", channelAdsKey(), response, huihuangAdsVO);
            return BasicResult.getFailResponse("上报广告侧接口请求失败", 0);
        }
    }
}
