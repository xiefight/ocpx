package huihuang.proxy.ocpx.middle.impl;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.keep.KeepParamEnum;
import huihuang.proxy.ocpx.ads.keep.KeepParamField;
import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.middle.baseadsreport.KeepReportFactory;
import org.springframework.stereotype.Component;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Component("bkeepChannelAds")
public class BaiduKeepChannelAds extends KeepReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_KEEP;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_KEEP;
    }

    @Override
    protected String channelName() {
        return BaiduPath.BAIDU_CHANNEL_NAME;
    }

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历广告主查找渠道对应的宏参数
        Set<KeepParamEnum> keepParamEnums = KeepParamEnum.keepBaiduMap.keySet();
        for (KeepParamEnum keep : keepParamEnums) {
            BaiduParamEnum baidu = KeepParamEnum.keepBaiduMap.get(keep);
            if (Objects.isNull(baidu) || StrUtil.isEmpty(baidu.getMacro())) {
                continue;
            }
            macro.append(baidu.getParam()).append("=").append(baidu.getMacro()).append("&");
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
        KeepParamField keepParamField = new KeepParamField();

        Set<Map.Entry<KeepParamEnum, BaiduParamEnum>> hbSet = KeepParamEnum.keepBaiduMap.entrySet();
        hbSet.stream().filter(hb -> Objects.nonNull(hb.getValue())).forEach(hb -> {
            KeepParamEnum keep = hb.getKey();
            BaiduParamEnum baidu = hb.getValue();
            String keepField = keep.getName();
            String baiduParam = baidu.getParam();
            String[] value = parameterMap.get(baiduParam);
            if (Objects.isNull(value) || value.length == 0) return;
            if ("null".equals(value[0]) || "NULL".equals(value[0])) return;
            if (value[0].startsWith("__") && value[0].endsWith("__")) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(keepField, keepParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(keepParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, keepParamField);
        return keepParamField;
    }

}
