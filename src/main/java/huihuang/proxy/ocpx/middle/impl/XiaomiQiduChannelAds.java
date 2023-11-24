package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamEnum;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamField;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IQiduAdsDao;
import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.QiduReportFactory;
import huihuang.proxy.ocpx.middle.baseadsreport.XiaomiLiangdamaoReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Component
public class XiaomiQiduChannelAds extends QiduReportFactory {

    @Autowired
    private IQiduAdsDao qiduAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_QIDU;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.XIAOMI_QIDU;
    }

    @Override
    protected String channelName() {
        return XiaomiPath.XIAOMI_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return qiduAdsDao;
    }


}
