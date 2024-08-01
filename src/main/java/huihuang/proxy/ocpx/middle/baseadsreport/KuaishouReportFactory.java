package huihuang.proxy.ocpx.middle.baseadsreport;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouAdsDTO;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouParamEnum;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouParamField;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IKuaishouAdsDao;
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
 * @Description:
 * @Author: xietao
 * @Date: 2023-06-01 15:55
 **/
public abstract class KuaishouReportFactory extends BaseSupport implements IChannelAds {

    @Autowired
    protected BaseServiceInner baseServiceInner;

    protected abstract String channelAdsKey();

    protected abstract String serverPathKey();

    protected abstract String channelName();

    protected abstract IKuaishouAdsDao adsDao();

    @Override
    protected void convertParams(Object adsObj) {
        KuaishouParamField kuaishouParamField = (KuaishouParamField) adsObj;
        if (null != kuaishouParamField.getCallback()) {
            kuaishouParamField.setCallback(URLEncoder.createQuery().encode(kuaishouParamField.getCallback(), StandardCharsets.UTF_8));
        }
//        kuaishouParamField.setApp_type(osConvertAppType(kuaishouParamField.getApp_type()));
        kuaishouParamField.setClick_id(String.valueOf(System.currentTimeMillis()));
        //时间戳，秒
//        String ts = Optional.ofNullable(kuaishouParamField.getTs()).orElse(String.valueOf(System.currentTimeMillis()));
//        kuaishouParamField.setTs(String.valueOf(Long.parseLong(ts) / 1000));
        if (null != kuaishouParamField.getUser_agent()) {
            kuaishouParamField.setUser_agent(URLEncoder.createQuery().encode(kuaishouParamField.getUser_agent(), StandardCharsets.UTF_8));
        }
        //签名
//        signature(kuaishouParamField);
        logger.info("clickReport {} 特殊参数进行转换 convertParams:{}", channelAdsKey(), kuaishouParamField);
    }

    @Override
    protected Response judgeParams(Object adsObj) {
        KuaishouParamField kuaishouParamField = (KuaishouParamField) adsObj;
        /*if (Objects.isNull(kuaishouParamField.getCallback())) {
            return BasicResult.getFailResponse(KuaishouParamEnum.CALLBACK.getName() + "不能为空");
        }*/
        if (Objects.isNull(kuaishouParamField.getAdid())) {
            return BasicResult.getFailResponse(KuaishouParamEnum.ADID.getName() + "不能为空");
        }
        if (Objects.isNull(kuaishouParamField.getIdfa())
                && Objects.isNull(kuaishouParamField.getImei())
                && Objects.isNull(kuaishouParamField.getOaid())
        ) {
            return BasicResult.getFailResponse("安卓设备：" + KuaishouParamEnum.IMEI.getName() + "、" + KuaishouParamEnum.OAID.getName() + "不能同时为空；"
                    + " ios设备" + KuaishouParamEnum.IDFA.getName() + "不能为空");
        }
        return BasicResult.getSuccessResponse();
    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        KuaishouParamField kuaishouParamField = (KuaishouParamField) adsObj;
        KuaishouAdsDTO kuaishouAdsDTO = new KuaishouAdsDTO();
        BeanUtil.copyProperties(kuaishouParamField, kuaishouAdsDTO);
        kuaishouAdsDTO.setChannelName(channelName());
        baseServiceInner.insertAdsObject(kuaishouAdsDTO, adsDao());
//        adsDao().insert(kuaishouAdsDTO);
        logger.info("clickReport {} 将原始参数保存数据库，返回数据库对象 saveOriginParamData:{}", channelAdsKey(), kuaishouAdsDTO);
        return kuaishouAdsDTO;
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {
        KuaishouParamField kuaishouParamField = (KuaishouParamField) adsObj;
        KuaishouAdsDTO kuaishouAdsDTO = (KuaishouAdsDTO) adsDtoObj;
        String ocpxUrl = queryServerPath() + serverPathKey() + Constants.ServerPath.ADS_CALLBACK + "/" + kuaishouAdsDTO.getId() + "?";
        logger.info("clickReport {} 客户回调渠道的url：{}", channelAdsKey(), ocpxUrl);
        String encodeUrl = URLEncoder.createQuery().encode(ocpxUrl, StandardCharsets.UTF_8);
//            ocpxUrl = URLEncoder.encode(ocpxUrl, "UTF-8");
        kuaishouParamField.setCallback(encodeUrl);
        logger.info("clickReport {} 回调参数 replaceCallbackUrl:{}", channelAdsKey(), kuaishouParamField);
    }

    @Override
    protected String initAdsUrl() {
        return KuaishouPath.URI + KuaishouPath.PROMOTION;
    }

    @Override
    protected Response reportAds(String adsUrl, Object adsDtoObj) throws Exception {
        logger.info("调用用户侧的地址 {} adsUrl:{}", channelAdsKey(), adsUrl);
        //todo 重定向 302问题
        HttpResponse response = HttpRequest.get(adsUrl).timeout(20000).header("token", "application/json").execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        KuaishouAdsDTO kuaishouAdsDTO = (KuaishouAdsDTO) adsDtoObj;
        KuaishouAdsDTO kuaishouAdsVO = new KuaishouAdsDTO();
        kuaishouAdsVO.setId(kuaishouAdsDTO.getId());
        if (null != kuaishouAdsDTO.getTableName()){
            kuaishouAdsVO.setTableName(kuaishouAdsDTO.getTableName());
        }
        //上报成功
        if (HttpStatus.HTTP_OK == response.getStatus() && Objects.requireNonNull(responseBodyMap).get("ret").equals(0)) {
            kuaishouAdsVO.setReportStatus(Constants.ReportStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(kuaishouAdsVO, adsDao());
            logger.info("clickReport {} 上报广告侧接口请求成功:{} 数据:{}", channelAdsKey(), response, kuaishouAdsVO);
            return BasicResult.getSuccessResponse(kuaishouAdsDTO.getId());
        } else {
            kuaishouAdsVO.setReportStatus(Constants.ReportStatus.FAIL.getCode() + "--" + JSONObject.toJSONString(responseBodyMap));
            baseServiceInner.updateAdsObject(kuaishouAdsVO, adsDao());
            logger.error("clickReport {} 上报广告侧接口请求失败:{} 数据:{}", channelAdsKey(), response, kuaishouAdsVO);
            return BasicResult.getFailResponse("上报广告侧接口请求失败", 0);
        }
    }
}
