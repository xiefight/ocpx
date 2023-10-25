package huihuang.proxy.ocpx.ads.liangdamao;

import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiParamEnum;
import huihuang.proxy.ocpx.channel.wifi.WifiParamEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiParamEnum;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @Author: xietao
 * @Date: 2023/5/28 12:09
 */
public enum LiangdamaoParamEnum {

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

    LiangdamaoParamEnum(String name, String type, Integer necessary, String remark) {
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
    public static Map<LiangdamaoParamEnum, XiaomiParamEnum> liangdamaoXiaomiMap;

    static {
        liangdamaoXiaomiMap = new HashMap<>();
        liangdamaoXiaomiMap.put(TP_ADV_ID, XiaomiParamEnum.LTJD_TP_ADV_ID);
        liangdamaoXiaomiMap.put(ACCESS_ID, XiaomiParamEnum.LTJD_ACCESS_ID);
        liangdamaoXiaomiMap.put(REQUEST_ID, null);//每次请求都不一样，以当前时间戳区分
        liangdamaoXiaomiMap.put(IMEI, null);//xiaomi没有imei的原值
        liangdamaoXiaomiMap.put(IMEI_MD5, XiaomiParamEnum.IMEI);
        liangdamaoXiaomiMap.put(OAID, XiaomiParamEnum.OAID);
        liangdamaoXiaomiMap.put(OAID_MD5, null);
        liangdamaoXiaomiMap.put(IDFA, null);// ios至少填一个，未确定
        liangdamaoXiaomiMap.put(IDFA_MD5, null);
        liangdamaoXiaomiMap.put(ADVERTISER_ID, null);// ltjd需要广告主id，未确定
        liangdamaoXiaomiMap.put(ANDROID_ID_MD5, XiaomiParamEnum.ANDROIDID);
        liangdamaoXiaomiMap.put(MAC_MD5, null);
        liangdamaoXiaomiMap.put(DEEP_LINK, null);
        liangdamaoXiaomiMap.put(TS, XiaomiParamEnum.TS);//注意：ltjd是秒，xiaomi是毫秒
        liangdamaoXiaomiMap.put(OS, null);// 未确定
        liangdamaoXiaomiMap.put(IP, XiaomiParamEnum.IP);
        liangdamaoXiaomiMap.put(IP_MD5, null);
        liangdamaoXiaomiMap.put(UA, XiaomiParamEnum.UA);
        liangdamaoXiaomiMap.put(CALLBACK_URL, XiaomiParamEnum.CALLBACK);
    }


    /**
     * baidu-ltjd
     */
    public static Map<LiangdamaoParamEnum, BaiduParamEnum> liangdamaoBaiduMap;

    static {
        liangdamaoBaiduMap = new HashMap<>();
        liangdamaoBaiduMap.put(TP_ADV_ID, BaiduParamEnum.YOUKU_TP_ADV_ID);
        liangdamaoBaiduMap.put(ACCESS_ID, BaiduParamEnum.YOUKU_ACCESS_ID);
        liangdamaoBaiduMap.put(REQUEST_ID, null);//每次请求都不一样，以当前时间戳区分
        liangdamaoBaiduMap.put(IMEI, null);
        liangdamaoBaiduMap.put(IMEI_MD5, BaiduParamEnum.IMEI_MD5);
        liangdamaoBaiduMap.put(OAID, BaiduParamEnum.OAID);
        liangdamaoBaiduMap.put(OAID_MD5, BaiduParamEnum.OAID_MD5);
        liangdamaoBaiduMap.put(IDFA, BaiduParamEnum.IDFA);
        liangdamaoBaiduMap.put(IDFA_MD5, null);
        liangdamaoBaiduMap.put(ADVERTISER_ID, null);
        liangdamaoBaiduMap.put(ANDROID_ID_MD5, BaiduParamEnum.ANDROID_ID_MD5);
        liangdamaoBaiduMap.put(MAC_MD5, BaiduParamEnum.MAC_MD5);
        liangdamaoBaiduMap.put(DEEP_LINK, BaiduParamEnum.DEEPLINK_URL);
        liangdamaoBaiduMap.put(TS, BaiduParamEnum.TS);
        liangdamaoBaiduMap.put(OS, BaiduParamEnum.OS_TYPE);
        liangdamaoBaiduMap.put(IP, BaiduParamEnum.IP);
        liangdamaoBaiduMap.put(IP_MD5, null);
        liangdamaoBaiduMap.put(UA, BaiduParamEnum.UA);
        liangdamaoBaiduMap.put(CALLBACK_URL, BaiduParamEnum.CALLBACK_URL);
    }


    /**
     * huawei-ltjd
     */
    public static Map<LiangdamaoParamEnum, HuaweiParamEnum> liangdamaoHuaweiMap;

    static {
        liangdamaoHuaweiMap = new HashMap<>();
        liangdamaoHuaweiMap.put(TP_ADV_ID, HuaweiParamEnum.YOUKU_TP_ADV_ID);
        liangdamaoHuaweiMap.put(ACCESS_ID, HuaweiParamEnum.YOUKU_ACCESS_ID);
        liangdamaoHuaweiMap.put(REQUEST_ID, null);//每次请求都不一样，以当前时间戳区分
        liangdamaoHuaweiMap.put(IMEI, HuaweiParamEnum.ID_TYPE);
        liangdamaoHuaweiMap.put(IMEI_MD5, null);
        liangdamaoHuaweiMap.put(OAID, HuaweiParamEnum.OAID);
        liangdamaoHuaweiMap.put(OAID_MD5, null);
        liangdamaoHuaweiMap.put(IDFA, null);
        liangdamaoHuaweiMap.put(IDFA_MD5, null);
        liangdamaoHuaweiMap.put(ADVERTISER_ID, null);
        liangdamaoHuaweiMap.put(ANDROID_ID_MD5, null);
        liangdamaoHuaweiMap.put(MAC_MD5, null);
        liangdamaoHuaweiMap.put(DEEP_LINK, null);
        liangdamaoHuaweiMap.put(TS, null);
        liangdamaoHuaweiMap.put(OS, null);
        liangdamaoHuaweiMap.put(IP, HuaweiParamEnum.IP);
        liangdamaoHuaweiMap.put(IP_MD5, null);
        liangdamaoHuaweiMap.put(UA, HuaweiParamEnum.USER_AGENT);
        liangdamaoHuaweiMap.put(CALLBACK_URL, HuaweiParamEnum.CALLBACK);
    }


    /**
     * wifi-xigua
     */
    public static Map<LiangdamaoParamEnum, WifiParamEnum> liangdamaoWifiMap;

    static {
        liangdamaoWifiMap = new HashMap<>();
//        xiguaWifiMap.put(TP_ADV_ID, WifiParamEnum.XIGUA_TP_ADV_ID);
        liangdamaoWifiMap.put(ACCESS_ID, WifiParamEnum.XIGUA_ACCESS_ID);
        liangdamaoWifiMap.put(REQUEST_ID, null);//每次请求都不一样，以当前时间戳区分
        liangdamaoWifiMap.put(IMEI, WifiParamEnum.PLAIN_IMEI);//xiaomi没有imei的原值
        liangdamaoWifiMap.put(IMEI_MD5, WifiParamEnum.IMEI);
        liangdamaoWifiMap.put(OAID, WifiParamEnum.OAID);
        liangdamaoWifiMap.put(OAID_MD5, WifiParamEnum.HASH_OAID);
        liangdamaoWifiMap.put(IDFA, WifiParamEnum.PLAIN_IDFA);// todo ios至少填一个，未确定
        liangdamaoWifiMap.put(IDFA_MD5, WifiParamEnum.IDFA);
        liangdamaoWifiMap.put(ADVERTISER_ID, WifiParamEnum.AID);//todo ltjd需要广告主id，未确定
        liangdamaoWifiMap.put(ANDROID_ID_MD5, WifiParamEnum.ANDROID_ID);
        liangdamaoWifiMap.put(MAC_MD5, WifiParamEnum.MAC);
        liangdamaoWifiMap.put(DEEP_LINK, null);
        liangdamaoWifiMap.put(TS, WifiParamEnum.STIME);//注意：ltjd是秒，xiaomi是毫秒
        liangdamaoWifiMap.put(OS, WifiParamEnum.OS);//todo 未确定
        liangdamaoWifiMap.put(IP, null);
        liangdamaoWifiMap.put(IP_MD5, null);
        liangdamaoWifiMap.put(UA, null);
        liangdamaoWifiMap.put(CALLBACK_URL, null);
    }

}
