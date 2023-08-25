package huihuang.proxy.ocpx.ads.huihuangmingtian.ads;

import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-08-25 13:35
 **/
@Component
public class DiantaoPath extends HuihuangmingtianPath {
    @Override
    public String baseAdsName() {
        return "diantao";
    }
}
