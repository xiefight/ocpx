package huihuang.proxy.ocpx.middle.baseadsreport.huihuangmingtian;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianParamEnum;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianParamField;
import huihuang.proxy.ocpx.channel.iqiyi.IQiyiParamEnum;
import huihuang.proxy.ocpx.common.Constants;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class IQiyiHuihuangReportFactory extends HuihuangMingtianReportFactory {

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历广告主查找渠道对应的宏参数
        Set<HuihuangmingtianParamEnum> huihuangmingtianParamEnums = HuihuangmingtianParamEnum.huihuangmingtianIQiyiMap.keySet();
        for (HuihuangmingtianParamEnum huihuangmingtian : huihuangmingtianParamEnums) {
            IQiyiParamEnum iqiyi = HuihuangmingtianParamEnum.huihuangmingtianIQiyiMap.get(huihuangmingtian);
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
        HuihuangmingtianParamField huihuangmingtianParamField = new HuihuangmingtianParamField();

        Set<Map.Entry<HuihuangmingtianParamEnum, IQiyiParamEnum>> hhSet = HuihuangmingtianParamEnum.huihuangmingtianIQiyiMap.entrySet();
        hhSet.stream().filter(hh -> Objects.nonNull(hh.getValue())).forEach(hh -> {
            HuihuangmingtianParamEnum huihuangmingtian = hh.getKey();
            IQiyiParamEnum iqiyi = hh.getValue();
            String huihuangmingtianField = huihuangmingtian.getName();
            String iqiyiParam = iqiyi.getParam();
            String[] value = parameterMap.get(iqiyiParam);
            if (Objects.isNull(value) || value.length == 0) return;
            if ("null".equals(value[0]) || "NULL".equals(value[0])) return;
            if (value[0].startsWith("__") && value[0].endsWith("__")) return;
            String val = value[0];
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(huihuangmingtianField, huihuangmingtianParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(huihuangmingtianParamField, val);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey(), huihuangmingtianParamField);
        return huihuangmingtianParamField;
    }


}
