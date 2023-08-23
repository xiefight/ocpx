package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouAdsDTO;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouEventTypeEnum;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IKuaishouAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.bussiness.service.basechannel.HuaweiChannelFactory;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2HuaweiVO;
import huihuang.proxy.ocpx.channel.huawei.HuaweiCallbackDTO;
import huihuang.proxy.ocpx.channel.huawei.HuaweiParamEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.KuaishouResponse;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.middle.factory.ChannelAdsFactory;
import huihuang.proxy.ocpx.util.CommonUtil;
import huihuang.proxy.ocpx.util.tuple.Tuple2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * baidu-jingdong
 *
 * @Author: xietao
 * @Date: 2023/5/11 17:26
 */
@Service("hkService")
public class HuaweiKuaishouServiceImpl extends HuaweiChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(HuaweiKuaishouServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IKuaishouAdsDao kuaishouAdsDao;
    @Autowired
    @Qualifier("kuaishouAdsHuaweiDao")
    private IKuaishouAdsDao kuaishouAdsHuaweiDao;
    @Autowired
    private BaseServiceInner baseServiceInner;

    String channelAdsKey = Constants.ChannelAdsKey.HUAWEI_KUAISHOU;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public KuaishouResponse adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        logger.info("adsCallBack {} 开始回调渠道  id:{}  parameterMap.size:{}", channelAdsKey, id, parameterMap.size());
        //根据id查询对应的点击记录
        Tuple2<IMarkDao, KuaishouAdsDTO> tuple2 = baseServiceInner.querySplitAdsObject(kuaishouAdsHuaweiDao, kuaishouAdsDao, "queryKuaishouAdsById", KuaishouAdsDTO.class, id);

        if (null == tuple2) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return new KuaishouResponse(1, "未根据找到对应的监测信息", id);
        }
        IKuaishouAdsDao ikuaishouAdsDao = (IKuaishouAdsDao) tuple2.getT1();
        KuaishouAdsDTO kuaishouAdsDTO = tuple2.getT2();

        Ads2HuaweiVO huaweiVO = new Ads2HuaweiVO();

        //转化类型字段
        String eventType = parameterMap.get("actionType")[0];
        long currentTime = System.currentTimeMillis();
        String eventTimes = String.valueOf(currentTime);
        logger.info("adsCallBack {} 开始回调渠道  eventType:{}", channelAdsKey, eventType);

        huaweiVO.setConversionType(KuaishouEventTypeEnum.kuaishouHuaweiEventTypeMap.get(eventType).getCode());
        huaweiVO.setConversionTime(String.valueOf(currentTime / 1000));
        huaweiVO.setTimestamp(eventTimes);
        huaweiVO.setTrackingEnabled(getContentFromExtra(kuaishouAdsDTO, HuaweiParamEnum.TRACKING_ENABLED.getParam(), "1"));
        huaweiVO.setContentId(getContentFromExtra(kuaishouAdsDTO, HuaweiParamEnum.CONTENT_ID.getParam(), null));
        huaweiVO.setCampaignId(kuaishouAdsDTO.getCampaignId());
        if (CommonUtil.strEmpty(kuaishouAdsDTO.getOaid())) {
            huaweiVO.setOaid(kuaishouAdsDTO.getOaid());
        }

        String huaweiSecret = "";
        String adsName = "";
        String accountId = kuaishouAdsDTO.getAccountId();
        String adid = kuaishouAdsDTO.getAdid();
        //使用快手的adid是为了兼容旧数据的区分，新数据通过accountId区分
        if (StrUtil.isEmpty(accountId)) {
            if (KuaishouPath.HUAWEI_KUAISHOU_ADID.equals(adid)) {
                huaweiSecret = HuaweiPath.KUAISHOU_SECRET;
                adsName = KuaishouPath.KUAISHOU_ADS_NAME;
            }
            if (KuaishouPath.HUAWEI_KUAISHOU2_ADID.equals(adid)) {
                huaweiSecret = HuaweiPath.KUAISHOU2_SECRET;
                adsName = KuaishouPath.KUAISHOU_ADS_NAME;
            }
            if (KuaishouPath.HUAWEI_KUAISHOUJISU_ADID.equals(adid)) {
                huaweiSecret = HuaweiPath.KUAISHOUJISU_SECRET;
                adsName = KuaishouPath.KUAISHOUJISU_ADS_NAME;
            }
            if (KuaishouPath.HUAWEI_KUAISHOUJISU2_ADID.equals(adid)) {
                huaweiSecret = HuaweiPath.KUAISHOUJISU2_SECRET;
                adsName = KuaishouPath.KUAISHOUJISU2_ADS_NAME;
            }
        } else {
            if (HuaweiPath.HW_KS_ACCOUNT_01.equals(accountId)) {
                huaweiSecret = HuaweiPath.KUAISHOU_SECRET;
                adsName = KuaishouPath.KUAISHOU_ADS_NAME;
            }
            if (HuaweiPath.HW_KS_ACCOUNT_02.equals(accountId)) {
                huaweiSecret = HuaweiPath.KUAISHOU2_SECRET;
                adsName = KuaishouPath.KUAISHOU_ADS_NAME;
            }
            if (HuaweiPath.HW_KSJS_ACCOUNT_01.equals(accountId)) {
                huaweiSecret = HuaweiPath.KUAISHOUJISU_SECRET;
                adsName = KuaishouPath.KUAISHOUJISU_ADS_NAME;
            }
            if (HuaweiPath.HW_KSJS_ACCOUNT_02.equals(accountId)) {
                huaweiSecret = HuaweiPath.KUAISHOUJISU2_SECRET;
                adsName = KuaishouPath.KUAISHOUJISU2_ADS_NAME;
            }
            if (HuaweiPath.HW_KSJS_ACCOUNT_03.equals(accountId)) {
                huaweiSecret = HuaweiPath.KUAISHOUJISU3_SECRET;
                adsName = KuaishouPath.KUAISHOUJISU3_ADS_NAME;
            }
            if (HuaweiPath.HW_KS_ACCOUNT_04.equals(accountId)) {
                huaweiSecret = HuaweiPath.KUAISHOU4_SECRET;
                adsName = KuaishouPath.KUAISHOU_ADS_NAME;
            }
        }

        huaweiVO.setAdsId(id);
        huaweiVO.setAdsName(adsName);
        huaweiVO.setSecret(huaweiSecret);
        huaweiVO.setCallbackUrl(kuaishouAdsDTO.getCallback());

        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, huaweiVO);
        Response response = super.baseAdsCallBack(huaweiVO);
        HuaweiCallbackDTO data = (HuaweiCallbackDTO) response.getData();

        //更新回调状态
        KuaishouAdsDTO kuaishouAds = new KuaishouAdsDTO();
        kuaishouAds.setId(id);
        kuaishouAds.setCallBackTime(String.valueOf(currentTime));
        if (response.getCode() == 0) {
            kuaishouAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(kuaishouAds, ikuaishouAdsDao);
            logger.info("adsCallBack {} {}", channelAdsKey, data);
            return new KuaishouResponse(0, "", data.getId());
        } else {
            kuaishouAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(kuaishouAds, ikuaishouAdsDao);
            logger.info("adsCallBack {} {}", channelAdsKey, data);
            return new KuaishouResponse(1, data.getCallBackMes(), data.getId());
        }
    }

}
