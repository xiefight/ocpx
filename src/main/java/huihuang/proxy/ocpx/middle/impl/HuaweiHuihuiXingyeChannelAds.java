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
            huihuiParamField.setConv_ext("Ego2Mjk2MTk1NDM1GAEgAiqVBGh0dHBzOi8vc2F0LWIwLnNlbnNvcnNkYXRhLmNuL2FkL3RyYWNrP2NoYW5uZWxfbmFtZT15b3VkYW8yX3RyYWNrJmNoYW5uZWxfbGlua190eXBlPWFwcCZfc2NfdGlkPWtwcyZjYWxsYmFja19pZD1xeTJrckt6MyZfY2hhbm5lbF9hcHBfaWQ9Y29tLnhpbmd5ZS5hcHAmX2RhdGFfdmVyc2lvbj0wLjYuMSZwcm9qZWN0PWRlZmF1bHQmX2NoYW5uZWxfdHJhY2tfa2V5PUUxbmlKdFpiJmxpbmtfdmVyc2lvbj0xJm9zPV9fb3NfXyZpcD1fX2lwX18mY2hhbm5lbF9hY2NvdW50X2lkPV9fc3BvbnNvcl9pZF9fJnVhPV9fdWFfXyZwa2c");
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
