package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.ILuyunPaipaiAdsDao;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.luyun.LuyunBaiduReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("blyppChannelAds")
public class BaiduLuyunPaipaiChannelAds extends LuyunBaiduReportFactory {

    @Autowired
    private ILuyunPaipaiAdsDao luyunPaipaiAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_LUYUN_PAIPAI;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_LUYUN_PAIPAI;
    }

    @Override
    protected String channelName() {
        return BaiduPath.BAIDU_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return luyunPaipaiAdsDao;
    }


}
