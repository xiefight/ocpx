package huihuang.proxy.ocpx.ads.quannenghudong.soul;

import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongPath;
import org.springframework.stereotype.Component;

@Component
public class QuannengSoulPath extends QuannengHudongPath {
    @Override
    public String baseAdsName() {
        return "quanneng-soul";
    }
}
