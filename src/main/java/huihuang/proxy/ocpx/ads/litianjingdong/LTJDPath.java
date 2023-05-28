package huihuang.proxy.ocpx.ads.litianjingdong;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoPath;
import org.springframework.stereotype.Component;

/**
 * @Description: 力天京东
 * @Author: xietao
 * @Date: 2023-04-24 15:56
 **/
@Component
public class LTJDPath extends LiangdamaoPath {

    public static final String LTJD_ADS_NAME = "ltjd";

    /**
     * 点击上报及转化数据回调接口
     */
    public static final String BASIC_URI = "https://convert.mongac.com/tracking/thirdparty/link?";

    @Override
    public String baseAdsName() {
        return LTJD_ADS_NAME;
    }

    @Override
    public String tpAdvId() {
        return "164";
    }
}
