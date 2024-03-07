package huihuang.proxy.ocpx.ads.huihui.tt;

import huihuang.proxy.ocpx.ads.huihui.HuihuiPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-11-28 14:05
 **/
@Component
public class HuihuiTTPath extends HuihuiPath {
    @Override
    public String baseAdsName() {
        return "huihui-tt";
    }
}
