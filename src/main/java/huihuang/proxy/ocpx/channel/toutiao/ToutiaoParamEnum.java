package huihuang.proxy.ocpx.channel.toutiao;

/**
 * @Description: 头条宏字段
 * 此处是全量字段，有些使用量不大的字段，只进行记录，无释义，查看详情请跳转
 * https://open.oceanengine.com/labels/7/docs/1696710655781900
 * @Author: xietao
 * @Date: 2023-04-20 15:11
 **/
public enum ToutiaoParamEnum {

    AID("_AID_", "aid", "广告计划id", 0),
    AID_NAME("_AID_NAME_", "aid_name", "广告计划名称", 0),
    CID("_CID_", "cid", "广告创意 id，长整型", 0),
    CID_NAME("_CID_NAME_", "cid_name", "广告创意名称", 0),
    CAMPAIGN_ID("_CAMPAIGN_ID_", "campaign_id", "广告组 id", 0),
    CAMPAIGN_NAME("_CAMPAIGN_NAME_", "campaign_name", "广告组名称", 0),
    CTYPE("_CTYPE_", "ctype", "创意样式", 0),
    ADVERTISER_ID("_ADVERTISER_ID_", "advertiser_id", "广告主id", 2),
    CSITE("_CSITE_", "csite", "广告投放位置", 0),
    CONVERT_ID("_CONVERT_ID_", "convert_id", "转化id", 0),
    REQUEST_ID("_REQUEST_ID_", "request_id", "请求下发的id", 0),
    TRACK_ID("_TRACK_ID_", "track_id", "请求下发的id&创意id的md5,16位", 0),
    SL("_SL_", "sl", "这次请求的语言 zh", 0),
    IMEI("_IMEI_", "imei", "安卓的设备 ID 的 md5 摘要，32位", 0),
    IDFA("_IDFA_", "idfa", "IOS 6+的设备id字段，32位", 0),
    IDFA_MD5("_IDFA_MD5_", "idfa_md5", "IOS 6+的设备id的md5摘要，32位", 0),
    ANDROIDID("_ANDROIDID_", "androidid", "安卓id原值的md5，32位", 0),
    OAID("_OAID_", "oaid", "Android Q及更高版本的设备号，32位", 0),
    OAID_MD5("_OAID_MD5_", "oaid_md5", "安卓的设备 ID 的 md5 摘要，32位", 2),
    OS("_OS_", "os", "操作系统平台 安卓：0 IOS：1 其他：3", 0),
    MAC("_MAC_", "mac", "移动设备mac地址", 0),
    MAC1("_MAC1_", "mac1", "移动设备 mac 地址", 0),
    IPV4("_IPV4_", "ipv4", "上报请求的对端 IP 地址", 0),
    IPV6("_IPV6_", "ipv6", "上报请求的对端 IP 地址", 0),
    IP("_IP_", "ip", "", 0),
    UA("_UA_", "ua", "用户代理", 0),
    GEO("_GEO_", "geo", "位置信息", 0),
    TS("_TS_", "ts", "客户端发生广告点击事件的时间，以毫秒为单位时间戳", 0),
    CALLBACK_PARAM("_CALLBACK_PARAM_", "callback_param", "一些跟广告信息相关的回调参数，内容是一个加密字符串，在调用事件回传接口的时候会用到", 0),
    CALLBACK_URL("_CALLBACK_URL_", "callback_url", "直接把调用事件回传接口的url生成出来，广告主可以直接使用", 0),
    MODEL("_MODEL_", "model", "手机型号", 0),
    UNION_SITE("_UNION_SITE_", "union_site", "对外广告位编码", 0),

    CAID("_CAID_", "caid", "", 2),
    CAID1("_CAID1_", "caid1", "", 2),
    CAID2("_CAID2_", "caid2", "", 2),
    CAID1_MD5("_CAID1_MD5_", "caid1_md5", "", 2),
    CAID2_MD5("_CAID2_MD5_", "caid2_md5", "", 2),

    PROMOTION_ID("_PROMOTION_ID_", "promotion_id", "巨量广告体验版的广告ID", 0),
    PROJECT_ID("_PROJECT_ID_", "project_id", "巨量广告体验版的项目ID", 0),
    PROMOTION_NAME("_PROMOTION_NAME_", "promotion_name", "巨量广告体验版中的广告名称", 0),
    PROJECT_NAME("_PROJECT_NAME_", "project_name", "巨量广告体验版中的项目名称", 0),
    MID1("_MID1_", "mid1", "图片素材宏参数（下发原始素材id）", 0),
    MID2("_MID2_", "mid2", "标题素材宏参数", 0),
    MID3("_MID3_", "mid3", "视频素材宏参数", 0),
    MID4("_MID4_", "mid4", "搭配试玩素材宏参数", 0),
    MID5("_MID5_", "mid5", "落地页素材宏参数", 0),
    MID6("_MID6_", "mid6", "安卓下载详情页素材宏参数", 0),


    /** 额外补充字段，根据各广告主定 */
    MEITUAN_SOURCE("","source","美团商务提供的渠道字段",0);

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
    /**
     * 支持监测行为 0：全支持 1：展示 2：有效触点
     */
    private Integer action;

    ToutiaoParamEnum(String macro, String param, String explain, Integer action) {
        this.macro = macro;
        this.param = param;
        this.explain = explain;
        this.action = action;
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

    public Integer getAction() {
        return action;
    }
}
