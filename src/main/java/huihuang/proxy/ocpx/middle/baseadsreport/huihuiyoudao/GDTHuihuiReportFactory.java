package huihuang.proxy.ocpx.middle.baseadsreport.huihuiyoudao;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.huihui.HuihuiParamEnum;
import huihuang.proxy.ocpx.ads.huihui.HuihuiParamField;
import huihuang.proxy.ocpx.channel.guangdiantong.GuangdiantongParamEnum;
import huihuang.proxy.ocpx.channel.guangdiantong.GuangdiantongPath;
import huihuang.proxy.ocpx.common.Constants;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class GDTHuihuiReportFactory extends BaseHuihuiReportFactory {

    @Override
    protected String channelName() {
        return GuangdiantongPath.GUANGDIANTONG_CHANNEL_NAME;
    }

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历客户侧查找渠道对应的宏参数
        Set<HuihuiParamEnum> huihuiParamEnums = HuihuiParamEnum.huihuiGDTMap.keySet();
        for (HuihuiParamEnum huihui : huihuiParamEnums) {
            GuangdiantongParamEnum gdt = HuihuiParamEnum.huihuiGDTMap.get(huihui);
            if (Objects.isNull(gdt) || StrUtil.isEmpty(gdt.getMacro())) {
                continue;
            }
            macro.append(gdt.getParam()).append("=").append(gdt.getMacro()).append("&");
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
        HuihuiParamField huihuiParamField = new HuihuiParamField();

        Set<Map.Entry<HuihuiParamEnum, GuangdiantongParamEnum>> bhSet = HuihuiParamEnum.huihuiGDTMap.entrySet();
        bhSet.stream().filter(hh -> Objects.nonNull(hh.getValue())).forEach(hh -> {
            HuihuiParamEnum huihui = hh.getKey();
            GuangdiantongParamEnum gdt = hh.getValue();
            //huihui的字段名
            String huihuiField = huihui.getName();
            //gdt的字段名
            String gdtParam = gdt.getParam();
            //gdt的参数值
            String[] value = parameterMap.get(gdtParam);
            if (Objects.isNull(value) || value.length == 0) return;
            if ("null".equals(value[0]) || "NULL".equals(value[0])) return;
            if (value[0].startsWith("__") && value[0].endsWith("__")) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(huihuiField, huihuiParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(huihuiParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return huihuiParamField;
    }

    @Override
    protected void convertParams(Object adsObj) {
        super.convertParams(adsObj);
        HuihuiParamField huihuiParamField = (HuihuiParamField) adsObj;
        if ("android".equals(huihuiParamField.getOs())) {
            huihuiParamField.setIdfa(null);
            huihuiParamField.setIdfa_md5(null);
        } else {
            huihuiParamField.setImei(null);
            huihuiParamField.setOaid(null);
            huihuiParamField.setOaid_md5(null);
        }

        huihuiParamField.setTs(System.currentTimeMillis() + "");
    }


}
