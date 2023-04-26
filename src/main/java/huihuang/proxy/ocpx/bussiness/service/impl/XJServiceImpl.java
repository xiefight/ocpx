package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.net.URLDecoder;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.litianjingdong.LTJDAdsDTO;
import huihuang.proxy.ocpx.ads.litianjingdong.LTJDEventTypeEnum;
import huihuang.proxy.ocpx.bussiness.dao.ads.ILtjdAdsDao;
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
import huihuang.proxy.ocpx.util.CommonUtil;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @Description: xiaomi-ltjd
 * @Author: xietao
 * @Date: 2023-04-24 17:42
 **/
@Service("xjService")
public class XJServiceImpl implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(XJServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private ILtjdAdsDao ltjdAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private IXiaomiCallbackDao xiaomiCallbackDao;

    @Override
    public IChannelAds channelAds() {
        String channelAdsKey = "xiaomi-ltjd";
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response clickReport(Map<String, String[]> parameterMap) throws Exception {
        IChannelAds channelAds = channelAds();
        return channelAds.clickReport(parameterMap);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        logger.info("adsCallBack  开始回调渠道  id:{}  parameterMap.size:{}",id,parameterMap.size());
        //转化类型字段
        String eventType = parameterMap.get("event_type")[0];
        String eventTimes = String.valueOf(System.currentTimeMillis());

        //根据id查询对应的点击记录
        LTJDAdsDTO ltjdAdsDTO = ltjdAdsDao.queryLtjdAdsById(id);
        String feedbackUrl = ltjdAdsDTO.getCallback_url();
        String callbackUrl = URLDecoder.decode(feedbackUrl, StandardCharsets.UTF_8);
        logger.info("adsCallBack  原url:{} decodeUrl:{}",feedbackUrl,callbackUrl);

        String[] split = callbackUrl.split("\\?");
        String channelUrl = split[0];
        Map<String, String> paramMap = CommonUtil.convertGetParamToMap(split[1]);
        String callback = URLEncoder.createQuery().encode(paramMap.get("callback"), StandardCharsets.UTF_8);
        logger.info("adsCallBack  渠道回调url：{}  参数：{}  只对callback进行encode：{}",channelUrl,paramMap,callback);
        //回传到渠道
        JSONObject json = new JSONObject();
        json.put("callback", callback);
        json.put("conv_time", eventTimes);
        json.put("eventType", LTJDEventTypeEnum.eventTypeMap.get(eventType).getCode());
        if (Objects.isNull(ltjdAdsDTO.getOaid())) {
            json.put("imei", ltjdAdsDTO.getImei_md5());
        } else {
            json.put("oaid", ltjdAdsDTO.getOaid());
        }
        String signature = signature(json);

        HttpResponse response = HttpRequest.post(channelUrl)
                .timeout(20000).form(json)
                .header("content-type", "application/json")
                .execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);

        //保存转化事件回调信息
        XiaomiCallbackDTO xiaomiCallbackDTO = new XiaomiCallbackDTO(id, feedbackUrl,
                String.valueOf(json.get("eventType")), eventTimes, ltjdAdsDTO.getImei_md5(), ltjdAdsDTO.getOaid(), signature);
        //更新回调状态
        LTJDAdsDTO ltjdAds = new LTJDAdsDTO();
        ltjdAds.setId(id);
        ltjdAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));
        if (HttpStatus.HTTP_OK == response.getStatus() && Objects.requireNonNull(responseBodyMap).get("code").equals("0")) {
            xiaomiCallbackDTO.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            ltjdAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            logger.info("adsCallBack  回调渠道成功：{} 数据：{}",responseBodyMap,ltjdAds);
        } else {
            xiaomiCallbackDTO.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            ltjdAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            logger.error("adsCallBack  回调渠道失败：{} 数据：{}",responseBodyMap,ltjdAds);
        }
        assert responseBodyMap != null;
        xiaomiCallbackDTO.setCallBackMes((String) responseBodyMap.get("code"));
        xiaomiCallbackDao.insert(xiaomiCallbackDTO);
        baseServiceInner.updateAdsObject(ltjdAds, ltjdAdsDao);

        return BasicResult.getSuccessResponse();
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
        String signatureStr = src + XiaomiPath.TLJD_SECRET;
        String signature = DigestUtil.md5Hex(signatureStr).toLowerCase();
        json.put("sign", signature);
        logger.info("adsCallBack  原始:{}  签名:{}", signatureStr, signature);
        return signature;
    }
}
