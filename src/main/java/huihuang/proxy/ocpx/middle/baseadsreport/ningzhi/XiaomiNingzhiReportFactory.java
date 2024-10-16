package huihuang.proxy.ocpx.middle.baseadsreport.ningzhi;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.ningzhi.NingzhiParamEnum;
import huihuang.proxy.ocpx.ads.ningzhi.NingzhiParamField;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiParamEnum;
import huihuang.proxy.ocpx.common.Constants;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class XiaomiNingzhiReportFactory extends NingzhiReportFactory {


    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历广告主查找渠道对应的宏参数
        Set<NingzhiParamEnum> ningzhiParamEnums = NingzhiParamEnum.ningzhiXiaomiMap.keySet();
        for (NingzhiParamEnum ningzhiEnum : ningzhiParamEnums) {
            XiaomiParamEnum xiaomi = NingzhiParamEnum.ningzhiXiaomiMap.get(ningzhiEnum);
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
        NingzhiParamField ningzhiParamField = new NingzhiParamField();

        Set<Map.Entry<NingzhiParamEnum, XiaomiParamEnum>> dbSet = NingzhiParamEnum.ningzhiXiaomiMap.entrySet();
        dbSet.stream().filter(hb -> Objects.nonNull(hb.getValue())).forEach(hb -> {
            NingzhiParamEnum ningzhi = hb.getKey();
            XiaomiParamEnum xiaomi = hb.getValue();
            String ningzhiField = ningzhi.getName();
            String xiaomiParam = xiaomi.getParam();
            String[] value = parameterMap.get(xiaomiParam);
            if (Objects.isNull(value) || value.length == 0) return;
            if ("null".equals(value[0]) || "NULL".equals(value[0])) return;
            if (value[0].startsWith("__") && value[0].endsWith("__")) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(ningzhiField, ningzhiParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(ningzhiParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey(), ningzhiParamField);
        return ningzhiParamField;
    }


}
