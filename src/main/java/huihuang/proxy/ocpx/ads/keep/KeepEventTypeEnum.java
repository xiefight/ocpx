package huihuang.proxy.ocpx.ads.keep;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.channel.baidu.BaiduEventTypeEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiEventTypeEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiEventTypeEnum;

import java.util.Map;

public enum KeepEventTypeEnum {

    ACTIVE("0", "激活"),
    REGISTER("1","注册"),
    PAID("2", "付费"),
    DAY1RETENTION("3", "次留");

    private String code;
    private String desc;

    KeepEventTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static Map<String, HuaweiEventTypeEnum> keepHuaweiEventTypeMap;

    static {
        keepHuaweiEventTypeMap = CollUtil.newHashMap();
        //激活
        keepHuaweiEventTypeMap.put(ACTIVE.code, HuaweiEventTypeEnum.ACTIVE);
        //注册
        keepHuaweiEventTypeMap.put(REGISTER.code, HuaweiEventTypeEnum.REGISTER);
        //付费
        keepHuaweiEventTypeMap.put(PAID.code, HuaweiEventTypeEnum.PAID);
        //次日回访
        keepHuaweiEventTypeMap.put(DAY1RETENTION.code, HuaweiEventTypeEnum.RETAIN);
    }


    public static Map<String, BaiduEventTypeEnum> keepBaiduEventTypeMap;

    static {
        keepBaiduEventTypeMap = CollUtil.newHashMap();
        //激活
        keepBaiduEventTypeMap.put(ACTIVE.code, BaiduEventTypeEnum.ACTIVE);
        //注册
        keepBaiduEventTypeMap.put(REGISTER.code, BaiduEventTypeEnum.REGISTER);
        //付费
        keepBaiduEventTypeMap.put(PAID.code, null);
        //次日回访
        keepBaiduEventTypeMap.put(DAY1RETENTION.code, BaiduEventTypeEnum.RETAIN_1DAY);
    }


    public static Map<String, XiaomiEventTypeEnum> keepXiaomiEventTypeMap;

    static {
        keepXiaomiEventTypeMap = CollUtil.newHashMap();
        //激活
        keepXiaomiEventTypeMap.put(ACTIVE.code, XiaomiEventTypeEnum.APP_ACTIVE);
        //注册
        keepXiaomiEventTypeMap.put(REGISTER.code, XiaomiEventTypeEnum.APP_REGISTER);
        //付费
        keepXiaomiEventTypeMap.put(PAID.code, XiaomiEventTypeEnum.APP_PAY);
        //次日回访
        keepXiaomiEventTypeMap.put(DAY1RETENTION.code, XiaomiEventTypeEnum.APP_RETENTION);
    }

}
