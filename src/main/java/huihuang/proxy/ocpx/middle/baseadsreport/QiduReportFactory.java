package huihuang.proxy.ocpx.middle.baseadsreport;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.qidu.QiduAdsDTO;
import huihuang.proxy.ocpx.ads.qidu.QiduParamEnum;
import huihuang.proxy.ocpx.ads.qidu.QiduParamField;
import huihuang.proxy.ocpx.ads.qidu.QiduPath;
import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongParamEnum;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiParamEnum;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.BaseSupport;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import huihuang.proxy.ocpx.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class QiduReportFactory extends BaseSupport implements IChannelAds {

    @Autowired
    protected BaseServiceInner baseServiceInner;

    protected abstract String channelAdsKey();

    protected abstract String serverPathKey();

    protected abstract String channelName();

    protected abstract IMarkDao adsDao();


    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历广告主查找渠道对应的宏参数
        Set<QiduParamEnum> qiduParamEnums = QiduParamEnum.qiduXiaomiMap.keySet();
        for (QiduParamEnum qidu : qiduParamEnums) {
            XiaomiParamEnum xiaomi = QiduParamEnum.qiduXiaomiMap.get(qidu);
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
    protected void convertParams(Object adsObj) {
        QiduParamField qiduParamField = (QiduParamField) adsObj;
        if (null != qiduParamField.getCallback()) {
            qiduParamField.setCallback(URLEncoder.createQuery().encode(qiduParamField.getCallback(), StandardCharsets.UTF_8));
        }
        //uniqueid
        qiduParamField.setClickid(RandomUtil.randomStamp());
//        if (null != qiduParamField.getAdAgent()) {
//            qiduParamField.setAdAgent(URLEncoder.createQuery().encode(qiduParamField.getAdAgent(), StandardCharsets.UTF_8));
//        }
//        if (qiduParamField.getIdfa()!=null){
//            qiduParamField.setOs("ios");
//        }else {
//            qiduParamField.setOs("android");
//        }
        logger.info("clickReport {} 特殊参数进行转换 convertParams:{}", channelAdsKey(), qiduParamField);
    }

    @Override
    protected Response judgeParams(Object adsObj) {
        QiduParamField qiduParamField = (QiduParamField) adsObj;
        if (Objects.isNull(qiduParamField.getCallback())) {
            return BasicResult.getFailResponse(QuannengHudongParamEnum.CALLBACK.getName() + "不能为空");
        }
//        if (Objects.isNull(qiduParamField.getUniqueid())) {
//            return BasicResult.getFailResponse(QuannengHudongParamEnum.UNIQUE_ID.getName() + "不能为空");
//        }
//        if (Objects.isNull(qiduParamField.getIdfa())
//                && Objects.isNull(qiduParamField.getImei())
//                && Objects.isNull(qiduParamField.getOaid())
//        ) {
//            return BasicResult.getFailResponse("安卓设备：" + QuannengHudongParamEnum.IMEI.getName() + "、" + QuannengHudongParamEnum.OAID.getName() + "不能同时为空；"
//                    + " ios设备" + QuannengHudongParamEnum.IDFA.getName() + "不能为空");
//        }
        return BasicResult.getSuccessResponse();
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        QiduParamField qiduParamField = new QiduParamField();

        Set<Map.Entry<QiduParamEnum, XiaomiParamEnum>> blSet = QiduParamEnum.qiduXiaomiMap.entrySet();
        blSet.stream().filter(bl -> Objects.nonNull(bl.getValue())).forEach(bl -> {
            QiduParamEnum qidu = bl.getKey();
            XiaomiParamEnum xiaomi = bl.getValue();
            String qiduField = qidu.getName();
            String baiduParam = xiaomi.getParam();
            String[] value = parameterMap.get(baiduParam);
            if (Objects.isNull(value) || value.length == 0) return;
            if ("null".equals(value[0]) || "NULL".equals(value[0])) return;
            if (value[0].startsWith("__") && value[0].endsWith("__")) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(qiduField, qiduParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(qiduParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
//        qiduParamField.setAccess_id(LiangdamaoPath.ACCESS_ID);
        return qiduParamField;
    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        QiduParamField qiduParamField = (QiduParamField) adsObj;
        QiduAdsDTO qiduAdsDTO = new QiduAdsDTO();
        BeanUtil.copyProperties(qiduParamField, qiduAdsDTO);
        qiduAdsDTO.setChannelName(channelName());
        baseServiceInner.insertAdsObject(qiduAdsDTO, adsDao());
        logger.info("clickReport {} 将原始参数保存数据库，返回数据库对象 saveOriginParamData:{}", channelAdsKey(), qiduAdsDTO);
        return qiduAdsDTO;
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {
        QiduParamField qiduParamField = (QiduParamField) adsObj;
        QiduAdsDTO qiduAdsDTO = (QiduAdsDTO) adsDtoObj;
        String ocpxUrl = queryServerPath() + serverPathKey() + Constants.ServerPath.ADS_CALLBACK + "/" + qiduAdsDTO.getId() + "?";
        logger.info("clickReport {} 客户回调渠道的url：{}", channelAdsKey(), ocpxUrl);
        String encodeUrl = URLEncoder.createQuery().encode(ocpxUrl, StandardCharsets.UTF_8);
//            ocpxUrl = URLEncoder.encode(ocpxUrl, "UTF-8");
        qiduParamField.setCallback(encodeUrl);
        logger.info("clickReport {} 回调参数 replaceCallbackUrl:{}", channelAdsKey(), qiduParamField);
    }

    @Override
    protected String initAdsUrl() {
        return QiduPath.BASIC_URI;
    }

    @Override
    protected Response reportAds(String adsUrl, Object adsDtoObj) throws Exception {
        logger.info("调用用户侧的地址 {} adsUrl:{}", channelAdsKey(), adsUrl);
        HttpResponse response = HttpRequest.get(adsUrl).timeout(20000).header("token", "application/json").execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        QiduAdsDTO qiduAdsDTO = (QiduAdsDTO) adsDtoObj;
        QiduAdsDTO qiduAdsVO = new QiduAdsDTO();
        qiduAdsVO.setId(qiduAdsDTO.getId());
        //上报成功
        if (HttpStatus.HTTP_OK == response.getStatus() && Integer.parseInt(String.valueOf(Objects.requireNonNull(responseBodyMap).get("error_code"))) == 0) {
            qiduAdsVO.setReportStatus(Constants.ReportStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(qiduAdsVO, adsDao());
            logger.info("clickReport {} 上报广告侧接口请求成功:{} 数据:{}", channelAdsKey(), response, qiduAdsVO);
            return BasicResult.getSuccessResponse(qiduAdsDTO.getId());
        } else {
            qiduAdsVO.setReportStatus(Constants.ReportStatus.FAIL.getCode() + "--" + JSONObject.toJSONString(responseBodyMap));
            baseServiceInner.updateAdsObject(qiduAdsVO, adsDao());
            logger.error("clickReport {} 上报广告侧接口请求失败:{} 数据:{}", channelAdsKey(), response, qiduAdsVO);
            return BasicResult.getFailResponse("上报广告侧接口请求失败", 0);
        }
    }
}
