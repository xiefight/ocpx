package huihuang.proxy.ocpx.ads.quannenghudong.eleme;

import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongPath;
import org.springframework.stereotype.Component;

@Component("quannengElemePath")
public class QuannengElemePath extends QuannengHudongPath {
    @Override
    public String baseAdsName() {
        return "quanneng-eleme";
    }
}
