package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.ILuyunXiaohongshuAdsDao;
import huihuang.proxy.ocpx.channel.iqiyi.IQiyiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.luyun.LuyunIQiyiReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ilyxhsChannelAds")
public class IQiyiLuyunXiaohongshuChannelAds extends LuyunIQiyiReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.IQIYI_LUYUN_XIAOHONGSHU;

    @Autowired
    private ILuyunXiaohongshuAdsDao lyxhsAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.IQIYI_LUYUN_XIAOHONGSHU;
    }

    @Override
    protected String channelName() {
        return IQiyiPath.IQIYI_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return lyxhsAdsDao;
    }
}
