package huihuang.proxy.ocpx.ads.weibo.kuaishou;

import huihuang.proxy.ocpx.ads.weibo.WeiboPath;
import org.springframework.stereotype.Component;

@Component
public class WeiboKuaishouPath extends WeiboPath {
    @Override
    public String baseAdsName() {
        return "weibo-kuaishou";
    }

    public static final String EXPOSURE_URI = "https://vs.biz.weibo.com/x/pv?fid=957&a=93017&mi=&si=200032&";

    public static final String BASIC_URI = "https://vs.biz.weibo.com/x/bhv?fid=957&a=93017&mi=&si=200032&";

}
