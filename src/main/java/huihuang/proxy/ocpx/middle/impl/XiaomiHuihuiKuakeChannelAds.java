package huihuang.proxy.ocpx.middle.impl;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.huihui.HuihuiParamField;
import huihuang.proxy.ocpx.ads.huihui.tantan.HuihuiTantanPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuiKuakeAdsDao;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuiyoudao.XiaomiHuihuiReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
        if (StrUtil.isEmpty(huihuiParamField.getConv_ext())){
            if ("xmhhkk02".equals(huihuiParamField.getOcpxAccount())){
                huihuiParamField.setConv_ext("Ego1NDYwNjgyMDgxGAEgAiqYBGh0dHBzOi8vdW5ldC5xdWFyay5jbi92My9hZC95b3VkYW8_dHlwZT1jbGljayZ0YXJnZXRQa2c9Y29tLnF1YXJrLmJyb3dzZXImY2FsbGJhY2tVcmw9aHR0cHMlM0ElMkYlMkZjb252LnlvdWRhby5jb20lMkZhcGklMkZ0cmFjayUzRmNvbnZfYWN0aW9uJTNEYW5kcm9pZF9hY3RpdmF0ZSUyNmNvbnZfZXh0JTNEX19jb252X18mY29udj1fX2NvbnZfXyZpbWVpU3VtPV9faW1laV9fJm9haWQ9X19vYWlkX18mb2FpZFN1bT1fX29haWRfbWQ1X18maWRmYT1fX2lkZmFfXyZpZGZhU3VtPV9faWRmYV9tZDVfXyZjYWlkPV9fY2FpZF9fJnJlcUlkPV9fcmVxX2lkX18mc3BvbnNvcklkPV9fc3BvbnNvcl9pZF9fJmNhbXBhaWduSWQ9X19jYW1wYWlnbl9pZF9fJmdyb3VwSWQ9X19ncm91cF9pZF9fJmNvbnRlbnRJZD1fX2NvbnRlbnRfaWRfXyZkZXZlbG9wZXJJZD1fX2RldmVsb3Blcl9pZF9fJmFwcElkPV9fYXBwX2lkX18mdGltZT1fX3RzXyZpcD1fX2lwX18mdWE9X191YV9fJm9zPV9fb3NfXyZjaD1ra0BxdWFya19hZF9hbmRfd3l5ZF9wc196ejAxXzI0MkhodHRwczovL2Rvd25sb2FkLnVjLmNuL2Rvd25sb2FkL3F1YXJrP2NoPWtrQHF1YXJrX2FkX2FuZF93eXlkX3BzX3p6MDFfMjQ6FgjorwJYlp4TYNy7R2iI4IgDcNjCmAw");
            }else {
                huihuiParamField.setConv_ext("Ego1ODEwODkzMTQ4GAEgAiqeBGh0dHBzOi8vdW5ldC5xdWFyay5jbi92My9hZC95b3VkYW8_dHlwZT1jbGljayZ0YXJnZXRQa2c9Y29tLnF1YXJrLmJyb3dzZXImY2FsbGJhY2tVcmw9aHR0cHMlM0ElMkYlMkZjb252LnlvdWRhby5jb20lMkZhcGklMkZ0cmFjayUzRmNvbnZfYWN0aW9uJTNEYW5kcm9pZF9hY3RpdmF0ZSUyNmNvbnZfZXh0JTNEX19jb252X18mY29udj1fX2NvbnZfXyZpbWVpU3VtPV9faW1laV9fJm9haWQ9X19vYWlkX18mb2FpZFN1bT1fX29haWRfbWQ1X18maWRmYT1fX2lkZmFfXyZpZGZhU3VtPV9faWRmYV9tZDVfXyZjYWlkPV9fY2FpZF9fJnJlcUlkPV9fcmVxX2lkX18mc3BvbnNvcklkPV9fc3BvbnNvcl9pZF9fJmNhbXBhaWduSWQ9X19jYW1wYWlnbl9pZF9fJmdyb3VwSWQ9X19ncm91cF9pZF9fJmNvbnRlbnRJZD1fX2NvbnRlbnRfaWRfXyZkZXZlbG9wZXJJZD1fX2RldmVsb3Blcl9pZF9fJmFwcElkPV9fYXBwX2lkX18mdGltZT1fX3RzXyZpcD1fX2lwX18mdWE9X191YV9fJm9zPV9fb3NfXyZjaD1ra0BxdWFya19hZF9hbmRfd3l5ZF93cF9waG90b196cV96el8xMk5odHRwczovL2Rvd25sb2FkLnVjLmNuL2Rvd25sb2FkL3F1YXJrP2NoPWtrQHF1YXJrX2FkX2FuZF93eXlkX3dwX3Bob3RvX3pxX3p6XzE6FgjsyQJYiqETYIjlR2jmuokDcLremQw");
            }
        }
//        huihuiParamField.setTp_adv_id(tantanPath.tpAdvId());
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, huihuiParamField);
        huihuiParamField.setRedirect("false");
        return huihuiParamField;
    }

}
