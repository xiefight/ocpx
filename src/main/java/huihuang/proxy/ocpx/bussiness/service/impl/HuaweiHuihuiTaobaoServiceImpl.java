package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.ads.huihui.HuihuiAdsDTO;
import huihuang.proxy.ocpx.ads.huihui.HuihuiEventTypeEnum;
import huihuang.proxy.ocpx.ads.huihui.taobao.HuihuiTaobaoPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuiTaobaoAdsDao;
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

@Service("hhhtbService")
public class HuaweiHuihuiTaobaoServiceImpl extends HuaweiChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(HuaweiHuihuiTaobaoServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private IHuihuiTaobaoAdsDao taobaoAdsDao;
    @Autowired
    private HuihuiTaobaoPath huihuiTaobaoPath;

    String channelAdsKey = Constants.ChannelAdsKey.HUAWEI_HUIHUI_TAOBAO;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("conv_action")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  event:{}", channelAdsKey, id, eventType);
        //根据id查询对应的点击记录
        HuihuiAdsDTO taobaoAdsDTO = taobaoAdsDao.queryTaobaoYoudaoAdsById(id);
        if (null == taobaoAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        Ads2HuaweiVO huaweiVO = new Ads2HuaweiVO();
        if (HuaweiPath.HW_HH_TAOBAO_ACCOUNT_01.equals(taobaoAdsDTO.getOcpxAccount())) {
            huaweiVO.setSecret(HuaweiPath.TAOBAO_01_SECRET);
        }

        long currentTime = System.currentTimeMillis();
        huaweiVO.setAdsId(id);
        huaweiVO.setAdsName(huihuiTaobaoPath.baseAdsName());
        huaweiVO.setCallbackUrl(taobaoAdsDTO.getCallback());

        huaweiVO.setEventType(eventType);
        huaweiVO.setTimestamp(String.valueOf(currentTime));
        huaweiVO.setCampaignId(getContentFromExtra(taobaoAdsDTO, HuaweiParamEnum.CAMPAIGN_ID.getParam(), null));
        huaweiVO.setContentId(getContentFromExtra(taobaoAdsDTO, HuaweiParamEnum.CONTENT_ID.getParam(), null));
        huaweiVO.setTrackingEnabled(getContentFromExtra(taobaoAdsDTO, HuaweiParamEnum.TRACKING_ENABLED.getParam(), "1"));
        huaweiVO.setConversionTime(String.valueOf(currentTime / 1000));
        huaweiVO.setConversionType(HuihuiEventTypeEnum.huihuiHuaweiEventTypeMap.get(eventType).getCode());
        huaweiVO.setOaid(taobaoAdsDTO.getOaid());
        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, huaweiVO);

        Response response = super.baseAdsCallBack(huaweiVO);
        HuaweiCallbackDTO data = (HuaweiCallbackDTO) response.getData();

        //更新回调状态
        HuihuiAdsDTO taobaoAds = new HuihuiAdsDTO();
        taobaoAds.setId(id);
        taobaoAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));

        if (response.getCode() == 0) {
            taobaoAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(taobaoAds, taobaoAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            taobaoAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(taobaoAds, taobaoAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }

}
