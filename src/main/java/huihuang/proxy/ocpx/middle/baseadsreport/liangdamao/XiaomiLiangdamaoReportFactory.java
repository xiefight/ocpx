package huihuang.proxy.ocpx.middle.baseadsreport.liangdamao;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamEnum;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamField;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoPath;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiParamEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiPath;
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
public abstract class XiaomiLiangdamaoReportFactory extends BaseLiangdamaoReportFactory {

    @Override
    protected String channelName() {
        return XiaomiPath.XIAOMI_CHANNEL_NAME;
    }

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历ltjd查找xiaomi对应的宏参数
        Set<LiangdamaoParamEnum> liangdamaoParamEnums = LiangdamaoParamEnum.liangdamaoXiaomiMap.keySet();
        for (LiangdamaoParamEnum liangdamao : liangdamaoParamEnums) {
            XiaomiParamEnum xiaomi = LiangdamaoParamEnum.liangdamaoXiaomiMap.get(liangdamao);
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
        LiangdamaoParamField liangdamaoParamField = new LiangdamaoParamField();

        Set<Map.Entry<LiangdamaoParamEnum, XiaomiParamEnum>> xlSet = LiangdamaoParamEnum.liangdamaoXiaomiMap.entrySet();
        xlSet.stream().filter(xl -> Objects.nonNull(xl.getValue())).forEach(xl -> {
            LiangdamaoParamEnum liangdamao = xl.getKey();
            XiaomiParamEnum xiaomi = xl.getValue();
            //ltjd的字段名
            String liangdamaoField = liangdamao.getName();
            //xiaomi的字段名
            String xiaomiParam = xiaomi.getParam();
            //xiaomi的参数值
            String[] value = parameterMap.get(xiaomiParam);
            if (Objects.isNull(value) || value.length == 0) return;
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


    //计算签名
    private void signature(LiangdamaoParamField liangdamaoParamField) {
        String access_id = liangdamaoParamField.getAccess_id();
        String ts = liangdamaoParamField.getTs();
        String src = "access_id=" + access_id + "&ts=" + ts;
        String signatureStr = src + LiangdamaoPath.SECRET;
        String signature = DigestUtil.md5Hex(signatureStr).toLowerCase();
        logger.info("clickReport {} 原始:{}  签名:{}", channelAdsKey(), signatureStr, signature);
        liangdamaoParamField.setSignature(signature);
    }

}
