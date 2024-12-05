package huihuang.proxy.ocpx.middle.baseadsreport.weibo;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import huihuang.proxy.ocpx.ads.weibo.WeiboParamEnum;
import huihuang.proxy.ocpx.ads.weibo.WeiboParamField;
import huihuang.proxy.ocpx.channel.oppo.OppoParamEnum;
import huihuang.proxy.ocpx.common.Constants;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class OppoWeiboReportFactory extends WeiboReportFactory {

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历kuaishou查找baidu对应的宏参数
        Set<WeiboParamEnum> weiboParamEnums = WeiboParamEnum.weiboOppoMap.keySet();
        for (WeiboParamEnum weibo : weiboParamEnums) {
            OppoParamEnum oppo = WeiboParamEnum.weiboOppoMap.get(weibo);
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
        WeiboParamField weiboParamField = new WeiboParamField();

        Set<Map.Entry<WeiboParamEnum, OppoParamEnum>> hbSet = WeiboParamEnum.weiboOppoMap.entrySet();
        hbSet.stream().filter(hb -> Objects.nonNull(hb.getValue())).forEach(hb -> {
            WeiboParamEnum weibo = hb.getKey();
            OppoParamEnum oppo = hb.getValue();
            String weiboField = weibo.getName();
            String huaweiParam = oppo.getParam();
            String[] value = parameterMap.get(huaweiParam);
            if (Objects.isNull(value) || value.length == 0) return;
            if ("null".equals(value[0]) || "NULL".equals(value[0])) return;
            if (value[0].startsWith("$") && value[0].endsWith("$")) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(weiboField, weiboParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(weiboParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey(), weiboParamField);
        return weiboParamField;
    }


    @Override
    protected void convertParams(Object adsObj) {
        WeiboParamField weiboParamField = (WeiboParamField) adsObj;
        String oaid_md5 = weiboParamField.getOaid_md5();
        if (oaid_md5 != null) {
            weiboParamField.setOaid_md5(MD5.create().digestHex(oaid_md5));
        }
        super.convertParams(weiboParamField);
    }

}
