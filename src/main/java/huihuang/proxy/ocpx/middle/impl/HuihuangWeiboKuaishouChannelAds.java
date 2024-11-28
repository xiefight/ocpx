package huihuang.proxy.ocpx.middle.impl;

import cn.hutool.core.net.URLEncoder;
import huihuang.proxy.ocpx.ads.weibo.WeiboParamField;
import huihuang.proxy.ocpx.ads.weibo.kuaishou.WeiboKuaishouPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IWeiboKuaishouAdsDao;
import huihuang.proxy.ocpx.channel.huihuang.HuihuangChannelPath;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.weibo.HuihuangWeiboReportFactory;
import huihuang.proxy.ocpx.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Component("hhwbksChannelAds")
public class HuihuangWeiboKuaishouChannelAds extends HuihuangWeiboReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.HUIHUANG_WEIBO_KUAISHOU;

    @Autowired
    private IWeiboKuaishouAdsDao wbksAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.HUIHUANG_WEIBO_KUAISHOU;
    }

    @Override
    protected String channelName() {
        return HuihuangChannelPath.CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return wbksAdsDao;
    }

    @Override
    protected String initAdsUrl() {
        return null;
    }

    @Override
    protected String initAdsUrl(Object adsObj) {
        WeiboParamField weiboParamField = (WeiboParamField) adsObj;
        String monitorType = weiboParamField.getMonitorType();
        if ("0".equals(monitorType)) {
            //曝光监测
            return WeiboKuaishouPath.EXPOSURE_URI;
        } else {
            //点击监测
            return WeiboKuaishouPath.BASIC_URI;
        }
    }


    @Override
    protected void convertParams(Object adsObj) {
        super.convertParams(adsObj);
        WeiboParamField weiboParamField = (WeiboParamField) adsObj;
        if (weiboParamField.getAccount_id() == null){
            weiboParamField.setAccount_id("hhwbks01");
        }
        logger.info("clickReport {} 特殊参数进行转换 convertParams:{}", channelAdsKey(), weiboParamField);
    }

}
