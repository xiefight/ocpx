package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.net.URLDecoder;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangFengmangEventTypeEnum;
import huihuang.proxy.ocpx.ads.huihui.HuihuiAdsDTO;
import huihuang.proxy.ocpx.ads.huihui.HuihuiEventTypeEnum;
import huihuang.proxy.ocpx.ads.huihui.zhipu.HuihuiZhipuqingyanPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuiZhipuqingyanAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.bussiness.service.basechannel.BaiduChannelFactory;
import huihuang.proxy.ocpx.bussiness.service.basechannel.HonorChannelFactory;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2BaiduVO;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2HonorVO;
import huihuang.proxy.ocpx.channel.baidu.BaiduCallbackDTO;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
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

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service("honorhhzhipuqingyanService")
public class HonorHuihuiZhipuqingyanServiceImpl extends HonorChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(HonorHuihuiZhipuqingyanServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IHuihuiZhipuqingyanAdsDao zhipuqingyanAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private HuihuiZhipuqingyanPath huihuiZhipuqingyanPath;

    String channelAdsKey = Constants.ChannelAdsKey.HONOR_HUIHUI_ZHIPUQINGYAN;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("conv_action")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  event:{}", channelAdsKey, id, eventType);

        //根据id查询对应的点击记录
        HuihuiAdsDTO huihuiAdsDTO = zhipuqingyanAdsDao.queryZhipuqingyanAdsById(id);
        if (null == huihuiAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }
        String ocpxAccount = huihuiAdsDTO.getOcpxAccount();

        long currentTime = System.currentTimeMillis();
        Ads2HonorVO honorVO = new Ads2HonorVO();
        honorVO.setAdsId(id);
        honorVO.setAdsName(huihuiZhipuqingyanPath.baseAdsName());
//        honorVO.setCallbackUrl(huihuangmingtianAdsDTO.getCallbackUrl());

//        honorVO.setTimestamp(String.valueOf(currentTime));
        honorVO.setConversionTime(String.valueOf(currentTime));
        honorVO.setConversionId(HuihuiEventTypeEnum.huihuiHonorEventTypeMap.get(eventType).getCode());
        honorVO.setTrackId(getContentFromExtra(huihuiAdsDTO, HonorParamEnum.TRACK_ID.getParam(),
                getContentFromExtra(huihuiAdsDTO, "trackId", "")));
        honorVO.setAdvertiserId(getContentFromExtra(huihuiAdsDTO, HonorParamEnum.ADVERTISER_ID.getParam(),
                getContentFromExtra(huihuiAdsDTO, "advertiserId", "")));
        honorVO.setOaid(huihuiAdsDTO.getOaid());
//        if (HuaweiPath.HW_HH_JINGDONG_ACCOUNT_01.equals(huihuiAdsDTO.getAccountId())) {
//            honorVO.setSecret(HuaweiPath.LTJD_02_SECRET);
//        }
        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, honorVO);

        Response response = super.baseAdsCallBack(honorVO);
        HonorCallbackDTO data = (HonorCallbackDTO) response.getData();

        //更新回调状态
        HuihuiAdsDTO huihuiAds = new HuihuiAdsDTO();
        huihuiAds.setId(id);
        huihuiAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));
        if (response.getCode() == 0) {
            huihuiAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(huihuiAds, zhipuqingyanAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            huihuiAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(huihuiAds, zhipuqingyanAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }

}
