package huihuang.proxy.ocpx.bussiness.service.basechannel;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.bussiness.dao.channel.IHonorCallbackDao;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2HonorVO;
import huihuang.proxy.ocpx.channel.honor.HonorCallbackDTO;
import huihuang.proxy.ocpx.channel.honor.HonorPath;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.util.CommonUtil;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class HonorChannelFactory {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IHonorCallbackDao honorCallbackDao;

    protected Response baseAdsCallBack(Ads2HonorVO honorVO) throws Exception {

        String channelUrl = HonorPath.CALLBACK_URL;
        //回传到渠道
        JSONObject json = new JSONObject();
//        json.put("callback", honorVO.getCallbackUrl());
        json.put("conversionId", honorVO.getConversionId());
        json.put("conversionTime", honorVO.getConversionTime());
        json.put("advertiserId", honorVO.getAdvertiserId());
        json.put("trackId", honorVO.getTrackId());
        if (CommonUtil.strEmpty(honorVO.getOaid())) {
            json.put("oaid", honorVO.getOaid());
        }

        StringBuilder url = new StringBuilder(channelUrl);
        Set<Map.Entry<String, Object>> entries = json.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            url.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
//        final String authSign = buildAuthorizationHeader(json.toJSONString(), honorVO.getSecret());
        logger.info("baseAdsCallBack 回传渠道url：{}", url);
//        HttpResponse response = HttpRequest.post(url.toString()).header("Authorization", authSign).body(json.toJSONString()).execute();

        HttpResponse response = HttpRequest.get(url.toString()).body(json.toJSONString()).execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        //保存转化事件回调信息
        HonorCallbackDTO honorCallbackDTO = new HonorCallbackDTO(honorVO.getAdsId(), honorVO.getConversionId(),
                honorVO.getOaid(), honorVO.getAdvertiserId(), honorVO.getConversionTime(), honorVO.getAdsName());

        if (HttpStatus.HTTP_OK == response.getStatus() && Objects.requireNonNull(responseBodyMap).get("code").equals(0)) {
            honorCallbackDTO.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            honorCallbackDTO.setCallBackMes(String.valueOf(responseBodyMap.get("code")));
            honorCallbackDao.insert(honorCallbackDTO);
            return BasicResult.getSuccessResponse(honorCallbackDTO);
        } else {
            honorCallbackDTO.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            honorCallbackDTO.setCallBackMes(responseBodyMap.get("code") + "  " + responseBodyMap.get("message"));
            honorCallbackDao.insert(honorCallbackDTO);
            return BasicResult.getFailResponse(honorCallbackDTO.getCallBackMes(), honorCallbackDTO);
        }
    }


    /**
     * 从extra中获取指定字段值
     *
     * @param adsDTO     实体数据
     * @param contentKey 指定字段key
     * @param defaultVal 默认值
     * @return 指定字段值
     */
    protected String getContentFromExtra(Object adsDTO, String contentKey, String defaultVal) {
        String contentValue = defaultVal;
        PropertyDescriptor descriptor;
        try {
            descriptor = new PropertyDescriptor("extra", adsDTO.getClass());
            Method getMethod = descriptor.getReadMethod();
            String extra = (String) getMethod.invoke(adsDTO);
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
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            logger.error("查找华为额外字段出错", e);
        }
        return contentValue;
    }

    public static String fitExtras(Map<String, String[]> parameterMap, String... extras) {
//        if (extras.length == 0) {
//            extras[0] = HuaweiParamEnum.CONTENT_ID.getParam();
//            extras[1] = HuaweiParamEnum.EVENT_TYPE.getParam();
//            extras[2] = HuaweiParamEnum.TRACE_TIME.getParam();
//            extras[3] = HuaweiParamEnum.TRACKING_ENABLED.getParam();
//        }
        StringBuilder extraStr = new StringBuilder();
        for (String extra : extras) {
            String[] cids = parameterMap.get(extra);
            if (Objects.nonNull(cids) && cids.length > 0) {
                String cid = cids[0];
                extraStr.append("&").append(extra).append("=").append(cid);
            }
        }
        return extraStr.toString();
    }

}
