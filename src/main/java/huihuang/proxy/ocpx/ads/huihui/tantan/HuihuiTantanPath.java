package huihuang.proxy.ocpx.ads.huihui.tantan;

import huihuang.proxy.ocpx.ads.huihui.HuihuiPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-06-09 08:33
 **/
@Component
public class HuihuiTantanPath extends HuihuiPath {

    public static final String TANTAN_ADS_NAME = "tantan";

    @Override
    public String baseAdsName() {
        return TANTAN_ADS_NAME;
    }

}
