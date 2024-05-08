package huihuang.proxy.ocpx.bussiness.service.basechannel;

import cn.hutool.core.net.URLDecoder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.bussiness.dao.channel.IGDTCallbackDao;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2GuangdiantongVO;
import huihuang.proxy.ocpx.channel.guangdiantong.GuangdiantongCallbackDTO;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GDTChannelFactory {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IGDTCallbackDao gdtCallbackDao;

    protected Response baseAdsCallBack(Ads2GuangdiantongVO gdtVO) throws Exception {

        String channelUrl = gdtVO.getChannelUrl();
        String callback = URLDecoder.decode(channelUrl, StandardCharsets.UTF_8);
        //回传到渠道
        List<Map<String, Object>> actionList = new ArrayList<>();

        JSONObject actions = new JSONObject();
        actions.put("actions", actionList);

        JSONObject actionMap = new JSONObject();
        actionList.add(actionMap);

        actionMap.put("action_type", gdtVO.getActionType());
        JSONObject userJson = new JSONObject();
        if (null != gdtVO.getHashImei()) {
            userJson.put("hash_imei", gdtVO.getHashImei());
        }
        if (null != gdtVO.getHashIdfa()) {
            userJson.put("hash_idfa", gdtVO.getHashIdfa());
        }
        if (null != gdtVO.getOaid()) {
            userJson.put("oaid", gdtVO.getOaid());
        }
        if (null != gdtVO.getHashOaid()) {
            userJson.put("hash_oaid", gdtVO.getHashOaid());
        }
        actionMap.put("user_id", userJson);

        JSONObject paramJson = new JSONObject();
        if (null != gdtVO.getLengthOfStay()) {
            paramJson.put("length_of_stay", gdtVO.getLengthOfStay());
            actionMap.put("action_param", paramJson);
        }

        StringBuilder url = new StringBuilder(callback);
        logger.info("baseAdsCallBack 回传渠道url：{}", url);
        HttpResponse response = HttpRequest.post(url.toString()).body(actions.toJSONString()).execute();

        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);

        //保存转化事件回调信息

        GuangdiantongCallbackDTO gdtDTO = new GuangdiantongCallbackDTO(gdtVO.getAdsId(),
                String.valueOf(gdtVO.getActionType()), gdtVO.getAdsName());
        if (HttpStatus.HTTP_OK == response.getStatus() && Objects.requireNonNull(responseBodyMap).get("code").equals(0)) {
            gdtDTO.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            gdtDTO.setCallBackMes(String.valueOf(responseBodyMap.get("message")));
            gdtCallbackDao.insert(gdtDTO);
            return BasicResult.getSuccessResponse(gdtDTO);
        } else {
            gdtDTO.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            gdtDTO.setCallBackMes(responseBodyMap.get("status") + "  " + responseBodyMap.get("message"));
            gdtCallbackDao.insert(gdtDTO);
            return BasicResult.getFailResponse(gdtDTO.getCallBackMes(), gdtDTO);
        }
    }

}
