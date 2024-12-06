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
    CAID("__CAID__", "Caid", "设备标识"),

    ACCOUNT_ID("","account_id","我们自定义的baidu渠道标识，体现在监测链接中"),


    /** 额外补充字段，根据各广告主定，渠道和广告主的宏字段不具有映射关系时，下达监测链接后，广告主根据该字段进行字段赋值 */
    YOUKU_TP_ADV_ID("","tp_adv_id","力天京东提供的渠道标识"),
    YOUKU_ACCESS_ID("","access_id","力天京东提供的渠道标识"),

    KUAISHOU_ADID("","adid","快手投放渠道标识"),

    XIANYU_AID("","aid","闲鱼投放渠道标识"),
    XIANYU_SID("","sid","闲鱼投放渠道标识"),
    XIANYU_CONV_EXT("","conv_ext","闲鱼投放渠道标识"),


    HUIHUANG_CHAIN_CODE("","chainCode","辉煌标识"),
    HUIHUANG_TASK_ID("","taskId","由辉煌明天提供任务 id"),
    HUIHUANG_APP("","app","辉煌明天提供App 号，14 飞猪,9 点淘"),
    HUIHUANG_SOURCE("","source",""),
    HUIHUANG_ADVERTISING_SPACE_ID("","advertisingSpaceId",""),
    HUIHUANG_AID("","aid","辉煌明天提供广告数据 id "),
    HUIHUANG_ADID("","adid",""),
    HUIHUANG_CHANNEL("","channel",""),


    QUANNENGHUDONG_PID("","pid","全能互动广告商标识"),
    QUANNENGHUDONG_UNIQUEID("","uniqueid","全能互动请求唯一id"),

    DINGYUN_ADID("","adid","丁耘adid"),

    KEEP_APPID("","appid","keep appid"),
    KEEP_CHANNEL("","channel","keep channel"),

    NINGZHI_YYQ("","yyq","宁致的yyq"),

    MONITOR_TYPE("","monitorType","监测类型 0：曝光  1：点击 默认点击"),

    JIYUE_TYPE("","type","jiyue家的产品type"),
    JIYUE_TOKEN("","token","jiyue家的产品token"),

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
