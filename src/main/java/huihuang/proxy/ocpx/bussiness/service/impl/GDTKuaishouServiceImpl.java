package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.ads.huihui.HuihuiAdsDTO;
import huihuang.proxy.ocpx.ads.huihui.HuihuiEventTypeEnum;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouAdsDTO;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouEventTypeEnum;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IKuaishouAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.bussiness.service.basechannel.GDTChannelFactory;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2GuangdiantongVO;
import huihuang.proxy.ocpx.channel.guangdiantong.GuangdiantongCallbackDTO;
import huihuang.proxy.ocpx.channel.guangdiantong.GuangdiantongEventTypeEnum;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.KuaishouResponse;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.middle.factory.ChannelAdsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("gdtksService")
public class GDTKuaishouServiceImpl extends GDTChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(GDTKuaishouServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    @Qualifier("kuaishouAdsGdtDao")
    private IKuaishouAdsDao kuaishouAdsGdtDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
//    @Autowired
//    private KuaishouPath ksPath;

    String channelAdsKey = Constants.ChannelAdsKey.GDT_KUAISHOU;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public KuaishouResponse adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("actionType")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, eventType);
        //根据id查询对应的点击记录
        KuaishouAdsDTO ksAdsDTO = kuaishouAdsGdtDao.queryKuaishouAdsById(id);
        if (null == ksAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return new KuaishouResponse(1, "未根据找到对应的监测信息", id);
        }

        long currentTime = System.currentTimeMillis();

        Ads2GuangdiantongVO gdtVO = new Ads2GuangdiantongVO();
        gdtVO.setAdsId(id);
        gdtVO.setAdsName(KuaishouPath.KUAISHOU_ADS_NAME);
        String actionType = KuaishouEventTypeEnum.kuaishouGuangdiantongEventTypeMap.get(eventType).getCode();
        gdtVO.setActionType(actionType);
        if (GuangdiantongEventTypeEnum.START_APP.getCode().equals(actionType)) {
            gdtVO.setLengthOfStay("1");
        }
        gdtVO.setHashIdfa(ksAdsDTO.getIdfa());
        gdtVO.setHashImei(ksAdsDTO.getImei());
//        gdtVO.setOaid(ksAdsDTO.getOaid());
        gdtVO.setHashOaid(ksAdsDTO.getOaid());
        gdtVO.setChannelUrl(ksAdsDTO.getCallback());

        Response response = super.baseAdsCallBack(gdtVO);
        GuangdiantongCallbackDTO data = (GuangdiantongCallbackDTO) response.getData();

        //更新回调状态
        KuaishouAdsDTO kuaishouAdsDTO = new KuaishouAdsDTO();
        kuaishouAdsDTO.setId(id);
        kuaishouAdsDTO.setCallBackTime(String.valueOf(currentTime));

        if (response.getCode() == 0) {
            kuaishouAdsDTO.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(kuaishouAdsDTO, kuaishouAdsGdtDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return new KuaishouResponse(0, "", data.getId());
        } else {
            kuaishouAdsDTO.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(kuaishouAdsDTO, kuaishouAdsGdtDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return new KuaishouResponse(1, data.getCallBackMes(), data.getId());
        }

    }

}
