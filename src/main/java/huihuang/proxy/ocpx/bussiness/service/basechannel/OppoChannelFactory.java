package huihuang.proxy.ocpx.bussiness.service.basechannel;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.bussiness.dao.channel.IOppoCallbackDao;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2OppoVO;
import huihuang.proxy.ocpx.channel.oppo.OppoCallbackDTO;
import huihuang.proxy.ocpx.channel.oppo.OppoPath;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.Map;
import java.util.Objects;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-06-04 16:21
 **/
public class OppoChannelFactory {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IOppoCallbackDao oppoCallbackDao;

    protected Response baseAdsCallBack(Ads2OppoVO oppoVO) throws Exception {

        String channelUrl = OppoPath.CALLBACK_URL;
        //回传到渠道
        JSONObject json = new JSONObject();
        if (oppoVO.getImei() != null) {
            json.put("imei", oppoVO.getImei());
            json.put("type", 1);
        } else if (oppoVO.getOuId() != null) {
            json.put("ouId", oppoVO.getOuId());
            json.put("type", 2);
        } else {
            json.put("type", 0);
        }
        json.put("timestamp", oppoVO.getTimestamp());
        json.put("pkg", oppoVO.getPkg());
        json.put("dataType", oppoVO.getDataType());
        json.put("channel", oppoVO.getChannel());
        //归因
        json.put("ascribeType", 1);
        json.put("adId", oppoVO.getAdId());

        StringBuilder url = new StringBuilder(channelUrl);
        logger.info("baseAdsCallBack 回传渠道url：{}", url);
        HttpResponse response = HttpRequest.post(url.toString())
                .header("signature", signature(json.toJSONString(), oppoVO.getTimestamp()))
                .header("Content-Type", "application/json")
                .header("timestamp", String.valueOf(oppoVO.getTimestamp()))
                .body(json.toJSONString()).execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        //保存转化事件回调信息
        OppoCallbackDTO oppoCallbackDTO = new OppoCallbackDTO(oppoVO.getAdsId(), oppoVO.getImei(), oppoVO.getOuId(), String.valueOf(oppoVO.getTimestamp()), oppoVO.getPkg(),
                oppoVO.getDataType(), oppoVO.getChannel(), json.getInteger("type"), oppoVO.getAscribeType(), oppoVO.getAdId(), oppoVO.getAdsName());


        if (HttpStatus.HTTP_OK == response.getStatus() && Objects.requireNonNull(responseBodyMap).get("ret").equals(0)) {
            oppoCallbackDTO.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            oppoCallbackDTO.setCallBackMes(String.valueOf(responseBodyMap.get("code")));
            oppoCallbackDao.insert(oppoCallbackDTO);
            return BasicResult.getSuccessResponse(oppoCallbackDTO);
        } else {
            oppoCallbackDTO.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            oppoCallbackDTO.setCallBackMes(responseBodyMap.get("ret") + "  " + responseBodyMap.get("msg"));
            oppoCallbackDao.insert(oppoCallbackDTO);
            return BasicResult.getFailResponse(oppoCallbackDTO.getCallBackMes(), oppoCallbackDTO);
        }
    }

    /**
     * 签名计算
     */
    private String signature(String postData, Long timestamp) {
        String originSign = postData + timestamp + OppoPath.SALT;
        return DigestUtil.md5Hex(originSign).toLowerCase();
    }

    /**
     * @param data 要AES加密的字符
     * @return
     * @throws GeneralSecurityException
     */
    protected static String encode(byte[] data) throws GeneralSecurityException {
        final Key dataKey = new SecretKeySpec(Base64.decodeBase64(OppoPath.BASE64KEY), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, dataKey);

        byte[] encryptData = cipher.doFinal(data);

        return Base64.encodeBase64String(encryptData).replaceAll("\r", "").replaceAll("\n", "");
    }

}
