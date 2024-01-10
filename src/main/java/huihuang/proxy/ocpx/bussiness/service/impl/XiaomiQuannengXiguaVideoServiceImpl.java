package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.ads.huihui.HuihuiEventTypeEnum;
import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongAdsDTO;
import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongEventTypeEnum;
import huihuang.proxy.ocpx.ads.quannenghudong.xiguavideo.QuannengXiguaVideoPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IQuannengXiguaVideoAdsDao;
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

@Service("xqxvService")
public class XiaomiQuannengXiguaVideoServiceImpl extends XiaomiChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(XiaomiQuannengXiguaVideoServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IQuannengXiguaVideoAdsDao xiguaAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private QuannengXiguaVideoPath quannengXiguaPath;


    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_QUANNENG_XIGUA_VIDEO;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("action_type")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  event:{}", channelAdsKey, id, eventType);
        //根据id查询对应的点击记录
        QuannengHudongAdsDTO quannengXiguaAdsDTO = xiguaAdsDao.queryQuannengXiguaVideoAdsById(id);
        if (null == quannengXiguaAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

//        if (eventType.equals(QuannengHudongEventTypeEnum.ACTIVATE.getCode())) {
//            eventType = eventType + "new";
//        }

        Ads2XiaomiVO xiaomiVO = new Ads2XiaomiVO();
        xiaomiVO.setAdsId(id);
        xiaomiVO.setAdsName(quannengXiguaPath.baseAdsName());
        xiaomiVO.setEventType(QuannengHudongEventTypeEnum.quannengHudongXiaomiEventTypeMap.get(eventType).getCode());
        xiaomiVO.setEventTimes(String.valueOf(System.currentTimeMillis()));
        xiaomiVO.setCallBackUrl(quannengXiguaAdsDTO.getCallback());
        xiaomiVO.setOaid(quannengXiguaAdsDTO.getOaid());
        xiaomiVO.setImei(quannengXiguaAdsDTO.getImei());

        Response response = super.baseAdsCallBack(xiaomiVO);
        XiaomiCallbackDTO data = (XiaomiCallbackDTO) response.getData();

        //更新回调状态
        QuannengHudongAdsDTO quannengXiguaAds = new QuannengHudongAdsDTO();
        quannengXiguaAds.setId(id);
        quannengXiguaAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));

        if (response.getCode() == 0) {
            quannengXiguaAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(quannengXiguaAds, xiguaAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            quannengXiguaAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(quannengXiguaAds, xiguaAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }


}
