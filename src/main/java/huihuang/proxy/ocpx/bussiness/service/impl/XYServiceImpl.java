package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.youku.YoukuAdsDTO;
import huihuang.proxy.ocpx.ads.youku.YoukuEventTypeEnum;
import huihuang.proxy.ocpx.ads.youku.YoukuPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IYoukuAdsDao;
import huihuang.proxy.ocpx.bussiness.dao.channel.IXiaomiCallbackDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiCallbackDTO;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiPath;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.middle.factory.ChannelAdsFactory;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * xiaomi-youku
 *
 * @Author: xietao
 * @Date: 2023/4/27 10:47
 */
@Service("xyService")
public class XYServiceImpl implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(XYServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IYoukuAdsDao youkuAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private IXiaomiCallbackDao xiaomiCallbackDao;

    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_YOUKU;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        logger.info("adsCallBack {} 开始回调渠道  id:{}  parameterMap.size:{}", channelAdsKey, id, parameterMap.size());
        //转化类型字段
        String eventType = parameterMap.get("event_type")[0];
        String eventTimes = String.valueOf(System.currentTimeMillis());

        //根据id查询对应的点击记录
        YoukuAdsDTO youkuAdsDTO = youkuAdsDao.queryYoukuAdsById(id);
        if (null == youkuAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        String channelUrl = XiaomiPath.CALLBACK_URL;
        String feedbackUrl = youkuAdsDTO.getCallback_url();
//        String callback = URLEncoder.createQuery().encode(feedbackUrl, StandardCharsets.UTF_8);
//        logger.info("adsCallBack  渠道回调url：{}  只对callback进行encode：{}", channelUrl + feedbackUrl, feedbackUrl);
        //回传到渠道
        JSONObject json = new JSONObject();
        json.put("callback", feedbackUrl);
        json.put("conv_time", eventTimes);
        json.put("convType", YoukuEventTypeEnum.eventTypeMap.get(eventType).getCode());
        if (Objects.isNull(youkuAdsDTO.getOaid())) {
            json.put("imei", youkuAdsDTO.getImei_md5());
        } else {
            json.put("oaid", youkuAdsDTO.getOaid());
        }

        StringBuilder url = new StringBuilder(channelUrl);
        Set<Map.Entry<String, Object>> entries = json.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            url.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        String src = url.substring(0, url.length() - 1);
        String signature = signature(json);
//        url.append("sign=").append(signature);
        HttpResponse response = HttpRequest.get(src).execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);

        //保存转化事件回调信息
        XiaomiCallbackDTO xiaomiCallbackDTO = new XiaomiCallbackDTO(id, feedbackUrl,
                String.valueOf(json.get("convType")), eventTimes, youkuAdsDTO.getImei_md5(), youkuAdsDTO.getOaid(), signature, YoukuPath.YOUKU_ADS_NAME);
        //更新回调状态
        YoukuAdsDTO youkuAds = new YoukuAdsDTO();
        youkuAds.setId(id);
        youkuAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));
        if (HttpStatus.HTTP_OK == response.getStatus() && Objects.requireNonNull(responseBodyMap).get("code").equals(0)) {
            xiaomiCallbackDTO.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            youkuAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            logger.info("adsCallBack {} 回调渠道成功：{} 数据：{}", channelAdsKey, responseBodyMap, youkuAds);
            xiaomiCallbackDTO.setCallBackMes(String.valueOf(responseBodyMap.get("code")));
            xiaomiCallbackDao.insert(xiaomiCallbackDTO);
            baseServiceInner.updateAdsObject(youkuAds, youkuAdsDao);
            logger.info("adsCallBack {} xiaomiCallbackDTO：{}", channelAdsKey, xiaomiCallbackDTO);
            return BasicResult.getSuccessResponse(xiaomiCallbackDTO.getId());
        } else {
            xiaomiCallbackDTO.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            youkuAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            logger.error("adsCallBack {} 回调渠道失败：{} 数据：{}", channelAdsKey, responseBodyMap, youkuAds);
            xiaomiCallbackDTO.setCallBackMes(responseBodyMap.get("code") + "  " + responseBodyMap.get("failMsg"));
            xiaomiCallbackDao.insert(xiaomiCallbackDTO);
            baseServiceInner.updateAdsObject(youkuAds, youkuAdsDao);
            logger.info("adsCallBack {} xiaomiCallbackDTO：{}", channelAdsKey, xiaomiCallbackDTO);
            return BasicResult.getFailResponse(xiaomiCallbackDTO.getCallBackMes());
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
        String signatureStr = src + XiaomiPath.YOUKU_SECRET;
        String signature = DigestUtil.md5Hex(signatureStr).toLowerCase();
        json.put("sign", signature);
        logger.info("adsCallBack {} 原始:{}  签名:{}", channelAdsKey, signatureStr, signature);
        return signature;
    }
}
