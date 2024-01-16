package huihuang.proxy.ocpx.ads.quannenghudong.jinritoutiao;

import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-11-02 11:21
 **/
@Component
public class QuannengJinritoutiaoPath extends QuannengHudongPath {

    @Override
    public String baseAdsName() {
        return "quanneng-jinritoutiao";
    }
}
