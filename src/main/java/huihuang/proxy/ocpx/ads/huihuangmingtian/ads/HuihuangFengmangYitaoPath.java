package huihuang.proxy.ocpx.ads.huihuangmingtian.ads;

import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianPath;
import org.springframework.stereotype.Component;

/**
 * 丰芒的
 */

@Component
public class HuihuangFengmangYitaoPath extends HuihuangmingtianPath {
    @Override
    public String baseAdsName() {
        return "huihuangfengmang-yitao";
    }
}