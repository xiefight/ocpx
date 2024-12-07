package huihuang.proxy.ocpx.middle.baseadsreport.weibo;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.weibo.WeiboParamEnum;
import huihuang.proxy.ocpx.ads.weibo.WeiboParamField;
import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.channel.huihuang.HuihuangChannelParamEnum;
import huihuang.proxy.ocpx.common.Constants;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class HuihuangWeiboReportFactory extends WeiboReportFactory {

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历广告主查找渠道对应的宏参数
        Set<WeiboParamEnum> weiboParamEnums = WeiboParamEnum.weiboHuihuangMap.keySet();
        for (WeiboParamEnum weibo : weiboParamEnums) {
            HuihuangChannelParamEnum huihuang = WeiboParamEnum.weiboHuihuangMap.get(weibo);
            if (Objects.isNull(huihuang) || StrUtil.isEmpty(huihuang.getMacro())) {
                continue;
            }
            macro.append(huihuang.getParam()).append("=").append(huihuang.getMacro()).append("&");
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
        WeiboParamField weiboParamField = new WeiboParamField();

        Set<Map.Entry<WeiboParamEnum, HuihuangChannelParamEnum>> hbSet = WeiboParamEnum.weiboHuihuangMap.entrySet();
        hbSet.stream().filter(hb -> Objects.nonNull(hb.getValue())).forEach(hb -> {
            WeiboParamEnum weiboEnum = hb.getKey();
            HuihuangChannelParamEnum huihuangEnum = hb.getValue();
            String weiboField = weiboEnum.getName();
            String huihuangParam = huihuangEnum.getParam();
            String[] value = parameterMap.get(huihuangParam);
            if (Objects.isNull(value) || value.length == 0) return;
            if ("null".equals(value[0]) || "NULL".equals(value[0])) return;
            if (value[0].startsWith("__") || value[0].endsWith("__")) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(weiboField, weiboParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(weiboParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey(), weiboParamField);
        return weiboParamField;
    }

}
