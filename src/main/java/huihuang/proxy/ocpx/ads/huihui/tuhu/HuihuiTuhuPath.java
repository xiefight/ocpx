package huihuang.proxy.ocpx.ads.huihui.tuhu;

import huihuang.proxy.ocpx.ads.huihui.HuihuiPath;
import org.springframework.stereotype.Component;

/**
 *
 * @Author: xietao
 * @Date: 2023/8/16 10:19
 */
@Component
public class HuihuiTuhuPath extends HuihuiPath {

    public static final String TUHU_ADS_NAME = "tuhu";

    @Override
    public String baseAdsName() {
        return TUHU_ADS_NAME;
    }

}
