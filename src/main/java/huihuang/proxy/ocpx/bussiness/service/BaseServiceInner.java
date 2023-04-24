package huihuang.proxy.ocpx.bussiness.service;

import huihuang.proxy.ocpx.ads.meituan.MeiTuanAdsDTO;
import huihuang.proxy.ocpx.bussiness.dao.IMeiTuanAdsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
     * 更新点击上报信息
     */
    public void updateMeiTuanAds(MeiTuanAdsDTO meiTuanAdsDTO) {
        meiTuanAdsDao.update(meiTuanAdsDTO);
    }


    /**
     * 整理广告侧的url
     */
    public String initAdsUrlAndParam(String adsUrl, Object adsObj) {
        StringBuilder stringBuilder = new StringBuilder(adsUrl);
        //将参数拼接到url中以发送get请求
        Field[] declaredFields = adsObj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            String fieldName = field.getName();
            PropertyDescriptor descriptor;
            try {
                descriptor = new PropertyDescriptor(fieldName, adsObj.getClass());
                Method getMethod = descriptor.getReadMethod();
                Object fieldValue = getMethod.invoke(adsObj);
                if (Objects.nonNull(fieldValue)) {
                    stringBuilder.append("&").append(fieldName).append("=").append(fieldValue);
                }
            } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }

    public String convertOs(String appType) {
        switch (appType) {
            case "android":
                return "0";
            case "ios":
                return "1";
        }
        return "";
    }

}
