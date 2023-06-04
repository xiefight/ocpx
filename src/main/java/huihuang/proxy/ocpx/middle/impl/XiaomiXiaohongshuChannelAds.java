package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamField;
import huihuang.proxy.ocpx.ads.xiaohongshu.XiaohongshuPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IXiaohongshuAdsDao;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.XiaomiLiangdamaoReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 * @Author: xietao
 * @Date: 2023/6/4 10:12
 */
@Component("xiaomiXhsChannelAds")
public class XiaomiXiaohongshuChannelAds extends XiaomiLiangdamaoReportFactory {

    @Autowired
    private IXiaohongshuAdsDao xiaohongshuAdsDao;
    @Autowired
    private XiaohongshuPath xiaohongshuPath;

    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_XIAOHONGSHU;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.XIAOMI_XIAOHONGSHU;
    }

    @Override
    protected IMarkDao adsDao() {
        return xiaohongshuAdsDao;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        LiangdamaoParamField liangdamaoParamField = (LiangdamaoParamField) super.channelParamToAdsParam(parameterMap);
        liangdamaoParamField.setTp_adv_id(xiaohongshuPath.tpAdvId());
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, liangdamaoParamField);
        return liangdamaoParamField;
    }

}
