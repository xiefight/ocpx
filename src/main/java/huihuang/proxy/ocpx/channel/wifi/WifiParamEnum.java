package huihuang.proxy.ocpx.channel.wifi;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-24 16:57
 **/
public enum WifiParamEnum {

    AID("__AID__", "aid", "广告主 ID"),
    CID("__CID__", "cid", "广告创意 ID"),
    CGID("__CGID__", "cgid", "广告创意组 ID"),
    UID("__UID__", "uid", "广告单元 ID"),
    PID("__PID__", "pid", "广告计划 ID"),
    SID("__SID__", "sid", "广告检索 ID"),
    STIME("__STIME__", "stime", "广告检索时间"),
    OS("__OS__", "os", "0 代表 Android，1 代表 iOS"),
    IDFA("__IDFA__", "idfa", "iOS 设备的 IDFA 的Md5 值"),
    MAC("__MAC__", "mac", "设备 MAC 的 Md5 值"),
    IMEI("__IMEI__", "imei", "Android 设备的 imei的 Md5 值"),
    //    无("_无_", "response_validate", "是否检测返回值（默认为 true）"),
    OAID("__OAID__", "oaid", "设备 oaid 值"),
    ANDROID_ID("__ANDROID_ID__", "android_id", "Android 设备的android id 的 Md5 值"),
    HASH_OAID("__HASH_OAID__", "hash_oaid", "oaid 的 MD5 值"),
    PLAIN_IDFA("__PLAIN_IDFA__", "plain_idfa", "iOS 设备的 IDFA，明文。仅限__S2S__时使用"),
    PLAIN_MAC("__PLAIN_MAC__", "plain_mac", "设备 MAC，明文。仅限__S2S__时使用"),
    PLAIN_IMEI("__PLAIN_IMEI__", "plain_imei", "Android 设备的 imei，明文。仅限__S2S__时使用"),
    S2S("__S2S__", "s2s", "仅仅是个标记，存在即服务端转发"),


    /** 额外补充字段，根据各广告主定，渠道和广告主的宏字段不具有映射关系时，下达监测链接后，广告主根据该字段进行字段赋值 */
//    XIGUA_TP_ADV_ID("","tp_adv_id","力天京东提供的渠道标识"),
    XIGUA_ACCESS_ID("","access_id","力天京东提供的渠道标识"),


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

    WifiParamEnum(String macro, String param, String explain) {
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
