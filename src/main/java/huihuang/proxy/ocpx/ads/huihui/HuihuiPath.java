package huihuang.proxy.ocpx.ads.huihui;

/** 
 * 
 * @Author: xietao
 * @Date: 2023/6/8 17:21
 */ 
public abstract class HuihuiPath {

    // 上报广告主时使用
//    public static final String ACCESS_ID = "tp97c55258d39edfe4232dbc485465dc92";
//    public static final String SECRET = "328a8df2988727656e399bd7f27c52ad";

//    public static final String LTJD_ADS_NAME = "ltjd";

    protected abstract String baseAdsName();
//    protected abstract String tpAdvId();

    /**
     * 点击上报及转化数据回调接口
     */
    public static final String BASIC_URI = "http://ad.huihui.cn/api2/click?";

    public static final String BASIC_URI_2 = "https://c.adtest.cn/api2/click?";

    public static final String DID = "40da2b44617246b9";

}
