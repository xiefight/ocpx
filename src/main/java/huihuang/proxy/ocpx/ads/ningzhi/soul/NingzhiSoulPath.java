package huihuang.proxy.ocpx.ads.ningzhi.soul;

import huihuang.proxy.ocpx.ads.ningzhi.NingzhiPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-10-16 00:51
 **/
@Component
public class NingzhiSoulPath extends NingzhiPath {
    @Override
    public String baseAdsName() {
        return "ningzhi-soul";
    }
}
