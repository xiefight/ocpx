package huihuang.proxy.ocpx.ads.quannenghudong.iqiyi;

import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-09-28 18:49
 **/
@Component
public class QuannengIQiyiPath extends QuannengHudongPath {
    @Override
    public String baseAdsName() {
        return "quanneng-iqiyi";
    }
}
