package huihuang.proxy.ocpx.ads.litianjingdong;

import huihuang.proxy.ocpx.ads.youku.YoukuParamEnum;
import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiParamEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-24 16:28
 **/
public enum LTJDParamEnum {

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

    LTJDParamEnum(String name, String type, Integer necessary, String remark) {
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
    public static Map<LTJDParamEnum, XiaomiParamEnum> xjMap;

    static {
        xjMap = new HashMap<>();
        xjMap.put(TP_ADV_ID, XiaomiParamEnum.LTJD_TP_ADV_ID);
        xjMap.put(ACCESS_ID, XiaomiParamEnum.LTJD_ACCESS_ID);
        xjMap.put(REQUEST_ID, null);//每次请求都不一样，以当前时间戳区分
        xjMap.put(IMEI, null);//xiaomi没有imei的原值
        xjMap.put(IMEI_MD5, XiaomiParamEnum.IMEI);
        xjMap.put(OAID, XiaomiParamEnum.OAID);
        xjMap.put(OAID_MD5, null);
        xjMap.put(IDFA, null);// todo ios至少填一个，未确定
        xjMap.put(IDFA_MD5, null);
        xjMap.put(ADVERTISER_ID, null);//todo ltjd需要广告主id，未确定
        xjMap.put(ANDROID_ID_MD5, XiaomiParamEnum.ANDROIDID);
        xjMap.put(MAC_MD5, null);
        xjMap.put(DEEP_LINK, null);
        xjMap.put(TS, XiaomiParamEnum.TS);//注意：ltjd是秒，xiaomi是毫秒
        xjMap.put(OS, null);//todo 未确定
        xjMap.put(IP, XiaomiParamEnum.IP);
        xjMap.put(IP_MD5, null);
        xjMap.put(UA, XiaomiParamEnum.UA);
        xjMap.put(CALLBACK_URL, XiaomiParamEnum.CALLBACK);
    }


    /**
     * baidu-ltjd
     */
    public static Map<LTJDParamEnum, BaiduParamEnum> baiduLtjdMap;

    static {
        baiduLtjdMap = new HashMap<>();
        baiduLtjdMap.put(TP_ADV_ID, BaiduParamEnum.YOUKU_TP_ADV_ID);
        baiduLtjdMap.put(ACCESS_ID, BaiduParamEnum.YOUKU_ACCESS_ID);
        baiduLtjdMap.put(REQUEST_ID, null);//每次请求都不一样，以当前时间戳区分
        baiduLtjdMap.put(IMEI, null);
        baiduLtjdMap.put(IMEI_MD5, BaiduParamEnum.IMEI_MD5);
        baiduLtjdMap.put(OAID, BaiduParamEnum.OAID);
        baiduLtjdMap.put(OAID_MD5, BaiduParamEnum.OAID_MD5);
        baiduLtjdMap.put(IDFA, BaiduParamEnum.IDFA);
        baiduLtjdMap.put(IDFA_MD5, null);
        baiduLtjdMap.put(ADVERTISER_ID, null);
        baiduLtjdMap.put(ANDROID_ID_MD5, BaiduParamEnum.ANDROID_ID_MD5);
        baiduLtjdMap.put(MAC_MD5, BaiduParamEnum.MAC_MD5);
        baiduLtjdMap.put(DEEP_LINK, BaiduParamEnum.DEEPLINK_URL);
        baiduLtjdMap.put(TS, BaiduParamEnum.TS);
        baiduLtjdMap.put(OS, BaiduParamEnum.OS_TYPE);
        baiduLtjdMap.put(IP, BaiduParamEnum.IP);
        baiduLtjdMap.put(IP_MD5, null);
        baiduLtjdMap.put(UA, BaiduParamEnum.UA);
        baiduLtjdMap.put(CALLBACK_URL, BaiduParamEnum.CALLBACK_URL);
    }

}
