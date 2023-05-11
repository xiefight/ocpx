package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.xiguavideo.XiguaAdsDTO;
import huihuang.proxy.ocpx.ads.xiguavideo.XiguaEventTypeEnum;
import huihuang.proxy.ocpx.ads.xiguavideo.XiguaPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IXiguaAdsDao;
import huihuang.proxy.ocpx.bussiness.dao.channel.IWifiCallbackDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.channel.wifi.WifiCallbackDTO;
import huihuang.proxy.ocpx.channel.wifi.WifiPath;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.middle.factory.ChannelAdsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * wifi-xigua
 *
 * @Author: xietao
 * @Date: 2023/5/9 21:56
 */
@Service("wxService")
public class WifiXiguaServiceImpl implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(WifiXiguaServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IXiguaAdsDao xiguaAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private IWifiCallbackDao wifiCallbackDao;

    String channelAdsKey = Constants.ChannelAdsKey.WIFI_XIGUA;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        logger.info("adsCallBack {} 开始回调渠道  id:{}  parameterMap.size:{}", channelAdsKey, id, parameterMap.size());
        //转化类型字段
        String eventType = parameterMap.get("event_type")[0];
        String eventTimes = String.valueOf(System.currentTimeMillis() / 1000);

        //根据id查询对应的点击记录
        XiguaAdsDTO xiguaAdsDTO = xiguaAdsDao.queryXiguaAdsById(id);
        if (null == xiguaAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        String channelUrl = WifiPath.CALLBACK_URL;
        //回传到渠道
        JSONObject json = new JSONObject();
        json.put("clientid", XiguaPath.CLIENT_ID);
        json.put("ts", eventTimes);
        json.put("event_type", XiguaEventTypeEnum.xiguaWifiEventTypeMap.get(eventType).getCode());

        StringBuilder url = new StringBuilder(channelUrl);
        Set<Map.Entry<String, Object>> entries = json.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            url.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        String src = url.substring(0, url.length() - 1);
        //拼接上点击上报时，渠道侧必要的回传参数
        String extra = xiguaAdsDTO.getExtra();
        if (StrUtil.isNotEmpty(extra)) {
            src += extra;
        }
        logger.info("adsCallBack {} 回传渠道的url ：{}", channelAdsKey, src);
//        String signature = signature(json);
        HttpResponse response = HttpRequest.get(src).execute();

        //保存转化事件回调信息
        WifiCallbackDTO wifiCallbackDTO = new WifiCallbackDTO(id, String.valueOf(json.get("clientid")), String.valueOf(json.get("event_type")),
                eventTimes, XiguaPath.XIGUA_ADS_NAME, extra);
        //更新回调状态
        XiguaAdsDTO xiguaAds = new XiguaAdsDTO();
        xiguaAds.setId(id);
        xiguaAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));

        if (HttpStatus.HTTP_OK == response.getStatus()) {
            wifiCallbackDTO.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            xiguaAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            logger.error("adsCallBack {} 回调渠道失败  xiguaAds数据：{}", channelAdsKey, xiguaAds);
            wifiCallbackDTO.setCallBackMes("回调渠道成功");
            wifiCallbackDao.insert(wifiCallbackDTO);
            baseServiceInner.updateAdsObject(xiguaAds, xiguaAdsDao);
            logger.info("adsCallBack {} wifiCallbackDTO：{}", channelAdsKey, wifiCallbackDTO);
            return BasicResult.getSuccessResponse(wifiCallbackDTO.getId());
        } else {
            wifiCallbackDTO.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            xiguaAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            logger.error("adsCallBack {} 回调渠道失败  xiguaAds数据：{}", channelAdsKey, xiguaAds);
            wifiCallbackDTO.setCallBackMes("回调渠道失败");
            wifiCallbackDao.insert(wifiCallbackDTO);
            baseServiceInner.updateAdsObject(xiguaAds, xiguaAdsDao);
            logger.info("adsCallBack {} wifiCallbackDTO：{}", channelAdsKey, wifiCallbackDTO);
            return BasicResult.getFailResponse(wifiCallbackDTO.getCallBackMes());
        }
        /*Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);

        //保存转化事件回调信息
        WifiCallbackDTO wifiCallbackDTO = new WifiCallbackDTO(id, String.valueOf(json.get("clientid")), String.valueOf(json.get("event_type")),
                eventTimes, WifiPath.XIGUA_ADS_NAME, extra);
        //更新回调状态
        YoukuAdsDTO xiguaAds = new YoukuAdsDTO();
        xiguaAds.setId(id);
        xiguaAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));
        if (HttpStatus.HTTP_OK == response.getStatus() && Objects.requireNonNull(responseBodyMap).get("code").equals(0)) {
            wifiCallbackDTO.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            xiguaAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            logger.info("adsCallBack {} 回调渠道成功：{} 数据：{}", channelAdsKey, responseBodyMap, xiguaAds);
            wifiCallbackDTO.setCallBackMes(String.valueOf(responseBodyMap.get("code")));
            wifiCallbackDao.insert(wifiCallbackDTO);
            baseServiceInner.updateAdsObject(xiguaAds, xiguaAdsDao);
            logger.info("adsCallBack {} wifiCallbackDTO：{}", channelAdsKey, wifiCallbackDTO);
            return BasicResult.getSuccessResponse(wifiCallbackDTO.getId());
        } else {
            wifiCallbackDTO.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            xiguaAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            logger.error("adsCallBack {} 回调渠道失败：{} 数据：{}", channelAdsKey, responseBodyMap, xiguaAds);
            wifiCallbackDTO.setCallBackMes(responseBodyMap.get("code") + "  " + responseBodyMap.get("failMsg"));
            wifiCallbackDao.insert(wifiCallbackDTO);
            baseServiceInner.updateAdsObject(xiguaAds, xiguaAdsDao);
            logger.info("adsCallBack {} wifiCallbackDTO：{}", channelAdsKey, wifiCallbackDTO);
            return BasicResult.getFailResponse(wifiCallbackDTO.getCallBackMes());
        }*/
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
        logger.info("adsCallBack {} 原始:{}  签名:{}", channelAdsKey, signatureStr, signature);
        return signature;
    }
}
