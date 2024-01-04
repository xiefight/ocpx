package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IDingyunFanqiechangtingAdsDao;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.dingyun.XiaomiDingyunReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("xdyfqctChannelAds")
public class XiaomiDingyunFanqiechangtingChannelAds extends XiaomiDingyunReportFactory {

    @Autowired
    private IDingyunFanqiechangtingAdsDao dyfqctAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_DINGYUN_FANQIECHANGTING;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.XIAOMI_DINGYUN_FANQIECHANGTING;
    }

    @Override
    protected String channelName() {
        return XiaomiPath.XIAOMI_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return dyfqctAdsDao;
    }

}
