package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IKeepAdsDao;
import huihuang.proxy.ocpx.channel.iqiyi.IQiyiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.luyun.LuyunIQiyiReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ilykeepChannelAds")
public class IQiyiLuyunKeepChannelAds extends LuyunIQiyiReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.IQIYI_LUYUN_KEEP;

    @Autowired
    private IKeepAdsDao lykeepAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.IQIYI_LUYUN_KEEP;
    }

    @Override
    protected String channelName() {
        return IQiyiPath.IQIYI_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return lykeepAdsDao;
    }
}
