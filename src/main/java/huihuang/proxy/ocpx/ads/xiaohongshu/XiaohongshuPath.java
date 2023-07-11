package huihuang.proxy.ocpx.ads.xiaohongshu;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-06-04 10:08
 **/
@Component
public class XiaohongshuPath extends LiangdamaoPath {

    public static final String XIAOHONGSHU_ADS_NAME = "xiaohongshu";

    @Override
    public String baseAdsName() {
        return XIAOHONGSHU_ADS_NAME;
    }

    @Override
    public String tpAdvId() {
        return "225";
    }
}
