package huihuang.proxy.ocpx.ads.quannenghudong.hemajuchang;

import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-12-18 17:01
 **/
@Component
public class QuannengHemajuchangPath extends QuannengHudongPath {
    @Override
    public String baseAdsName() {
        return "quanneng-hemajuchang";
    }
}
