package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.huihui.HuihuiParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuiTongchengAdsDao;
import huihuang.proxy.ocpx.bussiness.service.basechannel.HuaweiChannelFactory;
import huihuang.proxy.ocpx.channel.huawei.HuaweiParamEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuiyoudao.HuaweiHuihuiReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("hhhtcChannelAds")
public class HuaweiHuihuiTongchengChannelAds extends HuaweiHuihuiReportFactory {

    @Autowired
    private IHuihuiTongchengAdsDao tongchengAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.HUAWEI_HUIHUI_TONGCHENG;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.HUAWEI_HUIHUI_TONGCHENG;
    }

    @Override
    protected IMarkDao adsDao() {
        return tongchengAdsDao;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        HuihuiParamField huihuiParamField = (HuihuiParamField) super.channelParamToAdsParam(parameterMap);
//        huihuiParamField.setTp_adv_id(tantanPath.tpAdvId());
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, huihuiParamField);
        huihuiParamField.setRedirect("false");
        if (HuaweiPath.HW_HH_TONGCHENG_ACCOUNT_01.equals(huihuiParamField.getOcpxAccount())) {
            huihuiParamField.setConv_ext("Ego1MTIwNjY3NjMxGAEgAirGA2h0dHBzOi8vYWR2ZXJ0LjU4LmNvbS93YW5neWk_Y29udj1fX2NvbnZfXyZkZXZpY2U9X19kZXZpY2VfaWRfXyZpbWVpPV9faW1laV9fJmFuZHJvaWRfaWQ9X19hbmRyb2lkX2lkX18mb2FpZD1fX29haWRfXyZvYWlkX21kNT1fX29haWRfbWQ1X18maWRmYT1fX2lkZmFfXyZpZGZhX21kNT1fX2lkZmFfbWQ1X18mY2FpZD1fX2NhaWRfXyZjYWlkX21kNT1fX2NhaWRfbWQ1X18mYWFpZD1fX2FhaWRfXyZyZXE9X19yZXFfaWRfXyZzcG9uc29yPV9fc3BvbnNvcl9pZF9fJmNhbXBhaWduPV9fY2FtcGFpZ25faWRfXyZncm91cD1fX2dyb3VwX2lkX18mY29udGVudD1fX2NvbnRlbnRfaWRfXyZkZXZlbG9wZXI9X19kZXZlbG9wZXJfaWRfXyZhcHA9X19hcHBfaWRfXyZpcD1fX2lwX18mdWE9X191YV9fJm1hYz1fX21hY19fJm1hYzE9X19tYWMxX18mb3M9X19vc19fJm1vZGVsPV9fbW9kZWxfXyZ0cz1fX3RzX18yOyBodHRwOi8vYXBpLndpcmVsZXNzLjU4LmNvbS9hcGkvcmVkaXJlY3QvZG93bi8xMzY2P3BpZD0xMzY2OhYIjv4CWN6lE2CKtkhoxNeKA3D32ZsM");
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
