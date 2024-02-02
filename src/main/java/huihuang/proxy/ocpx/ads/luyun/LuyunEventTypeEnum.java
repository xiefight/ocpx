package huihuang.proxy.ocpx.ads.luyun;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.channel.baidu.BaiduEventTypeEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiEventTypeEnum;
import huihuang.proxy.ocpx.channel.iqiyi.IQiyiEventTypeEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiEventTypeEnum;

import java.util.Map;

public enum LuyunEventTypeEnum {

    ACTIVE("0", "激活"),
    REGISTER("1","注册"),
    PAID("2", "付费"),
    DAY1RETENTION("3", "次留");

    private String code;
    private String desc;

    LuyunEventTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static Map<String, HuaweiEventTypeEnum> luyunHuaweiEventTypeMap;

    static {
        luyunHuaweiEventTypeMap = CollUtil.newHashMap();
        //激活
        luyunHuaweiEventTypeMap.put(ACTIVE.code, HuaweiEventTypeEnum.ACTIVE);
        //注册
        luyunHuaweiEventTypeMap.put(REGISTER.code, HuaweiEventTypeEnum.REGISTER);
        //付费
        luyunHuaweiEventTypeMap.put(PAID.code, HuaweiEventTypeEnum.PAID);
        //次日回访
        luyunHuaweiEventTypeMap.put(DAY1RETENTION.code, HuaweiEventTypeEnum.RETAIN);
    }


    public static Map<String, BaiduEventTypeEnum> luyunBaiduEventTypeMap;

    static {
        luyunBaiduEventTypeMap = CollUtil.newHashMap();
        //激活
        luyunBaiduEventTypeMap.put(ACTIVE.code, BaiduEventTypeEnum.ACTIVE);
        //注册
        luyunBaiduEventTypeMap.put(REGISTER.code, BaiduEventTypeEnum.REGISTER);
        //付费
        luyunBaiduEventTypeMap.put(PAID.code, null);
        //次日回访
        luyunBaiduEventTypeMap.put(DAY1RETENTION.code, BaiduEventTypeEnum.RETAIN_1DAY);
    }


    public static Map<String, XiaomiEventTypeEnum> luyunXiaomiEventTypeMap;

    static {
        luyunXiaomiEventTypeMap = CollUtil.newHashMap();
        //激活
        luyunXiaomiEventTypeMap.put(ACTIVE.code, XiaomiEventTypeEnum.APP_ACTIVE);
        //注册
        luyunXiaomiEventTypeMap.put(REGISTER.code, XiaomiEventTypeEnum.APP_REGISTER);
        //付费
        luyunXiaomiEventTypeMap.put(PAID.code, XiaomiEventTypeEnum.APP_PAY);
        //次日回访
        luyunXiaomiEventTypeMap.put(DAY1RETENTION.code, XiaomiEventTypeEnum.APP_RETENTION);
    }

    public static Map<String, IQiyiEventTypeEnum> luyunIQiyiEventTypeMap;

    static {
        luyunIQiyiEventTypeMap = CollUtil.newHashMap();
        //激活
        luyunIQiyiEventTypeMap.put(ACTIVE.code, IQiyiEventTypeEnum.ACTIVE);
        //注册
        luyunIQiyiEventTypeMap.put(REGISTER.code, IQiyiEventTypeEnum.REGISTER);
        //付费
        luyunIQiyiEventTypeMap.put(PAID.code, IQiyiEventTypeEnum.PAY);
        //次日回访
        luyunIQiyiEventTypeMap.put(DAY1RETENTION.code, IQiyiEventTypeEnum.RETENTION);
    }

}
