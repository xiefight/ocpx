package huihuang.proxy.ocpx.bussiness.service;

import huihuang.proxy.ocpx.bussiness.service.common.IConfigService;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.marketinterface.IMarkDto;
import huihuang.proxy.ocpx.util.CommonUtil;
import huihuang.proxy.ocpx.util.tuple.Tuple2;
import huihuang.proxy.ocpx.util.tuple.Tuple3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-22 15:29
 **/
@Component
public class BaseServiceInner {
    @Autowired
    private IConfigService configService;

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
     * @param splitAdsDao        原表，kuaishou_ads_baidu
     * @param splitAccountAdsDao 根据账户分表，kuaishou_ads_baidu_bdks01
     * @param methodName         查询方法名
     * @param id                 查询id
     * @param <T>                查询对象类型
     * @return IMarkDao查询的dao对象
     * T 查询到的对象数据
     * String 用到的表名
     * Boolean 是否是原表
     */
    public <T> Tuple3<IMarkDao, T, String> queryBaiduKuaishouAccountAdsObject(IMarkDao splitAdsDao, IMarkDao splitAccountAdsDao, String methodName, Integer id) {
        try {
            //获取百度快手账号和id映射的配置信息
            Set<Integer> configIds = CommonUtil.kuaishouBaiduIdTableMap.keySet();
            //判断id大小,这里不处理--计算的id在某一表中，但表不存在的情况,表一定是先于id存在的
            for (Integer configId : configIds) {
                /*if (id < 60000000){
                    String tableName = CommonUtil.NEW_ORIGIN_BAIDU_KUAISHOU_TABLE_NAME;
                    //todo 指定分表
                    //分表查
                    Method adsDaoQuery = splitAccountAdsDao.getClass().getMethod(methodName, Integer.class, String.class);
                    T t = (T) adsDaoQuery.invoke(splitAccountAdsDao, id, tableName);
                    if (null != t) {
                        return new Tuple3<>(splitAccountAdsDao, t, tableName);
                    }
                }*/
                //如果id小于配置的id,说明id小于分表之后最小的configId,数据在原百度快手表里
                if (id < configId) {
                    String tableName = CommonUtil.kuaishouBaiduIdTableMap.get(configId);
                    //原表查
                    /*if (CommonUtil.ORIGIN_BAIDU_KUAISHOU_TABLE_NAME.equals(tableName)) {
                        Method splitAdsDaoQuery = splitAdsDao.getClass().getMethod(methodName, Integer.class);
                        T t = (T) splitAdsDaoQuery.invoke(splitAdsDao, id);
                        if (null != t) {
                            return new Tuple3<>(splitAdsDao, t, "");
                        }
                    }*/
                    //分表查
                    Method adsDaoQuery = splitAccountAdsDao.getClass().getMethod(methodName, Integer.class, String.class);
                    T t = (T) adsDaoQuery.invoke(splitAccountAdsDao, id, tableName);
                    if (null != t) {
                        return new Tuple3<>(splitAccountAdsDao, t, tableName);
                    }
                }
            }
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

    public String typeConvertOs(String appType) {
        switch (appType) {
            case "0":
                return "android";
            case "1":
                return "ios";
        }
        return "";
    }


    /**
     * 服务器动时，将账户分表的 startId:tableName 的关系加载到内存中
     * ps：之前是在生成上报时加载的，从库中获取映射关系，然后创建表、将 startId+stepNum:tableName 的关系逐个写入内存；然后回传时，再从内存中获取
     * 但有一个问题：服务重启后，如果回传先于上报，内存中还没有数据，导致找不到表
     * 所以：回传时的查询，不能依赖于上报，而在服务启动一开始就加载
     */
    @PostConstruct
    public void startIdTableInit() {
        Map<String, Integer> baiduKuaishouAccountMap = configService.queryBaiduKuaishouAccountMap();
        CommonUtil.baiduKuaishouAccountMap = baiduKuaishouAccountMap;
        assert baiduKuaishouAccountMap != null;
        Set<String> tables = baiduKuaishouAccountMap.keySet();
        //保存id和表名的映射关系,方便回传更新时,根据id快速定位到表名
        // 假如配置项是: {"bdks01":1000,"bdks02":2000,"bdksjs01":3000,"bdksjs02":4000},
        // 在存储时,应该是{2000:"bdks01",3000:"bdks02",4000:"bdksjs01",5000:"bdksjs02"},这样在对比时,小于2000的，就是bdks01,小于3000的，就是bdks02
        // 步近 1000 0000
        for (String tableName : tables) {
            Integer startId = baiduKuaishouAccountMap.get(tableName);
            CommonUtil.kuaishouBaiduIdTableMap.put(startId + CommonUtil.STEP_NUM, tableName);
        }
    }

}
