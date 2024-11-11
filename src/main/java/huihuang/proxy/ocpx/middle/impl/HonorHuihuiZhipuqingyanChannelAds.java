package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.huihui.HuihuiParamField;
import huihuang.proxy.ocpx.ads.huihui.HuihuiPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuiZhipuqingyanAdsDao;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuiyoudao.BaiduHuihuiReportFactory;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuiyoudao.HonorHuihuiReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("honorzhipuqingyanChannelAds")
public class HonorHuihuiZhipuqingyanChannelAds extends HonorHuihuiReportFactory {

    @Autowired
    private IHuihuiZhipuqingyanAdsDao zhipuqingyanAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.HONOR_HUIHUI_ZHIPUQINGYAN;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.HONOR_HUIHUI_ZHIPUQINGYAN;
    }

    @Override
    protected IMarkDao adsDao() {
        return zhipuqingyanAdsDao;
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
//        if (BaiduPath.BAIDU_HUIHUI_HEMADUANJU_ACCOUNT_01.equals(huihuiParamField.getOcpxAccount())) {
//            //默认
//            huihuiParamField.setConv_ext("Ego2NDg4MzU3MjExGAEgAiqgBGh0dHBzOi8vbW9uaXRvci56cWlkcy5jb20vZ2xvcnktYWR2ZXJ0L2FkLzEwMTcvMzA_Y29udj1fX2NvbnZfXyZkZXZpY2VfaWQ9X19kZXZpY2VfaWRfXyZpbWVpPV9faW1laV9fJmFuZHJvaWRfaWQ9X19hbmRyb2lkX2lkX18mb2FpZD1fX29haWRfXyZvYWlkX21kNT1fX29haWRfbWQ1X18mY2FpZD1fX2NhaWRfXyZjYWlkX21kNT1fX2NhaWRfbWQ1X18mYWFpZD1fX2FhaWRfXyZyZXFfaWQ9X19yZXFfaWRfXyZzcG9uc29yX2lkPV9fc3BvbnNvcl9pZF9fJmFjY2lkPV9fY2FtcGFpZ25faWRfXyZwaWQ9X19ncm91cF9pZF9fJmNpZD1fX2NvbnRlbnRfaWRfXyZkZXZlbG9wZXJfaWQ9X19kZXZlbG9wZXJfaWRfXyZhcHBfaWQ9X19hcHBfaWRfXyZpcD1fX2lwX18mdWE9X191YV9fJm1hYz1fX21hY19fJm1hYzE9X19tYWMxX18mb3M9X19vc19fJm1vZGVsPV9fbW9kZWxfXyZ0cz1fX3RzX18maXNOZXdWZXJzaW9uPTAmcG5hbWU9Y29tLmR6LmhtamMmY2hhbm5lbENvZGU9SE1URkZPWDEwMDAxOTMmYm9va0lkPTQxMDAwMTA1NzgyJmNoYXB0ZXJJZD01Nzg2ODUwNjUyswFodHRwczovL2t5eS1kbC5kemhyZWFkLmNuL2R6LWRvd24tcGsvYmFzZS9kb3duUGsvZG93bi5hcGs_cGtuYT1jb20uZHouaG1qYyZjaGFubmVsQ29kZT1ITVRGRk9YMTAwMDE5MyZib29rSWQ9NDEwMDAxMDU3ODImY2hhcHRlcklkPTU3ODY4NTA2NSZ0b2tlbj02MzNlNzc4ODgzMWE0OTgyMmIyODIxMzRmNDc3YTgzYToWCOGRA1i8nxNgsNFIaOSPiwNwjrScDA");
//        }
        huihuiParamField.setDeveloper_id("5f7a582066184032");
        return huihuiParamField;
    }

}
