package huihuang.proxy.ocpx.ads.dingyun.xigua;

import huihuang.proxy.ocpx.ads.dingyun.DingyunPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-01-04 16:43
 **/
@Component
public class DingyunXiguaVideoPath extends DingyunPath {
    @Override
    public String baseAdsName() {
        return "dingyun-xigua";
    }
}
