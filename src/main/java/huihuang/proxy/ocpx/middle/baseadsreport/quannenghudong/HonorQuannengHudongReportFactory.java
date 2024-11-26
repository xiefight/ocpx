package huihuang.proxy.ocpx.middle.baseadsreport.quannenghudong;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongParamEnum;
import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongParamField;
import huihuang.proxy.ocpx.bussiness.service.basechannel.HonorChannelFactory;
import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.channel.honor.HonorParamEnum;
import huihuang.proxy.ocpx.common.Constants;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class HonorQuannengHudongReportFactory extends QuannengHudongReportFactory {

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历广告主查找渠道对应的宏参数
        Set<QuannengHudongParamEnum> quannengHudongParamEnums = QuannengHudongParamEnum.quannengHudongHonorMap.keySet();
        for (QuannengHudongParamEnum quannengHudong : quannengHudongParamEnums) {
            HonorParamEnum honor = QuannengHudongParamEnum.quannengHudongHonorMap.get(quannengHudong);
            if (Objects.isNull(honor) || StrUtil.isEmpty(honor.getMacro())) {
                continue;
            }
            macro.append(honor.getParam()).append("=").append(honor.getMacro()).append("&");
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

        Set<Map.Entry<QuannengHudongParamEnum, HonorParamEnum>> qhSet = QuannengHudongParamEnum.quannengHudongHonorMap.entrySet();
        qhSet.stream().filter(bh -> Objects.nonNull(bh.getValue())).forEach(bh -> {
            QuannengHudongParamEnum quannengHudong = bh.getKey();
            HonorParamEnum honor = bh.getValue();
            String quannengHudongField = quannengHudong.getName();
            String baiduParam = honor.getParam();
            String[] value = parameterMap.get(baiduParam);
            if (Objects.isNull(value) || value.length == 0) return;
            if ("null".equals(value[0]) || "NULL".equals(value[0])) return;
            if (value[0].startsWith("__") && value[0].endsWith("__")) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(quannengHudongField, quannengHudongParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(quannengHudongParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        String extras = HonorChannelFactory.fitExtras(parameterMap,
                HonorParamEnum.TRACK_ID.getParam(),
                "trackId",
                "advertiserId",
                HonorParamEnum.ADVERTISER_ID.getParam(),
                HonorParamEnum.GROUPID.getParam(),
                HonorParamEnum.CREATIVE_ID.getParam(),
                HonorParamEnum.REQUESTID.getParam());
        if (extras.length() > 0) {
            quannengHudongParamField.setExtra(extras);
        }
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey(), quannengHudongParamField);
        return quannengHudongParamField;
    }

}
