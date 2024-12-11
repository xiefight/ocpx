package huihuang.proxy.ocpx.channel.honor;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-03-25 13:38
 **/
public enum HonorParamEnum {


    ADVERTISER_ID("__ADVERTISER_ID__","advertiser_id","荣耀侧的广告主id，用于广告主回传转化事件时做归因用(需要回传转化给荣耀侧时必填)"),
    TRACK_ID("__TRACK_ID__","track_id","追踪id，用于广告主回传转化事件时做归因用(需要回传转化给荣耀侧时必填)"),
    TIME("__TIME__","trace_time","事件发生的时间(端侧替换，毫秒时间戳) (需要回传转化给荣耀侧时必填)"),

    IP("__IP__", "ip", "IP地址：客户端IP地址"),
    OS("__OS__", "os", "0表示Android，1表示iOS"),
    UA("__UA__", "user_agent", "用户代理"),
    OAID("__OAID__", "oaid", "用户OAID原始值"),
    REQUESTID("__REQUESTID__", "request_id", "用户OAID原始值"),
    GROUPID("__GROUPID__", "group_id", "广告分组id"),
    CAMPAIGNID("__CAMPAIGNID__", "campaign_id", "广告计划id"),
    CREATIVE_ID("__CREATIVE_ID__", "creative_id", "广告计划id"),

    ACCOUNT_ID("","account_id","我们自定义的渠道标识，体现在监测链接中"),


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


    HUIHUIYOUDAO_AID("","aid","闲鱼投放渠道标识"),
    HUIHUIYOUDAO_SID("","sid","闲鱼投放渠道标识"),
    HUIHUIYOUDAO_CONV_EXT("","conv_ext","闲鱼投放渠道标识"),

    QUANNENGHUDONG_PID("","pid","全能互动广告商标识"),
    QUANNENGHUDONG_UNIQUEID("","uniqueid","全能互动请求唯一id"),

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

    HonorParamEnum(String macro, String param, String explain) {
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
