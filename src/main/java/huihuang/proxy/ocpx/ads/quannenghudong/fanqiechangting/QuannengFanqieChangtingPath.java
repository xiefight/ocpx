package huihuang.proxy.ocpx.ads.quannenghudong.fanqiechangting;

import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-09-27 11:04
 **/
@Component("quannengFqctPath")
public class QuannengFanqieChangtingPath extends QuannengHudongPath {
    @Override
    public String baseAdsName() {
        return "quanneng-fanqie-changting";
    }
}
