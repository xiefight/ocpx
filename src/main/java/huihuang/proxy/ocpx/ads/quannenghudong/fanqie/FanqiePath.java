package huihuang.proxy.ocpx.ads.quannenghudong.fanqie;

import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-09-27 11:04
 **/
@Component("quannengFanqiePath")
public class FanqiePath extends QuannengHudongPath {
    @Override
    public String baseAdsName() {
        return "quanneng-fanqie";
    }
}