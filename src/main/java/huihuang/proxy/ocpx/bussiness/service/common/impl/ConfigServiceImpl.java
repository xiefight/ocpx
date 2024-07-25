package huihuang.proxy.ocpx.bussiness.service.common.impl;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.bussiness.dao.common.IConfigDao;
import huihuang.proxy.ocpx.bussiness.service.common.IConfigService;
import huihuang.proxy.ocpx.domain.Config;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-07-25 22:47
 **/
@Service
public class ConfigServiceImpl implements IConfigService {

    /*
     * 存储服务器地址
     */
    public Map<String, String> serverMap = new ConcurrentHashMap<>(2);

    /**
     * 数据库配置表查询的
     * 存储百度快手动态创建的表名和id映射
     * 通过表名查找id,作为该表的起始id,并自增
     *
     * 只作自己的缓存，不提供给外部,外部只能通过方法queryBaiduKuaishouAccountMap获取
     */
    private Map<String, Integer> kuaishouBaiduTableIdMap = new ConcurrentHashMap<>();

    @Autowired
    private IConfigDao configDao;

    /**
     * config中查询服务地址
     */
    public String queryServerPath() {
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
     * config中查询baidu-kuaishou-account-id-map
     */
    public Map<String, Integer> queryBaiduKuaishouAccountMap() {
        //缓存中为空，从数据库获取
        if (CollUtil.isEmpty(kuaishouBaiduTableIdMap) || !kuaishouBaiduTableIdMap.containsKey(Config.BAIDU_KUAISHOU_ACCOUNT_ID_MAP)) {
            String config = configDao.queryConfig();
            Map<String, Object> configMap = CollUtil.newHashMap();
            try {
                configMap = JsonParameterUtil.jsonToMap(config, Exception.class);
                assert configMap != null;
            } catch (Exception e) {
                e.printStackTrace();
            }
            kuaishouBaiduTableIdMap = (Map<String, Integer>) configMap.get(Config.BAIDU_KUAISHOU_ACCOUNT_ID_MAP);
        }
        return kuaishouBaiduTableIdMap;
    }

}
