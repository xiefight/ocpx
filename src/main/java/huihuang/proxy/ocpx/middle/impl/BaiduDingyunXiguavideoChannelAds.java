package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IDingyunXiguaVideoAdsDao;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.dingyun.BaiduDingyunReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("bdyxgChannelAds")
public class BaiduDingyunXiguavideoChannelAds extends BaiduDingyunReportFactory {

    @Autowired
    private IDingyunXiguaVideoAdsDao dyxgAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_DINGYUN_XIGUAVIDEO;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_DINGYUN_XIGUAVIDEO;
    }

    @Override
    protected String channelName() {
        return BaiduPath.BAIDU_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return dyxgAdsDao;
    }

}
