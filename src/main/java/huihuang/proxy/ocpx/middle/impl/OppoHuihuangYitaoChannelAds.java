package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangYitaoAdsDao;
import huihuang.proxy.ocpx.channel.oppo.OppoPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuangmingtian.OppoHuihuangReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("oppohhytChannelAds")
public class OppoHuihuangYitaoChannelAds extends OppoHuihuangReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.OPPO_HUIHUANG_YITAO;

    @Autowired
    private IHuihuangYitaoAdsDao hhytAdsOppoDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.OPPO_HUIHUANG_YITAO;
    }

    @Override
    protected String channelName() {
        return OppoPath.OPPO_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return hhytAdsOppoDao;
    }

}
