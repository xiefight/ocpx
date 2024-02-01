package huihuang.proxy.ocpx.middle.baseadsreport.luyun;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.luyun.LuyunParamEnum;
import huihuang.proxy.ocpx.ads.luyun.LuyunParamField;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiParamEnum;
import huihuang.proxy.ocpx.common.Constants;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class LuyunXiaomiReportFactory extends LuyunReportFactory {

    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历广告主查找渠道对应的宏参数
        Set<LuyunParamEnum> luyunParamEnums = LuyunParamEnum.luyunXiaomiMap.keySet();
        for (LuyunParamEnum luyun : luyunParamEnums) {
            XiaomiParamEnum xiaomi = LuyunParamEnum.luyunXiaomiMap.get(luyun);
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
        LuyunParamField keepParamField = new LuyunParamField();

        Set<Map.Entry<LuyunParamEnum, XiaomiParamEnum>> xlSet = LuyunParamEnum.luyunXiaomiMap.entrySet();
        xlSet.stream().filter(xl -> Objects.nonNull(xl.getValue())).forEach(xl -> {
            LuyunParamEnum keep = xl.getKey();
            XiaomiParamEnum xiaomi = xl.getValue();
            //ltjd的字段名
            String keepField = keep.getName();
            //xiaomi的字段名
            String xiaomiParam = xiaomi.getParam();
            //xiaomi的参数值
            String[] value = parameterMap.get(xiaomiParam);
            if (Objects.isNull(value) || value.length == 0) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(keepField, keepParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(keepParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return keepParamField;
    }
}
