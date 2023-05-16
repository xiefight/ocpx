package huihuang.proxy.ocpx.ads.tianmao;

import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiParamEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xietao
 * @Date: 2023/5/16 20:24
 */
public enum TianmaoParamEnum {

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

    ;

    private String name;
    private String type;
    private Integer necessary;
    private String remark;

    TianmaoParamEnum(String name, String type, Integer necessary, String remark) {
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
     * xiaomi-tianmao
     */
    public static Map<TianmaoParamEnum, XiaomiParamEnum> tianmaoXiaomiMap;

    static {
        tianmaoXiaomiMap = new HashMap<>();
        tianmaoXiaomiMap.put(TP_ADV_ID, XiaomiParamEnum.LTJD_TP_ADV_ID);
        tianmaoXiaomiMap.put(ACCESS_ID, XiaomiParamEnum.LTJD_ACCESS_ID);
        tianmaoXiaomiMap.put(REQUEST_ID, null);//每次请求都不一样，以当前时间戳区分
        tianmaoXiaomiMap.put(IMEI, null);//xiaomi没有imei的原值
        tianmaoXiaomiMap.put(IMEI_MD5, XiaomiParamEnum.IMEI);
        tianmaoXiaomiMap.put(OAID, XiaomiParamEnum.OAID);
        tianmaoXiaomiMap.put(OAID_MD5, null);
        tianmaoXiaomiMap.put(IDFA, null);
        tianmaoXiaomiMap.put(IDFA_MD5, null);
        tianmaoXiaomiMap.put(ADVERTISER_ID, null);
        tianmaoXiaomiMap.put(ANDROID_ID_MD5, XiaomiParamEnum.ANDROIDID);
        tianmaoXiaomiMap.put(MAC_MD5, null);
        tianmaoXiaomiMap.put(DEEP_LINK, null);
        tianmaoXiaomiMap.put(TS, XiaomiParamEnum.TS);//注意：ltjd是秒，xiaomi是毫秒
        tianmaoXiaomiMap.put(OS, null);
        tianmaoXiaomiMap.put(IP, XiaomiParamEnum.IP);
        tianmaoXiaomiMap.put(IP_MD5, null);
        tianmaoXiaomiMap.put(UA, XiaomiParamEnum.UA);
        tianmaoXiaomiMap.put(CALLBACK_URL, XiaomiParamEnum.CALLBACK);
    }

    /**
     * baidu-tianmao
     */
    public static Map<TianmaoParamEnum, BaiduParamEnum> tianmaoBaiduMap;

    static {
        tianmaoBaiduMap = new HashMap<>();
        tianmaoBaiduMap.put(TP_ADV_ID, BaiduParamEnum.YOUKU_TP_ADV_ID);
        tianmaoBaiduMap.put(ACCESS_ID, BaiduParamEnum.YOUKU_ACCESS_ID);
        tianmaoBaiduMap.put(REQUEST_ID, null);//每次请求都不一样，以当前时间戳区分
        tianmaoBaiduMap.put(IMEI, null);
        tianmaoBaiduMap.put(IMEI_MD5, BaiduParamEnum.IMEI_MD5);
        tianmaoBaiduMap.put(OAID, BaiduParamEnum.OAID);
        tianmaoBaiduMap.put(OAID_MD5, BaiduParamEnum.OAID_MD5);
        tianmaoBaiduMap.put(IDFA, BaiduParamEnum.IDFA);
        tianmaoBaiduMap.put(IDFA_MD5, null);
        tianmaoBaiduMap.put(ADVERTISER_ID, null);
        tianmaoBaiduMap.put(ANDROID_ID_MD5, BaiduParamEnum.ANDROID_ID_MD5);
        tianmaoBaiduMap.put(MAC_MD5, BaiduParamEnum.MAC_MD5);
        tianmaoBaiduMap.put(DEEP_LINK, BaiduParamEnum.DEEPLINK_URL);
        tianmaoBaiduMap.put(TS, BaiduParamEnum.TS);
        tianmaoBaiduMap.put(OS, BaiduParamEnum.OS_TYPE);
        tianmaoBaiduMap.put(IP, BaiduParamEnum.IP);
        tianmaoBaiduMap.put(IP_MD5, null);
        tianmaoBaiduMap.put(UA, BaiduParamEnum.UA);
        tianmaoBaiduMap.put(CALLBACK_URL, BaiduParamEnum.CALLBACK_URL);
    }


}
