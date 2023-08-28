package huihuang.proxy.ocpx.ads.huihuangmingtian;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.channel.baidu.BaiduEventTypeEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiEventTypeEnum;

import java.util.Map;

public enum HuihuangmingtianEventTypeEnum {

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

    HuihuangmingtianEventTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static Map<String, HuaweiEventTypeEnum> huihuangmingtianHuaweiEventTypeMap;

    static {
        huihuangmingtianHuaweiEventTypeMap = CollUtil.newHashMap();
        huihuangmingtianHuaweiEventTypeMap.put(ACTIVATE.code, HuaweiEventTypeEnum.ACTIVE);
        huihuangmingtianHuaweiEventTypeMap.put(NEW_LOGIN.code, null);
        huihuangmingtianHuaweiEventTypeMap.put(DAY1RETENTION.code, HuaweiEventTypeEnum.RETAIN);
        huihuangmingtianHuaweiEventTypeMap.put(ORDER.code, HuaweiEventTypeEnum.PRE_ORDER);
        huihuangmingtianHuaweiEventTypeMap.put(PURCHASE.code, HuaweiEventTypeEnum.FIRST_PURCHASE);
        huihuangmingtianHuaweiEventTypeMap.put(FIRST_WEAK.code, HuaweiEventTypeEnum.RE_ENGAGE);
        huihuangmingtianHuaweiEventTypeMap.put(PAID.code, HuaweiEventTypeEnum.PAID);
    }

    public static Map<String, BaiduEventTypeEnum> huihuangmingtianBaiduEventTypeMap;

    static {
        huihuangmingtianBaiduEventTypeMap = CollUtil.newHashMap();
        huihuangmingtianBaiduEventTypeMap.put(ACTIVATE.code, BaiduEventTypeEnum.ACTIVE);
        huihuangmingtianBaiduEventTypeMap.put(NEW_LOGIN.code, null);
        huihuangmingtianBaiduEventTypeMap.put(DAY1RETENTION.code, BaiduEventTypeEnum.RETAIN_1DAY);
        huihuangmingtianBaiduEventTypeMap.put(ORDER.code, BaiduEventTypeEnum.EC_BUY);
        huihuangmingtianBaiduEventTypeMap.put(PURCHASE.code, null);
        huihuangmingtianBaiduEventTypeMap.put(FIRST_WEAK.code, BaiduEventTypeEnum.FEED_DEEPLINK);
        huihuangmingtianBaiduEventTypeMap.put(PAID.code, BaiduEventTypeEnum.ORDERS);
    }

}
