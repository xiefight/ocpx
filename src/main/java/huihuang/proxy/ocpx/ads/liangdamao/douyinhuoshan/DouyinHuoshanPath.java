package huihuang.proxy.ocpx.ads.liangdamao.douyinhuoshan;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoPath;
import org.springframework.stereotype.Component;

@Component
public class DouyinHuoshanPath extends LiangdamaoPath {
    @Override
    public String baseAdsName() {
        return "douyinhuoshan";
    }

    @Override
    public String tpAdvId() {
        return "317";
    }
}
