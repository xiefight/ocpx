package huihuang.proxy.ocpx.ads.luyun.kuaikanmanhua;

import huihuang.proxy.ocpx.ads.luyun.LuyunPath;
import org.springframework.stereotype.Component;

@Component
public class LuyunKuaikanmanhuaPath extends LuyunPath {

    public static final String ADS_NAME = "luyun-kuaikanmanhua";


    @Override
    public String baseAdsName() {
        return ADS_NAME;
    }

}
