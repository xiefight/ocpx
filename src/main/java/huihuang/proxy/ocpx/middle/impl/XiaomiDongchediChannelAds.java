package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.dongchedi.DongchediPath;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.IDongchediAdsDao;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.liangdamao.XiaomiLiangdamaoReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 * @Author: xietao
 * @Date: 2023/8/4 13:28
 */
@Component
public class XiaomiDongchediChannelAds extends XiaomiLiangdamaoReportFactory {

    @Autowired
    private IDongchediAdsDao dongchediAdsDao;
    @Autowired
    private DongchediPath dongchediPath;

    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_DONGCHEDI;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.XIAOMI_DONGCHEDI;
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
