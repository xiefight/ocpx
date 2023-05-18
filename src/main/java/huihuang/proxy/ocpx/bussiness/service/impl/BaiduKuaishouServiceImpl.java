package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.net.URLDecoder;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouAdsDTO;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouEventTypeEnum;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IKuaishouAdsDao;
import huihuang.proxy.ocpx.bussiness.dao.channel.IBaiduCallbackDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.channel.baidu.BaiduCallbackDTO;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
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
 * baidu-kuaishou
 *
 * @Author: xietao
 * @Date: 2023/5/15 20:28
 */
@Service("bkService")
public class BaiduKuaishouServiceImpl implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(BaiduKuaishouServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IKuaishouAdsDao kuaishouAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private IBaiduCallbackDao baiduCallbackDao;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_KUAISHOU;

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
        KuaishouAdsDTO kuaishouAdsDTO = kuaishouAdsDao.queryKuaishouAdsById(id);
        if (null == kuaishouAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

//        String channelUrl = BaiduPath.CALLBACK_URL;
        String callback = kuaishouAdsDTO.getCallback();
        String channelUrl = URLDecoder.decode(callback, StandardCharsets.UTF_8);
//        String callback = URLEncoder.createQuery().encode(feedbackUrl, StandardCharsets.UTF_8);
        logger.info("adsCallBack 渠道原url：{} 渠道decode回调url：{}", callback, channelUrl);
        channelUrl = channelUrl.replace("a_type={{ATYPE}}", "").replace("a_value={{AVALUE}}", "").replace("&&", "&");
        //回传到渠道
        JSONObject json = new JSONObject();
        json.put("a_type", KuaishouEventTypeEnum.kuaishouBaiduEventTypeMap.get(eventType).getCode());
        json.put("a_value", 0);
        json.put("cb_event_time", eventTimes);
        if (CommonUtil.strEmpty(kuaishouAdsDTO.getOaid())) {
            json.put("cb_oaid", kuaishouAdsDTO.getOaid());
        }
        if (CommonUtil.strEmpty(kuaishouAdsDTO.getOaid())) {
            json.put("cb_oaid_md5", kuaishouAdsDTO.getOaid());
        }
        if (CommonUtil.strEmpty(kuaishouAdsDTO.getIdfa())) {
            json.put("cb_idfa", kuaishouAdsDTO.getIdfa());
        }
        if (CommonUtil.strEmpty(kuaishouAdsDTO.getImei())) {
            json.put("cb_imei", kuaishouAdsDTO.getImei());
        }
        if (CommonUtil.strEmpty(kuaishouAdsDTO.getImei())) {
            json.put("cb_imei_md5", kuaishouAdsDTO.getImei());
        }
        if (CommonUtil.strEmpty(kuaishouAdsDTO.getAndroidId())) {
            json.put("cb_android_id_md5", kuaishouAdsDTO.getAndroidId());
        }
        if (CommonUtil.strEmpty(kuaishouAdsDTO.getIp())) {
            json.put("cb_ip", kuaishouAdsDTO.getIp());
        }


        StringBuilder url = new StringBuilder(channelUrl);
        Set<Map.Entry<String, Object>> entries = json.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            url.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
//        String src = url.substring(0, url.length() - 1);
        logger.info("adsCallBack {} 请求渠道url：{}", channelAdsKey, url);
//        String signature = signature(json);
//        url.append("sign=").append(signature);
        HttpResponse response = HttpRequest.get(url.toString()).execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);

        //保存转化事件回调信息
        BaiduCallbackDTO baiduCallbackDTO = new BaiduCallbackDTO(id, String.valueOf(json.get("a_type")), String.valueOf(json.get("cb_idfa")),
                String.valueOf(json.get("cb_imei")), String.valueOf(json.get("cb_imei_md5")),
                String.valueOf(json.get("cb_android_id_md5")), String.valueOf(json.get("cb_ip")), eventTimes, KuaishouPath.KUAISHOU_ADS_NAME);
        //更新回调状态
        KuaishouAdsDTO kuaishouAds = new KuaishouAdsDTO();
        kuaishouAds.setId(id);
        kuaishouAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));
        if (HttpStatus.HTTP_OK == response.getStatus() && Objects.requireNonNull(responseBodyMap).get("error_code").equals(0)) {
            baiduCallbackDTO.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            kuaishouAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            logger.info("adsCallBack {} 回调渠道成功：{} 数据：{}", channelAdsKey, responseBodyMap, kuaishouAds);
            baiduCallbackDTO.setCallBackMes(String.valueOf(responseBodyMap.get("code")));
            baiduCallbackDao.insert(baiduCallbackDTO);
            baseServiceInner.updateAdsObject(kuaishouAds, kuaishouAdsDao);
            logger.info("adsCallBack {} {}", channelAdsKey, baiduCallbackDTO);
            return BasicResult.getSuccessResponse(baiduCallbackDTO.getId());
        } else {
            baiduCallbackDTO.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            kuaishouAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            logger.error("adsCallBack {} 回调渠道失败：{} 数据：{}", channelAdsKey, responseBodyMap, kuaishouAds);
            baiduCallbackDTO.setCallBackMes(responseBodyMap.get("error_code") + "  " + responseBodyMap.get("error_msg"));
            baiduCallbackDao.insert(baiduCallbackDTO);
            baseServiceInner.updateAdsObject(kuaishouAds, kuaishouAdsDao);
            logger.info("adsCallBack {} {}", channelAdsKey, baiduCallbackDTO);
            return BasicResult.getFailResponse(baiduCallbackDTO.getCallBackMes());
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
        String signatureStr = src + BaiduPath.LTJD_SECRET;
        String signature = DigestUtil.md5Hex(signatureStr).toLowerCase();
        json.put("sign", signature);
        logger.info("adsCallBack {} 原始:{}  签名:{}", channelAdsKey, signatureStr, signature);
        return signature;
    }
}