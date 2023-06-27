package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.ads.fanqie.FanqiePath;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoAdsDTO;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoEventTypeEnum;
import huihuang.proxy.ocpx.bussiness.dao.ads.IFanqieAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.bussiness.service.basechannel.WifiChannelFactory;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2WifiVO;
import huihuang.proxy.ocpx.channel.wifi.WifiCallbackDTO;
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
 * 
 * @Author: xietao
 * @Date: 2023/6/19 11:56
 */ 
@Service("wfService")
public class WifiFanqieServiceImpl extends WifiChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(WifiFanqieServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IFanqieAdsDao fanqieAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private FanqiePath fanqiePath;

    String channelAdsKey = Constants.ChannelAdsKey.WIFI_FANQIE;

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

        //转化类型字段
        String eventType = parameterMap.get("event_type")[0];
        String eventTimes = String.valueOf(System.currentTimeMillis() / 1000);

        Ads2WifiVO wifiVO = new Ads2WifiVO();
        wifiVO.setAdsId(id);
        wifiVO.setAdsName(fanqiePath.baseAdsName());
        wifiVO.setClientId(FanqiePath.CLIENT_ID);
        wifiVO.setTs(eventTimes);
        wifiVO.setEventType(LiangdamaoEventTypeEnum.liangdamaoWifiEventTypeMap.get(eventType).getCode());
        wifiVO.setExtra(fanqieAdsDTO.getExtra());

        Response response = baseAdsCallBack(wifiVO);
        WifiCallbackDTO data = (WifiCallbackDTO) response.getData();

        //更新回调状态
        LiangdamaoAdsDTO fanqieAds = new LiangdamaoAdsDTO();
        fanqieAds.setId(id);
        fanqieAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));

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
