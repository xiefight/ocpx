package huihuang.proxy.ocpx.bussiness.service.basechannel;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.bussiness.dao.channel.IWifiCallbackDao;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2WifiVO;
import huihuang.proxy.ocpx.channel.wifi.WifiCallbackDTO;
import huihuang.proxy.ocpx.channel.wifi.WifiPath;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Set;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-06-02 16:37
 **/
public class WifiChannelFactory {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IWifiCallbackDao wifiCallbackDao;

    protected Response baseAdsCallBack(Ads2WifiVO wifiVO) throws Exception {
        String channelUrl = WifiPath.CALLBACK_URL;
        //回传到渠道
        JSONObject json = new JSONObject();
        json.put("clientid", wifiVO.getClientId());
        json.put("ts", wifiVO.getTs());
        json.put("event_type", wifiVO.getEventType());

        StringBuilder url = new StringBuilder(channelUrl);
        Set<Map.Entry<String, Object>> entries = json.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            url.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        String src = url.substring(0, url.length() - 1);
        //拼接上点击上报时，渠道侧必要的回传参数
        if (StrUtil.isNotEmpty(wifiVO.getExtra())) {
            src += wifiVO.getExtra();
        }
        logger.info("baseAdsCallBack 回传渠道url：{}", src);
        HttpResponse response = HttpRequest.get(src).execute();

        //保存转化事件回调信息
        WifiCallbackDTO wifiCallbackDTO = new WifiCallbackDTO(wifiVO.getAdsId(), wifiVO.getClientId(),
                wifiVO.getEventType(), wifiVO.getTs(), wifiVO.getAdsName(), wifiVO.getExtra());

        if (HttpStatus.HTTP_OK == response.getStatus()) {
            wifiCallbackDTO.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            wifiCallbackDTO.setCallBackMes("回调渠道成功");
            wifiCallbackDao.insert(wifiCallbackDTO);
            return BasicResult.getSuccessResponse(wifiCallbackDTO);
        } else {
            wifiCallbackDTO.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            wifiCallbackDTO.setCallBackMes("回调渠道失败");
            wifiCallbackDao.insert(wifiCallbackDTO);
            return BasicResult.getFailResponse(wifiCallbackDTO.getCallBackMes(), wifiCallbackDTO);
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
        String signatureStr = src + WifiPath.XIGUA_SECRET;
        String signature = DigestUtil.md5Hex(signatureStr).toLowerCase();
        json.put("sign", signature);
        return signature;
    }

}
