package huihuang.proxy.ocpx.channel.oppo;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-24 16:57
 **/
public enum OppoParamEnum {

    OS("$os$","os","操作系统 android"),
    OS_VERSION("$ov$", "os_version", "os 版本 5.1"),
    MODEL("$m$", "model", "机型 R9"),
    LANG("$lan$", "lang", "语言 ZH"),
    COUNTRY("$c$", "country", "国家 CN"),
    WIDTH("$w$", "width", "屏幕宽(像素)  960"),
    HEIGHT("$h$", "height", "屏幕高(像素) 1080"),
    PKG("$pkg$", "pkg", "广告所在 apk 包名 com.android.browser"),
    APP_VERSION("$av$", "app_version", "应用版本 1.4.0"),
    USERAGENT("$ua$", "useragent", ""),
    REFERER("$rf$", "referer", ""),
    NETTYPE("$nt$", "net_type", "网络类型 2G,3G,4G,5G,WIFI,UNKN"),
    CARRIER("$ca$", "carrier", "运营商 不用转换，传原始值"),
    PROGRESS("$progress$", "progress", "播放进度，单位为毫秒，视频广告时有效"),
    IMEI("$im$", "imei", "Imei md5 加密"),
    OAID("__OAID__", "OAID", "OAID 原值"),
    ADID("$ad$", "ad_id", "广告 ID"),
    ADNAME("$an$", "ad_name", "广告名称，注意需要 url encode"),
    REQID("$req$", "req_id", "请求 ID"),

    KUAISHOU_ADID("","adid","快手投放渠道标识"),


    ;


    /**
     * 宏字段
     */
    private String macro;
    /**
     * 宏对应的参数字段
     */
    private String param;
    /**
     * 宏含义
     */
    private String explain;

    OppoParamEnum(String macro, String param, String explain) {
        this.macro = macro;
        this.param = param;
        this.explain = explain;
    }

    public String getMacro() {
        return macro;
    }

    public String getParam() {
        return param;
    }

    public String getExplain() {
        return explain;
    }

}