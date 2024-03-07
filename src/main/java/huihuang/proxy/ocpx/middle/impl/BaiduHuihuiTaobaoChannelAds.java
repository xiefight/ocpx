package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.huihui.HuihuiParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuiTaobaoAdsDao;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuiyoudao.BaiduHuihuiReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("bdhhtbChannelAds")
public class BaiduHuihuiTaobaoChannelAds extends BaiduHuihuiReportFactory {

    @Autowired
    private IHuihuiTaobaoAdsDao taobaoAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_HUIHUI_TAOBAO;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_HUIHUI_TAOBAO;
    }

    @Override
    protected IMarkDao adsDao() {
        return taobaoAdsDao;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        HuihuiParamField huihuiParamField = (HuihuiParamField) super.channelParamToAdsParam(parameterMap);
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, huihuiParamField);
        huihuiParamField.setRedirect("false");
        return huihuiParamField;
    }

}
