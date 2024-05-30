package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.huihui.HuihuiParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuiXianyuAdsDao;
import huihuang.proxy.ocpx.cache.impl.GDTCache;
import huihuang.proxy.ocpx.channel.guangdiantong.GuangdiantongPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.exception.RepeatException;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuiyoudao.GDTHuihuiReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("gdthhxyChannelAds")
public class GDTHuihuiXianyuChannelAds extends GDTHuihuiReportFactory {

    @Autowired
    private IHuihuiXianyuAdsDao xianyuAdsDao;
    @Autowired
    private GDTCache gdtCache;

    String channelAdsKey = Constants.ChannelAdsKey.GDT_HUIHUI_XIANYU;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.GDT_HUIHUI_XIANYU;
    }

    @Override
    protected IMarkDao adsDao() {
        return xianyuAdsDao;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        HuihuiParamField huihuiParamField = (HuihuiParamField) super.channelParamToAdsParam(parameterMap);

        synchronized (this) {
            String key = huihuiParamField.getOcpxAccount() + "_";
            if (huihuiParamField.getImei() != null && gdtCache.find(key + huihuiParamField.getImei())) {
                logger.info("近十分钟内有重复请求:{}", key + huihuiParamField.getImei());
                throw new RepeatException("重复请求 " + key + huihuiParamField.getImei());
            } else {
                gdtCache.put(key + huihuiParamField.getImei(), 1);
            }
            if (huihuiParamField.getOaid() != null && gdtCache.find(key + huihuiParamField.getOaid())) {
                logger.info("近十分钟内有重复请求:{}", key + huihuiParamField.getOaid());
                throw new RepeatException("重复请求 " + key + huihuiParamField.getOaid());
            } else {
                gdtCache.put(key + huihuiParamField.getOaid(), 1);
            }
            if (huihuiParamField.getOaid_md5() != null && gdtCache.find(key + huihuiParamField.getOaid_md5())) {
                logger.info("近十分钟内有重复请求:{}", key + huihuiParamField.getOaid_md5());
                throw new RepeatException("重复请求 " + key + huihuiParamField.getOaid_md5());
            } else {
                gdtCache.put(key + huihuiParamField.getOaid_md5(), 1);
            }
            if (huihuiParamField.getIp() != null && gdtCache.find(key + huihuiParamField.getIp())) {
                logger.info("近十分钟内有重复请求:{}", key + huihuiParamField.getIp());
                throw new RepeatException("重复请求 " + key + huihuiParamField.getIp());
            } else {
                gdtCache.put(key + huihuiParamField.getIp(), 1);
            }
        }

        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, huihuiParamField);
        huihuiParamField.setRedirect("false");
        if (GuangdiantongPath.GUANGDIANTONG_HUIHUI_XIANYU_01.equals(huihuiParamField.getOcpxAccount())
                || GuangdiantongPath.GUANGDIANTONG_HUIHUI_XIANYU_02.equals(huihuiParamField.getOcpxAccount())
                || GuangdiantongPath.GUANGDIANTONG_HUIHUI_XIANYU_03.equals(huihuiParamField.getOcpxAccount())
                || GuangdiantongPath.GUANGDIANTONG_HUIHUI_XIANYU_04.equals(huihuiParamField.getOcpxAccount())
                || GuangdiantongPath.GUANGDIANTONG_HUIHUI_XIANYU_05.equals(huihuiParamField.getOcpxAccount())) {
            huihuiParamField.setConv_ext("Ego1MTQ0ODkyMTQ2GAEgAiqfA2h0dHBzOi8vd2NwLnRhb2Jhby5jb20vYWRzdHJhY2svdHJhY2suanNvbj9jaGFubmVsPTIyMDA4MDM0MzQyMDgmYWN0aW9uPTImb3M9MCZhcHA9MyZ0YXNrSWQ9MzE1NDA5MTgmaWRmYT1fX2lkZmFfXyZpbWVpTWQ1PV9faW1laV9fJm1hYzFNZDU9X19tYWMxX18maXA9X19pcF9fJnRpbWVzdGFtcD1fX3RzX18mdWE9X191YV9fJmFkdmVydGlzZXJJZD1fX3Nwb25zb3JfaWRfXyZncm91cElkPV9fZ3JvdXBfaWRfXyZjb252PV9fY29udl9fJnJlcXVlc3RJZD1fX3JlcV9pZF9fJmFkaWQ9X19jYW1wYWlnbl9pZF9fJmNpZD1fX2NvbnRlbnRfaWRfXyZhbmRyb2lkSWRNZDU9X2FuZHJvaWRfaWRfXyZvYWlkPV9fb2FpZF9fJm1hY01kNT1fX21hY19fJmFkQWdlbnQ9d2FuZ3lpLmxheGluLmRtcC5vY3Bkemh1Y2UueW91ZGRhby4zMTQ0NDgyU2h0dHBzOi8vZG93bmxvYWQuYWxpY2RuLmNvbS93aXJlbGVzcy9mbGVhbWFya2V0L2xhdGVzdC9mbGVhTWFya2V0XzE1Njg4OTc4ODc2NDIuYXBrOhYI9NcCWNCYE2Da-kdoo-WJA3C1qZoM");
        }
        return huihuiParamField;
    }

}
