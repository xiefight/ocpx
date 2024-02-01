package huihuang.proxy.ocpx.ads.luyun;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-02-01 15:49
 **/
public abstract class LuyunPath {

    protected abstract String baseAdsName();

    /**
     * 点击上报及转化数据回调接口
     */
    public static final String BASIC_URI = "http://cpa.hyxt666.com/click?";
}
