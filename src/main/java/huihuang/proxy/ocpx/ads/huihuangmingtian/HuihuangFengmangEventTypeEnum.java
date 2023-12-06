package huihuang.proxy.ocpx.ads.huihuangmingtian;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.channel.baidu.BaiduEventTypeEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiEventTypeEnum;
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
        huihuangmingtianXiaomiEventTypeMap.put(FIRST_PAID.code, XiaomiEventTypeEnum.APP_FIRST_PAY);
        huihuangmingtianXiaomiEventTypeMap.put(ORDER.code, XiaomiEventTypeEnum.APP_COMPLETE_ORDER);
        huihuangmingtianXiaomiEventTypeMap.put(OTHER.code, null);
        huihuangmingtianXiaomiEventTypeMap.put(DAY1RETENTION.code, XiaomiEventTypeEnum.APP_RETENTION);
        huihuangmingtianXiaomiEventTypeMap.put(COPY_PART.code, null);
        huihuangmingtianXiaomiEventTypeMap.put(FIRST_WEAK.code, XiaomiEventTypeEnum.APP_RE_ACTIVE);
    }

}
