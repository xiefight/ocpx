package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.ILuyunKuaikanmanhuaAdsDao;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.luyun.LuyunXiaomiReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class XiaomiLuyunKuaikanmanhuaChannelAds extends LuyunXiaomiReportFactory {
    @Autowired
    private ILuyunKuaikanmanhuaAdsDao luyunKuaikanmanhuaAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_LUYUN_KUAIKANMANHUA;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.XIAOMI_LUYUN_KUAIKANMANHUA;
    }

    @Override
    protected String channelName() {
        return XiaomiPath.XIAOMI_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return luyunKuaikanmanhuaAdsDao;
    }


}
