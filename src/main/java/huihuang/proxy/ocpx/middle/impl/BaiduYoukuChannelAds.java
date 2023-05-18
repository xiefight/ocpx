package huihuang.proxy.ocpx.middle.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.litianjingdong.LTJDPath;
import huihuang.proxy.ocpx.ads.youku.YoukuAdsDTO;
import huihuang.proxy.ocpx.ads.youku.YoukuParamEnum;
import huihuang.proxy.ocpx.ads.youku.YoukuParamField;
import huihuang.proxy.ocpx.ads.youku.YoukuPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IYoukuAdsDao;
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
import java.util.Optional;
import java.util.Set;

/**
 * baidu--youku
 *
 * @Author: xietao
 * @Date: 2023/5/10 22:28
 */
@Component("byChannelAds")
public class BaiduYoukuChannelAds extends BaseSupport implements IChannelAds {

    @Autowired
    private IYoukuAdsDao youkuAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_YOUKU;

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历youku查找xiaomi对应的宏参数
        Set<YoukuParamEnum> youkuParamEnums = YoukuParamEnum.baiduYoukuMap.keySet();
        for (YoukuParamEnum youku : youkuParamEnums) {
            BaiduParamEnum baidu = YoukuParamEnum.baiduYoukuMap.get(youku);
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
        return serverPath + Constants.ServerPath.BAIDU_YOUKU + Constants.ServerPath.CLICK_REPORT + "?" + macroStr;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        YoukuParamField youkuParamField = new YoukuParamField();

        Set<Map.Entry<YoukuParamEnum, BaiduParamEnum>> xjSet = YoukuParamEnum.baiduYoukuMap.entrySet();
        xjSet.stream().filter(xj -> Objects.nonNull(xj.getValue())).forEach(tm -> {
            YoukuParamEnum ltjd = tm.getKey();
            BaiduParamEnum baidu = tm.getValue();
            //ltjd的字段名
            String ltjdField = ltjd.getName();
            String baiduParam = baidu.getParam();
            String[] value = parameterMap.get(baiduParam);
            if (Objects.isNull(value) || value.length == 0) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(ltjdField, youkuParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(youkuParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, youkuParamField);
        return youkuParamField;
    }

    @Override
    protected void convertParams(Object adsObj) {
        YoukuParamField youkuParamField = (YoukuParamField) adsObj;
        if (null != youkuParamField.getCallback_url()) {
            youkuParamField.setCallback_url(URLEncoder.createQuery().encode(youkuParamField.getCallback_url(), StandardCharsets.UTF_8));
        }
//        youkuParamField.setApp_type(osConvertAppType(youkuParamField.getApp_type()));
        youkuParamField.setRequest_id(String.valueOf(System.currentTimeMillis()));
        //时间戳，秒
        String ts = Optional.ofNullable(youkuParamField.getTs()).orElse(String.valueOf(System.currentTimeMillis()));
        youkuParamField.setTs(String.valueOf(Long.parseLong(ts) / 1000));
        if (null != youkuParamField.getUa()) {
            youkuParamField.setUa(URLEncoder.createQuery().encode(youkuParamField.getUa(), StandardCharsets.UTF_8));
        }
        //签名
        signature(youkuParamField);
        logger.info("clickReport {} 特殊参数进行转换 convertParams:{}", channelAdsKey, youkuParamField);
    }

    @Override
    protected Response judgeParams(Object adsObj) {
        YoukuParamField youkuParamField = (YoukuParamField) adsObj;
        if (Objects.isNull(youkuParamField.getSignature())) {
            return BasicResult.getFailResponse(YoukuParamEnum.SIGNATURE.getName() + "不能为空");
        }
        if (Objects.isNull(youkuParamField.getTp_adv_id())) {
            return BasicResult.getFailResponse(YoukuParamEnum.TP_ADV_ID.getName() + "不能为空");
        }
        if (Objects.isNull(youkuParamField.getAccess_id())) {
            return BasicResult.getFailResponse(YoukuParamEnum.ACCESS_ID.getName() + "不能为空");
        }
        if (Objects.isNull(youkuParamField.getRequest_id())) {
            return BasicResult.getFailResponse(YoukuParamEnum.REQUEST_ID.getName() + "不能为空");
        }
        //如果ios设备为空，则判断安卓设备
        if (Objects.isNull(youkuParamField.getIdfa()) && Objects.isNull(youkuParamField.getIdfa_md5())
                && Objects.isNull(youkuParamField.getImei()) && Objects.isNull(youkuParamField.getImei_md5())
                && Objects.isNull(youkuParamField.getOaid()) && Objects.isNull(youkuParamField.getOaid_md5())) {
            return BasicResult.getFailResponse("安卓设备：" + YoukuParamEnum.IMEI.getName() + "、" + YoukuParamEnum.OAID.getName()
                    + "、" + YoukuParamEnum.IMEI_MD5.getName() + "、" + YoukuParamEnum.OAID_MD5.getName() + "不能同时为空；"
                    + " ios设备" + YoukuParamEnum.IDFA.getName() + "、" + YoukuParamEnum.IDFA_MD5.getName() + "不能同时为空");
        }
        return BasicResult.getSuccessResponse();
    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        YoukuParamField youkuParamField = (YoukuParamField) adsObj;
        YoukuAdsDTO youkuAdsDTO = new YoukuAdsDTO();
        BeanUtil.copyProperties(youkuParamField, youkuAdsDTO);
        youkuAdsDTO.setChannelName(BaiduPath.BAIDU_CHANNEL_NAME);
        youkuAdsDao.insert(youkuAdsDTO);
        logger.info("clickReport {} 将原始参数保存数据库，返回数据库对象 saveOriginParamData:{}", channelAdsKey, youkuAdsDTO);
        return youkuAdsDTO;
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {
        YoukuParamField youkuParamField = (YoukuParamField) adsObj;
        YoukuAdsDTO youkuAdsDTO = (YoukuAdsDTO) adsDtoObj;
        String ocpxUrl = queryServerPath() + Constants.ServerPath.BAIDU_YOUKU + Constants.ServerPath.ADS_CALLBACK + "/" + youkuAdsDTO.getId() + "?";
        logger.info("clickReport {} 客户回调渠道的url：{}", channelAdsKey, ocpxUrl);
        String encodeUrl = URLEncoder.createQuery().encode(ocpxUrl, StandardCharsets.UTF_8);
//            ocpxUrl = URLEncoder.encode(ocpxUrl, "UTF-8");
        youkuParamField.setCallback_url(encodeUrl);
        logger.info("clickReport {} 回调参数 replaceCallbackUrl:{}", channelAdsKey, youkuParamField);
    }

    @Override
    protected String initAdsUrl() {
        return LTJDPath.BASIC_URI;
    }

    @Override
    protected Response reportAds(String adsUrl, Object adsDtoObj) throws Exception {
        logger.info("调用用户侧youku的地址 {} adsUrl:{}", channelAdsKey, adsUrl);
        HttpResponse response = HttpRequest.get(adsUrl).timeout(20000).header("token", "application/json").execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        YoukuAdsDTO youkuAdsDTO = (YoukuAdsDTO) adsDtoObj;
        YoukuAdsDTO youkuAdsVO = new YoukuAdsDTO();
        youkuAdsVO.setId(youkuAdsDTO.getId());
        //上报成功
        if (HttpStatus.HTTP_OK == response.getStatus() && Objects.requireNonNull(responseBodyMap).get("code").equals("0")) {
            youkuAdsVO.setReportStatus(Constants.ReportStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(youkuAdsVO, youkuAdsDao);
            logger.info("clickReport {} 上报youku-广告侧接口请求成功:{} 数据:{}", channelAdsKey, response, youkuAdsVO);
            return BasicResult.getSuccessResponse(youkuAdsDTO.getId());
        } else {
            youkuAdsVO.setReportStatus(Constants.ReportStatus.FAIL.getCode() + "--" + JSONObject.toJSONString(responseBodyMap));
            baseServiceInner.updateAdsObject(youkuAdsVO, youkuAdsDao);
            logger.error("clickReport {} 上报youku-广告侧接口请求失败:{} 数据:{}", channelAdsKey, response, youkuAdsVO);
            return BasicResult.getFailResponse("上报youku-广告侧接口请求失败", 0);
        }
    }

    //计算签名
    private void signature(YoukuParamField youkuParamField) {
        String access_id = youkuParamField.getAccess_id();
        String ts = youkuParamField.getTs();
        String src = "access_id=" + access_id + "&ts=" + ts;
        String signatureStr = src + LTJDPath.SECRET;
        String signature = DigestUtil.md5Hex(signatureStr).toLowerCase();
        logger.info("clickReport {} 原始:{}  签名:{}", channelAdsKey, signatureStr, signature);
        youkuParamField.setSignature(signature);
    }


}
