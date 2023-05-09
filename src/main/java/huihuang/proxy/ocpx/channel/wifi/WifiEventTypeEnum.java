package huihuang.proxy.ocpx.channel.wifi;

/**
 * wifi万能钥匙事件类型枚举
 * @Author: xietao
 * @Date: 2023/5/9 20:31
 */
public enum WifiEventTypeEnum {

    APP_ACTIVE("1", "激活事件"),
    APP_REGISTER("2", "注册事件"),
    APP_PAY("3", "付费事件"),
    APP_RETENTION("5", "次留"),
    APP_ADDICTION("6", "关键行为"),
    EIGHTING_ACTIVE("7", "18 天拉活"),
    THIRTY_ACTIVE("8", "30 天拉活"),

    ;

    private String code;
    private String desc;

    WifiEventTypeEnum(String code, String desc) {
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
