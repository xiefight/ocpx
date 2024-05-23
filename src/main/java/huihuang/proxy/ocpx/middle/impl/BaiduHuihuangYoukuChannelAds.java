package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangYoukuAdsDao;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuangmingtian.BaiduHuihuangReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("bhhyoukuChannelAds")
public class BaiduHuihuangYoukuChannelAds extends BaiduHuihuangReportFactory {

    @Autowired
    private IHuihuangYoukuAdsDao hhyoukuAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_HUIHUANG_YOUKU;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_HUIHUANG_YOUKU;
    }

    @Override
    protected String channelName() {
        return BaiduPath.BAIDU_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return hhyoukuAdsDao;
    }

}
