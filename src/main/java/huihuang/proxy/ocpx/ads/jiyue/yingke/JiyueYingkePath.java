package huihuang.proxy.ocpx.ads.jiyue.yingke;

import huihuang.proxy.ocpx.ads.jiyue.JiyuePath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-12-06 16:37
 **/
@Component
public class JiyueYingkePath extends JiyuePath {
    @Override
    public String baseAdsName() {
        return "jiyue-yingke";
    }
}
