package huihuang.proxy.ocpx.ads.huihui.xinyu;

import huihuang.proxy.ocpx.ads.huihui.HuihuiPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-08-02 17:37
 **/
@Component
public class HuihuiXinyuPath extends HuihuiPath {

    public static final String XINYU_ADS_NAME = "xinyu-youdao";

    @Override
    public String baseAdsName() {
        return XINYU_ADS_NAME;
    }
}
