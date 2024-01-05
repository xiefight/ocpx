package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IDingyunXiguaVideoAdsDao;
import huihuang.proxy.ocpx.channel.huawei.HuaweiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.dingyun.HuaweiDingyunReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("hdyxgChannelAds")
public class HuaweiDingyunXiguavideoChannelAds extends HuaweiDingyunReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.HUAWEI_DINGYUN_XIGUAVIDEO;

    @Autowired
    private IDingyunXiguaVideoAdsDao dyxgAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.HUAWEI_DINGYUN_XIGUAVIDEO;
    }

    @Override
    protected String channelName() {
        return HuaweiPath.HUAWEI_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return dyxgAdsDao;
    }


}
