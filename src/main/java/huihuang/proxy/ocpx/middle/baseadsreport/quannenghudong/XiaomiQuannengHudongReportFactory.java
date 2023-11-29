package huihuang.proxy.ocpx.middle.baseadsreport.quannenghudong;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongParamEnum;
import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongParamField;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiParamEnum;
import huihuang.proxy.ocpx.common.Constants;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-11-29 14:24
 **/
public abstract class XiaomiQuannengHudongReportFactory extends QuannengHudongReportFactory {

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历广告主查找渠道对应的宏参数
        Set<QuannengHudongParamEnum> quannengHudongParamEnums = QuannengHudongParamEnum.quannengHudongXiaomiMap.keySet();
        for (QuannengHudongParamEnum quannengHudong : quannengHudongParamEnums) {
            XiaomiParamEnum xiaomi = QuannengHudongParamEnum.quannengHudongXiaomiMap.get(quannengHudong);
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
        QuannengHudongParamField quannengHudongParamField = new QuannengHudongParamField();

        Set<Map.Entry<QuannengHudongParamEnum, XiaomiParamEnum>> qhSet = QuannengHudongParamEnum.quannengHudongXiaomiMap.entrySet();
        qhSet.stream().filter(qh -> Objects.nonNull(qh.getValue())).forEach(qh -> {
            QuannengHudongParamEnum quannengHudong = qh.getKey();
            XiaomiParamEnum xiaomi = qh.getValue();
            String quannengHudongField = quannengHudong.getName();
            String xiaomiParam = xiaomi.getParam();
            String[] value = parameterMap.get(xiaomiParam);
            if (Objects.isNull(value) || value.length == 0) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(quannengHudongField, quannengHudongParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(quannengHudongParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey(), quannengHudongParamField);
        return quannengHudongParamField;
    }

}
