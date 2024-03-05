package huihuang.proxy.ocpx.ads.huihuang;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.channel.huawei.HuaweiEventTypeEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiEventTypeEnum;

import java.util.Map;

/**
 *
 * @Author: xietao
 * @Date: 2023/7/6 16:23
 */
public enum HuihuangEventTypeEnum {

    DAU("DAU", "首活"), //
    NU("NU", "新用户"),
    SED_RU("SED_RU", "回流"),
    REMAIN1D("REMAIN1D", "次留"),
    PAY1D("PAY1D", "当日付费"),

    ;

    private String code;
    private String desc;

    HuihuangEventTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static Map<String, HuaweiEventTypeEnum> huihuangHuaweiEventTypeMap;

    static {
        huihuangHuaweiEventTypeMap = CollUtil.newHashMap();
        //首活
        huihuangHuaweiEventTypeMap.put(DAU.code, null);
        //新用户
        huihuangHuaweiEventTypeMap.put(NU.code, HuaweiEventTypeEnum.ACTIVE);
        //回流
        huihuangHuaweiEventTypeMap.put(SED_RU.code, null);
        //次留
        huihuangHuaweiEventTypeMap.put(REMAIN1D.code, HuaweiEventTypeEnum.RETAIN);
        //当日付费
        huihuangHuaweiEventTypeMap.put(PAY1D.code, HuaweiEventTypeEnum.PAID);
    }


    public static Map<String, XiaomiEventTypeEnum> huihuangXiaomiEventTypeMap;

    static {
        huihuangXiaomiEventTypeMap = CollUtil.newHashMap();
        //首活
        huihuangXiaomiEventTypeMap.put(DAU.code, null);
        //新用户
        huihuangXiaomiEventTypeMap.put(NU.code, XiaomiEventTypeEnum.APP_ACTIVE);
        huihuangXiaomiEventTypeMap.put(NU.code+"new", XiaomiEventTypeEnum.APP_ACTIVE_NEW);
        //回流
        huihuangXiaomiEventTypeMap.put(SED_RU.code, null);
        //次留
        huihuangXiaomiEventTypeMap.put(REMAIN1D.code, XiaomiEventTypeEnum.APP_RETENTION);
        //当日付费
        huihuangXiaomiEventTypeMap.put(PAY1D.code, XiaomiEventTypeEnum.APP_PAY);
    }

}
