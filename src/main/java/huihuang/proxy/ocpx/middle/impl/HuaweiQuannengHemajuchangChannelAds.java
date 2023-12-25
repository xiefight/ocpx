package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IQuannengHemajuchangAdsDao;
import huihuang.proxy.ocpx.channel.huawei.HuaweiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.quannenghudong.HuaweiQuannengHudongReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("hqhemajuchangChannelAds")
public class HuaweiQuannengHemajuchangChannelAds extends HuaweiQuannengHudongReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.HUAWEI_QUANNENG_HEMAJUCHANG;

    @Autowired
    private IQuannengHemajuchangAdsDao hmjcAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.HUAWEI_QUANNENG_HEMAJUCHANG;
    }

    @Override
    protected String channelName() {
        return HuaweiPath.HUAWEI_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return hmjcAdsDao;
    }


}
