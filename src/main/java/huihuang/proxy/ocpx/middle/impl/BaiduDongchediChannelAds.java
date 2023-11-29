package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.dongchedi.DongchediPath;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.IDongchediAdsDao;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.liangdamao.BaiduLiangdamaoReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: xietao
 * @Date: 2023/8/1 17:00
 */
@Component("bdcdChannelAds")
public class BaiduDongchediChannelAds extends BaiduLiangdamaoReportFactory {

    @Autowired
    private DongchediPath dongchediPath;
    @Autowired
    private IDongchediAdsDao dongchediAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_DONGCHEDI;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_DONGCHEDI;
    }

    @Override
    protected IMarkDao adsDao() {
        return dongchediAdsDao;
    }


    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        LiangdamaoParamField liangdamaoParamField = (LiangdamaoParamField) super.channelParamToAdsParam(parameterMap);
        liangdamaoParamField.setTp_adv_id(dongchediPath.tpAdvId());
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, liangdamaoParamField);
        return liangdamaoParamField;
    }

}
