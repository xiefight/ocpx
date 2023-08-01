package huihuang.proxy.ocpx.ads.dongchedi;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoPath;
import org.springframework.stereotype.Component;

/**
 * @Author: xietao
 * @Date: 2023/8/1 16:54
 */
@Component
public class DongchediPath extends LiangdamaoPath {

    public static final String DONGCHEDI_ADS_NAME = "dongchedi";

    @Override
    public String baseAdsName() {
        return DONGCHEDI_ADS_NAME;
    }

    @Override
    public String tpAdvId() {
        return "228";
    }
}
