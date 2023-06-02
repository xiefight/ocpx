package huihuang.proxy.ocpx.ads.jdsousuo;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-06-02 19:00
 **/
@Component
public class JDSSPath extends LiangdamaoPath {

    public static final String JDSS_ADS_NAME = "jdss";

    @Override
    public String baseAdsName() {
        return JDSS_ADS_NAME;
    }

    @Override
    public String tpAdvId() {
        return "164";
    }
}
