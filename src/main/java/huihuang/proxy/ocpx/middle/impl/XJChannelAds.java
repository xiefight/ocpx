package huihuang.proxy.ocpx.middle.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.crypto.digest.DigestUtil;
import huihuang.proxy.ocpx.ads.litianjingdong.LTJDAdsDTO;
import huihuang.proxy.ocpx.ads.litianjingdong.LTJDParamEnum;
import huihuang.proxy.ocpx.ads.litianjingdong.LTJDParamField;
import huihuang.proxy.ocpx.ads.litianjingdong.LTJDPath;
import huihuang.proxy.ocpx.ads.meituan.MeiTuanAdsDTO;
import huihuang.proxy.ocpx.ads.meituan.MeiTuanParamField;
import huihuang.proxy.ocpx.bussiness.dao.ILtjdAdsDao;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiParamEnum;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.middle.BaseSupport;
import huihuang.proxy.ocpx.middle.IChannelAds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * @Description: tljd-xiaomi
 * @Author: xietao
 * @Date: 2023-04-24 17:31
 **/
@Component
public class XJChannelAds extends BaseSupport implements IChannelAds {

    @Autowired
    private ILtjdAdsDao ltjdAdsDao;

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历tljd查找xiaomi对应的宏参数
        Set<LTJDParamEnum> ltjdParamEnums = LTJDParamEnum.xjMap.keySet();
        for (LTJDParamEnum ltjd : ltjdParamEnums) {
            XiaomiParamEnum xiaomi = LTJDParamEnum.xjMap.get(ltjd);
            if (Objects.isNull(xiaomi)) {
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
        return serverPath + "/xjServer/clickReport" + "?" + macroStr;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        LTJDParamField ltjdParamField = new LTJDParamField();

        Set<Map.Entry<LTJDParamEnum, XiaomiParamEnum>> xjSet = LTJDParamEnum.xjMap.entrySet();
        xjSet.stream().filter(xj -> Objects.nonNull(xj.getValue())).forEach(tm -> {
            LTJDParamEnum ltjd = tm.getKey();
            XiaomiParamEnum xiaomi = tm.getValue();
            //ltjd的字段名
            String ltjdField = ltjd.getName();
            //xiaomi的字段名
            String xiaomiParam = xiaomi.getParam();
            //xiaomi的参数值
            String[] value = parameterMap.get(xiaomiParam);
            if (Objects.isNull(value) || value.length == 0) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(ltjdField, ltjdParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(ltjdParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return ltjdParamField;
    }

    @Override
    protected void convertParams(Object adsObj) {
        LTJDParamField ltjdParamField = (LTJDParamField) adsObj;
        ltjdParamField.setCallback_url(URLEncoder.createQuery().encode(ltjdParamField.getCallback_url(), StandardCharsets.UTF_8));
//        ltjdParamField.setApp_type(osConvertAppType(ltjdParamField.getApp_type()));
        ltjdParamField.setRequest_id(String.valueOf(System.currentTimeMillis()));
        //签名
        signature(ltjdParamField);
        //时间戳，秒
        String ts = Optional.ofNullable(ltjdParamField.getTs()).orElse(String.valueOf(System.currentTimeMillis()));
        ltjdParamField.setTs(String.valueOf(Long.parseLong(ts) / 1000));
    }

    @Override
    protected Response judgeParams(Object adsObj) throws Exception {
        LTJDParamField ltjdParamField = (LTJDParamField) adsObj;
        if (Objects.isNull(ltjdParamField.getSignature())) {
            return BasicResult.getFailResponse(LTJDParamEnum.SIGNATURE.getName() + "不能为空");
        }
        if (Objects.isNull(ltjdParamField.getTp_adv_id())) {
            return BasicResult.getFailResponse(LTJDParamEnum.TP_ADV_ID.getName() + "不能为空");
        }
        if (Objects.isNull(ltjdParamField.getAccess_id())) {
            return BasicResult.getFailResponse(LTJDParamEnum.ACCESS_ID.getName() + "不能为空");
        }
        if (Objects.isNull(ltjdParamField.getRequest_id())) {
            return BasicResult.getFailResponse(LTJDParamEnum.REQUEST_ID.getName() + "不能为空");
        }
        //如果ios设备为空，则判断安卓设备
        if (Objects.isNull(ltjdParamField.getIdfa()) && Objects.isNull(ltjdParamField.getIdfa_md5())
                && Objects.isNull(ltjdParamField.getImei()) && Objects.isNull(ltjdParamField.getImei_md5())
                && Objects.isNull(ltjdParamField.getOaid()) && Objects.isNull(ltjdParamField.getOaid_md5())) {
            return BasicResult.getFailResponse("安卓设备：" + LTJDParamEnum.IMEI.getName() + "、" + LTJDParamEnum.OAID.getName()
                    + "、" + LTJDParamEnum.IMEI_MD5.getName() + "、" + LTJDParamEnum.OAID_MD5.getName() + "不能同时为空；"
                    + " ios设备" + LTJDParamEnum.IDFA.getName() + "、" + LTJDParamEnum.IDFA_MD5.getName() + "不能同时为空");
        }
        return BasicResult.getSuccessResponse();
    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        LTJDParamField ltjdParamField = (LTJDParamField) adsObj;
        LTJDAdsDTO ltjdAdsDTO = new LTJDAdsDTO();
        BeanUtil.copyProperties(ltjdParamField, ltjdAdsDTO);
        ltjdAdsDao.insert(ltjdAdsDTO);
        return ltjdAdsDTO;
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {

    }

    @Override
    protected String initAdsUrl() {
        return null;
    }

    @Override
    protected Response reportAds(String adsUrl, Object adsDtoObj) throws Exception {
        return null;
    }

    //计算签名
    private void signature(LTJDParamField ltjdParamField) {
        String access_id = ltjdParamField.getAccess_id();
        String ts = ltjdParamField.getTs();
        String src = "access_id=" + access_id + "&ts=" + ts;
        String signatureStr = src + LTJDPath.SECRET;
        String signature = DigestUtil.md5Hex(signatureStr).toLowerCase();
        ltjdParamField.setSignature(signature);
    }


}
