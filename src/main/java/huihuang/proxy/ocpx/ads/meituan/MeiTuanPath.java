package huihuang.proxy.ocpx.ads.meituan;

/**
 * @Description: 美团（客户侧）
 * @Author: xietao
 * @Date: 2023-04-20 10:42
 **/
public class MeiTuanPath {

    /**
     * uri
     */
    public static final String BASIC_URI = "https://apimobile.meituan.com/prom/v2/";

    /**
     *  曝光上报接口
     */
    public static final String ACTION_MONITOR = "action_monitor?app=group&monitor_type=impression&mt_channel=meituanunion";

    /**
     * 点击上报及转化数据回调接口
     */
    public static final String VERIFY = "verify?app=group&coderesp=true&mt_channel=meituanunion";

}
