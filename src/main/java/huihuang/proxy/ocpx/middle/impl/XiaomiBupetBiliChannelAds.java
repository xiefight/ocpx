package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IBupetBiliAdsDao;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.buget.XiaomiBupetReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("xmbupetbiliChannelAds")
public class XiaomiBupetBiliChannelAds extends XiaomiBupetReportFactory {

    @Autowired
    private IBupetBiliAdsDao bupetBiliAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_BUPET_BILI;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.XIAOMI_BUPET_BILI;
    }

    @Override
    protected String channelName() {
        return XiaomiPath.XIAOMI_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return bupetBiliAdsDao;
    }

}
