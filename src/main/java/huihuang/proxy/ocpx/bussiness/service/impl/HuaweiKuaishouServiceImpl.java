package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouAdsDTO;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouEventTypeEnum;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IKuaishouAdsDao;
import huihuang.proxy.ocpx.bussiness.dao.channel.IHuaweiCallbackDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.channel.huawei.HuaweiCallbackDTO;
import huihuang.proxy.ocpx.channel.huawei.HuaweiParamEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.KuaishouResponse;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.middle.factory.ChannelAdsFactory;
import huihuang.proxy.ocpx.util.CommonUtil;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * baidu-jingdong
 *
 * @Author: xietao
 * @Date: 2023/5/11 17:26
 */
@Service("hkService")
public class HuaweiKuaishouServiceImpl implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(HuaweiKuaishouServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IKuaishouAdsDao kuaishouAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private IHuaweiCallbackDao huaweiCallbackDao;

    String channelAdsKey = Constants.ChannelAdsKey.HUAWEI_KUAISHOU;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public KuaishouResponse adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        logger.info("adsCallBack {} 开始回调渠道  id:{}  parameterMap.size:{}", channelAdsKey, id, parameterMap.size());
        //转化类型字段
        String eventType = parameterMap.get("actionType")[0];
        long currentTime = System.currentTimeMillis();
        String eventTimes = String.valueOf(currentTime);

        //根据id查询对应的点击记录
        KuaishouAdsDTO kuaishouAdsDTO = kuaishouAdsDao.queryKuaishouAdsById(id);
        if (null == kuaishouAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return new KuaishouResponse(1, "未根据找到对应的监测信息", id);
        }

        String channelUrl = HuaweiPath.CALLBACK_URL;
        String callback = kuaishouAdsDTO.getCallback();
        logger.info("adsCallBack 渠道原url：{} 渠道decode回调url：{}", callback, channelUrl);
        //回传到渠道
        JSONObject json = new JSONObject();
        json.put("callback", callback);
        json.put("campaign_id", kuaishouAdsDTO.getCampaignId());
        json.put("content_id", getContentFromExtra(kuaishouAdsDTO, HuaweiParamEnum.CONTENT_ID.getParam(), null));
        json.put("tracking_enabled", getContentFromExtra(kuaishouAdsDTO, HuaweiParamEnum.TRACKING_ENABLED.getParam(), "1"));
        json.put("conversion_type", KuaishouEventTypeEnum.kuaishouHuaweiEventTypeMap.get(eventType).getCode());
        json.put("conversion_time", String.valueOf(currentTime / 1000));
        json.put("timestamp", eventTimes);
        if (CommonUtil.strEmpty(kuaishouAdsDTO.getOaid())) {
            json.put("oaid", kuaishouAdsDTO.getOaid());
        }

        StringBuilder url = new StringBuilder(channelUrl);
        Set<Map.Entry<String, Object>> entries = json.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            url.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
//        String src = url.substring(0, url.length() - 1);

//        String signature = signature(json);
//        url.append("sign=").append(signature);
        String huaweiSecret = "";
        String adsName = "";
        if (KuaishouPath.HUAWEI_KUAISHOU_ADID.equals(kuaishouAdsDTO.getAdid())) {
            huaweiSecret = HuaweiPath.KUAISHOU_SECRET;
            adsName = KuaishouPath.KUAISHOU_ADS_NAME;
        }
        if (KuaishouPath.HUAWEI_KUAISHOUJISU_ADID.equals(kuaishouAdsDTO.getAdid()) || KuaishouPath.HUAWEI_KUAISHOUJISU2_ADID.equals(kuaishouAdsDTO.getAdid())) {
            huaweiSecret = HuaweiPath.KUAISHOUJISU_SECRET;
            adsName = KuaishouPath.KUAISHOUJISU_ADS_NAME;
        }
        logger.info("adsCallBack {} 请求渠道url：{} adid：{} huaweiSecret：{}", channelAdsKey, url, kuaishouAdsDTO.getAdid(), huaweiSecret);
        final String authSign = buildAuthorizationHeader(json.toJSONString(), huaweiSecret);
        HttpResponse response = HttpRequest.post(url.toString()).header("Authorization", authSign).body(json.toJSONString()).execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        //保存转化事件回调信息
        HuaweiCallbackDTO huaweiCallbackDTO = new HuaweiCallbackDTO(id, callback, String.valueOf(json.get("content_id")),
                String.valueOf(json.get("campaign_id")), String.valueOf(json.get("oaid")), String.valueOf(json.get("tracking_enbaled")),
                String.valueOf(json.get("conversion_type")), String.valueOf(json.get("conversion_time")), String.valueOf(json.get("timestamp")), adsName);

        //更新回调状态
        KuaishouAdsDTO kuaishouAds = new KuaishouAdsDTO();
        kuaishouAds.setId(id);
        kuaishouAds.setCallBackTime(String.valueOf(currentTime));
        if (HttpStatus.HTTP_OK == response.getStatus() && Objects.requireNonNull(responseBodyMap).get("resultCode").equals(0)) {
            huaweiCallbackDTO.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            kuaishouAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            logger.info("adsCallBack {} 回调渠道成功：{} 数据：{}", channelAdsKey, responseBodyMap, kuaishouAds);
            huaweiCallbackDTO.setCallBackMes(String.valueOf(responseBodyMap.get("code")));
            huaweiCallbackDao.insert(huaweiCallbackDTO);
            baseServiceInner.updateAdsObject(kuaishouAds, kuaishouAdsDao);
            logger.info("adsCallBack {} {}", channelAdsKey, huaweiCallbackDTO);
            return new KuaishouResponse(0, "", huaweiCallbackDTO.getId());
        } else {
            huaweiCallbackDTO.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            kuaishouAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            logger.error("adsCallBack {} 回调渠道失败：{} 数据：{}", channelAdsKey, responseBodyMap, kuaishouAds);
            huaweiCallbackDTO.setCallBackMes(responseBodyMap.get("resultCode") + "  " + responseBodyMap.get("resultMessage"));
            huaweiCallbackDao.insert(huaweiCallbackDTO);
            baseServiceInner.updateAdsObject(kuaishouAds, kuaishouAdsDao);
            logger.info("adsCallBack {} {}", channelAdsKey, huaweiCallbackDTO);
            return new KuaishouResponse(1, huaweiCallbackDTO.getCallBackMes(), huaweiCallbackDTO.getId());
        }
    }

    //计算签名

    /**
     * 计算请求头中的Authorization
     *
     * @param body 请求体json
     * @param key  密钥
     * @return Authorization 鉴权头
     */
    public static String buildAuthorizationHeader(String body, String key) {
// 广告主请求头header中的Authorization
        final String authorizationFormat = "Digest validTime=\"{0}\", response=\"{1}\"";
        String authorization = null;
        try {
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKey secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
            mac.init(secretKey);
            byte[] signatureBytes = mac.doFinal(bodyBytes);
            final String timestamp = String.valueOf(System.currentTimeMillis());
            final String signature = (signatureBytes == null) ? null : HexUtil.encodeHexStr(signatureBytes);
            authorization = MessageFormat.format(authorizationFormat, timestamp, signature)
            ;
        } catch (Exception e) {
            System.err.println("build Authorization Header failed！");
            e.printStackTrace();
        }
        System.out.println("generate Authorization Header: " + authorization);
        return authorization;
    }


    /**
     * 从extra中获取指定字段值
     *
     * @param kuaishouAdsDTO 实体数据
     * @param contentKey     指定字段key
     * @param defaultVal     默认值
     * @return 指定字段值
     */
    private String getContentFromExtra(KuaishouAdsDTO kuaishouAdsDTO, String contentKey, String defaultVal) {
        String contentValue = defaultVal;
        String extra = kuaishouAdsDTO.getExtra();
        if (StrUtil.isEmpty(extra)) {
            return contentValue;
        }
        String[] splits = extra.split("&");
        for (String splitStr : splits) {
            if (StrUtil.isEmpty(splitStr)) {
                continue;
            }
            String[] equalsStr = splitStr.split("=");
            String key = equalsStr[0];
            if (StrUtil.isNotEmpty(key) && key.equals(contentKey)) {
                contentValue = equalsStr[1];
                break;
            }
        }
        return contentValue;
    }
}
