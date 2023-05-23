package huihuang.proxy.ocpx.ads.fanqie;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.channel.baidu.BaiduEventTypeEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiEventTypeEnum;

import java.util.Map;

/**
 *
 * @Author: xietao
 * @Date: 2023/5/23 17:26
 */
public enum FanqieEventTypeEnum {

    ACTIVE("0", "激活"),
    REGISTER("1", "注册"),
    FIRST_BUY("2", "当日首购-一般指用户的后链路购买/下单等行为"),
    FIRST_WAKEUP("3", "首唤-广告外投deeplink拉端的行为，当日首次唤醒"),
    SECOND_OPEN("4", "次日回访，次日留存"),

    ;

    private String code;
    private String desc;

    FanqieEventTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static Map<String, XiaomiEventTypeEnum> eventTypeMap;

    static {
        eventTypeMap = CollUtil.newHashMap();
        //激活
        eventTypeMap.put(ACTIVE.code, XiaomiEventTypeEnum.APP_ACTIVE);
        //注册
        eventTypeMap.put(REGISTER.code, XiaomiEventTypeEnum.APP_REGISTER);
        //当日首购
        eventTypeMap.put(FIRST_BUY.code, XiaomiEventTypeEnum.APP_FIRST_PAY);
        //首唤
        eventTypeMap.put(FIRST_WAKEUP.code, null);
        //次日回访
        eventTypeMap.put(SECOND_OPEN.code, null);
    }

    public static Map<String, BaiduEventTypeEnum> fanqieBaiduEventTypeMap;

    static {
        fanqieBaiduEventTypeMap = CollUtil.newHashMap();
        //激活
        fanqieBaiduEventTypeMap.put(ACTIVE.code, BaiduEventTypeEnum.ACTIVE);
        //注册
        fanqieBaiduEventTypeMap.put(REGISTER.code, BaiduEventTypeEnum.REGISTER);
        //当日首购
        fanqieBaiduEventTypeMap.put(FIRST_BUY.code, BaiduEventTypeEnum.ORDERS);
        //首唤
        fanqieBaiduEventTypeMap.put(FIRST_WAKEUP.code, null);
        //次日回访
        fanqieBaiduEventTypeMap.put(SECOND_OPEN.code, BaiduEventTypeEnum.RETAIN_1DAY);
    }

}
