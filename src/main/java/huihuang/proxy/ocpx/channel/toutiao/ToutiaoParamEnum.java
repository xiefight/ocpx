package huihuang.proxy.ocpx.channel.toutiao;

/**
 * @Description: 头条宏字段
 * 此处是全量字段，有些使用量不大的字段，只进行记录，无释义，查看详情请跳转
 * https://open.oceanengine.com/labels/7/docs/1696710655781900
 * @Author: xietao
 * @Date: 2023-04-20 15:11
 **/
public enum ToutiaoParamEnum {

    AID("AID", "aid", "广告计划id", 0),
    AID_NAME("AID_NAME", "aid_name", "广告计划名称", 0),
    CID("CID", "cid", "广告创意 id，长整型", 0),
    CID_NAME("CID_NAME", "cid_name", "广告创意名称", 0),
    CAMPAIGN_ID("CAMPAIGN_ID", "campaign_id", "广告组 id", 0),
    CAMPAIGN_NAME("CAMPAIGN_NAME", "campaign_name", "广告组名称", 0),
    CTYPE("CTYPE", "ctype", "创意样式", 0),
    ADVERTISER_ID("ADVERTISER_ID", "advertiser_id", "广告主id", 2),
    CSITE("CSITE", "csite", "广告投放位置", 0),
    CONVERT_ID("CONVERT_ID", "convert_id", "转化id", 0),
    REQUEST_ID("REQUEST_ID", "request_id", "请求下发的id", 0),
    TRACK_ID("TRACK_ID", "track_id", "请求下发的id&创意id的md5,16位", 0),
    SL("SL", "sl", "这次请求的语言 zh", 0),
    IMEI("IMEI", "imei", "安卓的设备 ID 的 md5 摘要，32位", 0),
    IDFA("IDFA", "idfa", "IOS 6+的设备id字段，32位", 0),
    IDFA_MD5("IDFA_MD5", "idfa_md5", "IOS 6+的设备id的md5摘要，32位", 0),
    ANDROIDID("ANDROIDID", "androidid", "安卓id原值的md5，32位", 0),
    OAID("OAID", "oaid", "Android Q及更高版本的设备号，32位", 0),
    OAID_MD5("OAID_MD5", "oaid_md5", "安卓的设备 ID 的 md5 摘要，32位", 2),
    OS("OS", "os", "操作系统平台 安卓：0 IOS：1 其他：3", 0),
    MAC("MAC", "mac", "移动设备mac地址", 0),
    MAC1("MAC1", "mac1", "移动设备 mac 地址", 0),
    IPV4("IPV4", "ipv4", "上报请求的对端 IP 地址", 0),
    IPV6("IPV6", "ipv6", "上报请求的对端 IP 地址", 0),
    IP("IP", "ip", "", 0),
    UA("UA", "ua", "用户代理", 0),
    GEO("GEO", "geo", "位置信息", 0),
    TS("TS", "ts", "客户端发生广告点击事件的时间，以毫秒为单位时间戳", 0),
    CALLBACK_PARAM("CALLBACK_PARAM", "callback_param", "一些跟广告信息相关的回调参数，内容是一个加密字符串，在调用事件回传接口的时候会用到", 0),
    CALLBACK_URL("CALLBACK_URL", "callback_url", "直接把调用事件回传接口的url生成出来，广告主可以直接使用", 0),
    MODEL("MODEL", "model", "手机型号", 0),
    UNION_SITE("UNION_SITE", "union_site", "对外广告位编码", 0),

    CAID("CAID", "caid", "", 2),
    CAID1("CAID1", "caid1", "", 2),
    CAID2("CAID2", "caid2", "", 2),
    CAID1_MD5("CAID1_MD5", "caid1_md5", "", 2),
    CAID2_MD5("CAID2_MD5", "caid2_md5", "", 2),

    PROMOTION_ID("PROMOTION_ID", "promotion_id", "巨量广告体验版的广告ID", 0),
    PROJECT_ID("PROJECT_ID", "project_id", "巨量广告体验版的项目ID", 0),
    PROMOTION_NAME("PROMOTION_NAME", "promotion_name", "巨量广告体验版中的广告名称", 0),
    PROJECT_NAME("PROJECT_NAME", "project_name", "巨量广告体验版中的项目名称", 0),
    MID1("MID1", "mid1", "图片素材宏参数（下发原始素材id）", 0),
    MID2("MID2", "mid2", "标题素材宏参数", 0),
    MID3("MID3", "mid3", "视频素材宏参数", 0),
    MID4("MID4", "mid4", "搭配试玩素材宏参数", 0),
    MID5("MID5", "mid5", "落地页素材宏参数", 0),
    MID6("MID6", "mid6", "安卓下载详情页素材宏参数", 0),


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
