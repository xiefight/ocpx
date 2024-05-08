package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.ads.huihui.HuihuiAdsDTO;
import huihuang.proxy.ocpx.ads.huihui.HuihuiEventTypeEnum;
import huihuang.proxy.ocpx.ads.huihui.xianyu.HuihuiXianyuPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuiXianyuAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.bussiness.service.basechannel.GDTChannelFactory;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2GuangdiantongVO;
import huihuang.proxy.ocpx.channel.guangdiantong.GuangdiantongCallbackDTO;
import huihuang.proxy.ocpx.channel.guangdiantong.GuangdiantongEventTypeEnum;
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

@Service("gdthhxyService")
public class GDTHuihuiXianyuServiceImpl extends GDTChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(GDTHuihuiXianyuServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IHuihuiXianyuAdsDao hhxyAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private HuihuiXianyuPath hhxyPath;

    String channelAdsKey = Constants.ChannelAdsKey.GDT_HUIHUI_XIANYU;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("conv_action")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, eventType);
        //根据id查询对应的点击记录
        HuihuiAdsDTO hhydAdsDTO = hhxyAdsDao.queryXianyuAdsById(id);
        if (null == hhydAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        long currentTime = System.currentTimeMillis();

        Ads2GuangdiantongVO gdtVO = new Ads2GuangdiantongVO();
        gdtVO.setAdsId(id);
        gdtVO.setAdsName(hhxyPath.baseAdsName());
        String actionType = HuihuiEventTypeEnum.huihuiGDTEventTypeMap.get(eventType).getCode();
        gdtVO.setActionType(actionType);
        if (GuangdiantongEventTypeEnum.START_APP.getCode().equals(actionType)) {
            gdtVO.setLengthOfStay("1");
        }
        gdtVO.setHashIdfa(hhydAdsDTO.getIdfa_md5());
        gdtVO.setHashImei(hhydAdsDTO.getImei());
        gdtVO.setOaid(hhydAdsDTO.getOaid());
        gdtVO.setHashOaid(hhydAdsDTO.getOaid_md5());
        gdtVO.setChannelUrl(hhydAdsDTO.getCallback());

        Response response = super.baseAdsCallBack(gdtVO);
        GuangdiantongCallbackDTO data = (GuangdiantongCallbackDTO) response.getData();

        //更新回调状态
        HuihuiAdsDTO hhxyAds = new HuihuiAdsDTO();
        hhxyAds.setId(id);
        hhxyAds.setCallBackTime(String.valueOf(currentTime));

        if (response.getCode() == 0) {
            hhxyAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(hhxyAds, hhxyAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            hhxyAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(hhxyAds, hhxyAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }

    }

}
