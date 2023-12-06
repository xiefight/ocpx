package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangFanqiechangtingAdsDao;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuangmingtian.XiaomiHuihuangReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("xhhfqctChannelAds")
public class XiaomiHuihuangFanqiechangtingChannelAds extends XiaomiHuihuangReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_HUIHUANG_FANQIECHANGTING;

    @Autowired
    private IHuihuangFanqiechangtingAdsDao hhfqctAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.XIAOMI_HUIHUANG_FANQIECHANGTING;
    }

    @Override
    protected String channelName() {
        return XiaomiPath.XIAOMI_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return hhfqctAdsDao;
    }

}
