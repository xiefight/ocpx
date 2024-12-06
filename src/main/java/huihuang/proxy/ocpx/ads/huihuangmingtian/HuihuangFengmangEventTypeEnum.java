package huihuang.proxy.ocpx.ads.huihuangmingtian;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.channel.baidu.BaiduEventTypeEnum;
import huihuang.proxy.ocpx.channel.guangdiantong.GuangdiantongEventTypeEnum;
import huihuang.proxy.ocpx.channel.honor.HonorEventTypeEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiEventTypeEnum;
import huihuang.proxy.ocpx.channel.iqiyi.IQiyiEventTypeEnum;
import huihuang.proxy.ocpx.channel.oppo.OppoEventTypeEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiEventTypeEnum;

import java.util.Map;

public enum HuihuangFengmangEventTypeEnum {

    ACTIVATE("0", "激活"),
    REGISTER("1","注册"),
    FIRST_PAID("2","首次付费"),
    ORDER("3", "下单"),
    OTHER("5", "其他"),
    DAY1RETENTION("6", "次留"),
    COPY_PART("7", "复制报名"),
    FIRST_WEAK("8", "首次拉活"),
    FIRST_CLICK("9", "首次点击"),
    CALL_BACK("10", "召回"),
    EFFECT_READ("30", "有效阅读"),

    ;

    private String code;
    private String desc;

    HuihuangFengmangEventTypeEnum(String code, String desc) {
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
        huihuangmingtianHuaweiEventTypeMap.put(REGISTER.code, HuaweiEventTypeEnum.REGISTER);
        huihuangmingtianHuaweiEventTypeMap.put(FIRST_PAID.code, HuaweiEventTypeEnum.PAID);
        huihuangmingtianHuaweiEventTypeMap.put(ORDER.code, HuaweiEventTypeEnum.PRE_ORDER);
        huihuangmingtianHuaweiEventTypeMap.put(OTHER.code, null);
        huihuangmingtianHuaweiEventTypeMap.put(DAY1RETENTION.code, HuaweiEventTypeEnum.RETAIN);
        huihuangmingtianHuaweiEventTypeMap.put(COPY_PART.code, null);
        huihuangmingtianHuaweiEventTypeMap.put(FIRST_WEAK.code, null);
    }

    public static Map<String, BaiduEventTypeEnum> huihuangmingtianBaiduEventTypeMap;

    static {
        huihuangmingtianBaiduEventTypeMap = CollUtil.newHashMap();
        huihuangmingtianBaiduEventTypeMap.put(ACTIVATE.code, BaiduEventTypeEnum.ACTIVE);
        huihuangmingtianBaiduEventTypeMap.put(REGISTER.code, BaiduEventTypeEnum.REGISTER);
        huihuangmingtianBaiduEventTypeMap.put(FIRST_PAID.code, BaiduEventTypeEnum.ORDERS);
        huihuangmingtianBaiduEventTypeMap.put(ORDER.code, BaiduEventTypeEnum.EC_BUY);
        huihuangmingtianBaiduEventTypeMap.put(OTHER.code, null);
        huihuangmingtianBaiduEventTypeMap.put(DAY1RETENTION.code, BaiduEventTypeEnum.RETAIN_1DAY);
        huihuangmingtianBaiduEventTypeMap.put(COPY_PART.code, null);
        huihuangmingtianBaiduEventTypeMap.put(FIRST_WEAK.code, null);
    }


    public static Map<String, XiaomiEventTypeEnum> huihuangmingtianXiaomiEventTypeMap;

    static {
        huihuangmingtianXiaomiEventTypeMap = CollUtil.newHashMap();
        huihuangmingtianXiaomiEventTypeMap.put(ACTIVATE.code, XiaomiEventTypeEnum.APP_ACTIVE);
        //自定义新增激活
        huihuangmingtianXiaomiEventTypeMap.put(ACTIVATE.code + "new", XiaomiEventTypeEnum.APP_ACTIVE_NEW);
        huihuangmingtianXiaomiEventTypeMap.put(REGISTER.code, XiaomiEventTypeEnum.APP_REGISTER);
        huihuangmingtianXiaomiEventTypeMap.put(FIRST_PAID.code, XiaomiEventTypeEnum.APP_PAY);
//        huihuangmingtianXiaomiEventTypeMap.put(FIRST_PAID.code, XiaomiEventTypeEnum.APP_FIRST_PAY);
        huihuangmingtianXiaomiEventTypeMap.put(ORDER.code, XiaomiEventTypeEnum.APP_NEW_USER_PURCHASE);
//        huihuangmingtianXiaomiEventTypeMap.put(ORDER.code, XiaomiEventTypeEnum.APP_COMPLETE_ORDER);
        huihuangmingtianXiaomiEventTypeMap.put(OTHER.code, null);
        huihuangmingtianXiaomiEventTypeMap.put(DAY1RETENTION.code, XiaomiEventTypeEnum.APP_RETENTION);
        huihuangmingtianXiaomiEventTypeMap.put(COPY_PART.code, null);
        huihuangmingtianXiaomiEventTypeMap.put(FIRST_WEAK.code, XiaomiEventTypeEnum.APP_RE_ACTIVE);
    }


    public static Map<String, IQiyiEventTypeEnum> huihuangmingtianIQiyiEventTypeMap;

    static {
        huihuangmingtianIQiyiEventTypeMap = CollUtil.newHashMap();
        huihuangmingtianIQiyiEventTypeMap.put(ACTIVATE.code, IQiyiEventTypeEnum.ACTIVE);
        huihuangmingtianIQiyiEventTypeMap.put(REGISTER.code, IQiyiEventTypeEnum.REGISTER);
        huihuangmingtianIQiyiEventTypeMap.put(FIRST_PAID.code, IQiyiEventTypeEnum.PAY);
        huihuangmingtianIQiyiEventTypeMap.put(ORDER.code, null);
        huihuangmingtianIQiyiEventTypeMap.put(OTHER.code, null);
        huihuangmingtianIQiyiEventTypeMap.put(DAY1RETENTION.code, IQiyiEventTypeEnum.RETENTION);
        huihuangmingtianIQiyiEventTypeMap.put(COPY_PART.code, null);
        huihuangmingtianIQiyiEventTypeMap.put(FIRST_WEAK.code, null);
    }


    public static Map<String, HonorEventTypeEnum> huihuangmingtianHonorEventTypeMap;

    static {


        huihuangmingtianHonorEventTypeMap = CollUtil.newHashMap();
        huihuangmingtianHonorEventTypeMap.put(ACTIVATE.code, HonorEventTypeEnum.ACTIVE);
        huihuangmingtianHonorEventTypeMap.put(REGISTER.code, HonorEventTypeEnum.REGISTER);
        huihuangmingtianHonorEventTypeMap.put(FIRST_PAID.code, HonorEventTypeEnum.PAID);
        huihuangmingtianHonorEventTypeMap.put(ORDER.code, HonorEventTypeEnum.PRE_ORDER);
        huihuangmingtianHonorEventTypeMap.put(OTHER.code, null);
        huihuangmingtianHonorEventTypeMap.put(DAY1RETENTION.code, HonorEventTypeEnum.RETAIN);
        huihuangmingtianHonorEventTypeMap.put(COPY_PART.code, null);
        huihuangmingtianHonorEventTypeMap.put(FIRST_WEAK.code, null);
    }


    public static Map<String, GuangdiantongEventTypeEnum> huihuangmingtianGdtEventTypeMap;

    static {
        huihuangmingtianGdtEventTypeMap = CollUtil.newHashMap();
        huihuangmingtianGdtEventTypeMap.put(ACTIVATE.code, GuangdiantongEventTypeEnum.ACTIVE);
        huihuangmingtianGdtEventTypeMap.put(REGISTER.code, GuangdiantongEventTypeEnum.REGISTER);
        huihuangmingtianGdtEventTypeMap.put(FIRST_PAID.code, GuangdiantongEventTypeEnum.PURCHASE);
        huihuangmingtianGdtEventTypeMap.put(ORDER.code, null);
        huihuangmingtianGdtEventTypeMap.put(OTHER.code, null);
        huihuangmingtianGdtEventTypeMap.put(DAY1RETENTION.code, GuangdiantongEventTypeEnum.START_APP);
        huihuangmingtianGdtEventTypeMap.put(COPY_PART.code, null);
        huihuangmingtianGdtEventTypeMap.put(FIRST_WEAK.code, GuangdiantongEventTypeEnum.VIEW_CONTENT);
    }


    public static Map<String, OppoEventTypeEnum> huihuangmingtianOppoEventTypeMap;

    static {
        huihuangmingtianOppoEventTypeMap = CollUtil.newHashMap();
        huihuangmingtianOppoEventTypeMap.put(ACTIVATE.code, OppoEventTypeEnum.ACTIVE);
        huihuangmingtianOppoEventTypeMap.put(REGISTER.code, OppoEventTypeEnum.REGISTER);
        huihuangmingtianOppoEventTypeMap.put(FIRST_PAID.code, null);
        huihuangmingtianOppoEventTypeMap.put(ORDER.code, OppoEventTypeEnum.DEEP_PAGE_ACCESS);
        huihuangmingtianOppoEventTypeMap.put(OTHER.code, null);
        huihuangmingtianOppoEventTypeMap.put(DAY1RETENTION.code, OppoEventTypeEnum.RETAIN_2DAY);
        huihuangmingtianOppoEventTypeMap.put(COPY_PART.code, null);
        huihuangmingtianOppoEventTypeMap.put(FIRST_WEAK.code, null);
    }

}
