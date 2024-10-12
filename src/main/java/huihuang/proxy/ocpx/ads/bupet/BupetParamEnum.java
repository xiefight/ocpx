package huihuang.proxy.ocpx.ads.bupet;

import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiParamEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-01-04 08:44
 **/
public enum BupetParamEnum {

    IDFA("idfa", "String", 1, "用户设备的广告标识符，ipua 模糊归因时 idfa 为空"),
    IDFA_MD5("idfaMd5", "int", 1, "调用类型：1：rta，2：ocpx"),
    IMEI("imei", "String", 1, "国际移动设备标识符（MD5 加密）"),
    OAID("oaid", "String", 1, "开发匿名设备标识符"),
    OAID_MD5("oaidMd5", "String", 1, "android 设备 oaid的Md5"),
    ANDROID_ID("androidId", "String", 0, "android 设备 androidId"),
    ANDROID_ID_MD5("androidIdMd5", "String", 0, "android 设备 androidId"),
    IP("ip", "String", 0, "点击用户的实际 ip"),
    USER_AGENT("userAgent", "String", 0, "用户浏览器 ua"),
    CALLBACK("callback", "String", 1, "回调地址"),
    MODEL("model", "String", 1, "IOS 设备机型"),
    CAID("caid", "String", 1, "中国广告协会互联网广告标识，传输时需 urlencode 编码处理"),
    REQ_ID("reqId", "String", 0, "RTA 接口返回的请求 ID"),
    ACCOUNT_ID("accountId", "String", 0, "我们规定的账户id,用于同一pid区分不出来的情况下,使用accountId区分"),

    ;


    private String name;
    private String type;
    private Integer necessary;
    private String remark;

    BupetParamEnum(String name, String type, Integer necessary, String remark) {
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


    public static Map<BupetParamEnum, XiaomiParamEnum> bupetXiaomiMap;

    static {
        bupetXiaomiMap = new HashMap<>();
        bupetXiaomiMap.put(IMEI, XiaomiParamEnum.IMEI);
        bupetXiaomiMap.put(IDFA, null);
        bupetXiaomiMap.put(IDFA_MD5, null);
        bupetXiaomiMap.put(OAID, XiaomiParamEnum.OAID);
        bupetXiaomiMap.put(OAID_MD5, null);
        bupetXiaomiMap.put(CALLBACK, XiaomiParamEnum.CALLBACK);
        bupetXiaomiMap.put(ANDROID_ID, XiaomiParamEnum.ANDROIDID);
        bupetXiaomiMap.put(IP, XiaomiParamEnum.IP);
        bupetXiaomiMap.put(USER_AGENT, XiaomiParamEnum.UA);
        bupetXiaomiMap.put(CAID, null);
        bupetXiaomiMap.put(ACCOUNT_ID, XiaomiParamEnum.ACCOUNT_ID);

    }


    public static Map<BupetParamEnum, BaiduParamEnum> bupetBaiduMap;

    static {
        bupetBaiduMap = new HashMap<>();
        bupetBaiduMap.put(IMEI, BaiduParamEnum.IMEI_MD5);
        bupetBaiduMap.put(IDFA, BaiduParamEnum.IDFA);
        bupetBaiduMap.put(IDFA_MD5, null);
        bupetBaiduMap.put(OAID, BaiduParamEnum.OAID);
        bupetBaiduMap.put(OAID_MD5, BaiduParamEnum.OAID_MD5);
        bupetBaiduMap.put(CALLBACK, BaiduParamEnum.CALLBACK_URL);
        bupetBaiduMap.put(ANDROID_ID, null);
        bupetBaiduMap.put(ANDROID_ID_MD5, BaiduParamEnum.ANDROID_ID_MD5);
        bupetBaiduMap.put(IP, BaiduParamEnum.IP);
        bupetBaiduMap.put(USER_AGENT, BaiduParamEnum.UA);
        bupetBaiduMap.put(CAID, null);
        bupetBaiduMap.put(ACCOUNT_ID, BaiduParamEnum.ACCOUNT_ID);

    }

}
