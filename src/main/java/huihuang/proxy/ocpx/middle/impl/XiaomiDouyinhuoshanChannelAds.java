package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamField;
import huihuang.proxy.ocpx.ads.liangdamao.douyinhuoshan.DouyinHuoshanPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IDouyinhuoshanAdsDao;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.liangdamao.XiaomiLiangdamaoReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class XiaomiDouyinhuoshanChannelAds extends XiaomiLiangdamaoReportFactory {

    @Autowired
    private IDouyinhuoshanAdsDao douyinhuoshanAdsDao;
    @Autowired
    private DouyinHuoshanPath douyinhuoshanPath;

    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_DOUYINHUOSHAN;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.XIAOMI_DOUYINHUOSHAN;
    }

    @Override
    protected IMarkDao adsDao() {
        return douyinhuoshanAdsDao;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        LiangdamaoParamField liangdamaoParamField = (LiangdamaoParamField) super.channelParamToAdsParam(parameterMap);
        if (null == liangdamaoParamField.getTp_adv_id()) {
            liangdamaoParamField.setTp_adv_id(douyinhuoshanPath.tpAdvId());
        }
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, liangdamaoParamField);
        return liangdamaoParamField;
    }

}
