package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.huihui.HuihuiParamField;
import huihuang.proxy.ocpx.ads.huihui.HuihuiPath;
import huihuang.proxy.ocpx.ads.huihui.tt.HuihuiTTPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuiTTAdsDao;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuiyoudao.XiaomiHuihuiReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("xiaomiTTYoudaoChannelAds")
public class XiaomiTTYoudaoChannelAds extends XiaomiHuihuiReportFactory {

    @Autowired
    private IHuihuiTTAdsDao ttyoudaoAdsDao;
    @Autowired
    private HuihuiTTPath ttyoudaoPath;

    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_TT_YOUDAO;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.XIAOMI_HUIHUI_TT;
    }

    @Override
    protected IMarkDao adsDao() {
        return ttyoudaoAdsDao;
    }

    @Override
    protected String initAdsUrl() {
        return HuihuiPath.BASIC_URI_2;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        HuihuiParamField huihuiParamField = (HuihuiParamField) super.channelParamToAdsParam(parameterMap);
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, huihuiParamField);
        huihuiParamField.setRedirect("false");
        return huihuiParamField;
    }

}
