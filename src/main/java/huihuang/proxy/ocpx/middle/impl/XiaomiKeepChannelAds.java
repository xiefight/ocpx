package huihuang.proxy.ocpx.middle.impl;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.keep.KeepParamEnum;
import huihuang.proxy.ocpx.ads.keep.KeepParamField;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamEnum;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamField;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoPath;
import huihuang.proxy.ocpx.ads.liangdamao.douyin.DouyinPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IKeepAdsDao;
import huihuang.proxy.ocpx.channel.huawei.HuaweiParamEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiPath;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiParamEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.KeepReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Component
public class XiaomiKeepChannelAds extends KeepReportFactory {


    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_KEEP;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.XIAOMI_KEEP;
    }

    @Override
    protected String channelName() {
        return XiaomiPath.XIAOMI_CHANNEL_NAME;
    }

    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历广告主查找渠道对应的宏参数
        Set<KeepParamEnum> keepParamEnums = KeepParamEnum.keepXiaomiMap.keySet();
        for (KeepParamEnum keep : keepParamEnums) {
            XiaomiParamEnum xiaomi = KeepParamEnum.keepXiaomiMap.get(keep);
            if (Objects.isNull(xiaomi) || StrUtil.isEmpty(xiaomi.getMacro())) {
                continue;
            }
            macro.append(xiaomi.getParam()).append("=").append(xiaomi.getMacro()).append("&");
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
        KeepParamField keepParamField = new KeepParamField();

        Set<Map.Entry<KeepParamEnum, XiaomiParamEnum>> xlSet = KeepParamEnum.keepXiaomiMap.entrySet();
        xlSet.stream().filter(xl -> Objects.nonNull(xl.getValue())).forEach(xl -> {
            KeepParamEnum keep = xl.getKey();
            XiaomiParamEnum xiaomi = xl.getValue();
            //ltjd的字段名
            String keepField = keep.getName();
            //xiaomi的字段名
            String xiaomiParam = xiaomi.getParam();
            //xiaomi的参数值
            String[] value = parameterMap.get(xiaomiParam);
            if (Objects.isNull(value) || value.length == 0) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(keepField, keepParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(keepParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return keepParamField;
    }

}
