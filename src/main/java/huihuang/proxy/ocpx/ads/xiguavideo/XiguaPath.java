package huihuang.proxy.ocpx.ads.xiguavideo;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoPath;
import org.springframework.stereotype.Component;

/**
 * xigua
 *
 * @Author: xietao
 * @Date: 2023/5/9 20:26
 */
@Component
public class XiguaPath extends LiangdamaoPath {

    public static final String XIGUA_ADS_NAME = "xigua";

    public static final String CLIENT_ID = "dc557d524fdd1b29d684ea348890fc17";
    public static final String SECRET = "333DUFA6oZmi09QQ";

    @Override
    public String baseAdsName() {
        return XIGUA_ADS_NAME;
    }

    @Override
    public String tpAdvId() {
        return "175";
    }
}
