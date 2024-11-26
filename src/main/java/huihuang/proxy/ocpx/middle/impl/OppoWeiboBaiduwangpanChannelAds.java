package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.weibo.WeiboParamField;
import huihuang.proxy.ocpx.ads.weibo.baiduwangpan.WeiboBaiduwangpanPath;
import huihuang.proxy.ocpx.ads.weibo.wannianli.WeiboWannianliPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IWeiboBaiduwangpanAdsDao;
import huihuang.proxy.ocpx.channel.oppo.OppoPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.weibo.OppoWeiboReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("oppowbbdwpChannelAds")
public class OppoWeiboBaiduwangpanChannelAds extends OppoWeiboReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.OPPO_WEIBO_BAIDUWANGPAN;

    @Autowired
    private IWeiboBaiduwangpanAdsDao wbbdwpAdsOppoDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.OPPO_WEIBO_BAIDUWANGPAN;
    }

    @Override
    protected String channelName() {
        return OppoPath.OPPO_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return wbbdwpAdsOppoDao;
    }

    @Override
    protected String initAdsUrl() {
        return null;
    }

    @Override
    protected String initAdsUrl(Object adsObj) {
        WeiboParamField weiboParamField = (WeiboParamField) adsObj;
        String monitorType = weiboParamField.getMonitorType();
        if ("0".equals(monitorType)){
            //曝光监测
            return WeiboWannianliPath.EXPOSURE_URI;
        }else {
            //点击监测
            return WeiboWannianliPath.BASIC_URI;
        }
    }
}
