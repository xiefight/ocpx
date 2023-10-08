package huihuang.proxy.ocpx.ads.quannenghudong;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.channel.huawei.HuaweiEventTypeEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiEventTypeEnum;

import java.util.Map;

public enum QuannengHudongEventTypeEnum {

    ACTIVATE("activate", "激活"),
    REGISTER("register", "注册"),
    DAY1RETENTION("day1retention", "次留"),
    PURCHASE("purchase", "购买"),

    ;

    private String code;
    private String desc;

    QuannengHudongEventTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static Map<String, HuaweiEventTypeEnum> quannengHudongHuaweiEventTypeMap;

    static {
        quannengHudongHuaweiEventTypeMap = CollUtil.newHashMap();
        //激活
        quannengHudongHuaweiEventTypeMap.put(ACTIVATE.code, HuaweiEventTypeEnum.ACTIVE);
        //注册
        quannengHudongHuaweiEventTypeMap.put(REGISTER.code, HuaweiEventTypeEnum.REGISTER);
        //次留
        quannengHudongHuaweiEventTypeMap.put(DAY1RETENTION.code, HuaweiEventTypeEnum.RETAIN);
        //购买
        quannengHudongHuaweiEventTypeMap.put(PURCHASE.code, HuaweiEventTypeEnum.PAID);
    }


    public static Map<String, XiaomiEventTypeEnum> quannengHudongXiaomiEventTypeMap;

    static {
        quannengHudongXiaomiEventTypeMap = CollUtil.newHashMap();
        //激活
        quannengHudongXiaomiEventTypeMap.put(ACTIVATE.code, XiaomiEventTypeEnum.APP_ACTIVE);
        //自定义新增激活
        quannengHudongXiaomiEventTypeMap.put(ACTIVATE.code + "new", XiaomiEventTypeEnum.APP_ACTIVE_NEW);
        //注册
        quannengHudongXiaomiEventTypeMap.put(REGISTER.code, XiaomiEventTypeEnum.APP_REGISTER);
        //次留
        quannengHudongXiaomiEventTypeMap.put(DAY1RETENTION.code, XiaomiEventTypeEnum.APP_RETENTION);
        //购买
        quannengHudongXiaomiEventTypeMap.put(PURCHASE.code, XiaomiEventTypeEnum.APP_PURCHASE);
    }

}
