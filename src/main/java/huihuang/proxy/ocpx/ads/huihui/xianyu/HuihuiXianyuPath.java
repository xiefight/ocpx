package huihuang.proxy.ocpx.ads.huihui.xianyu;

import huihuang.proxy.ocpx.ads.huihui.HuihuiPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-08-08 11:40
 **/
@Component
public class HuihuiXianyuPath extends HuihuiPath {

    /**
     * 闲鱼1：33936替换36490
     * 闲鱼2：35270 替换36494
     */

    public static final String XIANYU_ADS_NAME = "xianyu";

    @Override
    public String baseAdsName() {
        return XIANYU_ADS_NAME;
    }

}
