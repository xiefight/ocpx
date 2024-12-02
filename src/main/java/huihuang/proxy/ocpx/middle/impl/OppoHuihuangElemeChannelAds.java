package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianParamField;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianPath;
import huihuang.proxy.ocpx.ads.huihuangmingtian.ads.HuihuangElemePath;
import huihuang.proxy.ocpx.ads.weibo.WeiboParamField;
import huihuang.proxy.ocpx.ads.weibo.kuaishou.WeiboKuaishoujisuPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangElemeAdsDao;
import huihuang.proxy.ocpx.channel.oppo.OppoPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuangmingtian.OppoHuihuangReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("oppohhelemeChannelAds")
public class OppoHuihuangElemeChannelAds extends OppoHuihuangReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.OPPO_HUIHUANG_ELEME;

    @Autowired
    private IHuihuangElemeAdsDao hhelemeAdsOppoDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.OPPO_HUIHUANG_ELEME;
    }

    @Override
    protected String channelName() {
        return OppoPath.OPPO_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return hhelemeAdsOppoDao;
    }

    @Override
    protected void convertParams(Object adsObj) {
        super.convertParams(adsObj);
        HuihuangmingtianParamField huihuangmingtianParamField = (HuihuangmingtianParamField) adsObj;
        huihuangmingtianParamField.setVersion("v2");
    }


    @Override
    protected String initAdsUrl() {
        return null;
    }

    @Override
    protected String initAdsUrl(Object adsObj) {
        HuihuangmingtianParamField huihuangmingtianParamField = (HuihuangmingtianParamField) adsObj;
        String monitorType = huihuangmingtianParamField.getMonitorType();
        if ("0".equals(monitorType)) {
            //曝光监测
            return HuihuangElemePath.EXPOSURE_URI;
        } else {
            //点击监测
            return HuihuangmingtianPath.BASIC_URI;
        }
    }
}
