package huihuang.proxy.ocpx.bussiness.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import huihuang.proxy.ocpx.ads.meituan.MeiTuanAdsDTO;
import huihuang.proxy.ocpx.ads.meituan.MeiTuanParamField;
import huihuang.proxy.ocpx.ads.meituan.MeiTuanPath;
import huihuang.proxy.ocpx.bussiness.dao.IMeiTuanAdsDao;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-22 15:29
 **/
@Component
public class BaseServiceInner {
    
    @Autowired
    private IMeiTuanAdsDao meiTuanAdsDao;

    /**
     * 上报给广告侧
     */
    public  void reportAds(Integer id, String adsUrl) throws Exception {
        HttpResponse response = HttpRequest.get(adsUrl).timeout(20000).header("token", "application/json").execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        //上报成功
        if (HttpStatus.HTTP_OK == response.getStatus() && responseBodyMap.get("ret").equals(0)) {
            updateReportStatus(id, Constants.ReportStatus.SUCCESS.getCode());
        } else {
            updateReportStatus(id, Constants.ReportStatus.FAIL.getCode());
        }
    }

    /**
     * 更新点击上报信息
     */
    public  void updateMeiTuanAds(MeiTuanAdsDTO meiTuanAdsDTO) {
        meiTuanAdsDao.update(meiTuanAdsDTO);
    }


    /**
     * 更新上报状态
     */
    public  void updateReportStatus(Integer id, String status) {
        MeiTuanAdsDTO meiTuanAdsDTO = new MeiTuanAdsDTO();
        meiTuanAdsDTO.setId(id);
        meiTuanAdsDTO.setReportStatus(status);
        meiTuanAdsDao.update(meiTuanAdsDTO);
    }

    /**
     * 整理广告侧的url
     *
     * @param meiTuanParamField
     * @return
     */
    public  String initAdsUrl(MeiTuanParamField meiTuanParamField) {
        StringBuilder adsUrl = new StringBuilder(MeiTuanPath.BASIC_URI + MeiTuanPath.VERIFY);
        //将参数拼接到url中以发送get请求
        Field[] declaredFields = meiTuanParamField.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            String fieldName = field.getName();
            PropertyDescriptor descriptor;
            try {
                descriptor = new PropertyDescriptor(fieldName, meiTuanParamField.getClass());
                Method getMethod = descriptor.getReadMethod();
                Object fieldValue = getMethod.invoke(meiTuanParamField);
                if (Objects.nonNull(fieldValue)) {
                    adsUrl.append("&").append(fieldName).append("=").append(fieldValue);
                }
            } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return adsUrl.toString();
    }


    public  String convertAppType(String os) {
        switch (os) {
            case "0":
                return "android";
            case "1":
                return "ios";
        }
        return "";
    }

    public  String convertOs(String appType) {
        switch (appType) {
            case "android":
                return "0";
            case "ios":
                return "1";
        }
        return "";
    }
    
}
