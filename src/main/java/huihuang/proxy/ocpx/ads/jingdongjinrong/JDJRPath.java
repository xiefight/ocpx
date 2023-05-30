package huihuang.proxy.ocpx.ads.jingdongjinrong;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-05-30 17:53
 **/
@Component
public class JDJRPath extends LiangdamaoPath {

    public static final String JDJR_ADS_NAME = "jdjr";

    @Override
    public String baseAdsName() {
        return JDJR_ADS_NAME;
    }

    @Override
    public String tpAdvId() {
        return "193";
    }
}
