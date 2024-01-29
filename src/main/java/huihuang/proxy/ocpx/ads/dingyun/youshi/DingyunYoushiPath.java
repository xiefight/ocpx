package huihuang.proxy.ocpx.ads.dingyun.youshi;

import huihuang.proxy.ocpx.ads.dingyun.DingyunPath;
import org.springframework.stereotype.Component;

@Component
public class DingyunYoushiPath extends DingyunPath {
    @Override
    public String baseAdsName() {
        return "dingyun-youshi";
    }
}
