package huihuang.proxy.ocpx.ads.ningzhi;

public abstract class NingzhiPath {


    protected abstract String baseAdsName();

    /**
     * 点击上报及转化数据回调接口
     */
    public static final String BASIC_URI = "http://api.ningzhishidai.com/click.api?";

}
