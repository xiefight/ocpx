package huihuang.proxy.ocpx.channel.xiaomi;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-24 16:57
 **/
public enum XiaomiParamEnum {

    IMEI("_IMEI_", "imei", "用户设备 IMEI 进行 MD5 加密"),
    IMEI2("_IMEI2_", "imei2", "同 imei。如果选择了该宏，则必须同时选择MEID"),
    MEID("_MEID_", "meid", "同 imei。如果选择了该宏，则必须同时选择IMEI2"),
    OAID("_OAID_", "oaid", "oaid 与imei 可以都设置，但每个点击仅会回传其中一种。即如果回传了 oaid，则不会有imei/imei2/meid 参数；如果回传了imei/imei2/meid，则不会回传 oaid 参数"),
    TS("_TS_", "click_time", "广告点击发生时间-毫秒"),
    APPID("_APPID_", "app_id", "小米渠道投放的渠道包的 id"),
    ADID("_ADID_", "adid", "广告创意 id"),
    CAMPAIGNID("_CAMPAIGNID_", "campaign_id", "广告计划 id"),
    CUSTOMERID("_CUSTOMERID_", "customer_id", "账户id"),
    CALLBACK("_CALLBACK_", "callback", "激活回调参数"),
    SIGN("_SIGN_", "sign", "签名-使用替换后的 url + secret key 进行 md5 后生成，该参数请放在 feedback url 最后一个位置"),
    //    无("_无_", "response_validate", "是否检测返回值（默认为 true）"),
    EXPID("_EXPID_", "expId", "实验 id"),
    IP("_IP_", "ip", "IP"),
    UA("_UA_", "ua", "Useragent 用户代理"),
    ANDROIDID("_ANDROIDID_", "androidId", "安卓 id"),
    ADNAME("_ADNAME_", "adName", "广告创意名称"),


    /** 额外补充字段，根据各广告主定 */
//    TLJD_TP_ADV_ID("","tp_adv_id","天力京东提供的渠道标识"),
//    TLJD_ACCESS_ID("","access_id","天力京东提供的渠道标识"),


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

    XiaomiParamEnum(String macro, String param, String explain) {
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
