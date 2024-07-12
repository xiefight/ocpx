package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangFengmangEventTypeEnum;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianAdsDTO;
import huihuang.proxy.ocpx.ads.huihuangmingtian.ads.HuihuangFengmangXianyuPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangFengmangXianyuAdsDao;
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

@Service("gdthhztxyService")
public class GdtHuihuangZhitouXianyuServiceImpl extends GDTChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(GdtHuihuangZhitouXianyuServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IHuihuangFengmangXianyuAdsDao hhxyffmAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private HuihuangFengmangXianyuPath hhxyfmPath;

    String channelAdsKey = Constants.ChannelAdsKey.GDT_HUIHUANG_XIANYU;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("event_type")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, eventType);
        //根据id查询对应的点击记录
        HuihuangmingtianAdsDTO hhxyfmAdsDTO = hhxyffmAdsDao.queryHuihuangFengmangXianyuAdsById(id);
        if (null == hhxyfmAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        long currentTime = System.currentTimeMillis();

        Ads2GuangdiantongVO gdtVO = new Ads2GuangdiantongVO();
        gdtVO.setAdsId(id);
        gdtVO.setAdsName(hhxyfmPath.baseAdsName());
        String actionType = HuihuangFengmangEventTypeEnum.huihuangmingtianGdtEventTypeMap.get(eventType).getCode();
        gdtVO.setActionType(actionType);
        if (GuangdiantongEventTypeEnum.START_APP.getCode().equals(actionType)) {
            gdtVO.setLengthOfStay("1");
        }
        gdtVO.setHashIdfa(hhxyfmAdsDTO.getIdfaMd5());
        gdtVO.setHashImei(hhxyfmAdsDTO.getImeiMd5());
        gdtVO.setOaid(hhxyfmAdsDTO.getOaid());
        gdtVO.setHashOaid(hhxyfmAdsDTO.getOaidMd5());
        gdtVO.setChannelUrl(hhxyfmAdsDTO.getCallbackUrl());

        Response response = super.baseAdsCallBack(gdtVO);
        GuangdiantongCallbackDTO data = (GuangdiantongCallbackDTO) response.getData();

        //更新回调状态
        HuihuangmingtianAdsDTO hhxyfmAds = new HuihuangmingtianAdsDTO();
        hhxyfmAds.setId(id);
        hhxyfmAds.setCallBackTime(String.valueOf(currentTime));

        if (response.getCode() == 0) {
            hhxyfmAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(hhxyfmAds, hhxyffmAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            hhxyfmAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(hhxyfmAds, hhxyffmAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }

    }

}
