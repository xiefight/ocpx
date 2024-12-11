package huihuang.proxy.ocpx.middle.baseadsreport.weibo;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.weibo.WeiboParamEnum;
import huihuang.proxy.ocpx.ads.weibo.WeiboParamField;
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

public abstract class HonorWeiboReportFactory extends WeiboReportFactory {

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历广告主查找渠道对应的宏参数
        Set<WeiboParamEnum> weiboParamEnums = WeiboParamEnum.weiboHonorMap.keySet();
        for (WeiboParamEnum weibo : weiboParamEnums) {
            HonorParamEnum honor = WeiboParamEnum.weiboHonorMap.get(weibo);
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
        WeiboParamField weiboParamField = new WeiboParamField();

        Set<Map.Entry<WeiboParamEnum, HonorParamEnum>> hbSet = WeiboParamEnum.weiboHonorMap.entrySet();
        hbSet.stream().filter(hb -> Objects.nonNull(hb.getValue())).forEach(hb -> {
            WeiboParamEnum weiboEnum = hb.getKey();
            HonorParamEnum honorEnum = hb.getValue();
            String weiboField = weiboEnum.getName();
            String baiduParam = honorEnum.getParam();
            String[] value = parameterMap.get(baiduParam);
            if (Objects.isNull(value) || value.length == 0) return;
            if ("null".equals(value[0]) || "NULL".equals(value[0])) return;
            if (value[0].startsWith("__") && value[0].endsWith("__")) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(weiboField, weiboParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(weiboParamField, value[0]);
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
            weiboParamField.setExtra(extras);
        }
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey(), weiboParamField);
        return weiboParamField;
    }

}
