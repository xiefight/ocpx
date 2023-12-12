package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangHongguoduanjuAdsDao;
import huihuang.proxy.ocpx.channel.huawei.HuaweiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuangmingtian.HuaweiHuihuangReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("hhhhgdjChannelAds")
public class HuaweiHuihuangHongguoduanjuChannelAds extends HuaweiHuihuangReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.HUAWEI_HUIHUANG_HONGGUODUANJU;

    @Autowired
    private IHuihuangHongguoduanjuAdsDao hhhgdjAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.HUAWEI_HUIHUANG_HONGGUODUANJU;
    }

    @Override
    protected String channelName() {
        return HuaweiPath.HUAWEI_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return hhhgdjAdsDao;
    }


}
