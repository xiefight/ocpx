package huihuang.proxy.ocpx.middle.baseadsreport.huihuangmingtian;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianParamEnum;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianParamField;
import huihuang.proxy.ocpx.bussiness.service.basechannel.HonorChannelFactory;
import huihuang.proxy.ocpx.channel.honor.HonorParamEnum;
import huihuang.proxy.ocpx.common.Constants;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class HonorHuihuangReportFactory extends HuihuangMingtianReportFactory {

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历广告主查找渠道对应的宏参数
        Set<HuihuangmingtianParamEnum> huihuangmingtianParamEnums = HuihuangmingtianParamEnum.huihuangmingtianHonorMap.keySet();
        for (HuihuangmingtianParamEnum huihuangmingtian : huihuangmingtianParamEnums) {
            HonorParamEnum honor = HuihuangmingtianParamEnum.huihuangmingtianHonorMap.get(huihuangmingtian);
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
        HuihuangmingtianParamField huihuangmingtianParamField = new HuihuangmingtianParamField();

        Set<Map.Entry<HuihuangmingtianParamEnum, HonorParamEnum>> hhSet = HuihuangmingtianParamEnum.huihuangmingtianHonorMap.entrySet();
        hhSet.stream().filter(hh -> Objects.nonNull(hh.getValue())).forEach(hh -> {
            HuihuangmingtianParamEnum huihuangmingtian = hh.getKey();
            HonorParamEnum honor = hh.getValue();
            String huihuangmingtianField = huihuangmingtian.getName();
            String honorParam = honor.getParam();
            String[] value = parameterMap.get(honorParam);
            if (Objects.isNull(value) || value.length == 0) return;
            String val = value[0];
            //特殊处理channel字段
            if (HonorParamEnum.HUIHUANG_CHANNEL.getParam().equals(honorParam)) {
                for (String channel : value) {
                    if (!"-1".equals(channel)) {
                        val = channel;
                        break;
                    }
                }
            }
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(huihuangmingtianField, huihuangmingtianParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(huihuangmingtianParamField, val);
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
            huihuangmingtianParamField.setExtra(extras);
        }
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey(), huihuangmingtianParamField);
        return huihuangmingtianParamField;
    }


}
