package huihuang.proxy.ocpx.channel.oppo;

/**
 *
 * @Author: xietao
 * @Date: 2023/6/4 15:12
 */
public enum OppoEventTypeEnum {

    ACTIVE(1, "激活"),
    REGISTER(2, "注册"),
    GAME_PAY(3, "游戏付费"),
    RETAIN_2DAY(4, "次日留存"),
    APPLICATION_CREDIT(5, "应用内授信"),
    APPLICATION_ORDER(6, "应用内下单（电商）"),
    DEEP_PAGE_ACCESS(7, "应用付费"),
    USER_DEFINED(8, "客户自定义"),
    RETAIN_3DAY(9, "第 3 日留存"),
    RETAIN_4DAY(10, "第 4 日留存"),
    RETAIN_5DAY(11, "第 5 日留存"),
    RETAIN_6DAY(12, "第 6 日留存"),
    RETAIN_7DAY(13, "第 7 日留存"),
    RETAIN_8DAY(14, "第 8 日留存"),
    FEED_DEEPLINK(15, "拉活"),
    QUICK_APP_PAY(16, "快应用付费"),
    RETAIN_2DAY_DP(17, "次留（DP）"),
    UNLOAD_ACTIVE(18, "卸载激活"),
    QUICK_APP_KEY_BEHAVIOR(19, "快应用关键行为"),
    APPLICATION_KEY_BEHAVIOR(20, "游戏/应用关键行为"),
    KEEP_RETAIN(21, "长留高质量人群（废弃）"),
    APP_PAY_COUNT(22, "应用付费次数"),
    KEEP_RETAIN_ANYWAY(23, "长留自定义"),

    ;

    private Integer code;
    private String desc;

    OppoEventTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
