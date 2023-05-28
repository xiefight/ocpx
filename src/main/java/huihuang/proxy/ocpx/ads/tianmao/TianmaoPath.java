package huihuang.proxy.ocpx.ads.tianmao;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoPath;
import huihuang.proxy.ocpx.ads.litianjingdong.LTJDPath;
import org.springframework.stereotype.Component;

/**
 *
 * @Author: xietao
 * @Date: 2023/5/16 21:11
 */
@Component
public class TianmaoPath extends LiangdamaoPath {

    public static final String TIANMAO_ADS_NAME = "tianmao";

    @Override
    public String baseAdsName() {
        return TIANMAO_ADS_NAME;
    }

    @Override
    public String tpAdvId() {
        return "192";//"183";
    }
}
