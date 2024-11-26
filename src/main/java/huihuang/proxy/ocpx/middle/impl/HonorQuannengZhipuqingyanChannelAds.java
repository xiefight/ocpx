package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IQuannengZhipuqingyanAdsDao;
import huihuang.proxy.ocpx.channel.honor.HonorPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.quannenghudong.BaiduQuannengHudongReportFactory;
import huihuang.proxy.ocpx.middle.baseadsreport.quannenghudong.HonorQuannengHudongReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("honorqnzpqyChannelAds")
public class HonorQuannengZhipuqingyanChannelAds extends HonorQuannengHudongReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.HONOR_QUANNENG_ZHIPUQINGYAN;

    @Autowired
    private IQuannengZhipuqingyanAdsDao zpqyAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.HONOR_QUANNENG_ZHIPUQINGYAN;
    }

    @Override
    protected String channelName() {
        return HonorPath.HONOR_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return zpqyAdsDao;
    }


}
