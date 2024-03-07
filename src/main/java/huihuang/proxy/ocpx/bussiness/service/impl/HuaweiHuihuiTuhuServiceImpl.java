package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.ads.huihui.HuihuiAdsDTO;
import huihuang.proxy.ocpx.ads.huihui.HuihuiEventTypeEnum;
import huihuang.proxy.ocpx.ads.huihui.tuhu.HuihuiTuhuPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuiTuhuAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.bussiness.service.basechannel.HuaweiChannelFactory;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2HuaweiVO;
import huihuang.proxy.ocpx.channel.huawei.HuaweiCallbackDTO;
import huihuang.proxy.ocpx.channel.huawei.HuaweiParamEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiPath;
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
 * @Author: xietao
 * @Date: 2023/8/16 10:18
 */
@Service("htService")
public class HuaweiHuihuiTuhuServiceImpl extends HuaweiChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(HuaweiHuihuiTuhuServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private IHuihuiTuhuAdsDao tuhuAdsDao;
    @Autowired
    private HuihuiTuhuPath huihuiTuhuPath;

    String channelAdsKey = Constants.ChannelAdsKey.HUAWEI_TUHU;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        logger.info("adsCallBack {} 开始回调渠道  id:{}  event:{}", channelAdsKey, id, parameterMap.get("conv_action")[0]);

        //根据id查询对应的点击记录
        HuihuiAdsDTO tuhuAdsDTO = tuhuAdsDao.queryTuhuAdsById(id);
        if (null == tuhuAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        long currentTime = System.currentTimeMillis();
        Ads2HuaweiVO huaweiVO = new Ads2HuaweiVO();
        huaweiVO.setAdsId(id);
        huaweiVO.setAdsName(huihuiTuhuPath.baseAdsName());
        huaweiVO.setCallbackUrl(tuhuAdsDTO.getCallback());

        huaweiVO.setEventType(parameterMap.get("conv_action")[0]);
        huaweiVO.setTimestamp(String.valueOf(currentTime));
        huaweiVO.setCampaignId(getContentFromExtra(tuhuAdsDTO, HuaweiParamEnum.CAMPAIGN_ID.getParam(), null));
        huaweiVO.setContentId(getContentFromExtra(tuhuAdsDTO, HuaweiParamEnum.CONTENT_ID.getParam(), null));
        huaweiVO.setTrackingEnabled(getContentFromExtra(tuhuAdsDTO, HuaweiParamEnum.TRACKING_ENABLED.getParam(), "1"));
        huaweiVO.setConversionTime(String.valueOf(currentTime / 1000));
        huaweiVO.setConversionType(HuihuiEventTypeEnum.huihuiHuaweiEventTypeMap.get(parameterMap.get("conv_action")[0]).getCode());
        huaweiVO.setOaid(tuhuAdsDTO.getOaid());
        huaweiVO.setSecret(HuaweiPath.TUHU_SECRET);
        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, huaweiVO);

        Response response = super.baseAdsCallBack(huaweiVO);
        HuaweiCallbackDTO data = (HuaweiCallbackDTO) response.getData();

        //更新回调状态
        HuihuiAdsDTO tuhuAds = new HuihuiAdsDTO();
        tuhuAds.setId(id);
        tuhuAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));

        if (response.getCode() == 0) {
            tuhuAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(tuhuAds, tuhuAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            tuhuAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(tuhuAds, tuhuAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }

}
