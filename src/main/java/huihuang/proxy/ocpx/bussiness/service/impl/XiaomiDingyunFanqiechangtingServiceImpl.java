package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.ads.dingyun.DingyunAdsDTO;
import huihuang.proxy.ocpx.ads.dingyun.DingyunEventTypeEnum;
import huihuang.proxy.ocpx.ads.dingyun.fanqiechangting.DingyunFanqiechangtingPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IDingyunFanqiechangtingAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.bussiness.service.basechannel.XiaomiChannelFactory;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2XiaomiVO;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiCallbackDTO;
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

@Service("xdyfqctService")
public class XiaomiDingyunFanqiechangtingServiceImpl extends XiaomiChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(XiaomiDingyunFanqiechangtingServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IDingyunFanqiechangtingAdsDao dyfqctAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private DingyunFanqiechangtingPath dyfqctPath;


    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_DINGYUN_FANQIECHANGTING;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("actionType")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  event:{}", channelAdsKey, id, eventType);
        //根据id查询对应的点击记录
        DingyunAdsDTO dyAdsDTO = dyfqctAdsDao.queryDingyunFanqiechangtingAdsById(id);
        if (null == dyAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

//        if (eventType.equals(DingyunEventTypeEnum.ACTIVATE.getCode())) {
//            eventType = eventType + "new";
//        }

        Ads2XiaomiVO xiaomiVO = new Ads2XiaomiVO();
        xiaomiVO.setAdsId(id);
        xiaomiVO.setAdsName(dyfqctPath.baseAdsName());
        xiaomiVO.setEventType(DingyunEventTypeEnum.dingyunXiaomiEventTypeMap.get(eventType).getCode());
        xiaomiVO.setEventTimes(String.valueOf(System.currentTimeMillis()));
        xiaomiVO.setCallBackUrl(dyAdsDTO.getCallback());
        xiaomiVO.setOaid(dyAdsDTO.getOaid());
        xiaomiVO.setImei(dyAdsDTO.getImeiMd5());

        Response response = super.baseAdsCallBack(xiaomiVO);
        XiaomiCallbackDTO data = (XiaomiCallbackDTO) response.getData();

        //更新回调状态
        DingyunAdsDTO dyAds = new DingyunAdsDTO();
        dyAds.setId(id);
        dyAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));

        if (response.getCode() == 0) {
            dyAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(dyAds, dyfqctAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            dyAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(dyAds, dyfqctAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }


}
