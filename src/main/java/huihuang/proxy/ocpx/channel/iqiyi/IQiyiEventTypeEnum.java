package huihuang.proxy.ocpx.channel.iqiyi;

/**
 * @Author: xietao
 * @Date: 2023/6/29 21:40
 */
public enum IQiyiEventTypeEnum {

    ACTIVE(0, "active 激活"),
    REGISTER(1, "register 注册"),
    PAY(2, "pay 首次付费"),
    RETENTION(3, "Retention 次日留存"),

    //其他的事件未定义
    ;

    private Integer code;
    private String desc;

    IQiyiEventTypeEnum(Integer code, String desc) {
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
