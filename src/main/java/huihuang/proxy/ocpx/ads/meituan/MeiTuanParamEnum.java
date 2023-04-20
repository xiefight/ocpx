package huihuang.proxy.ocpx.ads.meituan;

/**
 * @Description: 美团入参描述
 * @Author: xietao
 * @Date: 2023-04-20 14:07
 **/
public enum MeiTuanParamEnum {

    SOURCE("source", "String", 1, "由美团商务提供，一个渠道持有一个美团的source"),
    APP_TYPE("app_type", "String", 1, "设备类型，枚举值，全小写，安卓设备填 android，IOS 设备填 ios，其余设备不填写"),
    MD5_IDFA("md5_idfa", "String", 1, "IOS-idfa 的原生值 md5 值，32 位小写"),
    MD5_IMEI("md5_imei", "String", 1, "Android-imei 的原生值 md5 值，32 位小写"),
    OAID("oaid", "String", 1, "Android-oaid 的原生值"),
    MD5_OAID("md5_oaid", "String", 1, "Android-oaid 的原生值 md5 值，32 位小写"),
    FEEDBACK_URL("feedback_url", "String", 1, "转化回调媒体的 url"),
    ACTION_TIME("action_time", "String", 1, "行为发生时间戳，毫秒，不传默认行为发生时间是上报的时刻"),

    ADGROUP_ID("adgroup_id", "String", 0, "渠道侧广告组 ID"),
    CAMPAIGN_ID("campaign_id", "String", 0, "渠道侧投放计划 id, 用来追踪投放计划转化效果"),
    CAMPAIGN_NAME("campaign_name", "String", 0, "渠道侧广告计划名称"),
    CREATIVE_ID("creative_id", "String", 0, "渠道侧投放创意 id， 用来追踪投放创意转化效果"),
    PLACEMENT_ID("placement_id", "String", 0, "媒体侧的广告位， 用来追踪媒体不同广告位的转化效果"),
    APP_INSTALL("app_install", "String", 0, "安装转化目标 app 的标识， 0: 未安装转化目标 app, 1: 安装了转化目标 app"),

    ;

    private String name;
    private String type;
    private Integer necessary;
    private String remark;

    MeiTuanParamEnum(String name, String type, Integer necessary, String remark) {
        this.name = name;
        this.type = type;
        this.necessary = necessary;
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Integer getNecessary() {
        return necessary;
    }

    public String getRemark() {
        return remark;
    }

}
