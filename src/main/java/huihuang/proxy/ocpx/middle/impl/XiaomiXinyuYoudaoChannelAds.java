package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.huihui.HuihuiParamField;
import huihuang.proxy.ocpx.ads.huihui.xinyu.HuihuiXinyuPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuiXinyuAdsDao;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuiyoudao.XiaomiHuihuiReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: xietao
 * @Date: 2023/6/8 19:39
 */
@Component("xiaomiXinyuYoudaoChannelAds")
public class XiaomiXinyuYoudaoChannelAds extends XiaomiHuihuiReportFactory {

    @Autowired
    private IHuihuiXinyuAdsDao xinyuyoudaoAdsDao;
    @Autowired
    private HuihuiXinyuPath xinyuyoudaoPath;

    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_XINYU_YOUDAO;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.XIAOMI_HUIHUI_XINYU;
    }

    @Override
    protected IMarkDao adsDao() {
        return xinyuyoudaoAdsDao;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        HuihuiParamField huihuiParamField = (HuihuiParamField) super.channelParamToAdsParam(parameterMap);
//        huihuiParamField.setTp_adv_id(tantanPath.tpAdvId());
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, huihuiParamField);
        huihuiParamField.setRedirect("false");
        huihuiParamField.setConv_ext("Ego0OTU2NjY0ODkyGAEgAiqSA2h0dHBzOi8vc2hhcmstdHJhY2VyLm5ldGVhc2UuY29tL3YxL2xvZy9kZWZhdWx0P3Byb2plY3RfaWQ9MTA1NzIyMTE2JnNpZ249T3dQazg2SlJjQkhEUnU3c2szZWd5clcyajh6Y2dDMXgmbG9nX3R5cGU9ZHJwZl9jbGljayZvcz1hbmRyb2lkJmNvbnY9X19jb252X18maW1laT1fX2ltZWlfXyZ1ZGlkPV9fYW5kcm9pZF9pZF9fJm9haWQ9X19vYWlkX18mY2hhbm5lbF9pcD1fX2lwX18meW91ZGFvX3VkaWQ9X19kZXZpY2VfaWRfXyZyZXFfaWQ9X19yZXFfaWRfXyZhZHZlcnRpc2VyX2lkPV9fc3BvbnNvcl9pZF9fJmNhbXBhaWduX2lkPV9fY2FtcGFpZ25faWRfXyZhZGdyb3VwX2lkPV9fZ3JvdXBfaWRfXyZjaWQ9X19jb250ZW50X2lkX18mdWE9X191YV9fJm9zX3NyYz1fX29zX18mZGV2aWNlPV9fbW9kZWxfXzJKaHR0cHM6Ly9hcGkubW95aS4xNjMuY29tL2FwaS9wYWNrYWdlL2Rvd25sb2FkL2xhdGVzdD9tYXJrZXQ9eW91ZGFvd3kwMi5hcGs6FgjwhgJY8pITYIbtRmimuocDcM75lQw");
        return huihuiParamField;
    }

}
