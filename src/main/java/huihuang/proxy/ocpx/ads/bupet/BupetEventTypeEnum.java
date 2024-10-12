package huihuang.proxy.ocpx.ads.bupet;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.channel.baidu.BaiduEventTypeEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiEventTypeEnum;

import java.util.Map;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-01-04 08:40
 **/
public enum BupetEventTypeEnum {

    ACTIVATE("active", "激活"),
    REGISTER("register", "注册"),
    DAY1RETENTION("day1retention", "次日留存"),
    PURCHASE("purchase", "付费"),

    ;


    private String code;
    private String desc;

    BupetEventTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static Map<String, XiaomiEventTypeEnum> bupetXiaomiEventTypeMap;

    static {
        bupetXiaomiEventTypeMap = CollUtil.newHashMap();
        bupetXiaomiEventTypeMap.put(ACTIVATE.code, XiaomiEventTypeEnum.APP_ACTIVE);
        bupetXiaomiEventTypeMap.put(REGISTER.code, XiaomiEventTypeEnum.APP_REGISTER);
        bupetXiaomiEventTypeMap.put(DAY1RETENTION.code, XiaomiEventTypeEnum.APP_RETENTION);
        bupetXiaomiEventTypeMap.put(PURCHASE.code, XiaomiEventTypeEnum.APP_FIRST_PAY);
    }

    public static Map<String, BaiduEventTypeEnum> bupetBaiduEventTypeMap;

    static {
        bupetBaiduEventTypeMap = CollUtil.newHashMap();
        bupetBaiduEventTypeMap.put(ACTIVATE.code, BaiduEventTypeEnum.ACTIVE);
        bupetBaiduEventTypeMap.put(REGISTER.code, BaiduEventTypeEnum.REGISTER);
        bupetBaiduEventTypeMap.put(DAY1RETENTION.code, BaiduEventTypeEnum.RETAIN_1DAY);
        bupetBaiduEventTypeMap.put(PURCHASE.code, BaiduEventTypeEnum.ORDERS);
    }

}
