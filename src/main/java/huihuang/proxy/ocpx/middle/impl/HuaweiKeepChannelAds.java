package huihuang.proxy.ocpx.middle.impl;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.keep.KeepParamEnum;
import huihuang.proxy.ocpx.ads.keep.KeepParamField;
import huihuang.proxy.ocpx.bussiness.service.basechannel.HuaweiChannelFactory;
import huihuang.proxy.ocpx.channel.huawei.HuaweiParamEnum;
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

@Component("hkeepChannelAds")
public class HuaweiKeepChannelAds extends KeepReportFactory {


    String channelAdsKey = Constants.ChannelAdsKey.HUAWEI_KEEP;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.HUAWEI_KEEP;
    }

    @Override
    protected String channelName() {
        return null;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        KeepParamField keepParamField = new KeepParamField();

        Set<Map.Entry<KeepParamEnum, HuaweiParamEnum>> hbSet = KeepParamEnum.keepHuaweiMap.entrySet();
        hbSet.stream().filter(hb -> Objects.nonNull(hb.getValue())).forEach(hb -> {
            KeepParamEnum keep = hb.getKey();
            HuaweiParamEnum huawei = hb.getValue();
            String keepField = keep.getName();
            String huaweiParam = huawei.getParam();
            String[] value = parameterMap.get(huaweiParam);
            if (Objects.isNull(value) || value.length == 0) return;
            if ("null".equals(value[0]) || "NULL".equals(value[0])) return;
            if (value[0].startsWith("__") && value[0].endsWith("__")) return;
            String val = value[0];
            //特殊处理channel字段
            if (HuaweiParamEnum.HUIHUANG_CHANNEL.getParam().equals(huaweiParam)){
                for (String channel : value){
                    if (!"-1".equals(channel)){
                        val = channel;
                        break;
                    }
                }
            }
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(keepField, keepParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(keepParamField, val);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        String extras = HuaweiChannelFactory.fitExtras(parameterMap,
                HuaweiParamEnum.CONTENT_ID.getParam(),
                HuaweiParamEnum.EVENT_TYPE.getParam(),
                HuaweiParamEnum.TRACE_TIME.getParam(),
                HuaweiParamEnum.TRACKING_ENABLED.getParam(),
                HuaweiParamEnum.CAMPAIGN_ID.getParam());
        if (extras.length() > 0) {
            keepParamField.setExtra(extras);
        }

        return keepParamField;
    }

    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历广告主查找渠道对应的宏参数
        Set<KeepParamEnum> keepParamEnums = KeepParamEnum.keepHuaweiMap.keySet();
        for (KeepParamEnum keep : keepParamEnums) {
            HuaweiParamEnum huawei = KeepParamEnum.keepHuaweiMap.get(keep);
            if (Objects.isNull(huawei) || StrUtil.isEmpty(huawei.getMacro())) {
                continue;
            }
            macro.append(huawei.getParam()).append("=").append(huawei.getMacro()).append("&");
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
}
