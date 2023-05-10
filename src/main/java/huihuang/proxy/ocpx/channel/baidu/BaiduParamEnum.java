package huihuang.proxy.ocpx.channel.baidu;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-24 16:57
 **/
public enum BaiduParamEnum {

    USERID("__USER_ID__","userid","账户ID"),
    AID("__IDEA_ID__", "aid", "创意ID"),
    PID("__PLAN_ID__", "pid", "广告计划 ID"),
    UID("__UNIT_ID__", "uid", "广告单元 ID"),
    CALLBACK_URL("__CALLBACK_URL__", "callback_url", "效果数据回传URL"),
    EXT_INFO("__EXT_INFO__", "ext_info", "点击信息/曝光信息  callType=v2用户使用"),
    CLICK_ID("__CLICK_ID__", "click_id", "点击或曝光唯一标识"),
    SIZE("__SIZE__", "size", "点击或曝光唯一标识"),
    IDFA("__IDFA__", "idfa", "IOS设备标识：原值"),
    IMEI_MD5("__IMEI_MD5__", "imei_md5", "Android设备标识 MD5"),
    ANDROID_ID_MD5("__ANDROID_ID_MD5__", "android_id_md5", "Android 设备的android id 的 Md5 值"),
    IP("__IP__", "ip", "IP地址：客户端IP地址"),
    UA("__UA__", "ua", "用户代理"),
    OS_VERSION("__OS_VERSION__", "os_version", "操作系统版本"),
    OS_TYPE("__OS_TYPE__", "os_type", "安卓：0；iOS：1；其他：3"),
    TS("__TS__", "ts", "客户端发生广告点击事件的时间，以毫秒为单位时间戳"),
    SIGN("__SIGN__", "sign", "签名"),
    MAC_MD5("__MAC_MD5__", "mac_md5", "mac地址 MD5"),
    OAID_MD5("__OAID_MD5__", "oaid_md5", "oaid 的 MD5 值"),
    MAC("__MAC__", "mac1", "oaid 的 MD5 值"),
    OAID("__OAID__", "oaid", "原文oaid"),
    DEEPLINK_URL("__DEEPLINK_URL__", "deeplink_url", "deeplink链接"),
    MODEL("__MODEL__", "model", "客户端获取的原始设备信息数据"),
    CAID("__CAID__", "Caid", "deeplink链接"),


    /** 额外补充字段，根据各广告主定，渠道和广告主的宏字段不具有映射关系时，下达监测链接后，广告主根据该字段进行字段赋值 */
    YOUKU_TP_ADV_ID("","tp_adv_id","力天京东提供的渠道标识"),
    YOUKU_ACCESS_ID("","access_id","力天京东提供的渠道标识"),


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

    BaiduParamEnum(String macro, String param, String explain) {
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