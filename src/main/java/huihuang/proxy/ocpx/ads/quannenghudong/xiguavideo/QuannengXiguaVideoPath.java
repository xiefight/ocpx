package huihuang.proxy.ocpx.ads.quannenghudong.xiguavideo;

import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-09-28 18:49
 **/
@Component
public class QuannengXiguaVideoPath extends QuannengHudongPath {
    @Override
    public String baseAdsName() {
        return "quanneng-xiguavideo";
    }
}
