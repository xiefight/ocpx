package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IQuannengXiaohongshuAdsDao;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.quannenghudong.XiaomiQuannengHudongReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("xqxhsChannelAds")
public class XiaomiQuannengXiaohongshuChannelAds extends XiaomiQuannengHudongReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_QUANNENG_XIAOHONGSHU;

    @Autowired
    private IQuannengXiaohongshuAdsDao xhsAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.XIAOMI_QUANNENG_XIAOHONGSHU;
    }

    @Override
    protected String channelName() {
        return XiaomiPath.XIAOMI_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return xhsAdsDao;
    }

}
