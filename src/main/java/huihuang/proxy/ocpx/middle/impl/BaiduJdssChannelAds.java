package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.jdsousuo.JDSSPath;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.IJdssAdsDao;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.liangdamao.BaiduLiangdamaoReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 * @Author: xietao
 * @Date: 2023/6/2 19:04
 */
@Component("bjdssChannelAds")
public class BaiduJdssChannelAds extends BaiduLiangdamaoReportFactory {

    @Autowired
    private JDSSPath jdssPath;
    @Autowired
    private IJdssAdsDao jdssAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_JDSS;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_JDSS;
    }

    @Override
    protected IMarkDao adsDao() {
        return jdssAdsDao;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        LiangdamaoParamField liangdamaoParamField = (LiangdamaoParamField) super.channelParamToAdsParam(parameterMap);
        liangdamaoParamField.setTp_adv_id(jdssPath.tpAdvId());
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, liangdamaoParamField);
        return liangdamaoParamField;
    }


}
