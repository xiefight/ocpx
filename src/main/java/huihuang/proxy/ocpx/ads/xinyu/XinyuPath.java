package huihuang.proxy.ocpx.ads.xinyu;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoPath;
import org.springframework.stereotype.Component;

/**
 *
 * @Author: xietao
 * @Date: 2023/6/8 19:36
 */
@Component
public class XinyuPath extends LiangdamaoPath {

    public static final String XINYU_ADS_NAME = "xinyu";

    @Override
    public String baseAdsName() {
        return XINYU_ADS_NAME;
    }

    @Override
    public String tpAdvId() {
        return "202";
    }
}
