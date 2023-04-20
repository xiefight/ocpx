package huihuang.proxy.ocpx.util;

import cn.hutool.core.util.StrUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @Description: Json处理工具类
 * @Author: xietao
 * @Date: 2023-04-20 21:35
 **/
public class JsonParameterUtil {

    public static <T extends Exception> Map<String, Object> jsonToMap(String json, Class<T> exClass, String... mustContainsKey) throws Exception {
        if (null != mustContainsKey && mustContainsKey.length > 0 && StrUtil.isEmpty(json)) {
            throw reflectionException(exClass, "参数不能为空");
        }

        if (StrUtil.isEmpty(json)) {
            return null;
        }

        Map<String, Object> parameter = null;
        try {
            parameter = JacksonUtil.<Map<String, Object>>jsonToJavaBean(json, (Class)Map.class);
        } catch (Exception e) {
            throw reflectionException(exClass, "参数格式不是json");
        }
        if (null != parameter && null != mustContainsKey && mustContainsKey.length > 0) {
            String[] arrayOfString; int i; byte b; for (arrayOfString = mustContainsKey, i = arrayOfString.length, b = 0; b < i; ) { String mk = arrayOfString[b];
                if (parameter.containsKey(mk)) {
                    b++; continue;
                }
                throw reflectionException(exClass, "参数 " + mk + " 不能为空"); }

        }
        return parameter;
    }


    private static <T extends Exception> T reflectionException(Class<T> exceptionClass, String exceptionMessage) throws Exception {
        try {
            if (StrUtil.isEmpty(exceptionMessage)) {
                Constructor<T> constructor = exceptionClass.getConstructor(new Class[0]);
                return constructor.newInstance(new Object[0]);
            }
            Constructor<T> ctor = exceptionClass.getConstructor(new Class[] { String.class });
            return ctor.newInstance(new Object[] { exceptionMessage });
        }
        catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
//            logger.error("传递的异常类 {} 不能初始化", exceptionClass.getName());
            throw new Exception(e);
        } //            logger.error("传递的异常类 {} 因为安全原因不能初始化", exceptionClass.getName());

    }

}
