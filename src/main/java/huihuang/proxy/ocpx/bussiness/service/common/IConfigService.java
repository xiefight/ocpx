package huihuang.proxy.ocpx.bussiness.service.common;

import java.util.Map;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-07-25 22:46
 **/
public interface IConfigService {

    /**
     * config中查询服务地址
     */
    public String queryServerPath();
    /**
     * config中查询baidu-kuaishou-account-id-map
     */
    public Map<String, Integer> queryBaiduKuaishouAccountMap();
}
