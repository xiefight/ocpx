package huihuang.proxy.ocpx.middle.impl;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouParamEnum;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.IKuaishouAdsDao;
import huihuang.proxy.ocpx.channel.guangdiantong.GuangdiantongParamEnum;
import huihuang.proxy.ocpx.channel.guangdiantong.GuangdiantongPath;
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

@Component("gdtksChannelAds")
public class GDTKuaishouChannelAds extends KuaishouReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.GDT_KUAISHOU;

    @Autowired
    @Qualifier("kuaishouAdsGdtDao")
    private IKuaishouAdsDao kuaishouAdsGdtDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.GDT_KUAISHOU;
    }

    @Override
    protected String channelName() {
        return GuangdiantongPath.GUANGDIANTONG_CHANNEL_NAME;
    }

    @Override
    protected IKuaishouAdsDao adsDao() {
        return kuaishouAdsGdtDao;
    }


    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历kuaishou查找gdt对应的宏参数
        Set<KuaishouParamEnum> kuaishouParamEnums = KuaishouParamEnum.kuaishouGudangdiantongMap.keySet();
        for (KuaishouParamEnum kuaishou : kuaishouParamEnums) {
            GuangdiantongParamEnum gdtParam = KuaishouParamEnum.kuaishouGudangdiantongMap.get(kuaishou);
            if (Objects.isNull(gdtParam) || StrUtil.isEmpty(gdtParam.getMacro())) {
                continue;
            }
            macro.append(gdtParam.getParam()).append("=").append(gdtParam.getMacro()).append("&");
        }
        String macroStr = macro.toString();
        if (macroStr.endsWith("&")) {
            macroStr = macroStr.substring(0, macroStr.length() - 1);
        }
        //2.config中查找服务地址
        String serverPath = queryServerPath();
        //3.拼接监测地址
        return serverPath + Constants.ServerPath.GDT_KUAISHOU + Constants.ServerPath.CLICK_REPORT + "?" + macroStr;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        KuaishouParamField kuaishouParamField = new KuaishouParamField();

        Set<Map.Entry<KuaishouParamEnum, GuangdiantongParamEnum>> ksGdtSet = KuaishouParamEnum.kuaishouGudangdiantongMap.entrySet();
        ksGdtSet.stream().filter(gk -> Objects.nonNull(gk.getValue())).forEach(gk -> {
            KuaishouParamEnum kuaishou = gk.getKey();
            GuangdiantongParamEnum gdtParam = gk.getValue();
            //kuaishou的字段名
            String kuaishouField = kuaishou.getName();
            String baiduParam = gdtParam.getParam();
            String[] value = parameterMap.get(baiduParam);
            if (Objects.isNull(value) || value.length == 0) return;
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

    /**
     * 处理muid：oaid不为空时，muid赋值给imei，否则赋值给idfa
     *
     * @param adsObj
     */
    @Override
    protected void convertParams(Object adsObj) {
        super.convertParams(adsObj);
        KuaishouParamField kuaishouParamField = (KuaishouParamField) adsObj;
        logger.info("convertParams {} 处理kuaishouParamField前 {}", channelAdsKey, kuaishouParamField);
        //imei为空，说明muid是空的，不处理
        if (kuaishouParamField.getImei() == null || "".equals(kuaishouParamField.getImei())) {
            return;
        }
        //oaid不为空，说明是imei，否则是idfa
        if (kuaishouParamField.getOaid() != null) {
            return;
        }
        String imei = kuaishouParamField.getImei();
        kuaishouParamField.setIdfa(imei);
        kuaishouParamField.setImei(null);
        logger.info("convertParams {} 处理kuaishouParamField后 {}", channelAdsKey, kuaishouParamField);
    }

    /**
     * 特殊处理accountId
     */
    @Override
    protected Object saveOriginParamData(Object adsObj) {
        KuaishouParamField kuaishouParamField = (KuaishouParamField) adsObj;
        logger.info("clickReport {} 处理accountId前 {}", channelAdsKey, kuaishouParamField.getAccount_id());
        Object kuaishouAdsDTO = super.saveOriginParamData(adsObj);
        //这个accountId是我们定义的账户第，而快手侧也有这个参数，可能会用这个进行比对，为了不让快手侧误解，这里置空
        kuaishouParamField.setAccount_id(null);
        logger.info("clickReport {} 处理accountId后 {}", channelAdsKey, kuaishouParamField.getAccount_id());
        return kuaishouAdsDTO;
    }

}
