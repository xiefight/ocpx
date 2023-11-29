package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamField;
import huihuang.proxy.ocpx.ads.liangdamao.douyin.DouyinPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IDouyinAdsDao;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.liangdamao.XiaomiLiangdamaoReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class XiaomiDouyinChannelAds extends XiaomiLiangdamaoReportFactory {

    @Autowired
    private IDouyinAdsDao douyinAdsDao;
    @Autowired
    private DouyinPath douyinPath;

    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_DOUYIN;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.XIAOMI_DOUYIN;
    }

    @Override
    protected IMarkDao adsDao() {
        return douyinAdsDao;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        LiangdamaoParamField liangdamaoParamField = (LiangdamaoParamField) super.channelParamToAdsParam(parameterMap);
        if (null == liangdamaoParamField.getTp_adv_id()) {
            liangdamaoParamField.setTp_adv_id(douyinPath.tpAdvId());
        }
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, liangdamaoParamField);
        return liangdamaoParamField;
    }

}
