package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianParamField;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianPath;
import huihuang.proxy.ocpx.ads.huihuangmingtian.field.HuihuangmingtianParamFieldWphExposure;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangWeipinhuiAdsDao;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuangmingtian.BaiduHuihuangReportFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("bhhweipinhuiExposureChannelAds")
public class BaiduHuihuangWeipinhuiExposureChannelAds extends BaiduHuihuangReportFactory {

    @Autowired
    private IHuihuangWeipinhuiAdsDao hhweipinhuiAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_HUIHUANG_WEIPINHUI_EXPOSURE;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_HUIHUANG_WEIPINHUI_EXPOSURE;
    }

    @Override
    protected String channelName() {
        return BaiduPath.BAIDU_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return hhweipinhuiAdsDao;
    }

    @Override
    protected String initAdsUrl() {
        return HuihuangmingtianPath.EXPOSURE_URI;
    }


    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        HuihuangmingtianParamField huihuangParamField = (HuihuangmingtianParamField) super.channelParamToAdsParam(parameterMap);
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, huihuangParamField);
//        huihuangParamField.setVersion("v2");
        HuihuangmingtianParamFieldWphExposure weipinhuiExposureParamField = new HuihuangmingtianParamFieldWphExposure();
        BeanUtils.copyProperties(huihuangParamField, weipinhuiExposureParamField);

        weipinhuiExposureParamField.setType("1");
        weipinhuiExposureParamField.setApp_tracking_code("6cgrsayr");
        weipinhuiExposureParamField.setMonitor_spot_code("vv9t5lc9");

        return huihuangParamField;
    }

}
