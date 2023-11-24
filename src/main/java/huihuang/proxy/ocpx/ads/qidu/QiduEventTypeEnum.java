package huihuang.proxy.ocpx.ads.qidu;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiEventTypeEnum;

import java.util.Map;

public enum QiduEventTypeEnum {

    ACTIVATE("0", "激活"),
    REGISTER("1", "注册"),
    PURCHASE("2", "付费"),
    DAY1RETENTION("3", "次留"),

    ;

    private String code;
    private String desc;

    QiduEventTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static Map<String, XiaomiEventTypeEnum> qiduXiaomiEventTypeMap;

    static {
        qiduXiaomiEventTypeMap = CollUtil.newHashMap();
        //激活
        qiduXiaomiEventTypeMap.put(ACTIVATE.code, XiaomiEventTypeEnum.APP_ACTIVE);
        //自定义新增激活
        qiduXiaomiEventTypeMap.put(ACTIVATE.code + "new", XiaomiEventTypeEnum.APP_ACTIVE_NEW);
        //注册
        qiduXiaomiEventTypeMap.put(REGISTER.code, XiaomiEventTypeEnum.APP_REGISTER);
        //次留
        qiduXiaomiEventTypeMap.put(DAY1RETENTION.code, XiaomiEventTypeEnum.APP_RETENTION);
        //购买
        qiduXiaomiEventTypeMap.put(PURCHASE.code, XiaomiEventTypeEnum.APP_PURCHASE);
    }


}
