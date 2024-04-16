package huihuang.proxy.ocpx.ads.huihui.kuake;

import huihuang.proxy.ocpx.ads.huihui.HuihuiPath;
import org.springframework.stereotype.Component;

@Component
public class HuihuiKuakePath extends HuihuiPath {


    public static final String KUAKE_ADS_NAME = "huihui_kuake";

    @Override
    public String baseAdsName() {
        return KUAKE_ADS_NAME;
    }

}
