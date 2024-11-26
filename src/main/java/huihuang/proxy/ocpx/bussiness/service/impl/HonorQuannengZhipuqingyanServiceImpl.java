package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.ads.huihui.HuihuiEventTypeEnum;
import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongAdsDTO;
import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongEventTypeEnum;
import huihuang.proxy.ocpx.ads.quannenghudong.zhipuqingyan.QuannengZhipuqingyanPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IQuannengZhipuqingyanAdsDao;
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

@Service("honorqnzpqyService")
public class HonorQuannengZhipuqingyanServiceImpl extends HonorChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(HonorQuannengZhipuqingyanServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IQuannengZhipuqingyanAdsDao zhipuqingyanAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private QuannengZhipuqingyanPath quannengZhipuqingyanPath;

    String channelAdsKey = Constants.ChannelAdsKey.HONOR_QUANNENG_ZHIPUQINGYAN;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("action_type")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  event:{}", channelAdsKey, id, eventType);

        //根据id查询对应的点击记录
        QuannengHudongAdsDTO quannengAdsDTO = zhipuqingyanAdsDao.queryQuannengZhipuqingyanAdsById(id);
        if (null == quannengAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }
        String accountId = quannengAdsDTO.getAccountId();

        long currentTime = System.currentTimeMillis();
        Ads2HonorVO honorVO = new Ads2HonorVO();
        honorVO.setAdsId(id);
        honorVO.setAdsName(quannengZhipuqingyanPath.baseAdsName());
//        honorVO.setCallbackUrl(huihuangmingtianAdsDTO.getCallbackUrl());

//        honorVO.setTimestamp(String.valueOf(currentTime));
        honorVO.setConversionTime(String.valueOf(currentTime));
        honorVO.setConversionId(QuannengHudongEventTypeEnum.quannengHudongHonorEventTypeMap.get(eventType).getCode());
        honorVO.setTrackId(getContentFromExtra(quannengAdsDTO, HonorParamEnum.TRACK_ID.getParam(),
                getContentFromExtra(quannengAdsDTO, "trackId", "")));
        honorVO.setAdvertiserId(getContentFromExtra(quannengAdsDTO, HonorParamEnum.ADVERTISER_ID.getParam(),
                getContentFromExtra(quannengAdsDTO, "advertiserId", "")));
        honorVO.setOaid(quannengAdsDTO.getOaid());
//        if (HuaweiPath.HW_HH_JINGDONG_ACCOUNT_01.equals(quannengAdsDTO.getAccountId())) {
//            honorVO.setSecret(HuaweiPath.LTJD_02_SECRET);
//        }
        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, honorVO);

        Response response = super.baseAdsCallBack(honorVO);
        HonorCallbackDTO data = (HonorCallbackDTO) response.getData();

        //更新回调状态
        QuannengHudongAdsDTO quannengAds = new QuannengHudongAdsDTO();
        quannengAds.setId(id);
        quannengAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));
        if (response.getCode() == 0) {
            quannengAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(quannengAds, zhipuqingyanAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            quannengAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(quannengAds, zhipuqingyanAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }

}
