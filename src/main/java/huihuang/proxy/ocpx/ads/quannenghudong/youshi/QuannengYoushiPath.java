package huihuang.proxy.ocpx.ads.quannenghudong.youshi;

import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-11-03 15:52
 **/
@Component
public class QuannengYoushiPath extends QuannengHudongPath {
    @Override
    public String baseAdsName() {
        return "quanneng-youshi";
    }
}
