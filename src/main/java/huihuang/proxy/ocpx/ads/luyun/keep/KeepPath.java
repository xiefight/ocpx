package huihuang.proxy.ocpx.ads.luyun.keep;

import huihuang.proxy.ocpx.ads.luyun.LuyunPath;
import org.springframework.stereotype.Component;

@Component
public class KeepPath extends LuyunPath {

    public static final String ADS_NAME = "keep";


    @Override
    public String baseAdsName() {
        return ADS_NAME;
    }
}
