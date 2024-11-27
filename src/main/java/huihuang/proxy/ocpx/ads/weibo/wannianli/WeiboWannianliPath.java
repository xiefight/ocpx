package huihuang.proxy.ocpx.ads.weibo.wannianli;

import huihuang.proxy.ocpx.ads.weibo.WeiboPath;
import org.springframework.stereotype.Component;

@Component
public class WeiboWannianliPath extends WeiboPath {
    @Override
    public String baseAdsName() {
        return "weibo-wannianli";
    }

    public static final String EXPOSURE_URI = "https://vs.biz.weibo.com/x/pv?fid=952&a=84667&mi=&si=200032&";

    public static final String BASIC_URI = "https://vs.biz.weibo.com/x/bhv?fid=952&a=84667&mi=&si=200032&";

}
