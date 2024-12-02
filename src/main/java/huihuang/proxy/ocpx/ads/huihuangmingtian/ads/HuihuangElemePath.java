package huihuang.proxy.ocpx.ads.huihuangmingtian.ads;

import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianPath;
import org.springframework.stereotype.Component;

@Component
public class HuihuangElemePath extends HuihuangmingtianPath {
    @Override
    public String baseAdsName() {
        return "huihuang-eleme";
    }

    public static String EXPOSURE_URI = "https://alsc-ug-monitor-callback.alibaba.com/nagaMonitor/exposure?medium_source=51&delivery_type=4&ascribe_type=1&demander_type=5&biz_type=1";
}
