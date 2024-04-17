package huihuang.proxy.ocpx.ads.quannenghudong.uc;

import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongPath;
import org.springframework.stereotype.Component;

@Component
public class QuannengUCPath extends QuannengHudongPath {
    @Override
    public String baseAdsName() {
        return "quanneng-youku";
    }
}
