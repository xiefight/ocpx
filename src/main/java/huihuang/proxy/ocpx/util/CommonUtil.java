package huihuang.proxy.ocpx.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-23 23:00
 **/
public class CommonUtil {

    /**
     * 存放百度-快手完整表名的集合
     * kuaishou_ads_baidu_bdks01
     * 只用来标识表是否存在
     * 其他集合都可以从数据库配置中加载，但 kuaishouBaiduTables 这个结构不行，必须在插入上报时判断表是否存在，再放入该集合
     */
    public static Set<String> kuaishouBaiduTables = CollUtil.newHashSet();
    /**
     * 存放数据库配置加载的原始的 tableName:startId 的映射
     */
    public static Map<String, Integer> baiduKuaishouAccountMap = new ConcurrentHashMap<>();

    /**
     * 顺序存储百度快手动态创建的id和表名映射
     * 方便回传更新时,根据id快速定位到表名
     */
    public static Integer START_ID = 120000000;
    public static Integer STEP_NUM = 20000000; //每张表只能存储最多2000w的数据，其实是有问题的，只能弥补了
    public static Integer THRESHOLD = 18000000; //阈值，超过这个数，就把数据放到总表中//但总表的id不能大于6000w，所以总表分表
    public static String ORIGIN_BAIDU_KUAISHOU_TABLE_NAME = "kuaishou_ads_baidu";
    public static String PREFIX_BAIDU_KUAISHOU_TABLE_NAME = "kuaishou_ads_baidu_";
    public static String NEW_ORIGIN_BAIDU_KUAISHOU_TABLE_NAME = PREFIX_BAIDU_KUAISHOU_TABLE_NAME + "new";
    public static TreeMap<Integer, String> kuaishouBaiduIdTableMap;
    static {
        kuaishouBaiduIdTableMap = new TreeMap<>();
        //原表的id都是小于起始值60000000的
//        kuaishouBaiduIdTableMap.put(START_ID, ORIGIN_BAIDU_KUAISHOU_TABLE_NAME);
        kuaishouBaiduIdTableMap.put(START_ID, NEW_ORIGIN_BAIDU_KUAISHOU_TABLE_NAME);
    }


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


}
