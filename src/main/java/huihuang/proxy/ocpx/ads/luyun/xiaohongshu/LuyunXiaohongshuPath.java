package huihuang.proxy.ocpx.ads.luyun.xiaohongshu;

import huihuang.proxy.ocpx.ads.luyun.LuyunPath;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-06-04 23:36
 **/
@Component
public class LuyunXiaohongshuPath extends LuyunPath {

    public static final String ADS_NAME = "luyun-xiaohongshu";


    @Override
    public String baseAdsName() {
        return ADS_NAME;
    }

}
