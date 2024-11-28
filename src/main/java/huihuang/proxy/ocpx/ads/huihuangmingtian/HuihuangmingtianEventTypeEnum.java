package huihuang.proxy.ocpx.ads.huihuangmingtian;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.channel.baidu.BaiduEventTypeEnum;
import huihuang.proxy.ocpx.channel.honor.HonorEventTypeEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiEventTypeEnum;
import huihuang.proxy.ocpx.channel.iqiyi.IQiyiEventTypeEnum;
import huihuang.proxy.ocpx.channel.oppo.OppoEventTypeEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiEventTypeEnum;

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


    public static Map<String, XiaomiEventTypeEnum> huihuangmingtianXiaomiEventTypeMap;

    static {
        huihuangmingtianXiaomiEventTypeMap = CollUtil.newHashMap();
        huihuangmingtianXiaomiEventTypeMap.put(ACTIVATE.code, XiaomiEventTypeEnum.APP_ACTIVE);
        //自定义新增激活
        huihuangmingtianXiaomiEventTypeMap.put(ACTIVATE.code + "new", XiaomiEventTypeEnum.APP_ACTIVE_NEW);
        huihuangmingtianXiaomiEventTypeMap.put(NEW_LOGIN.code, null);
        huihuangmingtianXiaomiEventTypeMap.put(DAY1RETENTION.code, XiaomiEventTypeEnum.APP_RETENTION);
        huihuangmingtianXiaomiEventTypeMap.put(ORDER.code, XiaomiEventTypeEnum.APP_COMPLETE_ORDER);
        huihuangmingtianXiaomiEventTypeMap.put(PURCHASE.code, null);
        huihuangmingtianXiaomiEventTypeMap.put(FIRST_WEAK.code, null);
        huihuangmingtianXiaomiEventTypeMap.put(PAID.code, XiaomiEventTypeEnum.APP_PAY);
    }

    public static Map<String, IQiyiEventTypeEnum> huihuangmingtianIQiyiEventTypeMap;

    static {
        huihuangmingtianIQiyiEventTypeMap = CollUtil.newHashMap();
        huihuangmingtianIQiyiEventTypeMap.put(ACTIVATE.code, IQiyiEventTypeEnum.ACTIVE);
        huihuangmingtianIQiyiEventTypeMap.put(NEW_LOGIN.code, null);
        huihuangmingtianIQiyiEventTypeMap.put(DAY1RETENTION.code, IQiyiEventTypeEnum.RETENTION);
        huihuangmingtianIQiyiEventTypeMap.put(ORDER.code, null);
        huihuangmingtianIQiyiEventTypeMap.put(PURCHASE.code, null);
        huihuangmingtianIQiyiEventTypeMap.put(FIRST_WEAK.code, null);
        huihuangmingtianIQiyiEventTypeMap.put(PAID.code, IQiyiEventTypeEnum.PAY);
    }


    public static Map<String, OppoEventTypeEnum> huihuangmingtianOppoEventTypeMap;

    static {
        huihuangmingtianOppoEventTypeMap = CollUtil.newHashMap();
        huihuangmingtianOppoEventTypeMap.put(ACTIVATE.code, OppoEventTypeEnum.ACTIVE);
        huihuangmingtianOppoEventTypeMap.put(NEW_LOGIN.code, null);
        huihuangmingtianOppoEventTypeMap.put(DAY1RETENTION.code, OppoEventTypeEnum.RETAIN_2DAY);
        huihuangmingtianOppoEventTypeMap.put(ORDER.code, OppoEventTypeEnum.APPLICATION_ORDER);
        huihuangmingtianOppoEventTypeMap.put(PURCHASE.code, null);
        huihuangmingtianOppoEventTypeMap.put(FIRST_WEAK.code, null);
        huihuangmingtianOppoEventTypeMap.put(PAID.code, OppoEventTypeEnum.DEEP_PAGE_ACCESS);
    }

    public static Map<String, HonorEventTypeEnum> huihuangmingtianHonorEventTypeMap;

    static {
        huihuangmingtianHonorEventTypeMap = CollUtil.newHashMap();
        huihuangmingtianHonorEventTypeMap.put(ACTIVATE.code, HonorEventTypeEnum.ACTIVE);
        huihuangmingtianHonorEventTypeMap.put(NEW_LOGIN.code, null);
        huihuangmingtianHonorEventTypeMap.put(DAY1RETENTION.code, HonorEventTypeEnum.RETAIN);
        huihuangmingtianHonorEventTypeMap.put(ORDER.code, HonorEventTypeEnum.PRE_ORDER);
        huihuangmingtianHonorEventTypeMap.put(PURCHASE.code, HonorEventTypeEnum.FIRST_PURCHASE);
        huihuangmingtianHonorEventTypeMap.put(FIRST_WEAK.code, null);
        huihuangmingtianHonorEventTypeMap.put(PAID.code, HonorEventTypeEnum.PAID);
    }

}
