package huihuang.proxy.ocpx.ads.quannenghudong;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.channel.huawei.HuaweiEventTypeEnum;

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

}
