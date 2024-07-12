package huihuang.proxy.ocpx.channel.guangdiantong;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-05-07 15:06
 **/
public enum GuangdiantongEventTypeEnum {

    ACTIVE("ACTIVATE_APP", "active 激活"),
    REGISTER("REGISTER", "register 注册"),
    PURCHASE("PURCHASE", "pay 首次付费"),
    START_APP("START_APP", "次日留存 length_of_stay=1"),
    VIEW_CONTENT("VIEW_CONTENT", "关键页面访问"),

    //其他的事件未定义
    ;

    private String code;
    private String desc;

    GuangdiantongEventTypeEnum(String code, String desc) {
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
