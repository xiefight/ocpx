package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianParamField;
import huihuang.proxy.ocpx.ads.huihui.HuihuiParamField;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangFengmangXianyuAdsDao;
import huihuang.proxy.ocpx.cache.impl.GDTCache;
import huihuang.proxy.ocpx.channel.guangdiantong.GuangdiantongPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.exception.RepeatException;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuangmingtian.GdtHuihuangReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("gdthhfmxyChannelAds")
public class GdtHuihuangZhitouXianyuChannelAds extends GdtHuihuangReportFactory {

    @Autowired
    private IHuihuangFengmangXianyuAdsDao xianyuFmAdsDao;
    @Autowired
    private GDTCache gdtCache;

    String channelAdsKey = Constants.ChannelAdsKey.GDT_HUIHUANG_XIANYU;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.GDT_HUIHUANG_XIANYU;
    }

    @Override
    protected String channelName() {
        return GuangdiantongPath.GUANGDIANTONG_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return xianyuFmAdsDao;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        HuihuangmingtianParamField huihuangParamField = (HuihuangmingtianParamField) super.channelParamToAdsParam(parameterMap);

        synchronized (this) {
            String key = huihuangParamField.getAccount_id() + "_";
            if (huihuangParamField.getImeiMd5() != null && gdtCache.find(key + huihuangParamField.getImeiMd5())) {
                logger.info("近十分钟内有重复请求:{}", key + huihuangParamField.getImeiMd5());
                throw new RepeatException("重复请求 " + key + huihuangParamField.getImeiMd5());
            } else {
                gdtCache.put(key + huihuangParamField.getImeiMd5(), 1);
            }
            if (huihuangParamField.getOaidMd5() != null && gdtCache.find(key + huihuangParamField.getOaidMd5())) {
                logger.info("近十分钟内有重复请求:{}", key + huihuangParamField.getOaidMd5());
                throw new RepeatException("重复请求 " + key + huihuangParamField.getOaidMd5());
            } else {
                gdtCache.put(key + huihuangParamField.getOaidMd5(), 1);
            }
            if (huihuangParamField.getIp() != null && gdtCache.find(key + huihuangParamField.getIp())) {
                logger.info("近十分钟内有重复请求:{}", key + huihuangParamField.getIp());
                throw new RepeatException("重复请求 " + key + huihuangParamField.getIp());
            } else {
                gdtCache.put(key + huihuangParamField.getIp(), 1);
            }
        }
        huihuangParamField.setEventType("1"); // 拉活
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, huihuangParamField);
        return huihuangParamField;
    }


    /**
     * 处理muid：oaid不为空时，muid赋值给imei，否则赋值给idfa
     *
     * @param adsObj
     */
    @Override
    protected void convertParams(Object adsObj) {
        super.convertParams(adsObj);
        HuihuangmingtianParamField huihuangParamField = (HuihuangmingtianParamField) adsObj;
        logger.info("convertParams {} 处理huihuangParamField前 {}", channelAdsKey, huihuangParamField);
        //imei为空，说明muid是空的，不处理
        if (huihuangParamField.getImeiMd5() == null || "".equals(huihuangParamField.getImeiMd5())) {
            return;
        }
        //oaid不为空，说明是imei，否则是idfa
        if (huihuangParamField.getOaid() != null || huihuangParamField.getOaidMd5() != null) {
            return;
        }
        String imei = huihuangParamField.getImeiMd5();
        huihuangParamField.setIdfa(imei);
        huihuangParamField.setImeiMd5(null);
        logger.info("convertParams {} 处理huihuangParamField后 {}", channelAdsKey, huihuangParamField);
    }

}
