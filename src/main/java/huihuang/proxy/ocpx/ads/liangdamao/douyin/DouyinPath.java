package huihuang.proxy.ocpx.ads.liangdamao.douyin;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-10-11 16:04
 **/
@Component
public class DouyinPath extends LiangdamaoPath {
    @Override
    public String baseAdsName() {
        return "douyin";
    }

    @Override
    public String tpAdvId() {
        return "271";
    }
}
