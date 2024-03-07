package huihuang.proxy.ocpx.ads.huihui.qimao;

import huihuang.proxy.ocpx.ads.huihui.HuihuiPath;
import org.springframework.stereotype.Component;

@Component
public class HuihuiQimaoPath extends HuihuiPath {

    public static final String QIMAO_ADS_NAME = "qimao-youdao";

    @Override
    public String baseAdsName() {
        return QIMAO_ADS_NAME;
    }
}
