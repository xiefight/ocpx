package huihuang.proxy.ocpx.channel.iqiyi;

/**
 * @Author: xietao
 * @Date: 2023/6/29 21:40
 */
public enum IQiyiParamEnum {

    IDFA("__IDFA__", "idfa", "IOS设备标识：原值"),
    IMEI_MD5("__IMEI__", "imei", "Android设备标识 MD5"),
    OAID("__OAID__", "oaid", "原文oaid"),
    ANDROID_ID_MD5("__ANDROIDID__", "androidid", "Android 设备的android id 的 Md5 值"),
    MAC("__MAC__", "mac", "MAC转大写，去冒号 的 MD5 值"),
    MAC1("__MAC1__", "mac1", "MAC转大写，保留冒号 的 MD5 值"),
    IP("__IP__", "ip", "IP地址：客户端IP地址"),
    OS("__OS__", "os", "安卓：0；iOS：1；windows phone：2；其他：3"),
    TS("__TS__", "timestamp", "客户端发生广告点击事件的时间，以秒为单位时间戳"),
    UA("__UA__", "ua", "用户代理 user_agent"),
    CALLBACK_URL("__CALLBACK_URL__", "callback_url", "效果数据回传URL"),


    KUAISHOU_ADID("", "adid", "快手投放渠道标识"),

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

    IQiyiParamEnum(String macro, String param, String explain) {
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
