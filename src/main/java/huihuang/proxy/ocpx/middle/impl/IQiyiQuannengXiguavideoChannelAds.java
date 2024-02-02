package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IQuannengXiguaVideoAdsDao;
import huihuang.proxy.ocpx.channel.iqiyi.IQiyiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.quannenghudong.IQiyiQuannengHudongReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("iqnxgChannelAds")
public class IQiyiQuannengXiguavideoChannelAds extends IQiyiQuannengHudongReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.IQIYI_QUANNENG_XIGUAVIDEO;

    @Autowired
    private IQuannengXiguaVideoAdsDao qnxgAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.IQIYI_QUANNENG_XIGUAVIDEO;
    }

    @Override
    protected String channelName() {
        return IQiyiPath.IQIYI_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return qnxgAdsDao;
    }
}
