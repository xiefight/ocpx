package huihuang.proxy.ocpx.middle.baseadsreport.luyun;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.luyun.LuyunParamEnum;
import huihuang.proxy.ocpx.ads.luyun.LuyunParamField;
import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.channel.iqiyi.IQiyiParamEnum;
import huihuang.proxy.ocpx.common.Constants;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class LuyunIQiyiReportFactory extends LuyunReportFactory {

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历广告主查找渠道对应的宏参数
        Set<LuyunParamEnum> luyunParamEnums = LuyunParamEnum.luyunIQiyiMap.keySet();
        for (LuyunParamEnum luyun : luyunParamEnums) {
            IQiyiParamEnum iqiyi = LuyunParamEnum.luyunIQiyiMap.get(luyun);
            if (Objects.isNull(iqiyi) || StrUtil.isEmpty(iqiyi.getMacro())) {
                continue;
            }
            macro.append(iqiyi.getParam()).append("=").append(iqiyi.getMacro()).append("&");
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
        LuyunParamField luyunParamField = new LuyunParamField();

        Set<Map.Entry<LuyunParamEnum, IQiyiParamEnum>> hbSet = LuyunParamEnum.luyunIQiyiMap.entrySet();
        hbSet.stream().filter(hb -> Objects.nonNull(hb.getValue())).forEach(hb -> {
            LuyunParamEnum luyun = hb.getKey();
            IQiyiParamEnum iqiyi = hb.getValue();
            String luyunField = luyun.getName();
            String baiduParam = iqiyi.getParam();
            String[] value = parameterMap.get(baiduParam);
            if (Objects.isNull(value) || value.length == 0) return;
            if ("null".equals(value[0]) || "NULL".equals(value[0])) return;
            if (value[0].startsWith("__") && value[0].endsWith("__")) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(luyunField, luyunParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(luyunParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey(), luyunParamField);
        return luyunParamField;
    }
}