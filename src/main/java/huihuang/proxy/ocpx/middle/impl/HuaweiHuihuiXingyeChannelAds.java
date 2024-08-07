package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.huihui.HuihuiParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuiXingyeAdsDao;
import huihuang.proxy.ocpx.bussiness.service.basechannel.HuaweiChannelFactory;
import huihuang.proxy.ocpx.channel.huawei.HuaweiParamEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuiyoudao.HuaweiHuihuiReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("hhhxingyeChannelAds")
public class HuaweiHuihuiXingyeChannelAds extends HuaweiHuihuiReportFactory {

    @Autowired
    private IHuihuiXingyeAdsDao xingyeAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.HUAWEI_HUIHUI_XINGYE;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.HUAWEI_HUIHUI_XINGYE;
    }

    @Override
    protected IMarkDao adsDao() {
        return xingyeAdsDao;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        HuihuiParamField huihuiParamField = (HuihuiParamField) super.channelParamToAdsParam(parameterMap);
//        huihuiParamField.setTp_adv_id(tantanPath.tpAdvId());
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, huihuiParamField);
        huihuiParamField.setRedirect("false");
        if (HuaweiPath.HW_HH_XINGYE_ACCOUNT_01.equals(huihuiParamField.getOcpxAccount())) {
            huihuiParamField.setConv_ext("Ego1Nzk0MDc5MDc4GAEgAiqRBGh0dHBzOi8vc2F0LWIwLnNlbnNvcnNkYXRhLmNuL2FkL3RyYWNrP2NoYW5uZWxfbmFtZT15b3VkYW8yX3RyYWNrJmNoYW5uZWxfbGlua190eXBlPWFwcCZjYWxsYmFja19pZD1xeTJrckt6MyZfY2hhbm5lbF9hcHBfaWQ9Y29tLnhpbmd5ZS5hcHAmX2RhdGFfdmVyc2lvbj0wLjYuMSZwcm9qZWN0PWRlZmF1bHQmX2NoYW5uZWxfdHJhY2tfa2V5PXhWVzl5ZlRXJnRpZD1rcHMmbGlua192ZXJzaW9uPTEmb3M9X19vc19fJmlwPV9faXBfXyZjaGFubmVsX2FjY291bnRfaWQ9X19zcG9uc29yX2lkX18mdWE9X191YV9fJnBrZz1fX2FwcF9pZF9fJm1hYz1fX21hYzFfXyZjaGFubmVsX2NhbXBhaWduX2lkPV9fY2FtcGFpZ25faWRfXyZjaGFubmVsX2NsaWNrX2lkPV9fY29udl9fJmNoYW5uZWxfYWRfaWQ9X19jb250ZW50X2lkX18mY2xpY2tfdGltZT1fX3RzX18maW1laT1fX2ltZWlfXyZtb2RlbD1fX21vZGVsX18mYW5kcm9pZF9pZD1fX2FuZHJvaWRfaWRfXyZvYWlkPV9fb2FpZF9fJmNoYW5uZWxfYWRncm91cF9pZD1fX2dyb3VwX2lkX18ydGh0dHBzOi8vZmlsZWNkbi1vdGhlci54aW5neWVhaS5jb20veGluZ3llX29wZXJhdGlvbi9hcGsvNzE4OTM0OTRfMTcwNDI2NzI0MTgyMV94aW5neWVfcmVsZWFzZV8xLjkuMV94eV93YW5neWl4eGwuYXBrOhYI6P0CWM6gE2C840doxraJA3Dm1JkM");
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
