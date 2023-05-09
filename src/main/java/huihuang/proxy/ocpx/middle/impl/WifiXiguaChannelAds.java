package huihuang.proxy.ocpx.middle.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.litianjingdong.LTJDPath;
import huihuang.proxy.ocpx.ads.xiguavideo.XiguaAdsDTO;
import huihuang.proxy.ocpx.ads.xiguavideo.XiguaParamEnum;
import huihuang.proxy.ocpx.ads.xiguavideo.XiguaParamField;
import huihuang.proxy.ocpx.ads.xiguavideo.XiguaPath;
import huihuang.proxy.ocpx.ads.youku.YoukuParamEnum;
import huihuang.proxy.ocpx.ads.youku.YoukuPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IXiguaAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.channel.wifi.WifiParamEnum;
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
 * wifi-xigua
 *
 * @Author: xietao
 * @Date: 2023/5/9 21:04
 */
@Component("wxChannelAds")
public class WifiXiguaChannelAds extends BaseSupport implements IChannelAds {

    @Autowired
    private IXiguaAdsDao xiguaAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;

    String channelAdsKey = Constants.ChannelAdsKey.WIFI_XIGUA;

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        //wifi万能钥匙的宏参数必须全部使用
        StringBuilder macro = new StringBuilder();
        WifiParamEnum[] wifiParamEnums = WifiParamEnum.values();
        for (WifiParamEnum wifi : wifiParamEnums) {
            macro.append(wifi.getParam()).append("=").append(wifi.getMacro()).append("&");
        }
        String macroStr = macro.toString();
        if (macroStr.endsWith("&")) {
            macroStr = macroStr.substring(0, macroStr.length() - 1);
        }
        //2.config中查找服务地址
        String serverPath = queryServerPath();
        //3.拼接监测地址
        return serverPath + Constants.ServerPath.WIFI_XIGUA + Constants.ServerPath.CLICK_REPORT + "?" + macroStr;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        XiguaParamField xiguaParamField = new XiguaParamField();

        Set<Map.Entry<XiguaParamEnum, WifiParamEnum>> xjSet = XiguaParamEnum.xiguaWifiMap.entrySet();
        xjSet.stream().filter(xj -> Objects.nonNull(xj.getValue())).forEach(tm -> {
            XiguaParamEnum xigua = tm.getKey();
            WifiParamEnum wifi = tm.getValue();
            String xiguaField = xigua.getName();
            String wifiParam = wifi.getParam();
            //wifi的参数值
            String[] value = parameterMap.get(wifiParam);
            if (Objects.isNull(value) || value.length == 0) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(xiguaField, xiguaParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(xiguaParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, xiguaParamField);
        //处理广告侧无映射的，但又必须回调给渠道侧的额外参数
        fitExtras(parameterMap,xiguaParamField,
                WifiParamEnum.CID.getParam(),WifiParamEnum.SID.getParam(),WifiParamEnum.STIME.getParam(),
                WifiParamEnum.OS.getParam(),WifiParamEnum.IDFA.getParam(),WifiParamEnum.MAC.getParam(),
                WifiParamEnum.IMEI.getParam());
        return xiguaParamField;
    }

    @Override
    protected void convertParams(Object adsObj) {
        XiguaParamField xiguaParamField = (XiguaParamField) adsObj;
        //wifi侧无callback
        xiguaParamField.setRequest_id(String.valueOf(System.currentTimeMillis()));
        //时间戳，秒
        String ts = Optional.ofNullable(xiguaParamField.getTs()).orElse(String.valueOf(System.currentTimeMillis()));
        xiguaParamField.setTs(String.valueOf(Long.parseLong(ts) / 1000));
        //签名
        signature(xiguaParamField);
        logger.info("clickReport {} 特殊参数进行转换 convertParams:{}", channelAdsKey, xiguaParamField);
    }

    @Override
    protected Response judgeParams(Object adsObj) {
        XiguaParamField xiguaParamField = (XiguaParamField) adsObj;
        if (Objects.isNull(xiguaParamField.getSignature())) {
            return BasicResult.getFailResponse(YoukuParamEnum.SIGNATURE.getName() + "不能为空");
        }
        if (Objects.isNull(xiguaParamField.getTp_adv_id())) {
            return BasicResult.getFailResponse(YoukuParamEnum.TP_ADV_ID.getName() + "不能为空");
        }
        if (Objects.isNull(xiguaParamField.getAccess_id())) {
            return BasicResult.getFailResponse(YoukuParamEnum.ACCESS_ID.getName() + "不能为空");
        }
        if (Objects.isNull(xiguaParamField.getRequest_id())) {
            return BasicResult.getFailResponse(YoukuParamEnum.REQUEST_ID.getName() + "不能为空");
        }
        //如果ios设备为空，则判断安卓设备
        if (Objects.isNull(xiguaParamField.getIdfa()) && Objects.isNull(xiguaParamField.getIdfa_md5())
                && Objects.isNull(xiguaParamField.getImei()) && Objects.isNull(xiguaParamField.getImei_md5())
                && Objects.isNull(xiguaParamField.getOaid()) && Objects.isNull(xiguaParamField.getOaid_md5())) {
            return BasicResult.getFailResponse("安卓设备：" + YoukuParamEnum.IMEI.getName() + "、" + YoukuParamEnum.OAID.getName()
                    + "、" + YoukuParamEnum.IMEI_MD5.getName() + "、" + YoukuParamEnum.OAID_MD5.getName() + "不能同时为空；"
                    + " ios设备" + YoukuParamEnum.IDFA.getName() + "、" + YoukuParamEnum.IDFA_MD5.getName() + "不能同时为空");
        }
        return BasicResult.getSuccessResponse();
    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        XiguaParamField xiguaParamField = (XiguaParamField) adsObj;
        XiguaAdsDTO xiguaAdsDTO = new XiguaAdsDTO();
        BeanUtil.copyProperties(xiguaParamField, xiguaAdsDTO);
        xiguaAdsDao.insert(xiguaAdsDTO);
        logger.info("clickReport {} 将原始参数保存数据库，返回数据库对象 saveOriginParamData:{}", channelAdsKey, xiguaAdsDTO);
        return xiguaAdsDTO;
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {
        XiguaParamField xiguaParamField = (XiguaParamField) adsObj;
        XiguaAdsDTO xiguaAdsDTO = (XiguaAdsDTO) adsDtoObj;
        String ocpxUrl = queryServerPath() + Constants.ServerPath.WIFI_XIGUA + Constants.ServerPath.ADS_CALLBACK + "/" + xiguaAdsDTO.getId() + "?";
        logger.info("clickReport {} 客户回调渠道的url：{}", channelAdsKey, ocpxUrl);
        String encodeUrl = URLEncoder.createQuery().encode(ocpxUrl, StandardCharsets.UTF_8);
//            ocpxUrl = URLEncoder.encode(ocpxUrl, "UTF-8");
        xiguaParamField.setCallback_url(encodeUrl);
        logger.info("clickReport {} 回调参数 replaceCallbackUrl:{}", channelAdsKey, xiguaParamField);
    }

    @Override
    protected String initAdsUrl() {
        return XiguaPath.BASIC_URI;
    }

    @Override
    protected Response reportAds(String adsUrl, Object adsDtoObj) throws Exception {
        logger.info("调用用户侧的地址 {} adsUrl:{}", channelAdsKey, adsUrl);
        HttpResponse response = HttpRequest.get(adsUrl).timeout(20000).header("token", "application/json").execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        XiguaAdsDTO xiguaAdsDTO = (XiguaAdsDTO) adsDtoObj;
        XiguaAdsDTO xiguaAdsVO = new XiguaAdsDTO();
        xiguaAdsVO.setId(xiguaAdsDTO.getId());
        //上报成功
        if (HttpStatus.HTTP_OK == response.getStatus() && Objects.requireNonNull(responseBodyMap).get("code").equals("0")) {
            xiguaAdsVO.setReportStatus(Constants.ReportStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(xiguaAdsVO, xiguaAdsDao);
            logger.info("clickReport {} 上报youku-广告侧接口请求成功:{} 数据:{}", channelAdsKey, response, xiguaAdsVO);
            return BasicResult.getSuccessResponse(xiguaAdsDTO.getId());
        } else {
            xiguaAdsVO.setReportStatus(Constants.ReportStatus.FAIL.getCode() + "--" + JSONObject.toJSONString(responseBodyMap));
            baseServiceInner.updateAdsObject(xiguaAdsVO, xiguaAdsDao);
            logger.error("clickReport {} 上报youku-广告侧接口请求失败:{} 数据:{}", channelAdsKey, response, xiguaAdsVO);
            return BasicResult.getFailResponse("上报youku-广告侧接口请求失败", 0);
        }
    }

    //计算签名
    private void signature(XiguaParamField xiguaParamField) {
        String access_id = xiguaParamField.getAccess_id();
        String ts = xiguaParamField.getTs();
        String src = "access_id=" + access_id + "&ts=" + ts;
        String signatureStr = src + YoukuPath.SECRET;
        String signature = DigestUtil.md5Hex(signatureStr).toLowerCase();
        logger.info("clickReport {} 原始:{}  签名:{}", channelAdsKey, signatureStr, signature);
        xiguaParamField.setSignature(signature);
    }


    private void fitExtras(Map<String, String[]> parameterMap, XiguaParamField xiguaParamField, String... extras) {
        StringBuilder extraStr = new StringBuilder();
        for (String extra : extras) {
            String[] cids = parameterMap.get(extra);
            if (Objects.nonNull(cids) && cids.length > 0) {
                String cid = cids[0];
                extraStr.append("&").append(extra).append("=").append(cid);
            }
        }
        if (extras.length > 0) {
            xiguaParamField.setExtra(extraStr.toString());
        }
    }


}
