package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IDingyunDouyinhuoshanAdsDao;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.dingyun.XiaomiDingyunReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("xdydyhsChannelAds")
public class XiaomiDingyunDouyinhuoshanChannelAds extends XiaomiDingyunReportFactory {

    @Autowired
    private IDingyunDouyinhuoshanAdsDao dydyhsAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_DINGYUN_DOUYINHUOSHAN;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.XIAOMI_DINGYUN_DOUYINHUOSHAN;
    }

    @Override
    protected String channelName() {
        return XiaomiPath.XIAOMI_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return dydyhsAdsDao;
    }

}
