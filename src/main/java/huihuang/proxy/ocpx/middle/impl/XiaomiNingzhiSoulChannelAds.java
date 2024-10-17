package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.INingzhiSoulAdsDao;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.ningzhi.XiaomiNingzhiReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("xmningzhisoulChannelAds")
public class XiaomiNingzhiSoulChannelAds extends XiaomiNingzhiReportFactory {

    @Autowired
    private INingzhiSoulAdsDao ningzhiSoulAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_NINGZHI_SOUL;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.XIAOMI_NINGZHI_SOUL;
    }

    @Override
    protected String channelName() {
        return XiaomiPath.XIAOMI_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return ningzhiSoulAdsDao;
    }

}
