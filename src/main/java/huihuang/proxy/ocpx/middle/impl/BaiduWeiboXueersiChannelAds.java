package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.weibo.xueersi.WeiboXueersiPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IWeiboXueersiAdsDao;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.weibo.BaiduWeiboReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("bdwbxesChannelAds")
public class BaiduWeiboXueersiChannelAds extends BaiduWeiboReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_WEIBO_XUEERSI;

    @Autowired
    private IWeiboXueersiAdsDao wbxesAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_WEIBO_XUEERSI;
    }

    @Override
    protected String channelName() {
        return BaiduPath.BAIDU_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return wbxesAdsDao;
    }

    @Override
    protected String initAdsUrl() {
        //点击监测
        return WeiboXueersiPath.BASIC_URI;
    }
}
