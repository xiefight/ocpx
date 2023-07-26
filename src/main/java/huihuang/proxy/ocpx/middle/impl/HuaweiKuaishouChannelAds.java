package huihuang.proxy.ocpx.middle.impl;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouParamEnum;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.IKuaishouAdsDao;
import huihuang.proxy.ocpx.channel.huawei.HuaweiParamEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiPath;
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
 * huawei-kuaishou
 *
 * @Author: xietao
 * @Date: 2023/5/14 11:47
 */
@Component("hkChannelAds")
public class HuaweiKuaishouChannelAds extends KuaishouReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.HUAWEI_KUAISHOU;

    @Autowired
    @Qualifier("kuaishouAdsHuaweiDao")
    private IKuaishouAdsDao kuaishouAdsHuaweiDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.HUAWEI_KUAISHOU;
    }

    @Override
    protected String channelName() {
        return HuaweiPath.HUAWEI_CHANNEL_NAME;
    }

    @Override
    protected IKuaishouAdsDao adsDao() {
        return kuaishouAdsHuaweiDao;
    }


    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历广告主查找渠道对应的宏参数
        Set<KuaishouParamEnum> kuaishouParamEnums = KuaishouParamEnum.kuaishouHuaweiMap.keySet();
        for (KuaishouParamEnum kuaishou : kuaishouParamEnums) {
            HuaweiParamEnum huawei = KuaishouParamEnum.kuaishouHuaweiMap.get(kuaishou);
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
        return serverPath + Constants.ServerPath.HUAWEI_KUAISHOU + Constants.ServerPath.CLICK_REPORT + "?" + macroStr;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        KuaishouParamField kuaishouParamField = new KuaishouParamField();

        Set<Map.Entry<KuaishouParamEnum, HuaweiParamEnum>> hkSet = KuaishouParamEnum.kuaishouHuaweiMap.entrySet();
        hkSet.stream().filter(xj -> Objects.nonNull(xj.getValue())).forEach(hk -> {
            KuaishouParamEnum kuaishou = hk.getKey();
            HuaweiParamEnum huawei = hk.getValue();
            //kuaishou的字段名
            String kuaishouField = kuaishou.getName();
            String huaweiParam = huawei.getParam();
            String[] value = parameterMap.get(huaweiParam);
            if (Objects.isNull(value) || value.length == 0) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(kuaishouField, kuaishouParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(kuaishouParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        //存储华为这边必有而快手这不必有的参数，回传可能会用到
        fitExtras(parameterMap, kuaishouParamField,
                HuaweiParamEnum.CONTENT_ID.getParam(),
                HuaweiParamEnum.EVENT_TYPE.getParam(),
                HuaweiParamEnum.TRACE_TIME.getParam(),
                HuaweiParamEnum.TRACKING_ENABLED.getParam());
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, kuaishouParamField);
        return kuaishouParamField;
    }

    private void fitExtras(Map<String, String[]> parameterMap, KuaishouParamField kuaishouParamField, String... extras) {
        StringBuilder extraStr = new StringBuilder();
        for (String extra : extras) {
            String[] cids = parameterMap.get(extra);
            if (Objects.nonNull(cids) && cids.length > 0) {
                String cid = cids[0];
                extraStr.append("&").append(extra).append("=").append(cid);
            }
        }
        if (extras.length > 0) {
            kuaishouParamField.setExtra(extraStr.toString());
        }
    }

}
