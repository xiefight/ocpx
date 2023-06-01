package huihuang.proxy.ocpx.middle.impl;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamField;
import huihuang.proxy.ocpx.ads.xiguavideo.XiguaPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IXiguaAdsDao;
import huihuang.proxy.ocpx.channel.wifi.WifiParamEnum;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.WifiLiangdamaoReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * wifi-xigua
 *
 * @Author: xietao
 * @Date: 2023/5/9 21:04
 */
@Component("wxChannelAds")
public class WifiXiguaChannelAds extends WifiLiangdamaoReportFactory {

    @Autowired
    private XiguaPath xiguaPath;

    @Autowired
    private IXiguaAdsDao xiguaAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.WIFI_XIGUA;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.WIFI_XIGUA;
    }

    @Override
    protected IMarkDao adsDao() {
        return xiguaAdsDao;
    }

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        //wifi万能钥匙的宏参数必须全部使用
        StringBuilder macro = new StringBuilder();
        WifiParamEnum[] wifiParamEnums = WifiParamEnum.values();
        for (WifiParamEnum wifi : wifiParamEnums) {
            if (Objects.isNull(wifi) || StrUtil.isEmpty(wifi.getMacro())) {
                continue;
            }
            macro.append(wifi.getParam()).append("=").append(wifi.getMacro()).append("&");
        }
        String macroStr = macro.toString();
        if (macroStr.endsWith("&")) {
            macroStr = macroStr.substring(0, macroStr.length() - 1);
        }
        //2.config中查找服务地址
        String serverPath = queryServerPath();
        //3.拼接监测地址
        return serverPath + Constants.ServerPath.WIFI_XIGUA + Constants.ServerPath.CLICK_REPORT + "?" + macroStr;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        LiangdamaoParamField liangdamaoParamField = (LiangdamaoParamField) super.channelParamToAdsParam(parameterMap);
        liangdamaoParamField.setTp_adv_id(xiguaPath.tpAdvId());
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, liangdamaoParamField);
        return liangdamaoParamField;
    }

}
