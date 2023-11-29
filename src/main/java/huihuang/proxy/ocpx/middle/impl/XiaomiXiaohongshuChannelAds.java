package huihuang.proxy.ocpx.middle.impl;

import cn.hutool.crypto.digest.DigestUtil;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamField;
import huihuang.proxy.ocpx.ads.xiaohongshu.XiaohongshuPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IXiaohongshuAdsDao;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.liangdamao.XiaomiLiangdamaoReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;
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

    //上报给小红书时，如果有oaid，则oaidMd5必传，而小米没有oaidMD5，所以作特殊处理
    @Override
    protected Object saveOriginParamData(Object adsObj) {
        LiangdamaoParamField liangdamaoParamField = (LiangdamaoParamField) adsObj;
        if (null != liangdamaoParamField.getOaid()) {
            liangdamaoParamField.setOaid_md5(DigestUtil.md5Hex(liangdamaoParamField.getOaid()).toLowerCase(Locale.ROOT));
        }
        return super.saveOriginParamData(adsObj);
    }
}
