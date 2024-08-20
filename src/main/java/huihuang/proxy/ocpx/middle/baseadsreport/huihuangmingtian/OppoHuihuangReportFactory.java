package huihuang.proxy.ocpx.middle.baseadsreport.huihuangmingtian;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianParamEnum;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianParamField;
import huihuang.proxy.ocpx.channel.oppo.OppoNewParamEnum;
import huihuang.proxy.ocpx.channel.oppo.OppoParamEnum;
import huihuang.proxy.ocpx.common.Constants;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class OppoHuihuangReportFactory extends HuihuangMingtianReportFactory {

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历kuaishou查找baidu对应的宏参数
        Set<HuihuangmingtianParamEnum> huihuangmingtianParamEnums = HuihuangmingtianParamEnum.huihuangmingtianOppoMap.keySet();
        for (HuihuangmingtianParamEnum huihuangmingtian : huihuangmingtianParamEnums) {
            OppoParamEnum oppo = HuihuangmingtianParamEnum.huihuangmingtianOppoMap.get(huihuangmingtian);
            if (Objects.isNull(oppo) || StrUtil.isEmpty(oppo.getMacro())) {
                continue;
            }
            macro.append(oppo.getParam()).append("=").append(oppo.getMacro()).append("&");
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
        HuihuangmingtianParamField huihuangmingtianParamField = new HuihuangmingtianParamField();

        Set<Map.Entry<HuihuangmingtianParamEnum, OppoParamEnum>> hbSet = HuihuangmingtianParamEnum.huihuangmingtianOppoMap.entrySet();
        hbSet.stream().filter(hb -> Objects.nonNull(hb.getValue())).forEach(hb -> {
            HuihuangmingtianParamEnum huihuangmingtian = hb.getKey();
            OppoParamEnum oppo = hb.getValue();
            String huihuangmingtianField = huihuangmingtian.getName();
            String huaweiParam = oppo.getParam();
            String[] value = parameterMap.get(huaweiParam);
            if (Objects.isNull(value) || value.length == 0) return;
            if ("null".equals(value[0]) || "NULL".equals(value[0])) return;
            if (value[0].startsWith("__") && value[0].endsWith("__")) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(huihuangmingtianField, huihuangmingtianParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(huihuangmingtianParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey(), huihuangmingtianParamField);
        return huihuangmingtianParamField;
    }

}
