package huihuang.proxy.ocpx.middle.baseadsreport.dingyun;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.dingyun.DingyunParamEnum;
import huihuang.proxy.ocpx.ads.dingyun.DingyunParamField;
import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiParamEnum;
import huihuang.proxy.ocpx.common.Constants;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
public abstract class XiaomiDingyunReportFactory extends DingyunReportFactory {


    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历广告主查找渠道对应的宏参数
        Set<DingyunParamEnum> dingyunParamEnums = DingyunParamEnum.dingyunXiaomiMap.keySet();
        for (DingyunParamEnum dingyunEnum : dingyunParamEnums) {
            XiaomiParamEnum xiaomi = DingyunParamEnum.dingyunXiaomiMap.get(dingyunEnum);
            if (Objects.isNull(xiaomi) || StrUtil.isEmpty(xiaomi.getMacro())) {
                continue;
            }
            macro.append(xiaomi.getParam()).append("=").append(xiaomi.getMacro()).append("&");
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
        DingyunParamField dingyunParamField = new DingyunParamField();

        Set<Map.Entry<DingyunParamEnum, XiaomiParamEnum>> dbSet = DingyunParamEnum.dingyunXiaomiMap.entrySet();
        dbSet.stream().filter(hb -> Objects.nonNull(hb.getValue())).forEach(hb -> {
            DingyunParamEnum dingyun = hb.getKey();
            XiaomiParamEnum xiaomi = hb.getValue();
            String dingyunField = dingyun.getName();
            String baiduParam = xiaomi.getParam();
            String[] value = parameterMap.get(baiduParam);
            if (Objects.isNull(value) || value.length == 0) return;
            if ("null".equals(value[0]) || "NULL".equals(value[0])) return;
            if (value[0].startsWith("__") && value[0].endsWith("__")) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(dingyunField, dingyunParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(dingyunParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey(), dingyunParamField);
        return dingyunParamField;
    }


}
