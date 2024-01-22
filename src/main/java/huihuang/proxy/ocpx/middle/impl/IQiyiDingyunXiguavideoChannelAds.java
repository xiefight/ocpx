package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IDingyunXiguaVideoAdsDao;
import huihuang.proxy.ocpx.channel.iqiyi.IQiyiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.dingyun.IQiyiDingyunReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("idyxgChannelAds")
public class IQiyiDingyunXiguavideoChannelAds extends IQiyiDingyunReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.IQIYI_DINGYUN_XIGUAVIDEO;

    @Autowired
    private IDingyunXiguaVideoAdsDao dyxgAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.IQIYI_DINGYUN_XIGUAVIDEO;
    }

    @Override
    protected String channelName() {
        return IQiyiPath.IQIYI_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return dyxgAdsDao;
    }
}
