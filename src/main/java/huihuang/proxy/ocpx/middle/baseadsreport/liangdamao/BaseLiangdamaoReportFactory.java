package huihuang.proxy.ocpx.middle.baseadsreport.liangdamao;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoAdsDTO;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamEnum;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamField;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoPath;
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
import java.util.Optional;

/**
 * @Description: 上报客户的逻辑再抽象
 * 比如：百度和京东、优酷、番茄的对接，本质上是百度和粮大猫的对接，抽离出百度和粮大猫的公共部分，将京东、优酷、番茄的不同参数传入
 * @Author: xietao
 * @Date: 2023-05-23 17:39
 **/
public abstract class BaseLiangdamaoReportFactory extends BaseSupport implements IChannelAds {

    @Autowired
    protected BaseServiceInner baseServiceInner;

    protected abstract String channelAdsKey();

    protected abstract String serverPathKey();

    protected abstract IMarkDao adsDao();

    protected abstract String channelName();

    @Override
    protected void convertParams(Object adsObj) {
        LiangdamaoParamField liangdamaoParamField = (LiangdamaoParamField) adsObj;
        if (null != liangdamaoParamField.getCallback_url()) {
            liangdamaoParamField.setCallback_url(URLEncoder.createQuery().encode(liangdamaoParamField.getCallback_url(), StandardCharsets.UTF_8));
        }
//        liangdamaoParamField.setApp_type(osConvertAppType(liangdamaoParamField.getApp_type()));
        liangdamaoParamField.setRequest_id(RandomUtil.randomStamp());
        //时间戳，秒
        String ts = Optional.ofNullable(liangdamaoParamField.getTs()).orElse(String.valueOf(System.currentTimeMillis()));
        liangdamaoParamField.setTs(String.valueOf(Long.parseLong(ts) / 1000));
        if (null != liangdamaoParamField.getUa()) {
            liangdamaoParamField.setUa(URLEncoder.createQuery().encode(liangdamaoParamField.getUa(), StandardCharsets.UTF_8));
        }
        //签名
        signature(liangdamaoParamField);
    }

    @Override
    protected Response judgeParams(Object adsObj) {
        LiangdamaoParamField liangdamaoParamField = (LiangdamaoParamField) adsObj;
        if (Objects.isNull(liangdamaoParamField.getSignature())) {
            return BasicResult.getFailResponse(LiangdamaoParamEnum.SIGNATURE.getName() + "不能为空");
        }
        if (Objects.isNull(liangdamaoParamField.getTp_adv_id())) {
            return BasicResult.getFailResponse(LiangdamaoParamEnum.TP_ADV_ID.getName() + "不能为空");
        }
        if (Objects.isNull(liangdamaoParamField.getAccess_id())) {
            return BasicResult.getFailResponse(LiangdamaoParamEnum.ACCESS_ID.getName() + "不能为空");
        }
        if (Objects.isNull(liangdamaoParamField.getRequest_id())) {
            return BasicResult.getFailResponse(LiangdamaoParamEnum.REQUEST_ID.getName() + "不能为空");
        }
        //如果ios设备为空，则判断安卓设备
        if (Objects.isNull(liangdamaoParamField.getIdfa()) && Objects.isNull(liangdamaoParamField.getIdfa_md5())
                && Objects.isNull(liangdamaoParamField.getImei()) && Objects.isNull(liangdamaoParamField.getImei_md5())
                && Objects.isNull(liangdamaoParamField.getOaid()) && Objects.isNull(liangdamaoParamField.getOaid_md5())) {
            return BasicResult.getFailResponse("安卓设备：" + LiangdamaoParamEnum.IMEI.getName() + "、" + LiangdamaoParamEnum.OAID.getName()
                    + "、" + LiangdamaoParamEnum.IMEI_MD5.getName() + "、" + LiangdamaoParamEnum.OAID_MD5.getName() + "不能同时为空；"
                    + " ios设备" + LiangdamaoParamEnum.IDFA.getName() + "、" + LiangdamaoParamEnum.IDFA_MD5.getName() + "不能同时为空");
        }
        return BasicResult.getSuccessResponse();
    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        LiangdamaoParamField liangdamaoParamField = (LiangdamaoParamField) adsObj;
        LiangdamaoAdsDTO liangdamaoAdsDTO = new LiangdamaoAdsDTO();
        BeanUtil.copyProperties(liangdamaoParamField, liangdamaoAdsDTO);
        liangdamaoAdsDTO.setChannelName(channelName());
        baseServiceInner.insertAdsObject(liangdamaoAdsDTO, adsDao());
        logger.info("clickReport {} 将原始参数保存数据库，返回数据库对象 saveOriginParamData:{}", channelAdsKey(), liangdamaoAdsDTO);
        return liangdamaoAdsDTO;
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {
        LiangdamaoParamField liangdamaoParamField = (LiangdamaoParamField) adsObj;
        LiangdamaoAdsDTO liangdamaoAdsDTO = (LiangdamaoAdsDTO) adsDtoObj;
        String ocpxUrl = queryServerPath() + serverPathKey() + Constants.ServerPath.ADS_CALLBACK + "/" + liangdamaoAdsDTO.getId() + "?";
        logger.info("clickReport {} 客户回调渠道的url：{}", channelAdsKey(), ocpxUrl);
        String encodeUrl = URLEncoder.createQuery().encode(ocpxUrl, StandardCharsets.UTF_8);
//            ocpxUrl = URLEncoder.encode(ocpxUrl, "UTF-8");
        liangdamaoParamField.setCallback_url(encodeUrl);
        logger.info("clickReport {} 回调参数 replaceCallbackUrl:{}", channelAdsKey(), liangdamaoParamField);
    }

    @Override
    protected String initAdsUrl() {
        return LiangdamaoPath.BASIC_URI;
    }


    @Override
    protected Response reportAds(String adsUrl, Object adsDtoObj) throws Exception {
        logger.info("调用用户侧的地址 {} adsUrl:{}", channelAdsKey(), adsUrl);
        HttpResponse response = HttpRequest.get(adsUrl).timeout(20000).header("token", "application/json").execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        LiangdamaoAdsDTO liangdamaoAdsDTO = (LiangdamaoAdsDTO) adsDtoObj;
        LiangdamaoAdsDTO liangdamaoAdsVO = new LiangdamaoAdsDTO();
        liangdamaoAdsVO.setId(liangdamaoAdsDTO.getId());
        //上报成功
        if (HttpStatus.HTTP_OK == response.getStatus() && Objects.requireNonNull(responseBodyMap).get("code").equals("0")) {
            liangdamaoAdsVO.setReportStatus(Constants.ReportStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(liangdamaoAdsVO, adsDao());
            logger.info("clickReport {} 上报广告侧接口请求成功:{} 数据:{}", channelAdsKey(), response, liangdamaoAdsVO);
            return BasicResult.getSuccessResponse(liangdamaoAdsDTO.getId());
        } else {
            liangdamaoAdsVO.setReportStatus(Constants.ReportStatus.FAIL.getCode() + "--" + JSONObject.toJSONString(responseBodyMap));
            baseServiceInner.updateAdsObject(liangdamaoAdsVO, adsDao());
            logger.error("clickReport {} 上报广告侧接口请求失败:{} 数据:{}", channelAdsKey(), response, liangdamaoAdsVO);
            return BasicResult.getFailResponse("上报广告侧接口请求失败", 0);
        }
    }

    //计算签名
    private void signature(LiangdamaoParamField liangdamaoParamField) {
        String access_id = liangdamaoParamField.getAccess_id();
        String ts = liangdamaoParamField.getTs();
        String src = "access_id=" + access_id + "&ts=" + ts;
        String signatureStr = src + LiangdamaoPath.SECRET;
        String signature = DigestUtil.md5Hex(signatureStr).toLowerCase();
        logger.info("clickReport {} 原始:{}  签名:{}", channelAdsKey(), signatureStr, signature);
        liangdamaoParamField.setSignature(signature);
    }

}
