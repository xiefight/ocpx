package huihuang.proxy.ocpx.cache;

import java.util.Map;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-05-16 14:03
 **/
public interface InfoCache {

    void put(String key, Integer value);

    boolean find(String key);

    void remove(String key);

    void refresh(String key);

}
