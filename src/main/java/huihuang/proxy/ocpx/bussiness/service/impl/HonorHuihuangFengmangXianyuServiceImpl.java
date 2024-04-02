package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangFengmangEventTypeEnum;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianAdsDTO;
import huihuang.proxy.ocpx.ads.huihuangmingtian.ads.HuihuangFengmangXianyuPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangFengmangXianyuAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.bussiness.service.basechannel.HonorChannelFactory;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2HonorVO;
import huihuang.proxy.ocpx.channel.honor.HonorCallbackDTO;
import huihuang.proxy.ocpx.channel.honor.HonorParamEnum;
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

@Service("honorhhxyService")
public class HonorHuihuangFengmangXianyuServiceImpl extends HonorChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(HonorHuihuangFengmangXianyuServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private IHuihuangFengmangXianyuAdsDao hhxyAdsDao;
    @Autowired
    private HuihuangFengmangXianyuPath hhxyPath;

    String channelAdsKey = Constants.ChannelAdsKey.HONOR_HUIHUANG_XIANYU;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, parameterMap.get("event_type")[0]);

        //根据id查询对应的点击记录
        HuihuangmingtianAdsDTO huihuangmingtianAdsDTO = hhxyAdsDao.queryHuihuangFengmangXianyuAdsById(id);
        if (null == huihuangmingtianAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        long currentTime = System.currentTimeMillis();
        Ads2HonorVO honorVO = new Ads2HonorVO();
        honorVO.setAdsId(id);
        honorVO.setAdsName(hhxyPath.baseAdsName());
//        honorVO.setCallbackUrl(huihuangmingtianAdsDTO.getCallbackUrl());

//        honorVO.setTimestamp(String.valueOf(currentTime));
        honorVO.setConversionTime(String.valueOf(currentTime));
        honorVO.setConversionId(HuihuangFengmangEventTypeEnum.huihuangmingtianHonorEventTypeMap.get(parameterMap.get("event_type")[0]).getCode());
        honorVO.setTrackId(getContentFromExtra(huihuangmingtianAdsDTO, HonorParamEnum.TRACK_ID.getParam(),
                getContentFromExtra(huihuangmingtianAdsDTO, "trackId", "")));
        honorVO.setAdvertiserId(getContentFromExtra(huihuangmingtianAdsDTO, HonorParamEnum.ADVERTISER_ID.getParam(),
                getContentFromExtra(huihuangmingtianAdsDTO, "advertiserId", "")));
        honorVO.setOaid(huihuangmingtianAdsDTO.getOaid());
//        if (HuaweiPath.HW_HH_JINGDONG_ACCOUNT_01.equals(huihuangmingtianAdsDTO.getAccountId())) {
//            honorVO.setSecret(HuaweiPath.LTJD_02_SECRET);
//        }
        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, honorVO);

        Response response = super.baseAdsCallBack(honorVO);
        HonorCallbackDTO data = (HonorCallbackDTO) response.getData();

        //更新回调状态
        HuihuangmingtianAdsDTO huihuangmingtianAds = new HuihuangmingtianAdsDTO();
        huihuangmingtianAds.setId(id);
        huihuangmingtianAds.setCallBackTime(String.valueOf(currentTime));

        if (response.getCode() == 0) {
            huihuangmingtianAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(huihuangmingtianAds, hhxyAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            huihuangmingtianAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(huihuangmingtianAds, hhxyAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }

}
