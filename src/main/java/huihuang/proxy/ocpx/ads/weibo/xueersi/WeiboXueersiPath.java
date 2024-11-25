package huihuang.proxy.ocpx.ads.weibo.xueersi;

import huihuang.proxy.ocpx.ads.weibo.WeiboPath;
import org.springframework.stereotype.Component;

@Component
public class WeiboXueersiPath extends WeiboPath {
    @Override
    public String baseAdsName() {
        return "weibo-xueersi";
    }

    public static final String EXPOSURE_URI = "https://vs.biz.weibo.com/x/pv?fid=939&a=1532245&mi=&si=200032&";

    public static final String BASIC_URI = "https://vs.biz.weibo.com/x/bhv?fid=939&a=1532245&mi=&si=200032&";

}
