package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.ads.huihui.HuihuiAdsDTO;
import huihuang.proxy.ocpx.ads.huihui.HuihuiEventTypeEnum;
import huihuang.proxy.ocpx.ads.huihui.xianyu.HuihuiXianyuPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuiXianyuAdsDao;
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

@Service("hxyService")
public class HuaweiHuihuiXianyuServiceImpl extends HuaweiChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(HuaweiHuihuiXianyuServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private IHuihuiXianyuAdsDao xianyuAdsDao;
    @Autowired
    private HuihuiXianyuPath huihuiXianyuPath;

    String channelAdsKey = Constants.ChannelAdsKey.HUAWEI_XIANYU;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("conv_action")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  event:{}", channelAdsKey, id, eventType);
        //根据id查询对应的点击记录
        HuihuiAdsDTO xianyuAdsDTO = xianyuAdsDao.queryXianyuAdsById(id);
        if (null == xianyuAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        Ads2HuaweiVO huaweiVO = new Ads2HuaweiVO();
        //针对闲鱼aid=36626的户，闲鱼注册事件对应华为激活事件，这里特殊处理一下，而闲鱼的激活事件暂时不处理
        if ("36626".equals(xianyuAdsDTO.getAid())) {
            if (eventType.equals(HuihuiEventTypeEnum.ANDROID_ACTIVATE.getCode())
                    || eventType.equals(HuihuiEventTypeEnum.IOS_ACTIVATE.getCode())) {
                return BasicResult.getFailResponse("aid=36626的闲鱼户不需要回传激活事件:" + id);
            }
            if (eventType.equals(HuihuiEventTypeEnum.ANDROID_REGISTER.getCode())) {
                eventType = HuihuiEventTypeEnum.ANDROID_ACTIVATE.getCode();
            }
            if (eventType.equals(HuihuiEventTypeEnum.IOS_REGISTER.getCode())) {
                eventType = HuihuiEventTypeEnum.IOS_ACTIVATE.getCode();
            }
            if (HuaweiPath.HW_HH_XIANYU_ACCOUNT_01.equals(xianyuAdsDTO.getOcpxAccount())) {
                huaweiVO.setSecret(HuaweiPath.XIANYU36626_1_SECRET);
            } else if (HuaweiPath.HW_HH_XIANYU_ACCOUNT_02.equals(xianyuAdsDTO.getOcpxAccount())) {
                //当aid=36626时，使用的02户，需要处理注册激活事件对应
                huaweiVO.setSecret(HuaweiPath.XIANYU_SECRET);
            } else {
                huaweiVO.setSecret(HuaweiPath.XIANYU36626_SECRET);
            }
        } else if (HuaweiPath.HW_HH_XIANYU_ACCOUNT_02.equals(xianyuAdsDTO.getOcpxAccount())) {
            //02户本来不需要处理
            huaweiVO.setSecret(HuaweiPath.XIANYU42139_SECRET);
        } else if (HuaweiPath.HW_HH_XIANYU_ACCOUNT_03.equals(xianyuAdsDTO.getOcpxAccount())) {
            huaweiVO.setSecret(HuaweiPath.XIANYU_03_SECRET);
        } else {
            huaweiVO.setSecret(HuaweiPath.XIANYU_SECRET);
        }

        long currentTime = System.currentTimeMillis();
        huaweiVO.setAdsId(id);
        huaweiVO.setAdsName(huihuiXianyuPath.baseAdsName());
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
        HuihuiAdsDTO tuhuAds = new HuihuiAdsDTO();
        tuhuAds.setId(id);
        tuhuAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));

        if (response.getCode() == 0) {
            tuhuAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(tuhuAds, xianyuAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            tuhuAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(tuhuAds, xianyuAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }

}
