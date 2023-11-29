package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.fanqie.FanqiePath;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.IFanqieAdsDao;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.liangdamao.BaiduLiangdamaoReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * baidu--ltjd
 *
 * @Author: xietao
 * @Date: 2023/5/10 22:28
 */
@Component("bfChannelAds")
public class BaiduFanqieChannelAds extends BaiduLiangdamaoReportFactory {

    @Autowired
    private FanqiePath fanqiePath;
    @Autowired
    private IFanqieAdsDao fanqieAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_FANQIE;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_FANQIE;
    }

    @Override
    protected IMarkDao adsDao() {
        return fanqieAdsDao;
    }


    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        LiangdamaoParamField liangdamaoParamField = (LiangdamaoParamField) super.channelParamToAdsParam(parameterMap);
        liangdamaoParamField.setTp_adv_id(fanqiePath.tpAdvId());
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, liangdamaoParamField);
        return liangdamaoParamField;
    }

}
