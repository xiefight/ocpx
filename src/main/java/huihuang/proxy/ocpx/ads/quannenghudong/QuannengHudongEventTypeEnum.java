package huihuang.proxy.ocpx.ads.quannenghudong;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.channel.baidu.BaiduEventTypeEnum;
import huihuang.proxy.ocpx.channel.honor.HonorEventTypeEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiEventTypeEnum;
import huihuang.proxy.ocpx.channel.iqiyi.IQiyiEventTypeEnum;
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


    public static Map<String, BaiduEventTypeEnum> quannengHudongBaiduEventTypeMap;

    static {
        quannengHudongBaiduEventTypeMap = CollUtil.newHashMap();
        //激活
        quannengHudongBaiduEventTypeMap.put(ACTIVATE.code, BaiduEventTypeEnum.ACTIVE);
        //注册
        quannengHudongBaiduEventTypeMap.put(REGISTER.code, BaiduEventTypeEnum.REGISTER);
        //次留
        quannengHudongBaiduEventTypeMap.put(DAY1RETENTION.code, BaiduEventTypeEnum.RETAIN_1DAY);
        //购买
        quannengHudongBaiduEventTypeMap.put(PURCHASE.code, BaiduEventTypeEnum.ORDERS);
    }

    public static Map<String, IQiyiEventTypeEnum> quannengHudongIQiyiEventTypeMap;

    static {
        quannengHudongIQiyiEventTypeMap = CollUtil.newHashMap();
        //激活
        quannengHudongIQiyiEventTypeMap.put(ACTIVATE.code, IQiyiEventTypeEnum.ACTIVE);
        //注册
        quannengHudongIQiyiEventTypeMap.put(REGISTER.code, IQiyiEventTypeEnum.REGISTER);
        //次留
        quannengHudongIQiyiEventTypeMap.put(DAY1RETENTION.code, IQiyiEventTypeEnum.RETENTION);
        //购买
        quannengHudongIQiyiEventTypeMap.put(PURCHASE.code, IQiyiEventTypeEnum.PAY);
    }

    public static Map<String, HonorEventTypeEnum> quannengHudongHonorEventTypeMap;

    static {
        quannengHudongHonorEventTypeMap = CollUtil.newHashMap();
        //激活
        quannengHudongHonorEventTypeMap.put(ACTIVATE.code, HonorEventTypeEnum.ACTIVE);
        //注册
        quannengHudongHonorEventTypeMap.put(REGISTER.code, HonorEventTypeEnum.REGISTER);
        //次留
        quannengHudongHonorEventTypeMap.put(DAY1RETENTION.code, HonorEventTypeEnum.RETAIN);
        //购买
        quannengHudongHonorEventTypeMap.put(PURCHASE.code, HonorEventTypeEnum.FIRST_PURCHASE);
    }

}
