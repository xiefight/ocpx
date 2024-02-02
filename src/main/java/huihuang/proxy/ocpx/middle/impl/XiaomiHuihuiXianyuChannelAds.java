package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.huihui.HuihuiParamField;
import huihuang.proxy.ocpx.ads.xianyu.HuihuiXianyuPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IXianyuAdsDao;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuiyoudao.XiaomiHuihuiReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("xiaomiXianyuChannelAds")
public class XiaomiHuihuiXianyuChannelAds extends XiaomiHuihuiReportFactory {

    @Autowired
    private IXianyuAdsDao xianyuAdsDao;
    @Autowired
    private HuihuiXianyuPath huihuiXianyuPath;

    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_XIANYU;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.XIAOMI_XIANYU;
    }

    @Override
    protected IMarkDao adsDao() {
        return xianyuAdsDao;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        HuihuiParamField huihuiParamField = (HuihuiParamField) super.channelParamToAdsParam(parameterMap);
//        huihuiParamField.setTp_adv_id(tantanPath.tpAdvId());
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, huihuiParamField);
        huihuiParamField.setRedirect("false");
//        huihuiParamField.setConv_ext("Ego0Nzg1NjU4MzM2GAEgAiqcA2h0dHBzOi8vYWZmaWxpYXRlLnRhbnRhbmFwcC5jb20vYWRzL3dhbmd5aXlvdWRhby9odWFqaW4tNTA_Y29udj1fX2NvbnZfXyZkZXZpY2VfaWQ9X19kZXZpY2VfaWRfXyZpbWVpPV9faW1laV9fJmFuZHJvaWRfaWQ9X19hbmRyb2lkX2lkX18mb2FpZD1fX29haWRfXyZpZGZhPV9faWRmYV9fJmNhaWQ9X19jYWlkX18mcmVxX2lkPV9fcmVxX2lkX18mc3BvbnNvcl9pZD1fX3Nwb25zb3JfaWRfXyZjYW1wYWlnbl9pZD1fX2NhbXBhaWduX2lkX18mZ3JvdXBfaWQ9X19ncm91cF9pZF9fJmNvbnRlbnRfaWQ9X19jb250ZW50X2lkX18mZGV2ZWxvcGVyX2lkPV9fZGV2ZWxvcGVyX2lkX18mYXBwX2lkPV9fYXBwX2lkX18maXA9X19pcF9fJm9zPV9fb3NfXyZtYWM9X19tYWNfXyZtYWMxPV9fbWFjMV9fJnVhPV9fdWFfXyZ0cz1fX3RzX18yNWh0dHBzOi8vdGFudGFuYXBwLmNvbS90YW50YW5fV2FuZ3lpeW91ZGFvSHVhamluNTAuYXBrOhYIjIcCWMKUE2Cy7UZo9rqHA3Ds-pUM");
        return huihuiParamField;
    }

}