package huihuang.proxy.ocpx.bussiness.service.basechannel;

import cn.hutool.core.net.URLDecoder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.bussiness.dao.channel.IIQiyiCallbackDao;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2IQiyiVO;
import huihuang.proxy.ocpx.channel.iqiyi.IQiyiCallbackDTO;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @Author: xietao
 * @Date: 2023/6/29 22:27
 */
public class IQiyiChannelFactory {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IIQiyiCallbackDao iqiyiCallbackDao;

    protected Response baseAdsCallBack(Ads2IQiyiVO iqiyiVO) throws Exception {

        String channelUrl = iqiyiVO.getChannelUrl();
        String callback = URLDecoder.decode(channelUrl, StandardCharsets.UTF_8);
        //回传到渠道
        JSONObject json = new JSONObject();
//        json.put("et", iqiyiVO.getEventTime());
        json.put("event_type", iqiyiVO.getEventType());

        StringBuilder url = new StringBuilder(callback);
        Set<Map.Entry<String, Object>> entries = json.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            url.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
        logger.info("baseAdsCallBack 回传渠道url：{}", url);
        HttpResponse response = HttpRequest.get(url.toString()).execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);

        //保存转化事件回调信息
        IQiyiCallbackDTO iqiyiCallbackDTO = new IQiyiCallbackDTO(iqiyiVO.getAdsId(),
                String.valueOf(iqiyiVO.getEventType()), String.valueOf(iqiyiVO.getEventTime()), iqiyiVO.getAdsName());
        if (HttpStatus.HTTP_OK == response.getStatus() && Objects.requireNonNull(responseBodyMap).get("status").equals(200)) {
            iqiyiCallbackDTO.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            iqiyiCallbackDTO.setCallBackMes(String.valueOf(responseBodyMap.get("message")));
            iqiyiCallbackDao.insert(iqiyiCallbackDTO);
            return BasicResult.getSuccessResponse(iqiyiCallbackDTO);
        } else {
            iqiyiCallbackDTO.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            iqiyiCallbackDTO.setCallBackMes(responseBodyMap.get("status") + "  " + responseBodyMap.get("message"));
            iqiyiCallbackDao.insert(iqiyiCallbackDTO);
            return BasicResult.getFailResponse(iqiyiCallbackDTO.getCallBackMes(), iqiyiCallbackDTO);
        }
    }

}
