package huihuang.proxy.ocpx.ads.youku;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoPath;
import org.springframework.stereotype.Component;

/**
 * @Description: youku
 * @Author: xietao
 * @Date: 2023-04-27 09:27
 **/
@Component
public class YoukuPath extends LiangdamaoPath {

    public static final String YOUKU_ADS_NAME = "youku";

    @Override
    public String baseAdsName() {
        return YOUKU_ADS_NAME;
    }

    @Override
    public String tpAdvId() {
        return "169";
    }
}
