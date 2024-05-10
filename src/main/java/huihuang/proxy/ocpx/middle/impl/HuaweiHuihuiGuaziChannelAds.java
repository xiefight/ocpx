package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.huihui.HuihuiParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuiGuaziAdsDao;
import huihuang.proxy.ocpx.bussiness.service.basechannel.HuaweiChannelFactory;
import huihuang.proxy.ocpx.channel.huawei.HuaweiParamEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuiyoudao.HuaweiHuihuiReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("hhhgzChannelAds")
public class HuaweiHuihuiGuaziChannelAds extends HuaweiHuihuiReportFactory {

    @Autowired
    private IHuihuiGuaziAdsDao guaziAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.HUAWEI_HUIHUI_GUAZI;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.HUAWEI_HUIHUI_GUAZI;
    }

    @Override
    protected IMarkDao adsDao() {
        return guaziAdsDao;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        HuihuiParamField huihuiParamField = (HuihuiParamField) super.channelParamToAdsParam(parameterMap);
//        huihuiParamField.setTp_adv_id(tantanPath.tpAdvId());
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, huihuiParamField);
        huihuiParamField.setRedirect("false");
        if (HuaweiPath.HW_HH_GUAZI_ACCOUNT_01.equals(huihuiParamField.getOcpxAccount())) {
            huihuiParamField.setConv_ext("Ego1MzM2MDk0ODM1GAEgAiroAmh0dHBzOi8vc2VtcGFnZS5ndWF6aS5jb20vbW9uaXRvcl9hY3RpdmUvd2FuZ3lpP2NvbnY9X19jb252X18mbWQ1SW1laT1fX2ltZWlfXyZtZDVBbmRyb2lkSWQ9X19hbmRyb2lkX2lkX18mb2FpZD1fX29haWRfXyZtZDVPYWlkPV9fb2FpZF9tZDVfXyZhY2NvdW50SWQ9X19zcG9uc29yX2lkX18mZ2lkPV9fY2FtcGFpZ25faWRfXyZwaWQ9X19ncm91cF9pZF9fJmNpZD1fX2NvbnRlbnRfaWRfXyZpcD1fX2lwX18mdWE9X191YV9fJnRzPV9fdHNfXyZtZWRpYV90eXBlPTYwMzQmcHVibGlzaGVyVHlwZT02MDM0JmFnZW5jeT0mbGluZT0yJnRhcmdldFVzZXJUeXBlPTEmY2Ffcz10Z193eWhqbHgwMWFkJmNhX249d3loamx4MDFhZG1haW4wNTJWaHR0cHM6Ly9zZW1wYWdlLmd1YXppLmNvbS9tYXJrZXQvYXBwZG93bj9jYV9zPXRnX3d5aGpseDAxYWQmY2Ffbj13eWhqbHgwMWFkbWFpbiZsaW5lPTI6FgjOpgJYlJsTYJKrR2iAvYgDcISBmAw");
        }

        String extras = HuaweiChannelFactory.fitExtras(parameterMap,
                HuaweiParamEnum.CONTENT_ID.getParam(),
                HuaweiParamEnum.EVENT_TYPE.getParam(),
                HuaweiParamEnum.TRACE_TIME.getParam(),
                HuaweiParamEnum.TRACKING_ENABLED.getParam(),
                HuaweiParamEnum.CAMPAIGN_ID.getParam());
        if (extras.length() > 0) {
            huihuiParamField.setExtra(extras);
        }

        return huihuiParamField;
    }

}
