package huihuang.proxy.ocpx.middle.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouAdsDTO;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouParamEnum;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouParamField;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouPath;
import huihuang.proxy.ocpx.ads.litianjingdong.LTJDParamField;
import huihuang.proxy.ocpx.ads.youku.YoukuParamEnum;
import huihuang.proxy.ocpx.ads.youku.YoukuPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IKuaishouAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.channel.huawei.HuaweiParamEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiPath;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.middle.BaseAdsConstract.HuaweiLiangdamaoChannelFactory;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * huawei-jingdong
 *
 * @Author: xietao
 * @Date: 2023/5/28 16:02
 */
@Component("hjChannelAds")
public class HuaweiLtjdChannelAds extends HuaweiLiangdamaoChannelFactory {

    @Autowired
    private IKuaishouAdsDao kuaishouAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;

    String channelAdsKey = Constants.ChannelAdsKey.HUAWEI_KUAISHOU;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.HUAWEI_LTJD;
    }


    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        KuaishouParamField kuaishouParamField = new KuaishouParamField();

        Set<Map.Entry<KuaishouParamEnum, HuaweiParamEnum>> hkSet = KuaishouParamEnum.kuaishouHuaweiMap.entrySet();
        hkSet.stream().filter(xj -> Objects.nonNull(xj.getValue())).forEach(hk -> {
            KuaishouParamEnum kuaishou = hk.getKey();
            HuaweiParamEnum huawei = hk.getValue();
            //kuaishou的字段名
            String kuaishouField = kuaishou.getName();
            String huaweiParam = huawei.getParam();
            String[] value = parameterMap.get(huaweiParam);
            if (Objects.isNull(value) || value.length == 0) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(kuaishouField, kuaishouParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(kuaishouParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        //存储华为这边必有而广告侧（快手、粮大猫）这不必有的参数，回传可能会用到
        String extras = fitExtras(parameterMap,
                HuaweiParamEnum.CONTENT_ID.getParam(),
                HuaweiParamEnum.EVENT_TYPE.getParam(),
                HuaweiParamEnum.TRACE_TIME.getParam(),
                HuaweiParamEnum.TRACKING_ENABLED.getParam());
        if (extras.length() > 0) {
            kuaishouParamField.setExtra(extras);
        }
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, kuaishouParamField);
        return kuaishouParamField;
    }

    @Override
    protected Response judgeParams(Object adsObj) {
        KuaishouParamField kuaishouParamField = (KuaishouParamField) adsObj;
        if (Objects.isNull(kuaishouParamField.getCallback())) {
            return BasicResult.getFailResponse(KuaishouParamEnum.CALLBACK.getName() + "不能为空");
        }
        if (Objects.isNull(kuaishouParamField.getAdid())) {
            return BasicResult.getFailResponse(KuaishouParamEnum.ADID.getName() + "不能为空");
        }
        if (Objects.isNull(kuaishouParamField.getIdfa())
                && Objects.isNull(kuaishouParamField.getImei())
                && Objects.isNull(kuaishouParamField.getOaid())
        ) {
            return BasicResult.getFailResponse("安卓设备：" + KuaishouParamEnum.IMEI.getName() + "、" + KuaishouParamEnum.OAID.getName() + "不能同时为空；"
                    + " ios设备" + YoukuParamEnum.IDFA.getName() + "不能为空");
        }
        return BasicResult.getSuccessResponse();
    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        KuaishouParamField kuaishouParamField = (KuaishouParamField) adsObj;
        KuaishouAdsDTO kuaishouAdsDTO = new KuaishouAdsDTO();
        BeanUtil.copyProperties(kuaishouParamField, kuaishouAdsDTO);
        kuaishouAdsDTO.setChannelName(HuaweiPath.HUAWEI_CHANNEL_NAME);
        kuaishouAdsDao.insert(kuaishouAdsDTO);
        logger.info("clickReport {} 将原始参数保存数据库，返回数据库对象 saveOriginParamData:{}", channelAdsKey, kuaishouAdsDTO);
        return kuaishouAdsDTO;
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {
        KuaishouParamField kuaishouParamField = (KuaishouParamField) adsObj;
        KuaishouAdsDTO kuaishouAdsDTO = (KuaishouAdsDTO) adsDtoObj;
        String ocpxUrl = queryServerPath() + serverPathKey() + Constants.ServerPath.ADS_CALLBACK + "/" + kuaishouAdsDTO.getId() + "?";
        logger.info("clickReport {} 客户回调渠道的url：{}", channelAdsKey, ocpxUrl);
        String encodeUrl = URLEncoder.createQuery().encode(ocpxUrl, StandardCharsets.UTF_8);
//            ocpxUrl = URLEncoder.encode(ocpxUrl, "UTF-8");
        kuaishouParamField.setCallback(encodeUrl);
        logger.info("clickReport {} 回调参数 replaceCallbackUrl:{}", channelAdsKey, kuaishouParamField);
    }

    @Override
    protected String initAdsUrl() {
        return KuaishouPath.URI + KuaishouPath.PROMOTION;
    }

    @Override
    protected Response reportAds(String adsUrl, Object adsDtoObj) throws Exception {
        logger.info("调用用户侧的地址 {} adsUrl:{}", channelAdsKey, adsUrl);
        //todo 重定向 302问题
        HttpResponse response = HttpRequest.get(adsUrl).timeout(20000).header("token", "application/json").execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        KuaishouAdsDTO kuaishouAdsDTO = (KuaishouAdsDTO) adsDtoObj;
        KuaishouAdsDTO kuaishouAdsVO = new KuaishouAdsDTO();
        kuaishouAdsVO.setId(kuaishouAdsDTO.getId());
        //上报成功
        if (HttpStatus.HTTP_OK == response.getStatus() && Objects.requireNonNull(responseBodyMap).get("ret").equals(0)) {
            kuaishouAdsVO.setReportStatus(Constants.ReportStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(kuaishouAdsVO, kuaishouAdsDao);
            logger.info("clickReport {} 上报广告侧接口请求成功:{} 数据:{}", channelAdsKey, response, kuaishouAdsVO);
            return BasicResult.getSuccessResponse(kuaishouAdsDTO.getId());
        } else {
            kuaishouAdsVO.setReportStatus(Constants.ReportStatus.FAIL.getCode() + "--" + JSONObject.toJSONString(responseBodyMap));
            baseServiceInner.updateAdsObject(kuaishouAdsVO, kuaishouAdsDao);
            logger.error("clickReport {} 上报广告侧接口请求失败:{} 数据:{}", channelAdsKey, response, kuaishouAdsVO);
            return BasicResult.getFailResponse("上报广告侧接口请求失败", 0);
        }
    }

    //计算签名
    private void signature(LTJDParamField ltjdParamField) {
        String access_id = ltjdParamField.getAccess_id();
        String ts = ltjdParamField.getTs();
        String src = "access_id=" + access_id + "&ts=" + ts;
        String signatureStr = src + YoukuPath.SECRET;
        String signature = DigestUtil.md5Hex(signatureStr).toLowerCase();
        logger.info("clickReport {} 原始:{}  签名:{}", channelAdsKey, signatureStr, signature);
        ltjdParamField.setSignature(signature);
    }


}
