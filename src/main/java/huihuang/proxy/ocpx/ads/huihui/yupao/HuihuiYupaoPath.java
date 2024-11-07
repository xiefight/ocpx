package huihuang.proxy.ocpx.ads.huihui.yupao;

import huihuang.proxy.ocpx.ads.huihui.HuihuiPath;
import org.springframework.stereotype.Component;

@Component
public class HuihuiYupaoPath extends HuihuiPath {
    @Override
    public String baseAdsName() {
        return "huihui-yupao";
    }
}
