package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.ads.fanqie.FanqiePath;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoAdsDTO;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoEventTypeEnum;
import huihuang.proxy.ocpx.bussiness.dao.ads.IFanqieAdsDao;
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

/**
 * huawei-fanqie
 *
 * @Author: xietao
 * @Date: 2023/5/28 20:05
 */
@Service("hfService")
public class HuaweiFanqieServiceImpl extends HuaweiChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(HuaweiFanqieServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IFanqieAdsDao fanqieAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private FanqiePath fanqiePath;

    String channelAdsKey = Constants.ChannelAdsKey.HUAWEI_FANQIE;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        logger.info("adsCallBack {} 开始回调渠道  id:{}  parameterMap.size:{}", channelAdsKey, id, parameterMap.size());

        //根据id查询对应的点击记录
        LiangdamaoAdsDTO fanqieAdsDTO = fanqieAdsDao.queryFanqieAdsById(id);
        if (null == fanqieAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        long currentTime = System.currentTimeMillis();
        Ads2HuaweiVO huaweiVO = new Ads2HuaweiVO();
        huaweiVO.setAdsId(id);
        huaweiVO.setCallbackUrl(fanqieAdsDTO.getCallback_url());

        huaweiVO.setEventType(parameterMap.get("event_type")[0]);
        huaweiVO.setTimestamp(String.valueOf(currentTime));
        huaweiVO.setCampaignId(getContentFromExtra(fanqieAdsDTO, HuaweiParamEnum.CAMPAIGN_ID.getParam(), null));
        huaweiVO.setContentId(getContentFromExtra(fanqieAdsDTO, HuaweiParamEnum.CONTENT_ID.getParam(), null));
        huaweiVO.setTrackingEnabled(getContentFromExtra(fanqieAdsDTO, HuaweiParamEnum.TRACKING_ENABLED.getParam(), "1"));
        huaweiVO.setConversionTime(String.valueOf(currentTime / 1000));
        huaweiVO.setConversionType(LiangdamaoEventTypeEnum.liangdamaoHuaweiEventTypeMap.get(parameterMap.get("event_type")[0]).getCode());
        huaweiVO.setOaid(fanqieAdsDTO.getOaid());
        if ("186".equals(fanqieAdsDTO.getTp_adv_id())){
            huaweiVO.setSecret(HuaweiPath.FANQIE_SECRET);
            huaweiVO.setAdsName(fanqiePath.baseAdsName());
        }else if ("318".equals(fanqieAdsDTO.getTp_adv_id())){
            huaweiVO.setSecret(HuaweiPath.FANQIE_CHANGTING_SECRET);
            huaweiVO.setAdsName("fanqiechangting");
        }
        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, huaweiVO);

        Response response = super.baseAdsCallBack(huaweiVO);
        HuaweiCallbackDTO data = (HuaweiCallbackDTO) response.getData();

        //更新回调状态
        LiangdamaoAdsDTO fanqieAds = new LiangdamaoAdsDTO();
        fanqieAds.setId(id);
        fanqieAds.setCallBackTime(String.valueOf(currentTime));

        if (response.getCode() == 0) {
            fanqieAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(fanqieAds, fanqieAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            fanqieAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(fanqieAds, fanqieAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }

}
