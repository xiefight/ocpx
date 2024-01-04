package huihuang.proxy.ocpx.ads.dingyun;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-01-04 08:37
 **/
public abstract class DingyunPath {

    protected abstract String baseAdsName();

    /**
     * 点击上报及转化数据回调接口
     */
    public static final String BASIC_URI = "http://promotion-partner.dingyunwl.com/rest/click?";

}
