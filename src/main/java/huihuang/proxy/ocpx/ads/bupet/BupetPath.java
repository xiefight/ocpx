package huihuang.proxy.ocpx.ads.bupet;

public abstract class BupetPath {

    protected abstract String baseAdsName();

    /**
     * 点击上报及转化数据回调接口
     */
    public static final String BASIC_URI = "https://ad.bupet.net/openapi/click/upload/am26fi?";

}
