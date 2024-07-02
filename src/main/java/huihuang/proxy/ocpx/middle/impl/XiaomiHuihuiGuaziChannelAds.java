package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.huihui.HuihuiParamField;
import huihuang.proxy.ocpx.ads.huihui.tantan.HuihuiTantanPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuiGuaziAdsDao;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuiyoudao.XiaomiHuihuiReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("xmhhgzChannelAds")
public class XiaomiHuihuiGuaziChannelAds extends XiaomiHuihuiReportFactory {

    @Autowired
    private IHuihuiGuaziAdsDao guaziAdsDao;
    @Autowired
    private HuihuiTantanPath huihuiTantanPath;

    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_HUIHUI_GUAZI;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.XIAOMI_HUIHUI_GUAZI;
    }

    @Override
    protected IMarkDao adsDao() {
        return guaziAdsDao;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        HuihuiParamField huihuiParamField = (HuihuiParamField) super.channelParamToAdsParam(parameterMap);
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, huihuiParamField);
        huihuiParamField.setRedirect("false");
        if (null == huihuiParamField.getConv_ext()){
            huihuiParamField.setConv_ext("Ego2MjM5NDYzNDI0GAEgAiroAmh0dHBzOi8vc2VtcGFnZS5ndWF6aS5jb20vbW9uaXRvcl9hY3RpdmUvd2FuZ3lpP2NvbnY9X19jb252X18mbWQ1SW1laT1fX2ltZWlfXyZtZDVBbmRyb2lkSWQ9X19hbmRyb2lkX2lkX18mb2FpZD1fX29haWRfXyZtZDVPYWlkPV9fb2FpZF9tZDVfXyZhY2NvdW50SWQ9X19zcG9uc29yX2lkX18mZ2lkPV9fY2FtcGFpZ25faWRfXyZwaWQ9X19ncm91cF9pZF9fJmNpZD1fX2NvbnRlbnRfaWRfXyZpcD1fX2lwX18mdWE9X191YV9fJnRzPV9fdHNfXyZtZWRpYV90eXBlPTYwMzQmcHVibGlzaGVyVHlwZT02MDM0JmFnZW5jeT0mbGluZT0yJnRhcmdldFVzZXJUeXBlPTEmY2Ffcz10Z193eWhqbHgwMWFkJmNhX249d3loamx4MDFhZG1haW4yOTJWaHR0cHM6Ly9zZW1wYWdlLmd1YXppLmNvbS9tYXJrZXQvYXBwZG93bj9jYV9zPXRnX3d5aGpseDAxYWQmY2Ffbj13eWhqbHgwMWFkbWFpbiZsaW5lPTI6FgjP8gJYlJsTYIujSGittooDcOytmww");
        }
        return huihuiParamField;
    }

}
