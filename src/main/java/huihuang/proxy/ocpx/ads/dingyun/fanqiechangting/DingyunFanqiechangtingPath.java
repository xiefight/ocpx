package huihuang.proxy.ocpx.ads.dingyun.fanqiechangting;

import huihuang.proxy.ocpx.ads.dingyun.DingyunPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-01-04 17:54
 **/
@Component
public class DingyunFanqiechangtingPath extends DingyunPath {
    @Override
    public String baseAdsName() {
        return "dingyun-fanqiechangting";
    }
}
