package huihuang.proxy.ocpx.ads.quannenghudong.xiaohongshu;

import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongPath;
import org.springframework.stereotype.Component;

@Component
public class QuannengXiaohongshuPath extends QuannengHudongPath {
    @Override
    public String baseAdsName() {
        return "quanneng-xiaohongshu";
    }
}
