package huihuang.proxy.ocpx.middle.baseadsreport.liangdamao;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamEnum;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamField;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoPath;
import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.common.Constants;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @Description: 上报客户的逻辑再抽象
 * 比如：百度和京东、优酷、番茄的对接，本质上是百度和粮大猫的对接，抽离出百度和粮大猫的公共部分，将京东、优酷、番茄的不同参数传入
 * @Author: xietao
 * @Date: 2023-05-23 17:39
 **/
public abstract class BaiduLiangdamaoReportFactory extends BaseLiangdamaoReportFactory {

    @Override
    protected String channelName() {
        return BaiduPath.BAIDU_CHANNEL_NAME;
    }

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历liangdamao查找baidu对应的宏参数
        Set<LiangdamaoParamEnum> liangdamaoParamEnums = LiangdamaoParamEnum.liangdamaoBaiduMap.keySet();
        for (LiangdamaoParamEnum liangdamao : liangdamaoParamEnums) {
            BaiduParamEnum baidu = LiangdamaoParamEnum.liangdamaoBaiduMap.get(liangdamao);
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
        return serverPath + serverPathKey() + Constants.ServerPath.CLICK_REPORT + "?" + macroStr;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        LiangdamaoParamField liangdamaoParamField = new LiangdamaoParamField();

        Set<Map.Entry<LiangdamaoParamEnum, BaiduParamEnum>> blSet = LiangdamaoParamEnum.liangdamaoBaiduMap.entrySet();
        blSet.stream().filter(bl -> Objects.nonNull(bl.getValue())).forEach(bl -> {
            LiangdamaoParamEnum liangdamao = bl.getKey();
            BaiduParamEnum baidu = bl.getValue();
            //liangdamao的字段名
            String liangdamaoField = liangdamao.getName();
            String baiduParam = baidu.getParam();
            String[] value = parameterMap.get(baiduParam);
            if (Objects.isNull(value) || value.length == 0) return;
            if ("null".equals(value[0]) || "NULL".equals(value[0])) return;
            if (value[0].startsWith("__") && value[0].endsWith("__")) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(liangdamaoField, liangdamaoParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(liangdamaoParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        liangdamaoParamField.setAccess_id(LiangdamaoPath.ACCESS_ID);
        return liangdamaoParamField;
    }

}
