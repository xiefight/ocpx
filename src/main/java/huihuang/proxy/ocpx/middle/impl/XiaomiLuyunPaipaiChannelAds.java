package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.ILuyunPaipaiAdsDao;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.luyun.LuyunXiaomiReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class XiaomiLuyunPaipaiChannelAds extends LuyunXiaomiReportFactory {
    @Autowired
    private ILuyunPaipaiAdsDao luyunPaipaiAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_LUYUN_PAIPAI;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.XIAOMI_LUYUN_PAIPAI;
    }

    @Override
    protected String channelName() {
        return XiaomiPath.XIAOMI_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return luyunPaipaiAdsDao;
    }


}
