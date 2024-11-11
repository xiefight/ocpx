package huihuang.proxy.ocpx.middle.baseadsreport.huihuiyoudao;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.huihui.HuihuiAdsDTO;
import huihuang.proxy.ocpx.ads.huihui.HuihuiParamField;
import huihuang.proxy.ocpx.ads.huihui.HuihuiPath;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.BaseSupport;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import sun.net.util.IPAddressUtil;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: xietao
 * @Date: 2023/6/9 8:47
 */
public abstract class BaseHuihuiReportFactory extends BaseSupport implements IChannelAds {

    @Autowired
    protected BaseServiceInner baseServiceInner;

    protected abstract String channelAdsKey();

    protected abstract String serverPathKey();

    protected abstract IMarkDao adsDao();

    protected abstract String channelName();

    @Override
    protected void convertParams(Object adsObj) {
        HuihuiParamField huihuiParamField = (HuihuiParamField) adsObj;
        if (null != huihuiParamField.getCallback()) {
            huihuiParamField.setCallback(URLEncoder.createQuery().encode(huihuiParamField.getCallback(), StandardCharsets.UTF_8));
        }

        //imei：md5之后再转大写，这里只处理imei已经md5之后的转大写的操作，如果上层未进行md5，则先执行此步
        if (StrUtil.isNotEmpty(huihuiParamField.getImei())) {
            huihuiParamField.setImei(huihuiParamField.getImei().toUpperCase());
        }
        //idfa 原值大写
        if (StrUtil.isNotEmpty(huihuiParamField.getIdfa())) {
            huihuiParamField.setIdfa(huihuiParamField.getIdfa().toUpperCase());
        }
        //idfa_md5：原值大写后md5之后再转大写，这里只处理idfa_md5转大写的操作
        if (StrUtil.isNotEmpty(huihuiParamField.getIdfa_md5())) {
            huihuiParamField.setIdfa_md5(huihuiParamField.getIdfa_md5().toUpperCase());
        }
        //oaid_md5：原值大写后md5之后再转大写，这里只处理oaid_md5转大写的操作
        if (StrUtil.isNotEmpty(huihuiParamField.getOaid_md5())) {
            huihuiParamField.setOaid_md5(huihuiParamField.getOaid_md5().toUpperCase());
        }
        //caid_md5：原值大写后md5之后再转大写，这里只处理caid_md5转大写的操作
        if (StrUtil.isNotEmpty(huihuiParamField.getCaid_md5())) {
            huihuiParamField.setCaid_md5(huihuiParamField.getCaid_md5().toUpperCase());
        }
        //ip4 ip6的处理
        if (StrUtil.isNotEmpty(huihuiParamField.getIp())) {
            boolean iPv6LiteralAddress = IPAddressUtil.isIPv6LiteralAddress(huihuiParamField.getIp());
            if (iPv6LiteralAddress) {
                huihuiParamField.setIp(URLEncoder.createQuery().encode(huihuiParamField.getIp(), StandardCharsets.UTF_8));
            }
        }
        //时间戳，秒
        String ts = Optional.ofNullable(huihuiParamField.getTs()).orElse(String.valueOf(System.currentTimeMillis()));
        huihuiParamField.setTs(String.valueOf(Long.parseLong(ts)));
        //ua:需关注渠道传过来的是否是encode过的
        if (StrUtil.isNotEmpty(huihuiParamField.getUa())) {
            huihuiParamField.setUa(URLEncoder.createQuery().encode(huihuiParamField.getUa(), StandardCharsets.UTF_8));
        }

        //操作系统
        if (null == huihuiParamField.getOs() && (StrUtil.isNotEmpty(huihuiParamField.getIdfa()) || StrUtil.isNotEmpty(huihuiParamField.getIdfa_md5()))) {
            huihuiParamField.setOs("ios");
        } else {
            huihuiParamField.setOs("android");
        }
        //签名
//        signature(huihuiParamField);
    }

    @Override
    protected Response judgeParams(Object adsObj) {
        HuihuiParamField huihuiParamField = (HuihuiParamField) adsObj;

        if (StrUtil.isEmpty(huihuiParamField.getIdfa()) && StrUtil.isEmpty(huihuiParamField.getCaid())
                && StrUtil.isEmpty(huihuiParamField.getImei()) && StrUtil.isEmpty(huihuiParamField.getOaid())
                && (StrUtil.isEmpty(huihuiParamField.getIp()) || StrUtil.isEmpty(huihuiParamField.getUa()))) {
            return BasicResult.getFailResponse("无法获取设备号信息");
        }

        if (StrUtil.isEmpty(huihuiParamField.getTs())) {
            return BasicResult.getFailResponse("广告点击时间缺失");
        }
        //榮耀的回傳地址在extra中，沒有通過callback映射，所以這個條件先放開
//        if (StrUtil.isEmpty(huihuiParamField.getCallback())) {
//            return BasicResult.getFailResponse("回传地址缺失");
//        }
        if (StrUtil.isEmpty(huihuiParamField.getOs())) {
            return BasicResult.getFailResponse("操作系统缺失");
        }
        return BasicResult.getSuccessResponse();
    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        HuihuiParamField huihuiParamField = (HuihuiParamField) adsObj;
        HuihuiAdsDTO huihuiAdsDTO = new HuihuiAdsDTO();
        BeanUtil.copyProperties(huihuiParamField, huihuiAdsDTO);
        huihuiAdsDTO.setChannelName(channelName());
        baseServiceInner.insertAdsObject(huihuiAdsDTO, adsDao());
        logger.info("clickReport {} 将原始参数保存数据库，返回数据库对象 saveOriginParamData:{}", channelAdsKey(), huihuiAdsDTO);
        return huihuiAdsDTO;
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {
        HuihuiParamField huihuiParamField = (HuihuiParamField) adsObj;
        HuihuiAdsDTO huihuiAdsDTO = (HuihuiAdsDTO) adsDtoObj;
        String ocpxUrl = queryServerPath() + serverPathKey() + Constants.ServerPath.ADS_CALLBACK + "/" + huihuiAdsDTO.getId() + "?conv_action=__EVENT__";
        logger.info("clickReport {} 客户回调渠道的url：{}", channelAdsKey(), ocpxUrl);
//        String encodeUrl = URLEncoder.createQuery().encode(ocpxUrl, StandardCharsets.UTF_8);
//            ocpxUrl = URLEncoder.encode(ocpxUrl, "UTF-8");
        String encodeUrl = null;
        try {
            encodeUrl = java.net.URLEncoder.encode(ocpxUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        huihuiParamField.setCallback(encodeUrl);
        logger.info("clickReport {} 回调参数 replaceCallbackUrl:{}", channelAdsKey(), huihuiParamField);
    }


    @Override
    protected String initAdsUrl() {
        return HuihuiPath.BASIC_URI;
    }


    @Override
    protected Response reportAds(String adsUrl, Object adsDtoObj) throws Exception {
        //huihui有道上报时添加必填参数，拼接did
        adsUrl += "&did=" + HuihuiPath.DID;
        logger.info("调用用户侧的地址 {} adsUrl:{}", channelAdsKey(), adsUrl);
        HttpResponse response = HttpRequest.get(adsUrl).timeout(20000).header("token", "application/json").execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        HuihuiAdsDTO huihuiAdsDTO = (HuihuiAdsDTO) adsDtoObj;
        HuihuiAdsDTO huihuiAdsVO = new HuihuiAdsDTO();
        huihuiAdsVO.setId(huihuiAdsDTO.getId());
        //上报成功
        if (HttpStatus.HTTP_OK == response.getStatus()) {
            huihuiAdsVO.setReportStatus(Constants.ReportStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(huihuiAdsVO, adsDao());
            logger.info("clickReport {} 上报广告侧接口请求成功:{} 数据:{}", channelAdsKey(), response, huihuiAdsVO);
            return BasicResult.getSuccessResponse(huihuiAdsDTO.getId());
        } else {
            huihuiAdsVO.setReportStatus(Constants.ReportStatus.FAIL.getCode() + "--" + JSONObject.toJSONString(responseBodyMap));
            baseServiceInner.updateAdsObject(huihuiAdsVO, adsDao());
            logger.error("clickReport {} 上报广告侧接口请求失败:{} 数据:{}", channelAdsKey(), response, huihuiAdsVO);
            return BasicResult.getFailResponse("上报广告侧接口请求失败", 0);
        }
    }

}
