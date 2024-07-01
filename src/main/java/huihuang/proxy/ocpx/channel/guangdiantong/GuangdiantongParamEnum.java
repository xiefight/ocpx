package huihuang.proxy.ocpx.channel.guangdiantong;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-05-07 15:27
 **/
public enum GuangdiantongParamEnum {

    //对IMEI设备号转成小写，再进行md5编码，再小写，32位
    //对IDFA 设备号保持大写，进行 md5 编码，再小写，32位
    //d4b8f3898515056278ccf78a7a2cca2d
    MUID("__MUID__", "muid", "设备id（imei或idfa的加密值）"),
    HASH_ANDROID_ID("__HASH_ANDROID_ID__", "hash_android_id", "安卓id做md5加密后小写"),
    IP("__IP__", "ip", "用户终端的公共IPV4地址"),
    USER_AGENT("__USER_AGENT__", "user_agent", "用户代理（user_agent）"),
    //示例为url encode编码原值，广告主需要decode作为post请求url回传至AMS
    //http%3A%2F%2Ftracking.e.qq.com%2Fconv%3Fcb%3DxXx%252BxXx%253D%26conv_id%3D123
    CALLBACK("__CALLBACK__", "callback", "直接提供上报信息回传接口的 url"),
    HASH_OAID("__HASH_OAID__", "hash_oaid", "oaid取原值后做md5加密"),

    CLICK_ID("__CLICK_ID__", "click_id", "点击id"),
    CLICK_TIME("__CLICK_TIME__", "click_time", "点击时间 秒"),
    IMPRESSION_TIME("__IMPRESSION_TIME__", "impression_time", "点击时间 秒"),
    CAMPAIGN_ID("__CAMPAIGN_ID__", "campaign_id", "计划id"),
    ADGROUP_ID("__ADGROUP_ID__", "adgroup_id", "广告组id（实际为广告id）"),
    AD_ID("__AD_ID__", "ad_id", "广告id（实际为创意id）"),
    ACCOUNT_ID("__ACCOUNT_ID__", "account_id", "广告主id"),
    REQUEST_ID("__REQUEST_ID__", "request_id", "请求id"),
    IMPRESSION_ID("__IMPRESSION_ID__", "impression_id", "曝光id"),
    DEVICE_OS_TYPE("__DEVICE_OS_TYPE__", "device_os_type", "设备类型 ios, android"),

    ACCOUNT_ID_OWN("","account_id_own","我们自定义的标识，体现在监测链接中"),

    HUIHUIYOUDAO_AID("","aid","闲鱼投放渠道标识"),
    HUIHUIYOUDAO_SID("","sid","闲鱼投放渠道标识"),
    HUIHUIYOUDAO_CONV_EXT("","conv_ext","闲鱼投放渠道标识"),

    KUAISHOU_ADID("", "adid", "快手投放渠道标识"),


    HUIHUANG_CHAIN_CODE("","chainCode","辉煌标识"),
    HUIHUANG_TASK_ID("","taskId","由辉煌明天提供任务 id"),
    HUIHUANG_APP("","app","辉煌明天提供App 号，14 飞猪,9 点淘"),
    HUIHUANG_SOURCE("","source",""),
    HUIHUANG_ADVERTISING_SPACE_ID("","advertisingSpaceId",""),
    HUIHUANG_AID("","aid",""),
    HUIHUANG_CHANNEL("","channel",""),
    HUIHUANG_ADID("","adid",""),
    HUIHUANG_UID("","uid",""),
    HUIHUANG_CID("","cid",""),
    HUIHUANG_SID("","sid",""),
    HUIHUANG_EVENT_TYPE("","eventType",""),

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

    GuangdiantongParamEnum(String macro, String param, String explain) {
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
