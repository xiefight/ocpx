package huihuang.proxy.ocpx.channel.honor;

/**
 * @Description:
 * https://contentplatform-drcn.hihonorcdn.com/AdPlatformPortal/ad_portal/1750100655464644608.pdf
 *
 * 只展示了部分事件
 *
 * @Author: xietao
 * @Date: 2024-03-25 13:45
 **/
public enum HonorEventTypeEnum {

    ACTIVE("10001", "激活"),
    REGISTER("10002", "注册"),
    RETAIN("10003", "次留 用户激活后次日内打开 app"),
    PAID("10004", "付费 用户产生付费行为"),
    PRE_ORDER("10007", "下单"),
    FIRST_PURCHASE("10017", "首次购买"),
    RE_ENGAGE("10022", "用户唤醒 打开 app "),
    PAY_FOR_DAY("10025", "当日付费 用户当日产生付费行为 "),

    ;

    private String code;
    private String desc;

    HonorEventTypeEnum(String code, String desc) {
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
