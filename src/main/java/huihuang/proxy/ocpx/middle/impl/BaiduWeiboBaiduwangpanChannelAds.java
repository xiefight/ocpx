package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.weibo.WeiboParamField;
import huihuang.proxy.ocpx.ads.weibo.baiduwangpan.WeiboBaiduwangpanPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IWeiboBaiduwangpanAdsDao;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.weibo.BaiduWeiboReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("bdwbbdwpChannelAds")
public class BaiduWeiboBaiduwangpanChannelAds extends BaiduWeiboReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_WEIBO_WANNIANLI;

    @Autowired
    private IWeiboBaiduwangpanAdsDao wbbdwpAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_WEIBO_BAIDUWANGPAN;
    }

    @Override
    protected String channelName() {
        return BaiduPath.BAIDU_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return wbbdwpAdsDao;
    }

    @Override
    protected String initAdsUrl() {
        return null;
    }

    @Override
    protected String initAdsUrl(Object adsObj) {
        WeiboParamField weiboParamField = (WeiboParamField) adsObj;
        String monitorType = weiboParamField.getMonitorType();
        if ("0".equals(monitorType)) {
            //曝光监测
            return WeiboBaiduwangpanPath.EXPOSURE_URI;
        } else {
            //点击监测
            return WeiboBaiduwangpanPath.BASIC_URI;
        }
    }
}
