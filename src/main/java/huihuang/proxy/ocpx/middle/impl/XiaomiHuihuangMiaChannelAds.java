package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangMiaAdsDao;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuangmingtian.XiaomiHuihuangReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("xhhmiaChannelAds")
public class XiaomiHuihuangMiaChannelAds extends XiaomiHuihuangReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_HUIHUANG_MIA;

    @Autowired
    private IHuihuangMiaAdsDao hhmiaAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.XIAOMI_HUIHUANG_MIA;
    }

    @Override
    protected String channelName() {
        return XiaomiPath.XIAOMI_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return hhmiaAdsDao;
    }


    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        HuihuangmingtianParamField huihuangmingtianParamField = (HuihuangmingtianParamField) super.channelParamToAdsParam(parameterMap);
        huihuangmingtianParamField.setVersion("v2");
        huihuangmingtianParamField.setCid("true");
        return huihuangmingtianParamField;
    }

}
