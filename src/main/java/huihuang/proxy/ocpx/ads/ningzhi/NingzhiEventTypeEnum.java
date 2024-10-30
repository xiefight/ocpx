package huihuang.proxy.ocpx.ads.ningzhi;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.channel.baidu.BaiduEventTypeEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiEventTypeEnum;

import java.util.Map;

public enum NingzhiEventTypeEnum {

    ACTIVATE("activate", "激活"),
    REGISTER("register", "注册"),
    PAY("pay", "付费"),
    LEAVE("leave", "次留"),

    ;

    private String code;
    private String desc;

    NingzhiEventTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static Map<String, XiaomiEventTypeEnum> ningzhiXiaomiEventTypeMap;

    static {
        ningzhiXiaomiEventTypeMap = CollUtil.newHashMap();
        //激活
//        ningzhiXiaomiEventTypeMap.put(ACTIVATE.code, XiaomiEventTypeEnum.APP_ACTIVE);
        //自定义新增激活
//        ningzhiXiaomiEventTypeMap.put(ACTIVATE.code + "new", XiaomiEventTypeEnum.APP_ACTIVE_NEW);
        //注册
        ningzhiXiaomiEventTypeMap.put(REGISTER.code, XiaomiEventTypeEnum.APP_ACTIVE);
//        ningzhiXiaomiEventTypeMap.put(REGISTER.code, XiaomiEventTypeEnum.APP_REGISTER);
        //次留
        ningzhiXiaomiEventTypeMap.put(LEAVE.code, XiaomiEventTypeEnum.APP_RETENTION);
        //购买
        ningzhiXiaomiEventTypeMap.put(PAY.code, XiaomiEventTypeEnum.APP_PURCHASE);
    }


    public static Map<String, BaiduEventTypeEnum> ningzhiBaiduEventTypeMap;

    static {
        ningzhiBaiduEventTypeMap = CollUtil.newHashMap();
        //激活
//        ningzhiBaiduEventTypeMap.put(ACTIVATE.code, BaiduEventTypeEnum.ACTIVE);
        //注册
        ningzhiBaiduEventTypeMap.put(REGISTER.code, BaiduEventTypeEnum.ACTIVE);
        //次留
        ningzhiBaiduEventTypeMap.put(LEAVE.code, BaiduEventTypeEnum.RETAIN_1DAY);
        //购买
        ningzhiBaiduEventTypeMap.put(PAY.code, BaiduEventTypeEnum.ORDERS);
    }


}
