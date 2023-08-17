package huihuang.proxy.ocpx.middle.impl;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouParamEnum;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.IKuaishouAdsDao;
import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
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
 * @Date: 2023/8/17 16:51
 */
@Component("bdssksChannelAds")
public class BdssKuaishouChannelAds extends KuaishouReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.BDSS_KUAISHOU;

    @Autowired
    @Qualifier("kuaishouAdsBdssDao")
    private IKuaishouAdsDao kuaishouAdsBdssDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BDSS_KUAISHOU;
    }

    @Override
    protected String channelName() {
        return BaiduPath.BAIDU_CHANNEL_NAME;
    }

    @Override
    protected IKuaishouAdsDao adsDao() {
        return kuaishouAdsBdssDao;
    }

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历kuaishou查找baidu对应的宏参数
        Set<KuaishouParamEnum> kuaishouParamEnums = KuaishouParamEnum.kuaishouBaiduMap.keySet();
        for (KuaishouParamEnum kuaishou : kuaishouParamEnums) {
            BaiduParamEnum baidu = KuaishouParamEnum.kuaishouBaiduMap.get(kuaishou);
            if (Objects.isNull(baidu) || StrUtil.isEmpty(baidu.getMacro())) {
                continue;
            }
            macro.append(baidu.getParam()).append("=").append(baidu.getMacro()).append("&");
        }
        String macroStr = macro.toString();
        if (macroStr.endsWith("&")) {
            macroStr = macroStr.substring(0, macroStr.length() - 1);
        }
        //2.config中查找服务地址
        String serverPath = queryServerPath();
        //3.拼接监测地址
        return serverPath + Constants.ServerPath.BDSS_KUAISHOU + Constants.ServerPath.CLICK_REPORT + "?" + macroStr;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        KuaishouParamField kuaishouParamField = new KuaishouParamField();

        Set<Map.Entry<KuaishouParamEnum, BaiduParamEnum>> bkSet = KuaishouParamEnum.kuaishouBaiduMap.entrySet();
        bkSet.stream().filter(bk -> Objects.nonNull(bk.getValue())).forEach(tm -> {
            KuaishouParamEnum kuaishou = tm.getKey();
            BaiduParamEnum baidu = tm.getValue();
            //kuaishou的字段名
            String kuaishouField = kuaishou.getName();
            String baiduParam = baidu.getParam();
            String[] value = parameterMap.get(baiduParam);
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
        //特殊处理oaid
        String[] oaids = parameterMap.get(BaiduParamEnum.OAID.getParam());
        if (null != oaids && oaids.length > 0) {
            kuaishouParamField.setOaid(oaids[0]);
        } else {
            String[] oaidMd5s = parameterMap.get(BaiduParamEnum.OAID_MD5.getParam());
            if (null != oaidMd5s && oaidMd5s.length > 0) {
                kuaishouParamField.setOaid(oaidMd5s[0]);
            }
        }
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, kuaishouParamField);
        return kuaishouParamField;
    }


}
