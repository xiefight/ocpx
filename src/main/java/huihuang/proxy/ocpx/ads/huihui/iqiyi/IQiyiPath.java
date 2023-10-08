package huihuang.proxy.ocpx.ads.huihui.iqiyi;

import huihuang.proxy.ocpx.ads.huihui.HuihuiPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-10-08 13:57
 **/
@Component
public class IQiyiPath extends HuihuiPath {
    @Override
    public String baseAdsName() {
        return "huihui-iqiyi";
    }
}
