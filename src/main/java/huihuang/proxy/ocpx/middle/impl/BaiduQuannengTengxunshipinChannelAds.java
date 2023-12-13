package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IQuannengTengxunshipinAdsDao;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.quannenghudong.BaiduQuannengHudongReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("bqtxspChannelAds")
public class BaiduQuannengTengxunshipinChannelAds extends BaiduQuannengHudongReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_QUANNENG_TENGXUNSHIPIN;

    @Autowired
    private IQuannengTengxunshipinAdsDao txspAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_QUANNENG_TENGXUNSHIPIN;
    }

    @Override
    protected String channelName() {
        return BaiduPath.BAIDU_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return txspAdsDao;
    }


}
