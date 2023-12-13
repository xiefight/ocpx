package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangXiguaAdsDao;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuangmingtian.XiaomiHuihuangReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("xhhxgChannelAds")
public class XiaomiHuihuangXiguavideoChannelAds extends XiaomiHuihuangReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_HUIHUANG_XIGUAVIDEO;

    @Autowired
    private IHuihuangXiguaAdsDao hhxgAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.XIAOMI_HUIHUANG_XIGUAVIDEO;
    }

    @Override
    protected String channelName() {
        return XiaomiPath.XIAOMI_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return hhxgAdsDao;
    }

}
