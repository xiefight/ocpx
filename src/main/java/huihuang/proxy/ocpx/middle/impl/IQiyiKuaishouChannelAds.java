package huihuang.proxy.ocpx.middle.impl;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouParamEnum;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.IKuaishouAdsDao;
import huihuang.proxy.ocpx.channel.iqiyi.IQiyiParamEnum;
import huihuang.proxy.ocpx.channel.iqiyi.IQiyiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.middle.baseadsreport.KuaishouReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** 
 * 
 * @Author: xietao
 * @Date: 2023/6/29 22:49
 */ 
@Component("ikChannelAds")
public class IQiyiKuaishouChannelAds extends KuaishouReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.IQIYI_KUAISHOU;

    @Autowired
    @Qualifier("kuaishouAdsIqiyiDao")
    private IKuaishouAdsDao kuaishouAdsIqiyiDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.IQIYI_KUAISHOU;
    }

    @Override
    protected String channelName() {
        return IQiyiPath.IQIYI_CHANNEL_NAME;
    }

    @Override
    protected IKuaishouAdsDao adsDao() {
        return kuaishouAdsIqiyiDao;
    }


    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历kuaishou查找xiaomi对应的宏参数
        Set<KuaishouParamEnum> kuaishouParamEnums = KuaishouParamEnum.kuaishouIQiyiMap.keySet();
        for (KuaishouParamEnum kuaishou : kuaishouParamEnums) {
            IQiyiParamEnum iqiyi = KuaishouParamEnum.kuaishouIQiyiMap.get(kuaishou);
            if (Objects.isNull(iqiyi) || StrUtil.isEmpty(iqiyi.getMacro())) {
                continue;
            }
            macro.append(iqiyi.getParam()).append("=").append(iqiyi.getMacro()).append("&");
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
        KuaishouParamField kuaishouParamField = new KuaishouParamField();

        Set<Map.Entry<KuaishouParamEnum, IQiyiParamEnum>> ikSet = KuaishouParamEnum.kuaishouIQiyiMap.entrySet();
        ikSet.stream().filter(ik -> Objects.nonNull(ik.getValue())).forEach(ik -> {
            KuaishouParamEnum kuaishou = ik.getKey();
            IQiyiParamEnum iqiyi = ik.getValue();
            //kuaishou的字段名
            String kuaishouField = kuaishou.getName();
            String iqiyiParam = iqiyi.getParam();
            String[] value = parameterMap.get(iqiyiParam);
            if (Objects.isNull(value) || value.length == 0) return;
            if ("null".equals(value[0]) || "NULL".equals(value[0])) return;
            if (value[0].startsWith("__") && value[0].endsWith("__")) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(kuaishouField, kuaishouParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(kuaishouParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, kuaishouParamField);
        return kuaishouParamField;
    }

}
