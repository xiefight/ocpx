package huihuang.proxy.ocpx.middle.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouAdsDTO;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouParamEnum;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouParamField;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IKuaishouAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.middle.BaseSupport;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * baidu--kuaishou
 *
 * @Author: xietao
 * @Date: 2023/5/10 22:28
 */
@Component("bkChannelAds")
public class BaiduKuaishouChannelAds extends BaseSupport implements IChannelAds {

    @Autowired
    private IKuaishouAdsDao kuaishouAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_KUAISHOU;

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
        return serverPath + Constants.ServerPath.BAIDU_KUAISHOU + Constants.ServerPath.CLICK_REPORT + "?" + macroStr;
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

    @Override
    protected void convertParams(Object adsObj) {
        KuaishouParamField kuaishouParamField = (KuaishouParamField) adsObj;
        if (null != kuaishouParamField.getCallback()) {
            kuaishouParamField.setCallback(URLEncoder.createQuery().encode(kuaishouParamField.getCallback(), StandardCharsets.UTF_8));
        }
//        kuaishouParamField.setApp_type(osConvertAppType(kuaishouParamField.getApp_type()));
        kuaishouParamField.setClick_id(String.valueOf(System.currentTimeMillis()));
        //时间戳，秒
//        String ts = Optional.ofNullable(kuaishouParamField.getTs()).orElse(String.valueOf(System.currentTimeMillis()));
//        kuaishouParamField.setTs(String.valueOf(Long.parseLong(ts) / 1000));
        if (null != kuaishouParamField.getUser_agent()) {
            kuaishouParamField.setUser_agent(URLEncoder.createQuery().encode(kuaishouParamField.getUser_agent(), StandardCharsets.UTF_8));
        }
        //签名
//        signature(kuaishouParamField);
        logger.info("clickReport {} 特殊参数进行转换 convertParams:{}", channelAdsKey, kuaishouParamField);
    }

    @Override
    protected Response judgeParams(Object adsObj) {
        KuaishouParamField kuaishouParamField = (KuaishouParamField) adsObj;
        if (Objects.isNull(kuaishouParamField.getCallback())) {
            return BasicResult.getFailResponse(KuaishouParamEnum.CALLBACK.getName() + "不能为空");
        }
        if (Objects.isNull(kuaishouParamField.getAdid())) {
            return BasicResult.getFailResponse(KuaishouParamEnum.ADID.getName() + "不能为空");
        }
        if (Objects.isNull(kuaishouParamField.getIdfa())
                && Objects.isNull(kuaishouParamField.getImei())
                && Objects.isNull(kuaishouParamField.getOaid())
        ) {
            return BasicResult.getFailResponse("安卓设备：" + KuaishouParamEnum.IMEI.getName() + "、" + KuaishouParamEnum.OAID.getName() + "不能同时为空；"
                    + " ios设备" + KuaishouParamEnum.IDFA.getName() + "不能为空");
        }
        return BasicResult.getSuccessResponse();
    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        KuaishouParamField kuaishouParamField = (KuaishouParamField) adsObj;
        KuaishouAdsDTO kuaishouAdsDTO = new KuaishouAdsDTO();
        BeanUtil.copyProperties(kuaishouParamField, kuaishouAdsDTO);
        kuaishouAdsDTO.setChannelName(BaiduPath.BAIDU_CHANNEL_NAME);
        kuaishouAdsDao.insert(kuaishouAdsDTO);
        logger.info("clickReport {} 将原始参数保存数据库，返回数据库对象 saveOriginParamData:{}", channelAdsKey, kuaishouAdsDTO);
        return kuaishouAdsDTO;
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {
        KuaishouParamField kuaishouParamField = (KuaishouParamField) adsObj;
        KuaishouAdsDTO kuaishouAdsDTO = (KuaishouAdsDTO) adsDtoObj;
        String ocpxUrl = queryServerPath() + Constants.ServerPath.BAIDU_KUAISHOU + Constants.ServerPath.ADS_CALLBACK + "/" + kuaishouAdsDTO.getId() + "?";
        logger.info("clickReport {} 客户回调渠道的url：{}", channelAdsKey, ocpxUrl);
        String encodeUrl = URLEncoder.createQuery().encode(ocpxUrl, StandardCharsets.UTF_8);
//            ocpxUrl = URLEncoder.encode(ocpxUrl, "UTF-8");
        kuaishouParamField.setCallback(encodeUrl);
        logger.info("clickReport {} 回调参数 replaceCallbackUrl:{}", channelAdsKey, kuaishouParamField);
    }

    @Override
    protected String initAdsUrl() {
        return KuaishouPath.URI + KuaishouPath.PROMOTION;
    }

    @Override
    protected Response reportAds(String adsUrl, Object adsDtoObj) throws Exception {
        logger.info("调用用户侧的地址 {} adsUrl:{}", channelAdsKey, adsUrl);
        //todo 重定向 302问题
        HttpResponse response = HttpRequest.get(adsUrl).timeout(20000).header("token", "application/json").execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        KuaishouAdsDTO kuaishouAdsDTO = (KuaishouAdsDTO) adsDtoObj;
        KuaishouAdsDTO kuaishouAdsVO = new KuaishouAdsDTO();
        kuaishouAdsVO.setId(kuaishouAdsDTO.getId());
        //上报成功
        if (HttpStatus.HTTP_OK == response.getStatus() && Objects.requireNonNull(responseBodyMap).get("ret").equals("0")) {
            kuaishouAdsVO.setReportStatus(Constants.ReportStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(kuaishouAdsVO, kuaishouAdsDao);
            logger.info("clickReport {} 上报广告侧接口请求成功:{} 数据:{}", channelAdsKey, response, kuaishouAdsVO);
            return BasicResult.getSuccessResponse(kuaishouAdsDTO.getId());
        } else {
            kuaishouAdsVO.setReportStatus(Constants.ReportStatus.FAIL.getCode() + "--" + JSONObject.toJSONString(responseBodyMap));
            baseServiceInner.updateAdsObject(kuaishouAdsVO, kuaishouAdsDao);
            logger.error("clickReport {} 上报广告侧接口请求失败:{} 数据:{}", channelAdsKey, response, kuaishouAdsVO);
            return BasicResult.getFailResponse("上报广告侧接口请求失败", 0);
        }
    }


}
