package huihuang.proxy.ocpx.channel.huihuang;

public enum HuihuangChannelParamEnum {
    CHAIN_CODE("__CHAIN_CODE__", "chainCode", "链路编码,正式编码由辉煌明天提供"),
    SOURCE("__SOURCE__", "source", "资源号，由辉煌明天提供"),
    IDFA("__IDFA__", "idfa", "idfa 的原生值"),
    IDFA_MD5("__IDFA_MD5__", "idfaMd5", "iOS 设备广告标识 idfa md5值 小写"),
    IMEI_MD5("IMEI_MD5__", "imeiMd5", "安卓设备广告标识 IMEI md5值 小写"),
    OAID("__OAID__", "oaid", "oaid 的原生值"),
    OAID_MD5("__OAID_MD5__", "oaidMd5", "安卓设备广告标识 oaid md5值 小写"),
    TASKID("__TASKID__", "taskId", "任务 id，由辉煌明天提供"),
    APP("__APP__", "app", "App 号，14 飞猪,9 点淘"),

    IP("__IP__", "ip", "点击 ip"),
    UA("__UA__", "adAgent", "点 击 数 据 上 报 时http的 header 中的user_agent，一次urlencode 编码"),
    TMS("__TMS__", "tms", "点击时间，时间戳，单位毫秒"),

    AID("__AID__", "aid", "广告数据 id"),
    CAMPAIGN_ID("__CAMPAIGN_ID__", "campaignId", "广告数据计划 id"),
    OS("__OS__", "os", "0:android 1:ios"),
    CALLBACK_URL("__CALLBACK_URL__", "callbackUrl", "渠道回调地址"),
    ADVERTISING_SPACE_ID("__ADVERTISING_SPACE_ID__", "advertisingSpaceId", "广告位 id，由辉煌明天提供"),
    CHANNEL("__CHANNEL__", "channel", "渠道标识"),
    ADID("__ADID__", "adid", "渠道标识"),
    UID("__UID__", "uid", "来自优酷 pid，拉新需要，拉活不需要"),
    SID("__SID__", "sid", "可选参数有\n" +
            "DAU（拉活回传）\n" +
            "NU（拉新回传，必须指定 uid）\n" +
            "SED_RU（sedRu 回传，必须指定\n" +
            "uid）\n" +
            "NU_SED_RU（nu&sedRu 同时回\n" +
            "传，需指定 uid"),
    CID("__CID__", "cid", "REMAIN1D（次留深度回传）\n" +
            "PAY1D（当日付费）\n" +
            "PAY1D_REMAIN1D（次留&付费都回传）"),
    EVENT_TYPE("__EVENT_TYPE__", "eventType", "目标 1-拉活 2-拉新"),


    ACCOUNT_ID("__ACCOUNT_ID__", "account_id", ""),

    MONITOR_TYPE("", "monitorType", "监测类型 0：曝光  1：点击 默认点击"),

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

    HuihuangChannelParamEnum(String macro, String param, String explain) {
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
