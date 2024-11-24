package huihuang.proxy.ocpx.ads.weibo.baiduwangpan;

import huihuang.proxy.ocpx.ads.weibo.WeiboPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-11-23 23:44
 **/
@Component
public class WeiboBaiduwangpanPath extends WeiboPath {
    @Override
    public String baseAdsName() {
        return "weibo-baiduwangpan";
    }

    public static final String EXPOSURE_URI = "https://vs.biz.weibo.com/x/pv?fid=940&a=127262&mi=&si=200032&";

    public static final String BASIC_URI = "https://vs.biz.weibo.com/x/bhv?fid=940&a=127262&mi=&si=200032&";

}
