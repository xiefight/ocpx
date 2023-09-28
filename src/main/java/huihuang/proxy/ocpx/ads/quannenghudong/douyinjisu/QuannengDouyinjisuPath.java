package huihuang.proxy.ocpx.ads.quannenghudong.douyinjisu;

import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-09-28 10:41
 **/
@Component
public class QuannengDouyinjisuPath extends QuannengHudongPath {
    @Override
    public String baseAdsName() {
        return "quanneng-douyinjisu";
    }
}
