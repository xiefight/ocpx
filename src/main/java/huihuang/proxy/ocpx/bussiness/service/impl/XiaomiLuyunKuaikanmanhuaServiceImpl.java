package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.ads.luyun.LuyunAdsDTO;
import huihuang.proxy.ocpx.ads.luyun.LuyunEventTypeEnum;
import huihuang.proxy.ocpx.ads.luyun.kuaikanmanhua.LuyunKuaikanmanhuaPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.ILuyunKuaikanmanhuaAdsDao;
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

@Service("xlykkmhService")
public class XiaomiLuyunKuaikanmanhuaServiceImpl extends XiaomiChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(XiaomiLuyunKuaikanmanhuaServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private ILuyunKuaikanmanhuaAdsDao luyunKuaikanmanhuaAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private LuyunKuaikanmanhuaPath lykkmhPath;


    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_LUYUN_KUAIKANMANHUA;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("event_type")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, eventType);
        //根据id查询对应的点击记录
            LuyunAdsDTO keepAdsDTO = luyunKuaikanmanhuaAdsDao.queryLuyunKuaikanmanhuaAdsById(id);
        if (null == keepAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }
        Ads2XiaomiVO xiaomiVO = new Ads2XiaomiVO();
        xiaomiVO.setAdsId(id);
        xiaomiVO.setAdsName(lykkmhPath.baseAdsName());

//        if (eventType.equals(LiangdamaoEventTypeEnum.ACTIVE.getCode())) {
//            eventType = eventType + "new";
//        }
        xiaomiVO.setEventType(LuyunEventTypeEnum.luyunXiaomiEventTypeMap.get(eventType).getCode());
        xiaomiVO.setEventTimes(String.valueOf(System.currentTimeMillis()));
        xiaomiVO.setCallBackUrl(keepAdsDTO.getCallback());
        xiaomiVO.setOaid(keepAdsDTO.getOaid());
        xiaomiVO.setImei(keepAdsDTO.getImei());

        Response response = super.baseAdsCallBack(xiaomiVO);
        XiaomiCallbackDTO data = (XiaomiCallbackDTO) response.getData();

        //更新回调状态
        LuyunAdsDTO lyxhsAds = new LuyunAdsDTO();
        lyxhsAds.setId(id);
        lyxhsAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));

        if (response.getCode() == 0) {
            lyxhsAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(lyxhsAds, luyunKuaikanmanhuaAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            lyxhsAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(lyxhsAds, luyunKuaikanmanhuaAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }


}
