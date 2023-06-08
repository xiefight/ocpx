package huihuang.proxy.ocpx.ads.huihui;

import huihuang.proxy.ocpx.channel.xiaomi.XiaomiParamEnum;

import java.util.HashMap;
import java.util.Map;

/** 
 * 
 * @Author: xietao
 * @Date: 2023/6/8 17:16
 */ 
public enum HuihuiParamEnum {
    IDFA("idfa", "String", 1, "原值， 大写"),
    IDFA_MD5("idfa_md5", "String", 2, "原值大写后 md5 之后再转大写"),
    IMEI("imei", "String", 1, "md5 之后再转大写，对应渠道的imei_md5，如果只有imei，则先md5，再大写"),
//    IMEI_MD5("imei_md5", "String", 1, "imei原生值的md5， 32位小写"),
    OAID("oaid", "String", 1, "android 广告标识"),
    OAID_MD5("oaid_md5", "String", 1, "android 广告标识 md5值， 32位大写"),
    CAID("caid","String",1,"原值"),
    CAID_MD5("caid_md5","String",1,"原值大写后 md5 之后再转大写"),

    REQ_ID("reqid", "String", 1, "原始请求 id；若为RTA 投放，需要回传RTA 接口的请求 id;RTA 投放时必填 "),
    AAID("aaid","String",2,"阿里设备标识符"),

    IP("ip", "String", 1, "点击 ip；IPV4:A.B.C.D (四段，以“.”分隔)IPV6:需要 encode 一次 "),
    UA("ua", "String", 1, "点 击 数 据 上 报 时http的 header 中的user_agent，一次urlencode 编码"),
    TS("ts", "String", 1, "点击时间，时间戳，单位秒"),

    OS("os", "String", 1, "android 或 ios；"),
    CALLBACK("callback", "String", 1, "渠道回调地址"),
    MODEL("model", "String", 1, "手机机型"),

    ;

    private String name;
    private String type;
    private Integer necessary;
    private String remark;

    HuihuiParamEnum(String name, String type, Integer necessary, String remark) {
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

    /**
     * xiaomi-ltjd
     */
    public static Map<HuihuiParamEnum, XiaomiParamEnum> huihuiXiaomiMap;

    static {
        huihuiXiaomiMap = new HashMap<>();
        huihuiXiaomiMap.put(IMEI, XiaomiParamEnum.IMEI);
        huihuiXiaomiMap.put(OAID, XiaomiParamEnum.OAID);
        huihuiXiaomiMap.put(OAID_MD5, null);
        huihuiXiaomiMap.put(IDFA, null);// 小米无idfa
        huihuiXiaomiMap.put(IDFA_MD5, null);
        huihuiXiaomiMap.put(CAID, null);
        huihuiXiaomiMap.put(CAID_MD5, null);
        huihuiXiaomiMap.put(TS, XiaomiParamEnum.TS);//注意：ltjd是秒，xiaomi是毫秒
        huihuiXiaomiMap.put(OS, null);// 未确定
        huihuiXiaomiMap.put(IP, XiaomiParamEnum.IP);
        huihuiXiaomiMap.put(UA, XiaomiParamEnum.UA);
        huihuiXiaomiMap.put(CALLBACK, XiaomiParamEnum.CALLBACK);
        huihuiXiaomiMap.put(REQ_ID, null);
        huihuiXiaomiMap.put(AAID, null);
        huihuiXiaomiMap.put(MODEL, null);//小米机型？
    }


}
