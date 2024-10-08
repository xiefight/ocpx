package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangMiaAdsDao;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuangmingtian.BaiduHuihuangReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("bhhmiaChannelAds")
public class BaiduHuihuangMiaChannelAds extends BaiduHuihuangReportFactory {

    @Autowired
    private IHuihuangMiaAdsDao hhmiaAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_HUIHUANG_MIA;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_HUIHUANG_MIA;
    }

    @Override
    protected String channelName() {
        return BaiduPath.BAIDU_CHANNEL_NAME;
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
