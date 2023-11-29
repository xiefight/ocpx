package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamField;
import huihuang.proxy.ocpx.ads.youku.YoukuPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IYoukuAdsDao;
import huihuang.proxy.ocpx.bussiness.service.basechannel.HuaweiChannelFactory;
import huihuang.proxy.ocpx.channel.huawei.HuaweiParamEnum;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.liangdamao.HuaweiLiangdamaoReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * huawei-jingdong
 *
 * @Author: xietao
 * @Date: 2023/5/28 16:02
 */
@Component("hyChannelAds")
public class HuaweiYoukuChannelAds extends HuaweiLiangdamaoReportFactory {

    @Autowired
    private IYoukuAdsDao youkuAdsDao;

    @Autowired
    private YoukuPath youkuPath;

    String channelAdsKey = Constants.ChannelAdsKey.HUAWEI_YOUKU;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.HUAWEI_YOUKU;
    }

    @Override
    protected IMarkDao adsDao() {
        return youkuAdsDao;
    }


    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        LiangdamaoParamField liangdamaoParamField = (LiangdamaoParamField) super.channelParamToAdsParam(parameterMap);
        if (null == liangdamaoParamField.getTp_adv_id()){
            liangdamaoParamField.setTp_adv_id(youkuPath.tpAdvId());
        }
        //存储华为这边必有而广告侧（快手、粮大猫）这不必有的参数，回传可能会用到
        String extras = HuaweiChannelFactory.fitExtras(parameterMap,
                HuaweiParamEnum.CONTENT_ID.getParam(),
                HuaweiParamEnum.EVENT_TYPE.getParam(),
                HuaweiParamEnum.TRACE_TIME.getParam(),
                HuaweiParamEnum.TRACKING_ENABLED.getParam(),
                HuaweiParamEnum.CAMPAIGN_ID.getParam());
        if (extras.length() > 0) {
            liangdamaoParamField.setExtra(extras);
        }
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, liangdamaoParamField);
        return liangdamaoParamField;
    }


}
