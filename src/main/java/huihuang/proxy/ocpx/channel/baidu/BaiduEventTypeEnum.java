package huihuang.proxy.ocpx.channel.baidu;

/**
 * baidu事件类型枚举
 * @Author: xietao
 * @Date: 2023/5/9 20:31
 */
public enum BaiduEventTypeEnum {

    ACTIVE("activate", "激活事件"),
    REGISTER("register", "注册事件"),
    ORDERS("orders", "付费（成单）"),
    RETAIN_1DAY("retain_1day", "次日留存"),
    USER_DEFINED("user_defined", "客户自定义"),
    EC_BUY("ec_buy", "商品下单成功"),
    DEEP_PAGE_ACCESS("deep_page_access", "关键页面浏览"),
    FEED_DEEPLINK("feed_deeplink", "应用调起"),

    ;

    private String code;
    private String desc;

    BaiduEventTypeEnum(String code, String desc) {
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
