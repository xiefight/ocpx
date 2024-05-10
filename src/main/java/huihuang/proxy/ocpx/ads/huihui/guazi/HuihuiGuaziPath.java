package huihuang.proxy.ocpx.ads.huihui.guazi;

import huihuang.proxy.ocpx.ads.huihui.HuihuiPath;
import org.springframework.stereotype.Component;

@Component
public class HuihuiGuaziPath extends HuihuiPath {

    public static final String GUAZI_ADS_NAME = "guaziershouche";

    @Override
    public String baseAdsName() {
        return GUAZI_ADS_NAME;
    }

}
