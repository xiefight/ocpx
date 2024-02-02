package huihuang.proxy.ocpx.ads.luyun.paipai;

import huihuang.proxy.ocpx.ads.luyun.LuyunPath;
import org.springframework.stereotype.Component;

@Component
public class PaipaiPath extends LuyunPath {

    public static final String ADS_NAME = "paipai";


    @Override
    public String baseAdsName() {
        return ADS_NAME;
    }
}
