package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.litianjingdong.LTJDParamEnum;
import huihuang.proxy.ocpx.ads.litianjingdong.LTJDParamField;
import huihuang.proxy.ocpx.ads.meituan.MeiTuanParamEnum;
import huihuang.proxy.ocpx.ads.meituan.MeiTuanParamField;
import huihuang.proxy.ocpx.channel.toutiao.ToutiaoParamEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiParamEnum;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.middle.BaseSupport;
import huihuang.proxy.ocpx.middle.IChannelAds;
import org.springframework.stereotype.Component;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @Description: tljd-xiaomi
 * @Author: xietao
 * @Date: 2023-04-24 17:31
 **/
@Component
public class XJChannelAds extends BaseSupport implements IChannelAds {

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历tljd查找xiaomi对应的宏参数
        Set<LTJDParamEnum> ltjdParamEnums = LTJDParamEnum.xjMap.keySet();
        for (LTJDParamEnum ltjd : ltjdParamEnums) {
            XiaomiParamEnum xiaomi = LTJDParamEnum.xjMap.get(ltjd);
            if (Objects.isNull(xiaomi)) {
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
        return serverPath + "/xjServer/clickReport" + "?" + macroStr;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        LTJDParamField ltjdParamField = new LTJDParamField();

        Set<Map.Entry<LTJDParamEnum, XiaomiParamEnum>> xjSet = LTJDParamEnum.xjMap.entrySet();
        xjSet.stream().filter(xj -> Objects.nonNull(xj.getValue())).forEach(tm -> {
            LTJDParamEnum ltjd = tm.getKey();
            XiaomiParamEnum xiaomi = tm.getValue();
            //ltjd的字段名
            String ltjdField = ltjd.getName();
            //xiaomi的字段名
            String xiaomiParam = xiaomi.getParam();
            //xiaomi的参数值
            String[] value = parameterMap.get(xiaomiParam);
            if (Objects.isNull(value) || value.length == 0) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(ltjdField, ltjdParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(ltjdParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return ltjdParamField;
    }

    @Override
    protected Response judgeParams(Object adsObj) throws Exception {
        return null;
    }

    @Override
    protected void convertParams(Object adsObj) {

    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        return null;
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {

    }

    @Override
    protected String initAdsUrl() {
        return null;
    }

    @Override
    protected Response reportAds(String adsUrl, Object adsDtoObj) throws Exception {
        return null;
    }


}
