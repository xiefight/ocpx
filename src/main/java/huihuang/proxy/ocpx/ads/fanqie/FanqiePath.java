package huihuang.proxy.ocpx.ads.fanqie;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoPath;
import org.springframework.stereotype.Component;

/**
 * @Author: xietao
 * @Date: 2023/5/23 17:26
 */
@Component
public class FanqiePath extends LiangdamaoPath {

    public static final String FANQIE_ADS_NAME = "fanqie";

    @Override
    public String baseAdsName() {
        return FANQIE_ADS_NAME;
    }

    @Override
    public String tpAdvId() {
        return "186";
    }
}
