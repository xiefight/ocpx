package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangFanqieAdsDao;
import huihuang.proxy.ocpx.channel.huawei.HuaweiPath;
import huihuang.proxy.ocpx.channel.iqiyi.IQiyiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuangmingtian.IQiyiHuihuangReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ihhfqChannelAds")
public class IQiyiHuihuangFanqieChannelAds extends IQiyiHuihuangReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.IQIYI_HUIHUANG_FANQIE;

    @Autowired
    private IHuihuangFanqieAdsDao hhfqAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.IQIYI_HUIHUANG_FANQIE;
    }

    @Override
    protected String channelName() {
        return IQiyiPath.IQIYI_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return hhfqAdsDao;
    }
}
