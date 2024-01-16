package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangFengmangEventTypeEnum;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianAdsDTO;
import huihuang.proxy.ocpx.ads.huihuangmingtian.ads.HuihuangFanqiePath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangFanqieAdsDao;
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

@Service("ihhfqService")
public class IQiyiHuihuangFanqieServiceImpl extends IQiyiChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(IQiyiHuihuangFanqieServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IHuihuangFanqieAdsDao hhfqAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private HuihuangFanqiePath huihuangFanqiePath;

    String channelAdsKey = Constants.ChannelAdsKey.IQIYI_HUIHUANG_FANQIE;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("event_type")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, eventType);
        //根据id查询对应的点击记录
        HuihuangmingtianAdsDTO hhfqAdsDTO = hhfqAdsDao.queryHuihuangFanqieAdsById(id);
        if (null == hhfqAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        long currentTime = System.currentTimeMillis();
        Ads2IQiyiVO iQiyiVO = new Ads2IQiyiVO();
        iQiyiVO.setAdsId(id);
        iQiyiVO.setEventType(HuihuangFengmangEventTypeEnum.huihuangmingtianIQiyiEventTypeMap.get(eventType).getCode());
        iQiyiVO.setEventTime(Integer.valueOf(String.valueOf(currentTime / 1000)));
        iQiyiVO.setChannelUrl(hhfqAdsDTO.getCallbackUrl());
        iQiyiVO.setAdsName(huihuangFanqiePath.baseAdsName());

        Response response = super.baseAdsCallBack(iQiyiVO);
        IQiyiCallbackDTO data = (IQiyiCallbackDTO) response.getData();

        //更新回调状态
        HuihuangmingtianAdsDTO huihuangmingtianAds = new HuihuangmingtianAdsDTO();
        huihuangmingtianAds.setId(id);
        huihuangmingtianAds.setCallBackTime(String.valueOf(currentTime));

        if (response.getCode() == 0) {
            huihuangmingtianAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(huihuangmingtianAds, hhfqAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            huihuangmingtianAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(huihuangmingtianAds, hhfqAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }

    }

}
