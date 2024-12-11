package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.weibo.WeiboParamField;
import huihuang.proxy.ocpx.ads.weibo.wannianli.WeiboWannianliPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IWeiboXueersiAdsDao;
import huihuang.proxy.ocpx.channel.honor.HonorPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.weibo.BaiduWeiboReportFactory;
import huihuang.proxy.ocpx.middle.baseadsreport.weibo.HonorWeiboReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("honorwbxesChannelAds")
public class HonorWeiboXueersiChannelAds extends HonorWeiboReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.HONOR_WEIBO_XUEERSI;

    @Autowired
    private IWeiboXueersiAdsDao wbxesAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.HONOR_WEIBO_XUEERSI;
    }

    @Override
    protected String channelName() {
        return HonorPath.HONOR_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return wbxesAdsDao;
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
            return WeiboWannianliPath.EXPOSURE_URI;
        } else {
            //点击监测
            return WeiboWannianliPath.BASIC_URI;
        }
    }
}
