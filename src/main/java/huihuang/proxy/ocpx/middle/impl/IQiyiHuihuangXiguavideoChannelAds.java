package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangXiguaAdsDao;
import huihuang.proxy.ocpx.channel.iqiyi.IQiyiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuangmingtian.IQiyiHuihuangReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ihhxgChannelAds")
public class IQiyiHuihuangXiguavideoChannelAds extends IQiyiHuihuangReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.IQIYI_HUIHUANG_XIGUAVIDEO;

    @Autowired
    private IHuihuangXiguaAdsDao hhxgAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.IQIYI_HUIHUANG_XIGUAVIDEO;
    }

    @Override
    protected String channelName() {
        return IQiyiPath.IQIYI_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return hhxgAdsDao;
    }
}
