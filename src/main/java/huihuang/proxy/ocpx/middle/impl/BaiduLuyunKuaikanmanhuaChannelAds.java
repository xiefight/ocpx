package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.ILuyunKuaikanmanhuaAdsDao;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.luyun.LuyunBaiduReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("blykkmhChannelAds")
public class BaiduLuyunKuaikanmanhuaChannelAds extends LuyunBaiduReportFactory {

    @Autowired
    private ILuyunKuaikanmanhuaAdsDao luyunKkmhAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_LUYUN_KUAIKANMANHUA;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_LUYUN_KUAIKANMANHUA;
    }

    @Override
    protected String channelName() {
        return BaiduPath.BAIDU_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return luyunKkmhAdsDao;
    }


}
