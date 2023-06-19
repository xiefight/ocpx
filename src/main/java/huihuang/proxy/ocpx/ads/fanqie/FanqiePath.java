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

    public static final String CLIENT_ID = "998b6cc1aeab304d45bd9a54466f6524";
    public static final String SECRET = "YDlC4jjVqqi6aUzg";

    @Override
    public String baseAdsName() {
        return FANQIE_ADS_NAME;
    }

    @Override
    public String tpAdvId() {
        return "186";
    }
}
