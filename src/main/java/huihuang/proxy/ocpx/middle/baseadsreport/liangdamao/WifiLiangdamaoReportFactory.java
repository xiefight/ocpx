package huihuang.proxy.ocpx.middle.baseadsreport.liangdamao;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamEnum;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamField;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoPath;
import huihuang.proxy.ocpx.channel.wifi.WifiParamEnum;
import huihuang.proxy.ocpx.channel.wifi.WifiPath;
import huihuang.proxy.ocpx.common.Constants;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @Description: 上报客户的逻辑再抽象
 * 比如：百度和京东、优酷、番茄的对接，本质上是百度和粮大猫的对接，抽离出百度和粮大猫的公共部分，将京东、优酷、番茄的不同参数传入
 * @Author: xietao
 * @Date: 2023-05-23 17:39
 **/
public abstract class WifiLiangdamaoReportFactory extends BaseLiangdamaoReportFactory {

    @Override
    protected String channelName() {
        return WifiPath.WIFI_CHANNEL_NAME;
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
        return serverPath + serverPathKey() + Constants.ServerPath.CLICK_REPORT + "?" + macroStr;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        LiangdamaoParamField liangdamaoParamField = new LiangdamaoParamField();

        Set<Map.Entry<LiangdamaoParamEnum, WifiParamEnum>> lwSet = LiangdamaoParamEnum.liangdamaoWifiMap.entrySet();
        lwSet.stream().filter(lw -> Objects.nonNull(lw.getValue())).forEach(lw -> {
            LiangdamaoParamEnum liangdamao = lw.getKey();
            WifiParamEnum wifi = lw.getValue();
            String liangdamaoField = liangdamao.getName();
            String wifiParam = wifi.getParam();
            //wifi的参数值
            String[] value = parameterMap.get(wifiParam);
            if (Objects.isNull(value) || value.length == 0) return;
            if (value[0].startsWith("__") && value[0].endsWith("__")) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(liangdamaoField, liangdamaoParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(liangdamaoParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        liangdamaoParamField.setAccess_id(LiangdamaoPath.ACCESS_ID);
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey(), liangdamaoParamField);
        //处理广告侧无映射的，但又必须回调给渠道侧的额外参数
        fitExtras(parameterMap, liangdamaoParamField,
                WifiParamEnum.CID.getParam(), WifiParamEnum.SID.getParam(), WifiParamEnum.STIME.getParam(),
                WifiParamEnum.OS.getParam(), WifiParamEnum.IDFA.getParam(), WifiParamEnum.MAC.getParam(),
                WifiParamEnum.IMEI.getParam());
        return liangdamaoParamField;
    }

    private void fitExtras(Map<String, String[]> parameterMap, LiangdamaoParamField liangdamaoParamField, String... extras) {
        StringBuilder extraStr = new StringBuilder();
        for (String extra : extras) {
            String[] cids = parameterMap.get(extra);
            if (Objects.nonNull(cids) && cids.length > 0) {
                String cid = cids[0];
                extraStr.append("&").append(extra).append("=").append(cid);
            }
        }
        if (extras.length > 0) {
            liangdamaoParamField.setExtra(extraStr.toString());
        }
    }

}
