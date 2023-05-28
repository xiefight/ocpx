package huihuang.proxy.ocpx.ads.liangdamao;

/**
 * 粮大猫
 * @Author: xietao
 * @Date: 2023/5/28 12:09
 */
public abstract class LiangdamaoPath {

    // 上报广告主时使用
    public static final String ACCESS_ID = "tp97c55258d39edfe4232dbc485465dc92";
    public static final String SECRET = "328a8df2988727656e399bd7f27c52ad";

//    public static final String LTJD_ADS_NAME = "ltjd";

    protected abstract String baseAdsName();
    protected abstract String tpAdvId();

    /**
     * 点击上报及转化数据回调接口
     */
    public static final String BASIC_URI = "https://convert.mongac.com/tracking/thirdparty/link?";

}
