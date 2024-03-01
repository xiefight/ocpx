package huihuang.proxy.ocpx.ads.huihuangmingtian.ads;

import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianPath;
import org.springframework.stereotype.Component;

/**
 * 丰芒-优酷，使用的
 * huihuang/proxy/ocpx/ads/huihuang/HuihuangEventTypeEnum.java事件
 */

@Component
public class HuihuangYoukuPath extends HuihuangmingtianPath {
    @Override
    public String baseAdsName() {
        return "huihuang-youku";
    }
}
