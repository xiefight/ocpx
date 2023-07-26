package huihuang.proxy.ocpx.middle.impl;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouParamEnum;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.IKuaishouAdsDao;
import huihuang.proxy.ocpx.channel.oppo.OppoParamEnum;
import huihuang.proxy.ocpx.channel.oppo.OppoPath;
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
 * @Author: xietao
 * @Date: 2023/6/4 16:27
 */
@Component("okChannelAds")
public class OppoKuaishouChannelAds extends KuaishouReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.OPPO_KUAISHOU;

    @Autowired
    @Qualifier("kuaishouAdsOppoDao")
    private IKuaishouAdsDao kuaishouAdsOppoDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.OPPO_KUAISHOU;
    }

    @Override
    protected String channelName() {
        return OppoPath.OPPO_CHANNEL_NAME;
    }

    @Override
    protected IKuaishouAdsDao adsDao() {
        return kuaishouAdsOppoDao;
    }

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历kuaishou查找baidu对应的宏参数
        Set<KuaishouParamEnum> kuaishouParamEnums = KuaishouParamEnum.kuaishouOppoMap.keySet();
        for (KuaishouParamEnum kuaishou : kuaishouParamEnums) {
            OppoParamEnum oppo = KuaishouParamEnum.kuaishouOppoMap.get(kuaishou);
            if (Objects.isNull(oppo) || StrUtil.isEmpty(oppo.getMacro())) {
                continue;
            }
            macro.append(oppo.getParam()).append("=").append(oppo.getMacro()).append("&");
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

        Set<Map.Entry<KuaishouParamEnum, OppoParamEnum>> okSet = KuaishouParamEnum.kuaishouOppoMap.entrySet();
        okSet.stream().filter(ok -> Objects.nonNull(ok.getValue())).forEach(ok -> {
            KuaishouParamEnum kuaishou = ok.getKey();
            OppoParamEnum oppo = ok.getValue();
            //kuaishou的字段名
            String kuaishouField = kuaishou.getName();
            String oppoParam = oppo.getParam();
            String[] value = parameterMap.get(oppoParam);
            if (Objects.isNull(value) || value.length == 0) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(kuaishouField, kuaishouParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(kuaishouParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
//        kuaishouParamField.setExtra(String.valueOf(parameterMap.get(OppoParamEnum.ADID.getParam())[0]));
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, kuaishouParamField);
        return kuaishouParamField;
    }


}
