package huihuang.proxy.ocpx.ads.huihuangmingtian;

public abstract class HuihuangmingtianPath {


    protected abstract String baseAdsName();

    /**
     * 点击上报及转化数据回调接口
     */
    public static final String BASIC_URI = "https://hhmt-cog.yoqu.net/cpa/media?";


    /**
     * 曝光链接
     */
    public static final String EXPOSURE_URI = "https://dmp-data.vip.com/dmp/data/collector?";

}
