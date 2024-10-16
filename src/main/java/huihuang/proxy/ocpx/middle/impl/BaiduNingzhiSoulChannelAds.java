package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.INingzhiSoulAdsDao;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.buget.BaiduBupetReportFactory;
import huihuang.proxy.ocpx.middle.baseadsreport.ningzhi.BaiduNingzhiReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("bdningzhisoulChannelAds")
public class BaiduNingzhiSoulChannelAds extends BaiduNingzhiReportFactory {

    @Autowired
    private INingzhiSoulAdsDao ningzhiSoulAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_NINGZHI_SOUL;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_NINGZHI_SOUL;
    }

    @Override
    protected String channelName() {
        return BaiduPath.BAIDU_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return ningzhiSoulAdsDao;
    }

}
