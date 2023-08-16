package huihuang.proxy.ocpx.ads.huihui;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.channel.baidu.BaiduEventTypeEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiEventTypeEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiEventTypeEnum;

import java.util.Map;

/**
 * @Author: xietao
 * @Date: 2023/6/8 17:21
 */
public enum HuihuiEventTypeEnum {

    //安卓
    ANDROID_DOWNLOAD("android_download", "下载完成"),
    ANDROID_ACTIVATE("android_activate", "激活"),
    ANDROID_REGISTER("android_register", "注册"),
    ANDROID_DAY1RETENTION("android_day1retention", "次日回访，次日留存"),
    ANDROID_ADDTOCART("android_addtocart", "加入购物车"),
    ANDROID_PURCHASE("android_purchase", "购买"),
    ANDROID_CREDIT("android_credit", "授信"),
    ANDROID_CUSTOM("android_custom", "自定义"),

    //ios
    IOS_ACTIVATE("ios_activate", "激活"),
    IOS_REGISTER("ios_register", "注册"),
    IOS_DAY1RETENTION("ios_day1retention", "次日回访，次日留存"),
    IOS_ADDTOCART("ios_addtocart", "加入购物车"),
    IOS_PURCHASE("ios_purchase", "购买"),
    IOS_CREDIT("ios_credit", "授信"),
    IOS_CUSTOM("ios_custom", "自定义"),


    ;

    private String code;
    private String desc;

    HuihuiEventTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static Map<String, XiaomiEventTypeEnum> huihuiXiaomiEventTypeMap;

    static {
        huihuiXiaomiEventTypeMap = CollUtil.newHashMap();
        //下载
        huihuiXiaomiEventTypeMap.put(ANDROID_DOWNLOAD.code, null);
        //激活
        huihuiXiaomiEventTypeMap.put(ANDROID_ACTIVATE.code, XiaomiEventTypeEnum.APP_ACTIVE);
        huihuiXiaomiEventTypeMap.put(IOS_ACTIVATE.code, XiaomiEventTypeEnum.APP_ACTIVE);
        //注册
        huihuiXiaomiEventTypeMap.put(ANDROID_REGISTER.code, XiaomiEventTypeEnum.APP_REGISTER);
        huihuiXiaomiEventTypeMap.put(IOS_REGISTER.code, XiaomiEventTypeEnum.APP_REGISTER);
        //次日留存
        huihuiXiaomiEventTypeMap.put(ANDROID_DAY1RETENTION.code, XiaomiEventTypeEnum.APP_RETENTION);
        huihuiXiaomiEventTypeMap.put(IOS_DAY1RETENTION.code, XiaomiEventTypeEnum.APP_RETENTION);
        //加入购物车
        huihuiXiaomiEventTypeMap.put(ANDROID_ADDTOCART.code, null);
        huihuiXiaomiEventTypeMap.put(IOS_ADDTOCART.code, null);
        //购买
        huihuiXiaomiEventTypeMap.put(ANDROID_PURCHASE.code, XiaomiEventTypeEnum.APP_PURCHASE);
        huihuiXiaomiEventTypeMap.put(IOS_PURCHASE.code, XiaomiEventTypeEnum.APP_PURCHASE);
        //授信
        huihuiXiaomiEventTypeMap.put(ANDROID_CREDIT.code, XiaomiEventTypeEnum.APP_CREDIT);
        huihuiXiaomiEventTypeMap.put(IOS_CREDIT.code, XiaomiEventTypeEnum.APP_CREDIT);
        //自定义
        huihuiXiaomiEventTypeMap.put(ANDROID_CUSTOM.code, null);
        huihuiXiaomiEventTypeMap.put(IOS_CUSTOM.code, null);

    }


    public static Map<String, BaiduEventTypeEnum> huihuiBaiduEventTypeMap;

    static {
        huihuiBaiduEventTypeMap = CollUtil.newHashMap();
        //下载
        huihuiBaiduEventTypeMap.put(ANDROID_DOWNLOAD.code, null);
        //激活
        huihuiBaiduEventTypeMap.put(ANDROID_ACTIVATE.code, BaiduEventTypeEnum.ACTIVE);
        huihuiBaiduEventTypeMap.put(IOS_ACTIVATE.code, BaiduEventTypeEnum.ACTIVE);
        //注册
        huihuiBaiduEventTypeMap.put(ANDROID_REGISTER.code, BaiduEventTypeEnum.REGISTER);
        huihuiBaiduEventTypeMap.put(IOS_REGISTER.code, BaiduEventTypeEnum.REGISTER);
        //次日留存
        huihuiBaiduEventTypeMap.put(ANDROID_DAY1RETENTION.code, BaiduEventTypeEnum.RETAIN_1DAY);
        huihuiBaiduEventTypeMap.put(IOS_DAY1RETENTION.code, BaiduEventTypeEnum.RETAIN_1DAY);
        //加入购物车
        huihuiBaiduEventTypeMap.put(ANDROID_ADDTOCART.code, null);
        huihuiBaiduEventTypeMap.put(IOS_ADDTOCART.code, null);
        //购买
        huihuiBaiduEventTypeMap.put(ANDROID_PURCHASE.code, BaiduEventTypeEnum.EC_BUY);
        huihuiBaiduEventTypeMap.put(IOS_PURCHASE.code, BaiduEventTypeEnum.EC_BUY);
        //授信
        huihuiBaiduEventTypeMap.put(ANDROID_CREDIT.code, null);
        huihuiBaiduEventTypeMap.put(IOS_CREDIT.code, null);
        //自定义
        huihuiBaiduEventTypeMap.put(ANDROID_CUSTOM.code, null);
        huihuiBaiduEventTypeMap.put(IOS_CUSTOM.code, null);

    }


    public static Map<String, HuaweiEventTypeEnum> huihuiHuaweiEventTypeMap;

    static {
        huihuiHuaweiEventTypeMap = CollUtil.newHashMap();
        //下载
        huihuiHuaweiEventTypeMap.put(ANDROID_DOWNLOAD.code, null);
        //激活
        huihuiHuaweiEventTypeMap.put(ANDROID_ACTIVATE.code, HuaweiEventTypeEnum.ACTIVE);
        huihuiHuaweiEventTypeMap.put(IOS_ACTIVATE.code, HuaweiEventTypeEnum.ACTIVE);
        //注册
        huihuiHuaweiEventTypeMap.put(ANDROID_REGISTER.code, HuaweiEventTypeEnum.REGISTER);
        huihuiHuaweiEventTypeMap.put(IOS_REGISTER.code, HuaweiEventTypeEnum.REGISTER);
        //次日留存
        huihuiHuaweiEventTypeMap.put(ANDROID_DAY1RETENTION.code, HuaweiEventTypeEnum.RETAIN);
        huihuiHuaweiEventTypeMap.put(IOS_DAY1RETENTION.code, HuaweiEventTypeEnum.RETAIN);
        //加入购物车
        huihuiHuaweiEventTypeMap.put(ANDROID_ADDTOCART.code, HuaweiEventTypeEnum.ADD_TO_CART);
        huihuiHuaweiEventTypeMap.put(IOS_ADDTOCART.code, HuaweiEventTypeEnum.ADD_TO_CART);
        //购买
        huihuiHuaweiEventTypeMap.put(ANDROID_PURCHASE.code, HuaweiEventTypeEnum.FIRST_PURCHASE);
        huihuiHuaweiEventTypeMap.put(IOS_PURCHASE.code, HuaweiEventTypeEnum.FIRST_PURCHASE);
        //授信
        huihuiHuaweiEventTypeMap.put(ANDROID_CREDIT.code, HuaweiEventTypeEnum.CREDIT);
        huihuiHuaweiEventTypeMap.put(IOS_CREDIT.code, HuaweiEventTypeEnum.CREDIT);
        //自定义
        huihuiHuaweiEventTypeMap.put(ANDROID_CUSTOM.code, null);
        huihuiBaiduEventTypeMap.put(IOS_CUSTOM.code, null);

    }

}
