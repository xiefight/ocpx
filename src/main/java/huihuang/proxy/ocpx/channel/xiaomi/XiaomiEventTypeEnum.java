package huihuang.proxy.ocpx.channel.xiaomi;

/**
 * @Description: xiaomi事件类型枚举
 * https://api.e.mi.com/doc.html#/1.0.0-mdtag9b26f-omd/document-2bd1c4c260259b072818205a8ae20139
 * @Author: xietao
 * @Date: 2023-04-24 10:03
 **/
public enum XiaomiEventTypeEnum {

    APP_ACTIVE("APP_ACTIVE", "自定义激活"),
    APP_REGISTER("APP_REGISTER", "自定义注册"),
    APP_ACTIVE_NEW("APP_ACTIVE_NEW", "自定义新增激活"),
    APP_RE_ACTIVE("APP_RE_ACTIVE", "首次拉活"),
    APP_PAY("APP_PAY", "付费"),
    APP_FIRST_PAY("APP_FIRST_PAY", "首次付费"),
    APP_RETENTION("APP_RETENTION", "留存"),
    APP_PURCHASE("APP_PURCHASE", "购买"),
    APP_UNINSTALL("APP_UNINSTALL", "召回"),
    APP_RE_ACTIVE_WAKE_UP("APP_RE_ACTIVE_WAKE_UP", "拉活"),
    APP_NEW_USER_PURCHASE("APP_NEW_USER_PURCHASE", "首单购买"),

    APP_IPVUV("APP_IPVUV", "IPVUV"),
    APP_ADDICTION("APP_ADDICTION", "关键行为"),
    APP_VIEW_CONTENT("APP_VIEW_CONTENT", "商详页浏览"),

    ADD_CART("ADD_CART", "加购"),
    APP_COMPLETE_ORDER("APP_COMPLETE_ORDER", "下单"),
    APP_CREDIT("APP_CREDIT", "授信"),
    APP_SUBMIT("APP_SUBMIT", "完件"),
    APP_PAY_AMOUNT("APP_PAY_AMOUNT", "付费金额"),
    APP_ADD_DESKTOP("APP_ADD_DESKTOP", "加桌（仅快应用投放使用）"),
    APP_START_OPEN_ACCOUNT("APP_START_OPEN_ACCOUNT", "开户"),
    APP_OPEN_MIDDLE_PAGE("APP_OPEN_MIDDLE_PAGE", "打开中间页"),

    APP_OPEN_SHOP_PAGE("APP_OPEN_SHOP_PAGE", "打开店铺页"),
    APP_SHOP_ORDER("APP_SHOP_ORDER", "店铺下单"),
    APP_INQUIRE("APP_INQUIRE", "询价"),

    ;

    private String code;
    private String desc;

    XiaomiEventTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
