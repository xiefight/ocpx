package huihuang.proxy.ocpx.middle;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.bussiness.dao.IConfigDao;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-21 13:44
 **/
public class BaseSupport {

    @Autowired
    private IConfigDao configDao;

    protected String queryServerPath() {
        String config = configDao.queryConfig();
        Map<String, Object> configMap = CollUtil.newHashMap();
        try {
            configMap = JsonParameterUtil.jsonToMap(config, Exception.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (String) configMap.get("serverPath");
    }
}
