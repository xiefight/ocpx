package huihuang.proxy.ocpx.bussiness.service.basechannel;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoEventTypeEnum;
import huihuang.proxy.ocpx.bussiness.dao.channel.IXiaomiCallbackDao;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2XiaomiVO;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiCallbackDTO;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiPath;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-05-28 22:49
 **/
public class XiaomiChannelFactory {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IXiaomiCallbackDao xiaomiCallbackDao;

    protected Response baseAdsCallBack(Ads2XiaomiVO xiaomiVO) throws Exception {
        String channelUrl = XiaomiPath.CALLBACK_URL;
//        String callback = URLEncoder.createQuery().encode(feedbackUrl, StandardCharsets.UTF_8);
//        logger.info("adsCallBack  渠道回调url：{}  只对callback进行encode：{}", channelUrl + feedbackUrl, feedbackUrl);
        //回传到渠道
        JSONObject json = new JSONObject();
        json.put("callback", xiaomiVO.getCallBackUrl());
        json.put("conv_time", xiaomiVO.getEventTimes());
        json.put("convType", xiaomiVO.getEventType());
        if (Objects.isNull(xiaomiVO.getOaid())) {
            json.put("imei", xiaomiVO.getImei());
        } else {
            json.put("oaid", xiaomiVO.getOaid());
        }

        StringBuilder url = new StringBuilder(channelUrl);
        Set<Map.Entry<String, Object>> entries = json.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            url.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        String src = url.substring(0, url.length() - 1);
        String signature = signature(json);
//        url.append("sign=").append(signature);
        logger.info("baseAdsCallBack 回传渠道url：{}", src);
        HttpResponse response = HttpRequest.get(src).execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);

        //保存转化事件回调信息
        XiaomiCallbackDTO xiaomiCallbackDTO = new XiaomiCallbackDTO(xiaomiVO.getAdsId(), xiaomiVO.getCallBackUrl(),
                xiaomiVO.getEventType(), xiaomiVO.getEventTimes(), xiaomiVO.getImei(), xiaomiVO.getOaid(), signature, xiaomiVO.getAdsName());
        if (HttpStatus.HTTP_OK == response.getStatus() && Objects.requireNonNull(responseBodyMap).get("code").equals(0)) {
            xiaomiCallbackDTO.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            xiaomiCallbackDTO.setCallBackMes(String.valueOf(responseBodyMap.get("code")));
            xiaomiCallbackDao.insert(xiaomiCallbackDTO);
            return BasicResult.getSuccessResponse(xiaomiCallbackDTO);
        } else {
            xiaomiCallbackDTO.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            xiaomiCallbackDTO.setCallBackMes(String.valueOf(responseBodyMap.get("code")) + responseBodyMap.get("failMsg"));
            xiaomiCallbackDao.insert(xiaomiCallbackDTO);
            return BasicResult.getFailResponse(xiaomiCallbackDTO.getCallBackMes(), xiaomiCallbackDTO);
        }
    }

    //计算签名
    private String signature(Map<String, Object> json) {
        StringBuilder srcBuilder = new StringBuilder();
        Set<Map.Entry<String, Object>> entries = json.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            String key = entry.getKey();
            Object value = entry.getValue();
            srcBuilder.append(key).append("=").append(value).append("&");
        }
        String src = srcBuilder.substring(0, srcBuilder.length() - 1);
        String signatureStr = src + XiaomiPath.LTJD_SECRET;
        String signature = DigestUtil.md5Hex(signatureStr).toLowerCase();
        json.put("sign", signature);
        return signature;
    }

}
