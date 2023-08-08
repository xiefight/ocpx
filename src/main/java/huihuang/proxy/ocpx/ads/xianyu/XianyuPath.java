package huihuang.proxy.ocpx.ads.xianyu;

import huihuang.proxy.ocpx.ads.huihui.HuihuiPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-08-08 11:40
 **/
@Component
public class XianyuPath extends HuihuiPath {

    public static final String XIANYU_ADS_NAME = "xianyu";

    @Override
    public String baseAdsName() {
        return XIANYU_ADS_NAME;
    }

}
