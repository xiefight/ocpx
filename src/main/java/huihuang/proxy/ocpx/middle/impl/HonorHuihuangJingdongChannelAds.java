package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangJingdongAdsDao;
import huihuang.proxy.ocpx.channel.honor.HonorPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuangmingtian.HonorHuihuangReportFactory;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuangmingtian.HuaweiHuihuangReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("honorhhjdChannelAds")
public class HonorHuihuangJingdongChannelAds extends HonorHuihuangReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.HONOR_HUIHUANG_JINGDONG;

    @Autowired
    private IHuihuangJingdongAdsDao hhjdAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.HONOR_HUIHUANG_JINGDONG;
    }

    @Override
    protected String channelName() {
        return HonorPath.HONOR_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return hhjdAdsDao;
    }


//    @Override
//    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
//        HuihuangmingtianParamField huihuangmingtianParamField = (HuihuangmingtianParamField) super.channelParamToAdsParam(parameterMap);
//        huihuangmingtianParamField.setApp("3");
//        huihuangmingtianParamField.setOs("0");
//        huihuangmingtianParamField.setEventType("1");
//        return huihuangmingtianParamField;
//    }


}
