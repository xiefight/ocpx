package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.ads.luyun.LuyunAdsDTO;
import huihuang.proxy.ocpx.ads.luyun.LuyunEventTypeEnum;
import huihuang.proxy.ocpx.ads.luyun.keep.KeepPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IKeepAdsDao;
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

import java.util.Map;

@Service("hkeepService")
public class HuaweiKeepServiceImpl extends HuaweiChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(HuaweiKeepServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IKeepAdsDao keepAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private KeepPath keepPath;

    String channelAdsKey = Constants.ChannelAdsKey.HUAWEI_KEEP;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("event_type")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, eventType);

        //根据id查询对应的点击记录
        LuyunAdsDTO keepAdsDTO = keepAdsDao.queryKeepAdsById(id);
        if (null == keepAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        long currentTime = System.currentTimeMillis();
        Ads2HuaweiVO huaweiVO = new Ads2HuaweiVO();
        huaweiVO.setAdsId(id);
//        huaweiVO.setAdsName(keepPath.baseAdsName());
        huaweiVO.setAdsName(keepPath.baseAdsName());
        huaweiVO.setCallbackUrl(keepAdsDTO.getCallback());

        huaweiVO.setEventType(eventType);
        huaweiVO.setTimestamp(String.valueOf(currentTime));
        huaweiVO.setCampaignId(getContentFromExtra(keepAdsDTO, HuaweiParamEnum.CAMPAIGN_ID.getParam(), null));
        huaweiVO.setContentId(getContentFromExtra(keepAdsDTO, HuaweiParamEnum.CONTENT_ID.getParam(), null));
        huaweiVO.setTrackingEnabled(getContentFromExtra(keepAdsDTO, HuaweiParamEnum.TRACKING_ENABLED.getParam(), "1"));
        huaweiVO.setConversionTime(String.valueOf(currentTime / 1000));
        huaweiVO.setConversionType(LuyunEventTypeEnum.luyunHuaweiEventTypeMap.get(eventType).getCode());
        huaweiVO.setOaid(keepAdsDTO.getOaid());

        if (HuaweiPath.HW_KEEP_ACCOUNT_01.equals(keepAdsDTO.getAccountId())) {
            huaweiVO.setSecret(HuaweiPath.KEEP_SECRET_01);
        } else if (HuaweiPath.HW_KEEP_ACCOUNT_02.equals(keepAdsDTO.getAccountId())) {
            huaweiVO.setSecret(HuaweiPath.KEEP_SECRET_02);
        } else if (HuaweiPath.HW_KEEP_ACCOUNT_03.equals(keepAdsDTO.getAccountId())) {
            huaweiVO.setSecret(HuaweiPath.KEEP_SECRET_03);
        }
        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, huaweiVO);

        Response response = super.baseAdsCallBack(huaweiVO);
        HuaweiCallbackDTO data = (HuaweiCallbackDTO) response.getData();

        //更新回调状态
        LuyunAdsDTO keepAds = new LuyunAdsDTO();
        keepAds.setId(id);
        keepAds.setCallBackTime(String.valueOf(currentTime));

        if (response.getCode() == 0) {
            keepAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(keepAds, keepAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            keepAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(keepAds, keepAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }

}
