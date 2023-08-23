package huihuang.proxy.ocpx.channel.huawei;

/** 
 * 
 * @Author: xietao
 * @Date: 2023/5/14 11:29
 */ 
public enum HuaweiParamEnum {

    CONTENT_ID("{content_id}","content_id","素材id"),
    ADGROUP_ID("{adgroup_id}", "adgroup_id", "任务id"),
    CAMPAIGN_ID("{campaign_id}", "campaign_id", "计划id"),
    OAID("{oaid}", "oaid", "广告单元 ID"),
    TRACKING_ENABLED("{tracking_enabled}", "tracking_enabled", "0：不允许跟踪,1：允许跟踪"),
    IP("{ip}", "ip", "IP地址：客户端IP地址"),
    USER_AGENT("{user_agent}", "user_agent", "用户代理"),
    EVENT_TYPE("{event_type}", "event_type", "事件类型，取值为imp、click"),
    TRACE_TIME("{trace_time}", "trace_time", "客户端发生广告点击事件的时间，以毫秒为单位时间戳"),
    CALLBACK("{callback}", "callback", "回调参数，需要在回传的转化行为数据中携带 "),
    DEEP_LINK("{deep_link}", "deep_link", "deeplink链接"),
    OS_VERSION("{os_version}", "os_version", "操作系统版本"),
    DEVICE_ID("{device_id}", "device_id", "设备id的md5值"),
    ID_TYPE("{id_type}", "id_type", "设备类型,支持0,对应IMEI号（仅支持非华为手机且采集不到oaid的场景）"),

    /** 额外补充字段，根据各广告主定，渠道和广告主的宏字段不具有映射关系时，下达监测链接后，广告主根据该字段进行字段赋值 */
    YOUKU_TP_ADV_ID("","tp_adv_id","力天京东提供的渠道标识"),
    YOUKU_ACCESS_ID("","access_id","力天京东提供的渠道标识"),

    KUAISHOU_ADID("","adid","快手投放渠道标识"),
    ACCOUNT_ID("","account_id","我们自定义的华为渠道标识，体现在监测链接中"),

    HUIHUANG_CHAIN_CODE("","chainCode","辉煌标识"),

    TUHU_AID("","aid","途虎投放渠道标识"),
    TUHU_SID("","sid","途虎投放渠道标识"),
    TUHU_CONV_EXT("","conv_ext","途虎投放渠道标识"),

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

    HuaweiParamEnum(String macro, String param, String explain) {
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
