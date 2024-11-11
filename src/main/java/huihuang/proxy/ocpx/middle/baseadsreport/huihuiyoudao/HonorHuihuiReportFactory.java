package huihuang.proxy.ocpx.middle.baseadsreport.huihuiyoudao;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.huihui.HuihuiParamEnum;
import huihuang.proxy.ocpx.ads.huihui.HuihuiParamField;
import huihuang.proxy.ocpx.bussiness.service.basechannel.HonorChannelFactory;
import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.channel.honor.HonorParamEnum;
import huihuang.proxy.ocpx.channel.honor.HonorPath;
import huihuang.proxy.ocpx.common.Constants;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class HonorHuihuiReportFactory extends BaseHuihuiReportFactory {

    @Override
    protected String channelName() {
        return HonorPath.HONOR_CHANNEL_NAME;
    }

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历客户侧查找渠道对应的宏参数
        Set<HuihuiParamEnum> huihuiParamEnums = HuihuiParamEnum.huihuiHonorMap.keySet();
        for (HuihuiParamEnum huihui : huihuiParamEnums) {
            HonorParamEnum honor = HuihuiParamEnum.huihuiHonorMap.get(huihui);
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
        HuihuiParamField huihuiParamField = new HuihuiParamField();

        Set<Map.Entry<HuihuiParamEnum, HonorParamEnum>> bhSet = HuihuiParamEnum.huihuiHonorMap.entrySet();
        bhSet.stream().filter(bh -> Objects.nonNull(bh.getValue())).forEach(bh -> {
            HuihuiParamEnum huihui = bh.getKey();
            HonorParamEnum honor = bh.getValue();
            //huihui的字段名
            String huihuiField = huihui.getName();
            //baidu的字段名
            String honorParam = honor.getParam();
            //baidu的参数值
            String[] value = parameterMap.get(honorParam);
            if (Objects.isNull(value) || value.length == 0) return;
            if ("null".equals(value[0]) || "NULL".equals(value[0])) return;
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
                PropertyDescriptor descriptor = new PropertyDescriptor(huihuiField, huihuiParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(huihuiParamField, val);
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
            huihuiParamField.setExtra(extras);
        }
        return huihuiParamField;
    }


}
