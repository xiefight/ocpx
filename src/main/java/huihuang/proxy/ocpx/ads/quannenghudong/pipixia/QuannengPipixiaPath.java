package huihuang.proxy.ocpx.ads.quannenghudong.pipixia;

import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongPath;
import org.springframework.stereotype.Component;

@Component
public class QuannengPipixiaPath extends QuannengHudongPath {
    @Override
    public String baseAdsName() {
        return "quanneng-pipixia";
    }
}
