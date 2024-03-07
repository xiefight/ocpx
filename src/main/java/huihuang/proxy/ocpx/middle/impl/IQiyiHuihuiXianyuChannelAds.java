package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.huihui.HuihuiParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuiXianyuAdsDao;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuiyoudao.IQiyiHuihuiReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("iqyxyChannelAds")
public class IQiyiHuihuiXianyuChannelAds extends IQiyiHuihuiReportFactory {

    @Autowired
    private IHuihuiXianyuAdsDao xianyuAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.IQIYI_HUIHUI_XIANYU;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.IQIYI_HUIHUI_XIANYU;
    }

    @Override
    protected IMarkDao adsDao() {
        return xianyuAdsDao;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        HuihuiParamField huihuiParamField = (HuihuiParamField) super.channelParamToAdsParam(parameterMap);
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, huihuiParamField);
        huihuiParamField.setRedirect("false");
//        huihuiParamField.setConv_ext("Ego0ODY1NDM5NzIyGAEgAiqLA2h0dHBzOi8vbWt0LWFkdmVydGlzZW1lbnQudHVodS5jbi9leHQvYWR2ZXJ0aXNlbWVudC93YW5neWkvY2xpY2s_Y29udj1fX2NvbnZfXyZkZXZpY2VJZD1fX2RldmljZV9pZF9fJmltZWk9X19pbWVpX18mcmVxSWQ9X19yZXFfaWRfXyZhbmRyb2lkSWQ9X19hbmRyb2lkX2lkX18mb2FpZD1fX29haWRfXyZpZGZhPV9faWRmYV9fJnNwb25zb3JJZD1fX3Nwb25zb3JfaWRfXyZjYW1wYWlnbklkPV9fY2FtcGFpZ25faWRfXyZncm91cElkPV9fZ3JvdXBfaWRfXyZjb250ZW50SWQ9X19jb250ZW50X2lkX18mZGV2ZWxvcGVySWQ9X19kZXZlbG9wZXJfaWRfXyZhcHBJZD1fX2FwcF9pZF9fJmlwPV9faXBfXyZvcz1fX29zX18mdHM9X190c19fJnVhPV9fdWFfXyZtYWM9X19tYWNfXyZtYWMxPV9fbWFjMV9fMlQgaHR0cHM6Ly9jbC1nYXRld2F5LnR1aHUuY24vY2wtY29tbW9uLWFwaS9hcGkvZ2V0QXBwTGlua1JlZGlyZWN0P2NvZGU9M0JVWExWVEtLWi5hcGs6FgjElAJY2JcTYLKMR2je_YcDcIKLlww");
        return huihuiParamField;
    }

}
