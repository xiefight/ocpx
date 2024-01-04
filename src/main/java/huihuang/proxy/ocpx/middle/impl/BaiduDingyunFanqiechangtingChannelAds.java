package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IDingyunFanqiechangtingAdsDao;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.dingyun.BaiduDingyunReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("bdyfqctChannelAds")
public class BaiduDingyunFanqiechangtingChannelAds extends BaiduDingyunReportFactory {

    @Autowired
    private IDingyunFanqiechangtingAdsDao dyfqctAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_DINGYUN_FANQIECHANGTING;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_DINGYUN_FANQIECHANGTING;
    }

    @Override
    protected String channelName() {
        return BaiduPath.BAIDU_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return dyfqctAdsDao;
    }

}
