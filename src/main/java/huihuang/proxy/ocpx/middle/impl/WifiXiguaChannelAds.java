package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamField;
import huihuang.proxy.ocpx.ads.xiguavideo.XiguaPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IXiguaAdsDao;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.liangdamao.WifiLiangdamaoReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * wifi-xigua
 *
 * @Author: xietao
 * @Date: 2023/5/9 21:04
 */
@Component("wxChannelAds")
public class WifiXiguaChannelAds extends WifiLiangdamaoReportFactory {

    @Autowired
    private XiguaPath xiguaPath;

    @Autowired
    private IXiguaAdsDao xiguaAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.WIFI_XIGUA;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.WIFI_XIGUA;
    }

    @Override
    protected IMarkDao adsDao() {
        return xiguaAdsDao;
    }


    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        LiangdamaoParamField liangdamaoParamField = (LiangdamaoParamField) super.channelParamToAdsParam(parameterMap);
        liangdamaoParamField.setTp_adv_id(xiguaPath.tpAdvId());
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, liangdamaoParamField);
        return liangdamaoParamField;
    }

}
