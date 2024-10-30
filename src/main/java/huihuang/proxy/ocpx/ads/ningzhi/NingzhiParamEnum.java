package huihuang.proxy.ocpx.ads.ningzhi;

import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiParamEnum;

import java.util.HashMap;
import java.util.Map;

public enum NingzhiParamEnum {

    IDFA("idfa", "String", 1, "iOS设备的idfa原值"),
    IMEI("imei", "String", 1, "安卓设备的imei原值"),
    //    IMEI_MD5("imei_md5", "String", 0, "安卓设备的imei原值md5加密小写值"),
//    OAID("oaid", "String", 1, "安卓设备oaid原值"),
    OAID_MD5("oaid", "String", 0, "安卓设备oaid原值md5加密小写值"),
    ANDROID_ID("androidid", "String", 1, "安卓设备androidid原值"),
    IP("ip", "String", 1, "客户端ip"),
    UA("ua", "String", 0, "客户端设备ua，当ios设备获取不到idfa值时，需要传此值"),
    OS("os", "String", 0, "系统"),
    CLICK_ID("clickid", "String", 0, "点击id"),
    TS("ts", "String", 0, "请求时间（毫秒单位）"),
    CALLBACK("callback", "String", 1, "渠道回调地址"),
    ACCOUNT_ID("accountId", "String", 0, "我们规定的账户id,用于同一pid区分不出来的情况下,使用accountId区分"),

    YYQ("yyq", "String", 0, ""),

    ;

    private String name;
    private String type;
    private Integer necessary;
    private String remark;

    NingzhiParamEnum(String name, String type, Integer necessary, String remark) {
        this.name = name;
        this.type = type;
        this.necessary = necessary;
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Integer getNecessary() {
        return necessary;
    }

    public String getRemark() {
        return remark;
    }


    public static Map<NingzhiParamEnum, XiaomiParamEnum> ningzhiXiaomiMap;

    static {
        ningzhiXiaomiMap = new HashMap<>();
        ningzhiXiaomiMap.put(IDFA, null);
        ningzhiXiaomiMap.put(IMEI, XiaomiParamEnum.IMEI);//
//        qiduXiaomiMap.put(OAID, XiaomiParamEnum.OAID);
        ningzhiXiaomiMap.put(OAID_MD5, XiaomiParamEnum.OAID);
        ningzhiXiaomiMap.put(IP, XiaomiParamEnum.IP);
        ningzhiXiaomiMap.put(ANDROID_ID, null);
        ningzhiXiaomiMap.put(UA, XiaomiParamEnum.UA);
        ningzhiXiaomiMap.put(CALLBACK, XiaomiParamEnum.CALLBACK);
        ningzhiXiaomiMap.put(CLICK_ID, null);
        ningzhiXiaomiMap.put(TS, XiaomiParamEnum.TS);
        ningzhiXiaomiMap.put(OS, null);
        ningzhiXiaomiMap.put(ACCOUNT_ID, XiaomiParamEnum.ACCOUNT_ID);
        ningzhiXiaomiMap.put(YYQ, XiaomiParamEnum.NINGZHI_YYQ);
    }


    public static Map<NingzhiParamEnum, BaiduParamEnum> ningzhiBaiduMap;

    static {
        ningzhiBaiduMap = new HashMap<>();
        ningzhiBaiduMap.put(IDFA, null);
        ningzhiBaiduMap.put(IMEI, BaiduParamEnum.IMEI_MD5);//
//        ningzhiBaiduMap.put(OAID, XiaomiParamEnum.OAID);
        ningzhiBaiduMap.put(OAID_MD5, BaiduParamEnum.OAID);
        ningzhiBaiduMap.put(IP, BaiduParamEnum.IP);
        ningzhiBaiduMap.put(ANDROID_ID, null);
        ningzhiBaiduMap.put(UA, BaiduParamEnum.UA);
        ningzhiBaiduMap.put(CALLBACK, BaiduParamEnum.CALLBACK_URL);
        ningzhiBaiduMap.put(CLICK_ID, null);
        ningzhiBaiduMap.put(TS, BaiduParamEnum.TS);
        ningzhiBaiduMap.put(OS, BaiduParamEnum.OS_TYPE);
        ningzhiBaiduMap.put(ACCOUNT_ID, BaiduParamEnum.ACCOUNT_ID);
        ningzhiBaiduMap.put(YYQ, BaiduParamEnum.NINGZHI_YYQ);
    }


}
