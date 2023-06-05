package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouAdsDTO;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouEventTypeEnum;
import huihuang.proxy.ocpx.ads.litianjingdong.LTJDPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IKuaishouAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.bussiness.service.basechannel.HuaweiChannelFactory;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2HuaweiVO;
import huihuang.proxy.ocpx.channel.huawei.HuaweiCallbackDTO;
import huihuang.proxy.ocpx.channel.huawei.HuaweiParamEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiPath;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.middle.factory.ChannelAdsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Map;

/**
 * huawei-fanqie
 * @Author: xietao
 * @Date: 2023/5/28 20:05
 */
@Service("hfService")
public class HuaweiFanqieServiceImpl extends HuaweiChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(HuaweiFanqieServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IKuaishouAdsDao kuaishouAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private LTJDPath ltjdPath;

    String channelAdsKey = Constants.ChannelAdsKey.HUAWEI_LTJD;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        logger.info("adsCallBack {} 开始回调渠道  id:{}  parameterMap.size:{}", channelAdsKey, id, parameterMap.size());

        //根据id查询对应的点击记录
        KuaishouAdsDTO kuaishouAdsDTO = kuaishouAdsDao.queryKuaishouAdsById(id);
        if (null == kuaishouAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        long currentTime = System.currentTimeMillis();
        Ads2HuaweiVO huaweiVO = new Ads2HuaweiVO();
        huaweiVO.setAdsId(id);
        huaweiVO.setAdsName(ltjdPath.baseAdsName());
        huaweiVO.setCallbackUrl(kuaishouAdsDTO.getCallback());

        huaweiVO.setEventType(parameterMap.get("actionType")[0]);
        huaweiVO.setTimestamp(String.valueOf(currentTime));
        huaweiVO.setCampaignId(kuaishouAdsDTO.getCampaignId());
        huaweiVO.setContentId(getContentFromExtra(kuaishouAdsDTO, HuaweiParamEnum.CONTENT_ID.getParam(), null));
        huaweiVO.setTrackingEnabled(getContentFromExtra(kuaishouAdsDTO, HuaweiParamEnum.TRACKING_ENABLED.getParam(), "1"));
        huaweiVO.setConversionTime(String.valueOf(currentTime / 1000));
        huaweiVO.setConversionType(KuaishouEventTypeEnum.kuaishouHuaweiEventTypeMap.get(parameterMap.get("actionType")[0]).getCode());
        huaweiVO.setOaid(kuaishouAdsDTO.getOaid());
        huaweiVO.setSecret(HuaweiPath.FANQIE_SECRET);
        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, huaweiVO);

        Response response = super.baseAdsCallBack(huaweiVO);
        HuaweiCallbackDTO data = (HuaweiCallbackDTO) response.getData();

        //更新回调状态
        KuaishouAdsDTO kuaishouAds = new KuaishouAdsDTO();
        kuaishouAds.setId(id);
        kuaishouAds.setCallBackTime(String.valueOf(currentTime));

        if (response.getCode() == 0) {
            kuaishouAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(kuaishouAds, kuaishouAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            kuaishouAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(kuaishouAds, kuaishouAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }

    //计算签名

    /**
     * 计算请求头中的Authorization
     *
     * @param body 请求体json
     * @param key  密钥
     * @return Authorization 鉴权头
     */
    public static String buildAuthorizationHeader(String body, String key) {
// 广告主请求头header中的Authorization
        final String authorizationFormat = "Digest validTime=\"{0}\", response=\"{1}\"";
        String authorization = null;
        try {
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKey secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
            mac.init(secretKey);
            byte[] signatureBytes = mac.doFinal(bodyBytes);
            final String timestamp = String.valueOf(System.currentTimeMillis());
            final String signature = (signatureBytes == null) ? null : HexUtil.encodeHexStr(signatureBytes);
            authorization = MessageFormat.format(authorizationFormat, timestamp, signature)
            ;
        } catch (Exception e) {
            System.err.println("build Authorization Header failed！");
            e.printStackTrace();
        }
        System.out.println("generate Authorization Header: " + authorization);
        return authorization;
    }


    /**
     * 从extra中获取指定字段值
     *
     * @param kuaishouAdsDTO 实体数据
     * @param contentKey     指定字段key
     * @param defaultVal     默认值
     * @return 指定字段值
     */
    private String getContentFromExtra(KuaishouAdsDTO kuaishouAdsDTO, String contentKey, String defaultVal) {
        String contentValue = defaultVal;
        String extra = kuaishouAdsDTO.getExtra();
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
    }
}
