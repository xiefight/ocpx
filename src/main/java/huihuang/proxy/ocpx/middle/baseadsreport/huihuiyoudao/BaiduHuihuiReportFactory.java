package huihuang.proxy.ocpx.middle.baseadsreport.huihuiyoudao;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.huihui.HuihuiParamEnum;
import huihuang.proxy.ocpx.ads.huihui.HuihuiParamField;
import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.common.Constants;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @Author: xietao
 * @Date: 2023/8/8 11:55
 */
public abstract class BaiduHuihuiReportFactory extends BaseHuihuiReportFactory {

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
        //1.遍历客户侧查找渠道对应的宏参数
        Set<HuihuiParamEnum> huihuiParamEnums = HuihuiParamEnum.huihuiBaiduMap.keySet();
        for (HuihuiParamEnum huihui : huihuiParamEnums) {
            BaiduParamEnum baidu = HuihuiParamEnum.huihuiBaiduMap.get(huihui);
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
        HuihuiParamField huihuiParamField = new HuihuiParamField();

        Set<Map.Entry<HuihuiParamEnum, BaiduParamEnum>> bhSet = HuihuiParamEnum.huihuiBaiduMap.entrySet();
        bhSet.stream().filter(bh -> Objects.nonNull(bh.getValue())).forEach(bh -> {
            HuihuiParamEnum huihui = bh.getKey();
            BaiduParamEnum baidu = bh.getValue();
            //huihui的字段名
            String huihuiField = huihui.getName();
            //baidu的字段名
            String baiduParam = baidu.getParam();
            //baidu的参数值
            String[] value = parameterMap.get(baiduParam);
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


}
