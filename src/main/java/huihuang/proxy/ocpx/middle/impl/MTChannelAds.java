package huihuang.proxy.ocpx.middle.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.URLEncoder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import huihuang.proxy.ocpx.ads.meituan.MeiTuanAdsDTO;
import huihuang.proxy.ocpx.ads.meituan.MeiTuanParamEnum;
import huihuang.proxy.ocpx.ads.meituan.MeiTuanParamField;
import huihuang.proxy.ocpx.ads.meituan.MeiTuanPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IMeiTuanAdsMarkDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.channel.toutiao.ToutiaoParamEnum;
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
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-20 22:42
 **/
@Component
public class MTChannelAds extends BaseSupport implements IChannelAds {

    @Autowired
    private IMeiTuanAdsMarkDao meiTuanAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;

    @Override
    public String findMonitorAddress() {

        StringBuilder macro = new StringBuilder();
        //1.遍历meituan查找tuotiao对应的宏参数
        Set<MeiTuanParamEnum> meiTuanParamEnums = MeiTuanParamEnum.tmMap.keySet();
        for (MeiTuanParamEnum meiTuan : meiTuanParamEnums) {
            ToutiaoParamEnum toutiao = MeiTuanParamEnum.tmMap.get(meiTuan);
            if (Objects.isNull(toutiao) || StrUtil.isEmpty(toutiao.getMacro())) {
                continue;
            }
            macro.append(toutiao.getParam()).append("=").append(toutiao.getMacro()).append("&");
        }
        String macroStr = macro.toString();
        if (macroStr.endsWith("&")) {
            macroStr = macroStr.substring(0, macroStr.length() - 1);
        }
        //2.config中查找服务地址
        String serverPath = queryServerPath();
        //3.拼接监测地址
        return serverPath + "/tmServer/clickReport" + "?" + macroStr;
    }


    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        MeiTuanParamField meiTuanParamField = new MeiTuanParamField();

        Set<Map.Entry<MeiTuanParamEnum, ToutiaoParamEnum>> tmSet = MeiTuanParamEnum.tmMap.entrySet();
        tmSet.stream().filter(tm -> Objects.nonNull(tm.getValue())).forEach(tm -> {
            MeiTuanParamEnum meituan = tm.getKey();
            ToutiaoParamEnum toutiao = tm.getValue();
            //美团的字段名
            String meituanField = meituan.getName();
            //头条的字段名
            String toutiaoParam = toutiao.getParam();
            //头条的参数值
            String[] value = parameterMap.get(toutiaoParam);
            if (Objects.isNull(value) || value.length == 0) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(meituanField, meiTuanParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(meiTuanParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return meiTuanParamField;
    }

    @Override
    protected void convertParams(Object adsObj) {
        MeiTuanParamField meiTuanParamField = (MeiTuanParamField) adsObj;
        meiTuanParamField.setFeedback_url(URLEncoder.createQuery().encode(meiTuanParamField.getFeedback_url(), StandardCharsets.UTF_8));
        meiTuanParamField.setApp_type(osConvertAppType(meiTuanParamField.getApp_type()));
    }

    @Override
    protected Response judgeParams(Object adsObj) {
        MeiTuanParamField meiTuanParamField = (MeiTuanParamField) adsObj;
        if (Objects.isNull(meiTuanParamField.getSource())) {
            return BasicResult.getFailResponse(MeiTuanParamEnum.SOURCE.getName() + "不能为空");
        }
        if (Objects.isNull(meiTuanParamField.getAction_time())) {
            return BasicResult.getFailResponse(MeiTuanParamEnum.ACTION_TIME.getName() + "不能为空");
        }
        if (Objects.isNull(meiTuanParamField.getApp_type())) {
            return BasicResult.getFailResponse(MeiTuanParamEnum.APP_TYPE.getName() + "不能为空");
        }
        if (Objects.isNull(meiTuanParamField.getFeedback_url())) {
            return BasicResult.getFailResponse(MeiTuanParamEnum.FEEDBACK_URL.getName() + "不能为空");
        }
        //如果ios设备为空，则判断安卓设备
        if (Objects.isNull(meiTuanParamField.getMd5_idfa())) {
            if (Objects.isNull(meiTuanParamField.getMd5_imei()) || Objects.isNull(meiTuanParamField.getOaid()) || Objects.isNull(meiTuanParamField.getMd5_oaid())) {
                return BasicResult.getFailResponse(MeiTuanParamEnum.MD5_IMEI.getName() + "、" + MeiTuanParamEnum.OAID.getName() + "、" + MeiTuanParamEnum.MD5_OAID.getName() + "不能同时为空");
            }
        }
        return BasicResult.getSuccessResponse();
    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        MeiTuanParamField meiTuanParamField = (MeiTuanParamField) adsObj;
        MeiTuanAdsDTO meiTuanAdsDTO = new MeiTuanAdsDTO();
        BeanUtil.copyProperties(meiTuanParamField, meiTuanAdsDTO);
        meiTuanAdsDao.insert(meiTuanAdsDTO);
        return meiTuanAdsDTO;
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {
        MeiTuanParamField meiTuanParamField = (MeiTuanParamField) adsObj;
        MeiTuanAdsDTO meiTuanAdsDTO = (MeiTuanAdsDTO) adsDtoObj;
        String ocpxUrl = queryServerPath() + "/tmServer/adsCallBack/" + meiTuanAdsDTO.getId();
        String encodeUrl = URLEncoder.createQuery().encode(ocpxUrl, StandardCharsets.UTF_8);
//            ocpxUrl = URLEncoder.encode(ocpxUrl, "UTF-8");
        meiTuanParamField.setFeedback_url(encodeUrl);
    }

    @Override
    protected String initAdsUrl() {
        return MeiTuanPath.BASIC_URI + MeiTuanPath.VERIFY;
    }

    @Override
    protected Response reportAds(String adsUrl, Object adsDtoObj) throws Exception {
        HttpResponse response = HttpRequest.get(adsUrl).timeout(20000).header("token", "application/json").execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        MeiTuanAdsDTO meiTuanAdsDTO = (MeiTuanAdsDTO) adsDtoObj;
        MeiTuanAdsDTO meiTuanAdsVO = new MeiTuanAdsDTO();
        meiTuanAdsVO.setId(meiTuanAdsDTO.getId());
        //上报成功
        if (HttpStatus.HTTP_OK == response.getStatus() && responseBodyMap.get("ret").equals(0)) {
            meiTuanAdsVO.setReportStatus(Constants.ReportStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(meiTuanAdsVO, meiTuanAdsDao);
            return BasicResult.getSuccessResponse(meiTuanAdsDTO.getId());
        } else {
            meiTuanAdsVO.setReportStatus(Constants.ReportStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(meiTuanAdsVO, meiTuanAdsDao);
            return BasicResult.getFailResponse("上报meituan-广告侧接口请求失败", 0);
        }
    }



    private String osConvertAppType(String os) {
        switch (os) {
            case "0":
                return "android";
            case "1":
                return "ios";
        }
        return "";
    }

    /*private String convertOs(String appType) {
        switch (appType) {
            case "android":
                return "0";
            case "ios":
                return "1";
        }
        return "";
    }*/
}
