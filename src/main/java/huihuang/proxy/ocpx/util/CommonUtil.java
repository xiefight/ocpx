package huihuang.proxy.ocpx.util;

import cn.hutool.core.collection.CollUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-23 23:00
 **/
public class CommonUtil {

    /**
     * 将从get请求中获取到的参数组装成Map
     */
    public static Map<String, String> convertGetParamToMap(String getStr) {
        String[] strs = getStr.split("&");
        Map<String, String> dataMap = new HashMap<>(16);
        for (int i = 0; i < strs.length; i++) {
            String[] str = strs[i].split("=");
            if (str.length > 1) {
                dataMap.put(str[0], str[1]);
            } else {
                dataMap.put(str[0], "");
            }
        }
        return dataMap;
    }

    /**
     * 完善监测地址（在宏参数的基础上，拼接用户自己的参数）
     */
    public static String appendAddressParam(String monitorAddress, Map<String, Object> params) {
        StringBuilder sb = new StringBuilder(monitorAddress);
        if (CollUtil.isNotEmpty(params)) {
            Set<Map.Entry<String, Object>> paramSet = params.entrySet();
            for (Map.Entry<String, Object> param : paramSet) {
                String key = param.getKey();
                Object value = param.getValue();
                sb.append("&").append(key).append("=").append(value);
            }
        }
        return sb.toString();
    }

}
