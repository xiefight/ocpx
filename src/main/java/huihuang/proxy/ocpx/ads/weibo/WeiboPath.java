package huihuang.proxy.ocpx.ads.weibo;

public abstract class WeiboPath {

    protected abstract String baseAdsName();

    /**
     * 曝光上报
     */
    public static final String EXPOSURE_URI = "https://vs.biz.weibo.com/x/pv?";

    /**
     * 点击上报及转化数据回调接口
     */
    public static final String BASIC_URI = "https://vs.biz.weibo.com/x/bhv?";

}
