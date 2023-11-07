package huihuang.proxy.ocpx.ads.quannenghudong.douyinhuoshan;

import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongPath;
import org.springframework.stereotype.Component;

@Component
public class QuannengDouyinhuoshanPath extends QuannengHudongPath {
    @Override
    public String baseAdsName() {
        return "quanneng-douyinhuoshan";
    }
}
