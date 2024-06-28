package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangJingdongAdsDao;
import huihuang.proxy.ocpx.channel.iqiyi.IQiyiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuangmingtian.IQiyiHuihuangReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ihhjdChannelAds")
public class IQiyiHuihuangFengmangJingdongChannelAds extends IQiyiHuihuangReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.IQIYI_HUIHUANG_JINGDONG;

    @Autowired
    private IHuihuangJingdongAdsDao hhjdAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.IQIYI_HUIHUANG_JINGDONG;
    }

    @Override
    protected String channelName() {
        return IQiyiPath.IQIYI_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return hhjdAdsDao;
    }
}
