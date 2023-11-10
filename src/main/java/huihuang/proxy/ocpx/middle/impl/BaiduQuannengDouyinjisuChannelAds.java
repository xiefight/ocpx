package huihuang.proxy.ocpx.middle.impl;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongParamEnum;
import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.IQuannengDouyinjisuAdsDao;
import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.QuannengHudongReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Component("bqdyjsChannelAds")
public class BaiduQuannengDouyinjisuChannelAds extends QuannengHudongReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_QUANNENG_DOUYIN_JISU;

    @Autowired
    private IQuannengDouyinjisuAdsDao dyjsAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_QUANNENG_DOUYIN_JISU;
    }

    @Override
    protected String channelName() {
        return BaiduPath.BAIDU_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return dyjsAdsDao;
    }


    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历广告主查找渠道对应的宏参数
        Set<QuannengHudongParamEnum> quannengHudongParamEnums = QuannengHudongParamEnum.quannengHudongBaiduMap.keySet();
        for (QuannengHudongParamEnum quannengHudong : quannengHudongParamEnums) {
            BaiduParamEnum baidu = QuannengHudongParamEnum.quannengHudongBaiduMap.get(quannengHudong);
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
        return serverPath + Constants.ServerPath.BAIDU_QUANNENG_DOUYIN_JISU + Constants.ServerPath.CLICK_REPORT + "?" + macroStr;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        QuannengHudongParamField quannengHudongParamField = new QuannengHudongParamField();

        Set<Map.Entry<QuannengHudongParamEnum, BaiduParamEnum>> qhSet = QuannengHudongParamEnum.quannengHudongBaiduMap.entrySet();
        qhSet.stream().filter(bh -> Objects.nonNull(bh.getValue())).forEach(bh -> {
            QuannengHudongParamEnum quannengHudong = bh.getKey();
            BaiduParamEnum baidu = bh.getValue();
            String quannengHudongField = quannengHudong.getName();
            String baiduParam = baidu.getParam();
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
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, quannengHudongParamField);
        return quannengHudongParamField;
    }

}