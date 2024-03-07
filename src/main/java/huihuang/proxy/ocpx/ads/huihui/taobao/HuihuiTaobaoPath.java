package huihuang.proxy.ocpx.ads.huihui.taobao;

import huihuang.proxy.ocpx.ads.huihui.HuihuiPath;
import org.springframework.stereotype.Component;

@Component
public class HuihuiTaobaoPath extends HuihuiPath {

    public static final String TAOBAO_ADS_NAME = "taobao-youdao";

    @Override
    public String baseAdsName() {
        return TAOBAO_ADS_NAME;
    }
}
