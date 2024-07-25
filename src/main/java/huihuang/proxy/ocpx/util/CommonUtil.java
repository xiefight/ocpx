package huihuang.proxy.ocpx.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.bussiness.dao.common.IConfigDao;
import huihuang.proxy.ocpx.domain.Config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-23 23:00
 **/
public class CommonUtil {

    /*
     * 存储服务器地址
     */
    public static Map<String, String> serverMap = new ConcurrentHashMap<>(2);


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

    public static boolean strEmpty(String str) {
        return strEmpty(str, null);
    }

    //判断字符串不为空或不为指定字符
    public static boolean strEmpty(String str, String filterChars) {
        if (StrUtil.isEmpty(str)) {
            return false;
        }
        if ("NULL".equals(str) || "null".equals(str) || (StrUtil.isNotEmpty(filterChars) && filterChars.equals(str))) {
            return false;
        }
        return true;
    }

    /**
     * config中查询服务地址
     */
    public static String queryServerPath(IConfigDao configDao) {
        //缓存中为空，从数据库获取
        if (CollUtil.isEmpty(serverMap) || !serverMap.containsKey(Config.SERVER_PATH)) {
            String config = configDao.queryConfig();
            Map<String, Object> configMap = CollUtil.newHashMap();
            try {
                configMap = JsonParameterUtil.jsonToMap(config, Exception.class);
                assert configMap != null;
            } catch (Exception e) {
                e.printStackTrace();
            }
            String serverPath = (String) configMap.get(Config.SERVER_PATH);
            serverMap.put(Config.SERVER_PATH, serverPath);
            return serverPath;
        }
        return serverMap.get(Config.SERVER_PATH);
    }

    /**
     * 存储百度快手动态创建的表名
     */
    public static Set<String> kuaishouBaiduTables = new HashSet<>();


}
