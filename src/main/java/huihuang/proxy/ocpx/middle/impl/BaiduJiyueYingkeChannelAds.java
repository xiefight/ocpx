package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IJiyueYingkeAdsDao;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.jiyue.BaiduJiyueReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("bdjiyueyingkeChannelAds")
public class BaiduJiyueYingkeChannelAds extends BaiduJiyueReportFactory {

    @Autowired
    private IJiyueYingkeAdsDao jiyueYingkeAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_JIYUE_YINGKE;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_JIYUE_YINGKE;
    }

    @Override
    protected String channelName() {
        return BaiduPath.BAIDU_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return jiyueYingkeAdsDao;
    }

}
