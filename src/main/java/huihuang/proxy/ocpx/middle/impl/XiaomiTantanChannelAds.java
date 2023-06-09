package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.huihui.HuihuiParamField;
import huihuang.proxy.ocpx.ads.tantan.TantanPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.ITantanAdsDao;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.XiaomiHuihuiReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: xietao
 * @Date: 2023/6/8 19:39
 */
@Component("xiaomiTantanChannelAds")
public class XiaomiTantanChannelAds extends XiaomiHuihuiReportFactory {

    @Autowired
    private ITantanAdsDao tantanAdsDao;
    @Autowired
    private TantanPath tantanPath;

    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_TANTAN;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.XIAOMI_TANTAN;
    }

    @Override
    protected IMarkDao adsDao() {
        return tantanAdsDao;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        HuihuiParamField huihuiParamField = (HuihuiParamField) super.channelParamToAdsParam(parameterMap);
//        huihuiParamField.setTp_adv_id(tantanPath.tpAdvId());
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, huihuiParamField);
        return huihuiParamField;
    }

}
