package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongAdsDTO;
import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongEventTypeEnum;
import huihuang.proxy.ocpx.ads.quannenghudong.xiaohongshu.QuannengXiaohongshuPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IQuannengXiaohongshuAdsDao;
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

@Service("xqxhsService")
public class XiaomiQuannengXiaohongshuServiceImpl extends XiaomiChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(XiaomiQuannengXiaohongshuServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IQuannengXiaohongshuAdsDao xiaohongshuAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private QuannengXiaohongshuPath quannengXiaohongshuPath;


    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_QUANNENG_XIAOHONGSHU;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("action_type")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  event:{}", channelAdsKey, id, eventType);
        //根据id查询对应的点击记录
        QuannengHudongAdsDTO quannengXiguaAdsDTO = xiaohongshuAdsDao.queryQuannengXiaohongshuAdsById(id);
        if (null == quannengXiguaAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

//        if (eventType.equals(QuannengHudongEventTypeEnum.ACTIVATE.getCode())) {
//            eventType = eventType + "new";
//        }

        //只接收小红书的注册事件
        if(!eventType.equals(QuannengHudongEventTypeEnum.REGISTER.getCode())){
            logger.error("小红书只支持注册事件 eventType：{}",eventType);
            return BasicResult.getFailResponse("只支持注册事件");
        }
        //小红书的注册事件对应上小米的激活事件
        eventType = QuannengHudongEventTypeEnum.ACTIVATE.getCode();

        Ads2XiaomiVO xiaomiVO = new Ads2XiaomiVO();
        xiaomiVO.setAdsId(id);
        xiaomiVO.setAdsName(quannengXiaohongshuPath.baseAdsName());
        xiaomiVO.setEventType(QuannengHudongEventTypeEnum.quannengHudongXiaomiEventTypeMap.get(eventType).getCode());
        xiaomiVO.setEventTimes(String.valueOf(System.currentTimeMillis()));
        xiaomiVO.setCallBackUrl(quannengXiguaAdsDTO.getCallback());
        xiaomiVO.setOaid(quannengXiguaAdsDTO.getOaid());
        xiaomiVO.setImei(quannengXiguaAdsDTO.getImei());

        Response response = super.baseAdsCallBack(xiaomiVO);
        XiaomiCallbackDTO data = (XiaomiCallbackDTO) response.getData();

        //更新回调状态
        QuannengHudongAdsDTO quannengXiaohongshuAds = new QuannengHudongAdsDTO();
        quannengXiaohongshuAds.setId(id);
        quannengXiaohongshuAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));

        if (response.getCode() == 0) {
            quannengXiaohongshuAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(quannengXiaohongshuAds, xiaohongshuAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            quannengXiaohongshuAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(quannengXiaohongshuAds, xiaohongshuAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }


}
