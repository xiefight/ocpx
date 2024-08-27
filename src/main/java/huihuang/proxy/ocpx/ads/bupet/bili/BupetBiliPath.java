package huihuang.proxy.ocpx.ads.bupet.bili;

import huihuang.proxy.ocpx.ads.bupet.BupetPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-08-27 17:36
 **/
@Component
public class BupetBiliPath extends BupetPath {
    @Override
    public String baseAdsName() {
        return "bili";
    }
}
