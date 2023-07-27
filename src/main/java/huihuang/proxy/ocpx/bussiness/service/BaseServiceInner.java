package huihuang.proxy.ocpx.bussiness.service;

import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.marketinterface.IMarkDto;
import huihuang.proxy.ocpx.util.tuple.Tuple2;
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

    public void updateAdsObject(IMarkDto adsDTO, IMarkDao dao) {
        try {
            Method update = dao.getClass().getMethod("update", adsDTO.getClass());
            update.invoke(dao, adsDTO);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void insertAdsObject(IMarkDto adsDTO, IMarkDao dao) {
        try {
            Method update = dao.getClass().getMethod("insert", adsDTO.getClass());
            update.invoke(dao, adsDTO);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用于分表后的查询上报信息
     *
     * @param splitAdsDao 先从splitAdsDao中查
     * @param adsDao      查不出来从adsDao中查
     * @param methodName  查询的方法名，各个广告上报的查询方法不一样
     * @param clazz       要封装转换的对象
     * @param id          要查询的主键id
     * @param <T>
     * @return
     */
    public <T> Tuple2<IMarkDao, T> querySplitAdsObject(IMarkDao splitAdsDao, IMarkDao adsDao, String methodName, Class<T> clazz, Integer id) {
        try {
            Method splitAdsDaoQuery = splitAdsDao.getClass().getMethod(methodName, Integer.class);
            T t = (T) splitAdsDaoQuery.invoke(splitAdsDao, id);
            if (null != t) {
                return new Tuple2<>(splitAdsDao, t);
            }
            Method adsDaoQuery = adsDao.getClass().getMethod(methodName, Integer.class);
            t = (T) adsDaoQuery.invoke(adsDao, id);
            if (null != t) {
                return new Tuple2<>(adsDao, t);
            }
            return null;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 整理广告侧的url
     */
    public String initAdsUrlAndParam(String adsUrl, Object adsObj) {
        StringBuilder stringBuilder = new StringBuilder(adsUrl);
        //将参数拼接到url中以发送get请求
        //本类属性
        Field[] declaredFields = adsObj.getClass().getDeclaredFields();
        if (0 == declaredFields.length || (1 == declaredFields.length && "extra".equals(declaredFields[0].getName()))) {
            //父类属性
            declaredFields = adsObj.getClass().getSuperclass().getDeclaredFields();
        }
        for (Field field : declaredFields) {
            String fieldName = field.getName();
            PropertyDescriptor descriptor;
            try {
                descriptor = new PropertyDescriptor(fieldName, adsObj.getClass());
                Method getMethod = descriptor.getReadMethod();
                Object fieldValue = getMethod.invoke(adsObj);
                if (Objects.nonNull(fieldValue)) {
                    stringBuilder.append(fieldName).append("=").append(fieldValue).append("&");
                }
            } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        String src = stringBuilder.toString();
        if (stringBuilder.toString().endsWith("&")) {
            src = stringBuilder.substring(0, stringBuilder.length() - 1);
        }
        return src;
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
