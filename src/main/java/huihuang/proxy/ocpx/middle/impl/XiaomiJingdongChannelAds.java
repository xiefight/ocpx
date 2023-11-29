package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamField;
import huihuang.proxy.ocpx.ads.litianjingdong.LTJDPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.ILtjdAdsDao;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.liangdamao.XiaomiLiangdamaoReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description: ltjd-xiaomi
 * @Author: xietao
 * @Date: 2023-04-24 17:31
 **/
@Component
public class XiaomiJingdongChannelAds extends XiaomiLiangdamaoReportFactory {

    @Autowired
    private ILtjdAdsDao ltjdAdsDao;
    @Autowired
    private LTJDPath ltjdPath;

    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_LTJD;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.XIAOMI_LTJD;
    }

    @Override
    protected IMarkDao adsDao() {
        return ltjdAdsDao;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        LiangdamaoParamField liangdamaoParamField = (LiangdamaoParamField) super.channelParamToAdsParam(parameterMap);
        if (null == liangdamaoParamField.getTp_adv_id()){
            liangdamaoParamField.setTp_adv_id(ltjdPath.tpAdvId());
        }
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, liangdamaoParamField);
        return liangdamaoParamField;
    }

}
