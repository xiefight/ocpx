package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IQuannengYoushiAdsDao;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.quannenghudong.BaiduQuannengHudongReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("bqysChannelAds")
public class BaiduQuannengYoushiChannelAds extends BaiduQuannengHudongReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_QUANNENG_YOUSHI;

    @Autowired
    private IQuannengYoushiAdsDao ysAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_QUANNENG_YOUSHI;
    }

    @Override
    protected String channelName() {
        return BaiduPath.BAIDU_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return ysAdsDao;
    }


}
