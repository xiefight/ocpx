package huihuang.proxy.ocpx.cache.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import huihuang.proxy.ocpx.cache.InfoCache;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-05-16 14:04
 **/
@Component
public class GDTCache implements InfoCache {

    private final Cache<String, Integer> GDT_CACHE =
            Caffeine.newBuilder().expireAfterAccess(10, TimeUnit.MINUTES)
                    .build();


    @Override
    public void put(String key, Integer value) {
        GDT_CACHE.put(key, value);
    }

    @Override
    public boolean find(String key) {
        Integer s = GDT_CACHE.get(key, k -> null);
        return s != null;
    }

    @Override
    public void remove(String key) {
        GDT_CACHE.invalidate(key);
    }

    @Override
    public void refresh(String key) {

    }
}
