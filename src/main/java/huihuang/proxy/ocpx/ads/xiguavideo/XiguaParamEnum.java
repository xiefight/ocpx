package huihuang.proxy.ocpx.ads.xiguavideo;

import huihuang.proxy.ocpx.channel.wifi.WifiParamEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiParamEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xietao
 * @Date: 2023/5/9 20:26
 */
public enum XiguaParamEnum {

    SIGNATURE("signature", "String", 1, "签名，签名规则见本文档相关章节"),
    TP_ADV_ID("tp_adv_id", "String", 1, "渠道标识，我司分配"),
    ACCESS_ID("access_id", "String", 1, "渠道标识，我司分配 "),
    REQUEST_ID("request_id", "String", 1, "该字段每次请求时不能重复 "),
    IMEI("imei", "String", 1, "imei原生值"),
    IMEI_MD5("imei_md5", "String", 1, "imei原生值的md5， 32位小写"),
    OAID("oaid", "String", 1, "android 广告标识"),
    OAID_MD5("oaid_md5", "String", 1, "android 广告标识 md5值， 32位小写"),
    IDFA("idfa", "String", 1, "idfa原生值"),
    IDFA_MD5("idfa_md5", "String", 1, "idfa原生值的md5 值， 32位小写"),
    ADVERTISER_ID("advertiser_id", "String", 0, "广告主id"),
    ANDROID_ID_MD5("android_id_md5", "String", 0, "安卓id原值的md5，32位"),
    MAC_MD5("mac_md5", "String", 0, "移动设备mac地址,转换成大写字母,去掉“:”，并且取md5摘要后的结果"),
    DEEP_LINK("deep_link", "String", 0, "dp_link 链接，urlencode 编码 "),
    TS("ts", "String", 1, "点击时间，时间戳，单位秒"),
    OS("os", "String", 0, "用户所使 用设备的 系统， 0：android,1: ios, 2:windowsphone, 3:other"),
    IP("ip", "String", 0, "媒体投放系统获取的⽤户终端的公共IP地址"),
    IP_MD5("ip_md5", "String", 0, "ip地址md5值"),
    UA("ua", "String", 0, "用户代理(User Agent)，urlencode 编码"),
    CALLBACK_URL("callback_url", "String", 0, "渠道回调地址"),

    WIFI_SID("wifiSid","String",1,"wifi万能钥匙侧的广告检索ID，需要保存")

    ;

    private String name;
    private String type;
    private Integer necessary;
    private String remark;

    XiguaParamEnum(String name, String type, Integer necessary, String remark) {
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
     * xiaomi-xigua
     */
    public static Map<XiguaParamEnum, XiaomiParamEnum> xiguaXiaomiMap;

    static {
        xiguaXiaomiMap = new HashMap<>();
        xiguaXiaomiMap.put(TP_ADV_ID, XiaomiParamEnum.LTJD_TP_ADV_ID);
        xiguaXiaomiMap.put(ACCESS_ID, XiaomiParamEnum.LTJD_ACCESS_ID);
        xiguaXiaomiMap.put(REQUEST_ID, null);//每次请求都不一样，以当前时间戳区分
        xiguaXiaomiMap.put(IMEI, null);//xiaomi没有imei的原值
        xiguaXiaomiMap.put(IMEI_MD5, XiaomiParamEnum.IMEI);
        xiguaXiaomiMap.put(OAID, XiaomiParamEnum.OAID);
        xiguaXiaomiMap.put(OAID_MD5, null);
        xiguaXiaomiMap.put(IDFA, null);// todo ios至少填一个，未确定
        xiguaXiaomiMap.put(IDFA_MD5, null);
        xiguaXiaomiMap.put(ADVERTISER_ID, null);//todo ltjd需要广告主id，未确定
        xiguaXiaomiMap.put(ANDROID_ID_MD5, XiaomiParamEnum.ANDROIDID);
        xiguaXiaomiMap.put(MAC_MD5, null);
        xiguaXiaomiMap.put(DEEP_LINK, null);
        xiguaXiaomiMap.put(TS, XiaomiParamEnum.TS);//注意：ltjd是秒，xiaomi是毫秒
        xiguaXiaomiMap.put(OS, null);//todo 未确定
        xiguaXiaomiMap.put(IP, XiaomiParamEnum.IP);
        xiguaXiaomiMap.put(IP_MD5, null);
        xiguaXiaomiMap.put(UA, XiaomiParamEnum.UA);
        xiguaXiaomiMap.put(CALLBACK_URL, XiaomiParamEnum.CALLBACK);
    }


    /**
     * wifi-xigua
     */
    public static Map<XiguaParamEnum, WifiParamEnum> xiguaWifiMap;

    static {
        xiguaWifiMap = new HashMap<>();
        xiguaWifiMap.put(TP_ADV_ID, WifiParamEnum.XIGUA_TP_ADV_ID);
        xiguaWifiMap.put(ACCESS_ID, WifiParamEnum.XIGUA_ACCESS_ID);
        xiguaWifiMap.put(REQUEST_ID, null);//每次请求都不一样，以当前时间戳区分
        xiguaWifiMap.put(IMEI, WifiParamEnum.PLAIN_IMEI);//xiaomi没有imei的原值
        xiguaWifiMap.put(IMEI_MD5, WifiParamEnum.IMEI);
        xiguaWifiMap.put(OAID, WifiParamEnum.OAID);
        xiguaWifiMap.put(OAID_MD5, WifiParamEnum.HASH_OAID);
        xiguaWifiMap.put(IDFA, WifiParamEnum.PLAIN_IDFA);// todo ios至少填一个，未确定
        xiguaWifiMap.put(IDFA_MD5, WifiParamEnum.IDFA);
        xiguaWifiMap.put(ADVERTISER_ID, WifiParamEnum.AID);//todo ltjd需要广告主id，未确定
        xiguaWifiMap.put(ANDROID_ID_MD5, WifiParamEnum.ANDROID_ID);
        xiguaWifiMap.put(MAC_MD5, WifiParamEnum.MAC);
        xiguaWifiMap.put(DEEP_LINK, null);
        xiguaWifiMap.put(TS, WifiParamEnum.STIME);//注意：ltjd是秒，xiaomi是毫秒
        xiguaWifiMap.put(OS, WifiParamEnum.OS);//todo 未确定
        xiguaWifiMap.put(IP, null);
        xiguaWifiMap.put(IP_MD5, null);
        xiguaWifiMap.put(UA, null);
        xiguaWifiMap.put(CALLBACK_URL, null);
        xiguaWifiMap.put(WIFI_SID, WifiParamEnum.SID);
    }

}
