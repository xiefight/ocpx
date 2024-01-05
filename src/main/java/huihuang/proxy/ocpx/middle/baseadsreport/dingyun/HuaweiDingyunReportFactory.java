package huihuang.proxy.ocpx.middle.baseadsreport.dingyun;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.dingyun.DingyunParamEnum;
import huihuang.proxy.ocpx.ads.dingyun.DingyunParamField;
import huihuang.proxy.ocpx.bussiness.service.basechannel.HuaweiChannelFactory;
import huihuang.proxy.ocpx.channel.huawei.HuaweiParamEnum;
import huihuang.proxy.ocpx.common.Constants;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class HuaweiDingyunReportFactory extends DingyunReportFactory {


    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历广告主查找渠道对应的宏参数
        Set<DingyunParamEnum> dingyunParamEnums = DingyunParamEnum.dingyunHuaweiMap.keySet();
        for (DingyunParamEnum dingyunEnum : dingyunParamEnums) {
            HuaweiParamEnum huawei = DingyunParamEnum.dingyunHuaweiMap.get(dingyunEnum);
            if (Objects.isNull(huawei) || StrUtil.isEmpty(huawei.getMacro())) {
                continue;
            }
            macro.append(huawei.getParam()).append("=").append(huawei.getMacro()).append("&");
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

        Set<Map.Entry<DingyunParamEnum, HuaweiParamEnum>> dbSet = DingyunParamEnum.dingyunHuaweiMap.entrySet();
        dbSet.stream().filter(hb -> Objects.nonNull(hb.getValue())).forEach(hb -> {
            DingyunParamEnum dingyun = hb.getKey();
            HuaweiParamEnum huawei = hb.getValue();
            String dingyunField = dingyun.getName();
            String huaweiParam = huawei.getParam();
            String[] value = parameterMap.get(huaweiParam);
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

        //存储华为这边必有而快手这不必有的参数，回传可能会用到
        String extras = HuaweiChannelFactory.fitExtras(parameterMap,
                HuaweiParamEnum.CONTENT_ID.getParam(),
                HuaweiParamEnum.EVENT_TYPE.getParam(),
                HuaweiParamEnum.TRACE_TIME.getParam(),
                HuaweiParamEnum.TRACKING_ENABLED.getParam());
        if (extras.length() > 0) {
            dingyunParamField.setExtra(extras);
        }

        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey(), dingyunParamField);
        return dingyunParamField;
    }


}
