package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.huihui.HuihuiParamField;
import huihuang.proxy.ocpx.ads.huihui.xianyu.HuihuiXianyuPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuiXianyuAdsDao;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.huihuiyoudao.BaiduHuihuiReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: xietao
 * @Date: 2023/6/8 19:39
 */
@Component("baiduxianyuChannelAds")
public class BaiduHuihuiXianyuChannelAds extends BaiduHuihuiReportFactory {

    @Autowired
    private IHuihuiXianyuAdsDao xianyuAdsDao;
    @Autowired
    private HuihuiXianyuPath huihuiXianyuPath;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_HUIHUI_XIANYU;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_HUIHUI_XIANYU;
    }

    @Override
    protected IMarkDao adsDao() {
        return xianyuAdsDao;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        HuihuiParamField huihuiParamField = (HuihuiParamField) super.channelParamToAdsParam(parameterMap);
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, huihuiParamField);
        huihuiParamField.setRedirect("false");
        if (null == huihuiParamField.getConv_ext()){
            //默认
            huihuiParamField.setConv_ext("Ego0OTg0MjE4ODcyGAEgAiqfA2h0dHBzOi8vd2NwLnRhb2Jhby5jb20vYWRzdHJhY2svdHJhY2suanNvbj9jaGFubmVsPTIyMDA4MDM0MzQyMDgmYWN0aW9uPTImb3M9MCZhcHA9MyZ0YXNrSWQ9MzE1NDA5MTgmaWRmYT1fX2lkZmFfXyZpbWVpTWQ1PV9faW1laV9fJm1hYzFNZDU9X19tYWMxX18maXA9X19pcF9fJnRpbWVzdGFtcD1fX3RzX18mdWE9X191YV9fJmFkdmVydGlzZXJJZD1fX3Nwb25zb3JfaWRfXyZncm91cElkPV9fZ3JvdXBfaWRfXyZjb252PV9fY29udl9fJnJlcXVlc3RJZD1fX3JlcV9pZF9fJmFkaWQ9X19jYW1wYWlnbl9pZF9fJmNpZD1fX2NvbnRlbnRfaWRfXyZhbmRyb2lkSWRNZDU9X2FuZHJvaWRfaWRfXyZvYWlkPV9fb2FpZF9fJm1hY01kNT1fX21hY19fJmFkQWdlbnQ9d2FuZ3lpLmxheGluLmRtcC5vY3Bkemh1Y2UueW91ZGRhby4zMTQ0NDgyU2h0dHBzOi8vZG93bmxvYWQuYWxpY2RuLmNvbS93aXJlbGVzcy9mbGVhbWFya2V0L2xhdGVzdC9mbGVhTWFya2V0XzE1Njg4OTc4ODc2NDIuYXBrOhYIxpMCWNCYE2CUikdowveHA3Dg_ZYM");
        }
        return huihuiParamField;
    }

}
