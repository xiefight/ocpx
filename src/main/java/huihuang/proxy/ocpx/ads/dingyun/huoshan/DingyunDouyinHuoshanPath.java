package huihuang.proxy.ocpx.ads.dingyun.huoshan;

import huihuang.proxy.ocpx.ads.dingyun.DingyunPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-01-04 09:11
 **/
@Component
public class DingyunDouyinHuoshanPath extends DingyunPath {
    @Override
    public String baseAdsName() {
        return "dingyun-huoshan";
    }
}
