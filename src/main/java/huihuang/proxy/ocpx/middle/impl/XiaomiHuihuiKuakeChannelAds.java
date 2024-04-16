package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.huihui.HuihuiParamField;
import huihuang.proxy.ocpx.ads.huihui.tantan.HuihuiTantanPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuiKuakeAdsDao;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuiyoudao.XiaomiHuihuiReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("xiaomiHuihuiKuakeChannelAds")
public class XiaomiHuihuiKuakeChannelAds extends XiaomiHuihuiReportFactory {

    @Autowired
    private IHuihuiKuakeAdsDao kuakeAdsDao;
    @Autowired
    private HuihuiTantanPath huihuiTantanPath;

    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_HUIHUI_KUAKE;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.XIAOMI_HUIHUI_KUAKE;
    }

    @Override
    protected IMarkDao adsDao() {
        return kuakeAdsDao;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        HuihuiParamField huihuiParamField = (HuihuiParamField) super.channelParamToAdsParam(parameterMap);
//        huihuiParamField.setTp_adv_id(tantanPath.tpAdvId());
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, huihuiParamField);
        huihuiParamField.setRedirect("false");
        return huihuiParamField;
    }

}
