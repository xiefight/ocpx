package huihuang.proxy.ocpx.bussiness.service.basechannel;

import cn.hutool.core.util.HexUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouAdsDTO;
import huihuang.proxy.ocpx.bussiness.dao.channel.IHuaweiCallbackDao;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2HuaweiVO;
import huihuang.proxy.ocpx.channel.huawei.HuaweiCallbackDTO;
import huihuang.proxy.ocpx.channel.huawei.HuaweiPath;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.util.CommonUtil;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @Description: 抽象出huawei渠道的回调，不掺杂广告侧的逻辑，不用管广告侧是谁
 * @Author: xietao
 * @Date: 2023-05-28 13:02
 **/
public class HuaweiChannelFactory {

    @Autowired
    private IHuaweiCallbackDao huaweiCallbackDao;

    protected Response baseAdsCallBack(Ads2HuaweiVO huaweiVO) throws Exception {

        String channelUrl = HuaweiPath.CALLBACK_URL;
        //回传到渠道
        JSONObject json = new JSONObject();
        json.put("callback", huaweiVO.getCallbackUrl());
        json.put("campaign_id", huaweiVO.getCampaignId());
        json.put("content_id", huaweiVO.getContentId());
        json.put("tracking_enabled", huaweiVO.getTrackingEnabled());
        json.put("conversion_type", huaweiVO.getConversionType());
        json.put("conversion_time", huaweiVO.getConversionTime());
        json.put("timestamp", huaweiVO.getTimestamp());
        if (CommonUtil.strEmpty(huaweiVO.getOaid())) {
            json.put("oaid", huaweiVO.getOaid());
        }

        StringBuilder url = new StringBuilder(channelUrl);
        Set<Map.Entry<String, Object>> entries = json.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            url.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
        final String authSign = buildAuthorizationHeader(json.toJSONString(), huaweiVO.getSecret());
        HttpResponse response = HttpRequest.post(url.toString()).header("Authorization", authSign).body(json.toJSONString()).execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        //保存转化事件回调信息
        HuaweiCallbackDTO huaweiCallbackDTO = new HuaweiCallbackDTO(huaweiVO.getAdsId(), huaweiVO.getCallbackUrl(), huaweiVO.getContentId(),
                huaweiVO.getCampaignId(), huaweiVO.getOaid(), huaweiVO.getTrackingEnabled(),
                huaweiVO.getConversionType(), huaweiVO.getConversionTime(), huaweiVO.getTimestamp(), huaweiVO.getAdsName());

        //更新回调状态
        KuaishouAdsDTO kuaishouAds = new KuaishouAdsDTO();
        kuaishouAds.setId(huaweiVO.getAdsId());
        kuaishouAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));
        if (HttpStatus.HTTP_OK == response.getStatus() && Objects.requireNonNull(responseBodyMap).get("resultCode").equals(0)) {
            huaweiCallbackDTO.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            huaweiCallbackDTO.setCallBackMes(String.valueOf(responseBodyMap.get("code")));
            huaweiCallbackDao.insert(huaweiCallbackDTO);
            return BasicResult.getSuccessResponse(huaweiCallbackDTO);
        } else {
            huaweiCallbackDTO.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            huaweiCallbackDTO.setCallBackMes(responseBodyMap.get("resultCode") + "  " + responseBodyMap.get("resultMessage"));
            huaweiCallbackDao.insert(huaweiCallbackDTO);
            return BasicResult.getFailResponse(huaweiCallbackDTO.getCallBackMes(), huaweiCallbackDTO);
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
    private String buildAuthorizationHeader(String body, String key) {
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

}
