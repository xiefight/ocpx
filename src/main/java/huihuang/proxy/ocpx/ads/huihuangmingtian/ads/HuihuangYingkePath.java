package huihuang.proxy.ocpx.ads.huihuangmingtian.ads;

import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianPath;
import org.springframework.stereotype.Component;

@Component
public class HuihuangYingkePath extends HuihuangmingtianPath {
    @Override
    public String baseAdsName() {
        return "huihuang-yingke";
    }
}
