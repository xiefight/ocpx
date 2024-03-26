package huihuang.proxy.ocpx.middle.impl;

import cn.hutool.crypto.digest.MD5;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangJingdongAdsDao;
import huihuang.proxy.ocpx.channel.honor.HonorPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuangmingtian.HonorHuihuangReportFactory;
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


    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        HuihuangmingtianParamField huihuangmingtianParamField = (HuihuangmingtianParamField) super.channelParamToAdsParam(parameterMap);
        //辉煌京东v1.2版本，上报参数只有oaidMd5，没有oaid

        if (null != huihuangmingtianParamField.getOaid() && !"".equals(huihuangmingtianParamField.getOaid()) && !huihuangmingtianParamField.getOaid().equals("__OAID__")) {
            huihuangmingtianParamField.setOaidMd5(MD5.create().digestHex(huihuangmingtianParamField.getOaid()));
        }
        return huihuangmingtianParamField;
    }


}
