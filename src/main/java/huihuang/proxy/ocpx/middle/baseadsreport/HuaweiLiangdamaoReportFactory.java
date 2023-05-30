package huihuang.proxy.ocpx.middle.baseadsreport;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamEnum;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamField;
import huihuang.proxy.ocpx.channel.huawei.HuaweiParamEnum;
import huihuang.proxy.ocpx.common.Constants;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 上报客户的逻辑再抽象
 * * 比如：百度和京东、优酷、番茄的对接，本质上是百度和粮大猫的对接，抽离出百度和粮大猫的公共部分，将京东、优酷、番茄的不同参数传入
 *
 * @Author: xietao
 * @Date: 2023/5/28 15:56
 */
public abstract class HuaweiLiangdamaoReportFactory extends BaseLiangdamaoReportFactory {

    protected abstract String channelAdsKey();

    protected abstract String serverPathKey();

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历liangdamao查找huawei对应的宏参数
        Set<LiangdamaoParamEnum> liangdamaoParamEnums = LiangdamaoParamEnum.liangdamaoHuaweiMap.keySet();
        for (LiangdamaoParamEnum liangdamao : liangdamaoParamEnums) {
            HuaweiParamEnum huawei = LiangdamaoParamEnum.liangdamaoHuaweiMap.get(liangdamao);
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
        return serverPath + serverPathKey() + Constants.ServerPath.CLICK_REPORT + "?" + macroStr;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        LiangdamaoParamField liangdamaoParamField = new LiangdamaoParamField();

        Set<Map.Entry<LiangdamaoParamEnum, HuaweiParamEnum>> hlSet = LiangdamaoParamEnum.liangdamaoHuaweiMap.entrySet();
        hlSet.stream().filter(hl -> Objects.nonNull(hl.getValue())).forEach(hl -> {
            LiangdamaoParamEnum liangdamao = hl.getKey();
            HuaweiParamEnum huawei = hl.getValue();
            //liangdamao的字段名
            String liangdamaoField = liangdamao.getName();
            String huaweiParam = huawei.getParam();
            String[] value = parameterMap.get(huaweiParam);
            if (Objects.isNull(value) || value.length == 0) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(liangdamaoField, liangdamaoParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(liangdamaoParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return liangdamaoParamField;
    }

    protected String fitExtras(Map<String, String[]> parameterMap, String... extras) {
//        if (extras.length == 0) {
//            extras[0] = HuaweiParamEnum.CONTENT_ID.getParam();
//            extras[1] = HuaweiParamEnum.EVENT_TYPE.getParam();
//            extras[2] = HuaweiParamEnum.TRACE_TIME.getParam();
//            extras[3] = HuaweiParamEnum.TRACKING_ENABLED.getParam();
//        }
        StringBuilder extraStr = new StringBuilder();
        for (String extra : extras) {
            String[] cids = parameterMap.get(extra);
            if (Objects.nonNull(cids) && cids.length > 0) {
                String cid = cids[0];
                extraStr.append("&").append(extra).append("=").append(cid);
            }
        }
        return extraStr.toString();
    }

}
