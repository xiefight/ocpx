package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.jingdongjinrong.JDJRPath;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.IJdjrAdsDao;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.liangdamao.BaiduLiangdamaoReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: xietao
 * @Date: 2023/5/30 20:55
 */
@Component("bjdjrChannelAds")
public class BaiduJdjrChannelAds extends BaiduLiangdamaoReportFactory {

    @Autowired
    private JDJRPath jdjrPath;
    @Autowired
    private IJdjrAdsDao jdjrAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_JDJR;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_JDJR;
    }

    @Override
    protected IMarkDao adsDao() {
        return jdjrAdsDao;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        LiangdamaoParamField liangdamaoParamField = (LiangdamaoParamField) super.channelParamToAdsParam(parameterMap);
        liangdamaoParamField.setTp_adv_id(jdjrPath.tpAdvId());
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, liangdamaoParamField);
        return liangdamaoParamField;
    }

}
