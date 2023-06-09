package huihuang.proxy.ocpx.ads.tantan;

import huihuang.proxy.ocpx.ads.huihui.HuihuiPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-06-09 08:33
 **/
@Component
public class TantanPath extends HuihuiPath {

    public static final String TANTAN_ADS_NAME = "tantan";

    @Override
    public String baseAdsName() {
        return TANTAN_ADS_NAME;
    }

}
