package huihuang.proxy.ocpx.channel.huihuang;

public enum HuihuangChannelEventTypeEnum {

    ACTIVATE("1", "激活"),
    NEW_LOGIN("2", "新登"),
    DAY1RETENTION("7", "次日回访"),
    ORDER("10", "下单"),
    PURCHASE("11", "购买"),
    FIRST_WEAK("12", "首唤"),
    PAID("20", "付费"),


    ;

    private String code;
    private String desc;

    HuihuangChannelEventTypeEnum(String code, String desc) {
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
