package huihuang.proxy.ocpx.ads.quannenghudong.hongguoduanju;

import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongPath;
import org.springframework.stereotype.Component;

/**
 * @Description: 红果短剧（全能）
 * @Author: xietao
 * @Date: 2023-11-29 14:16
 **/
@Component
public class QuannengHongguoduanjuPath extends QuannengHudongPath {
    @Override
    public String baseAdsName() {
        return "quanneng-hongguoduanju";
    }
}
