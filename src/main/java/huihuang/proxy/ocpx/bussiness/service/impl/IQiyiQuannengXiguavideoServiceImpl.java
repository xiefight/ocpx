package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongAdsDTO;
import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongEventTypeEnum;
import huihuang.proxy.ocpx.ads.quannenghudong.xiguavideo.QuannengXiguaVideoPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IQuannengXiguaVideoAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.bussiness.service.basechannel.IQiyiChannelFactory;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2IQiyiVO;
import huihuang.proxy.ocpx.channel.iqiyi.IQiyiCallbackDTO;
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

@Service("iqnxgService")
public class IQiyiQuannengXiguavideoServiceImpl extends IQiyiChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(IQiyiQuannengXiguavideoServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IQuannengXiguaVideoAdsDao qnxgAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private QuannengXiguaVideoPath qnXiguavideoPath;

    String channelAdsKey = Constants.ChannelAdsKey.IQIYI_QUANNENG_XIGUAVIDEO;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("action_type")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, eventType);
        //根据id查询对应的点击记录
        QuannengHudongAdsDTO dyxgAdsDTO = qnxgAdsDao.queryQuannengXiguaVideoAdsById(id);
        if (null == dyxgAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        long currentTime = System.currentTimeMillis();
        Ads2IQiyiVO iQiyiVO = new Ads2IQiyiVO();
        iQiyiVO.setAdsId(id);
        iQiyiVO.setEventType(QuannengHudongEventTypeEnum.quannengHudongIQiyiEventTypeMap.get(eventType).getCode());
        iQiyiVO.setEventTime(Integer.valueOf(String.valueOf(currentTime / 1000)));
        iQiyiVO.setChannelUrl(dyxgAdsDTO.getCallback());
        iQiyiVO.setAdsName(qnXiguavideoPath.baseAdsName());

        Response response = super.baseAdsCallBack(iQiyiVO);
        IQiyiCallbackDTO data = (IQiyiCallbackDTO) response.getData();

        //更新回调状态
        QuannengHudongAdsDTO quannengAds = new QuannengHudongAdsDTO();
        quannengAds.setId(id);
        quannengAds.setCallBackTime(String.valueOf(currentTime));

        if (response.getCode() == 0) {
            quannengAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(quannengAds, qnxgAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            quannengAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(quannengAds, qnxgAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }

    }

}
