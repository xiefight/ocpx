package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.ads.huihui.HuihuiAdsDTO;
import huihuang.proxy.ocpx.ads.huihui.HuihuiEventTypeEnum;
import huihuang.proxy.ocpx.ads.huihui.xianyu.HuihuiXianyuPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuiQQReadAdsDao;
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

@Service("hhhqqrService")
public class HuaweiHuihuiQQReadServiceImpl extends HuaweiChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(HuaweiHuihuiQQReadServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private IHuihuiQQReadAdsDao qqrAdsDao;
    @Autowired
    private HuihuiXianyuPath huihuiQQRPath;

    String channelAdsKey = Constants.ChannelAdsKey.HUAWEI_HUIHUI_QQREAD;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("conv_action")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  event:{}", channelAdsKey, id, eventType);
        //根据id查询对应的点击记录
        HuihuiAdsDTO xianyuAdsDTO = qqrAdsDao.queryHuihuiQQReadAdsById(id);
        if (null == xianyuAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        Ads2HuaweiVO huaweiVO = new Ads2HuaweiVO();
        if (HuaweiPath.HW_HH_QQREAD_ACCOUNT_01.equals(xianyuAdsDTO.getOcpxAccount())) {
            huaweiVO.setSecret(HuaweiPath.QQREAD_01_SECRET);
        }


        long currentTime = System.currentTimeMillis();
        huaweiVO.setAdsId(id);
        huaweiVO.setAdsName(huihuiQQRPath.baseAdsName());
        huaweiVO.setCallbackUrl(xianyuAdsDTO.getCallback());

        huaweiVO.setEventType(eventType);
        huaweiVO.setTimestamp(String.valueOf(currentTime));
        huaweiVO.setCampaignId(getContentFromExtra(xianyuAdsDTO, HuaweiParamEnum.CAMPAIGN_ID.getParam(), null));
        huaweiVO.setContentId(getContentFromExtra(xianyuAdsDTO, HuaweiParamEnum.CONTENT_ID.getParam(), null));
        huaweiVO.setTrackingEnabled(getContentFromExtra(xianyuAdsDTO, HuaweiParamEnum.TRACKING_ENABLED.getParam(), "1"));
        huaweiVO.setConversionTime(String.valueOf(currentTime / 1000));
        huaweiVO.setConversionType(HuihuiEventTypeEnum.huihuiHuaweiEventTypeMap.get(eventType).getCode());
        huaweiVO.setOaid(xianyuAdsDTO.getOaid());
        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, huaweiVO);

        Response response = super.baseAdsCallBack(huaweiVO);
        HuaweiCallbackDTO data = (HuaweiCallbackDTO) response.getData();

        //更新回调状态
        HuihuiAdsDTO qqrAds = new HuihuiAdsDTO();
        qqrAds.setId(id);
        qqrAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));

        if (response.getCode() == 0) {
            qqrAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(qqrAds, qqrAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            qqrAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(qqrAds, qqrAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }

}
