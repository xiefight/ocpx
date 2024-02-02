package huihuang.proxy.ocpx.ads.luyun;

import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiParamEnum;
import huihuang.proxy.ocpx.channel.iqiyi.IQiyiParamEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiParamEnum;

import java.util.HashMap;
import java.util.Map;

public enum LuyunParamEnum {

    APPID("appid", "String", 1, "广告标识id，由广告主提供"),
    CHANNEL("channel", "String", 1, "渠道表示id,此为该渠道固定值"),
    IMEI("imei", "String", 1, "设备 imei，原值"),
    IMEI_MD5("imei_md5", "String", 1, "设备 imeimd5"),
    OAID("oaid", "String", 1, "设备 oaid，原值"),
    OAID_MD5("oaid_md5", "String", 1, "设备 oaid，md5"),
    ANDROID_ID("androidid", "String", 0, "androidId"),
    IP("ip", "String", 0, "媒体投放系统获取的⽤户终端的公共IP地址"),
    UA("ua", "String", 0, "客户端设备ua"),
    CALLBACK("callback", "String", 0, "回调url或参数"),

    ACCOUNT_ID("account_id", "String", 0, "媒体账户id"),

    ;

    private String name;
    private String type;
    private Integer necessary;
    private String remark;

    LuyunParamEnum(String name, String type, Integer necessary, String remark) {
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

    public static Map<LuyunParamEnum, HuaweiParamEnum> luyunHuaweiMap;

    static {
        luyunHuaweiMap = new HashMap<>();
        luyunHuaweiMap.put(APPID, HuaweiParamEnum.KEEP_APPID);
        luyunHuaweiMap.put(CHANNEL, HuaweiParamEnum.KEEP_CHANNEL);
        luyunHuaweiMap.put(IMEI, HuaweiParamEnum.ID_TYPE);//需要特殊处理
        luyunHuaweiMap.put(IMEI_MD5, null);
        luyunHuaweiMap.put(OAID, HuaweiParamEnum.OAID);
        luyunHuaweiMap.put(OAID_MD5, null);
        luyunHuaweiMap.put(ANDROID_ID, null);
        luyunHuaweiMap.put(IP, HuaweiParamEnum.IP);
        luyunHuaweiMap.put(ACCOUNT_ID, HuaweiParamEnum.ACCOUNT_ID);
        luyunHuaweiMap.put(UA, HuaweiParamEnum.USER_AGENT);
        luyunHuaweiMap.put(CALLBACK, HuaweiParamEnum.CALLBACK);
    }


    public static Map<LuyunParamEnum, BaiduParamEnum> luyunBaiduMap;

    static {
        luyunBaiduMap = new HashMap<>();
        luyunBaiduMap.put(APPID, BaiduParamEnum.KEEP_APPID);
        luyunBaiduMap.put(CHANNEL, BaiduParamEnum.KEEP_CHANNEL);
        luyunBaiduMap.put(IMEI, null);//需要特殊处理
        luyunBaiduMap.put(IMEI_MD5, BaiduParamEnum.IMEI_MD5);
        luyunBaiduMap.put(OAID, BaiduParamEnum.OAID);
        luyunBaiduMap.put(OAID_MD5, BaiduParamEnum.OAID_MD5);
        luyunBaiduMap.put(ANDROID_ID, BaiduParamEnum.ANDROID_ID_MD5);
        luyunBaiduMap.put(IP, BaiduParamEnum.IP);
        luyunBaiduMap.put(ACCOUNT_ID, BaiduParamEnum.ACCOUNT_ID);
        luyunBaiduMap.put(UA, BaiduParamEnum.UA);
        luyunBaiduMap.put(CALLBACK, BaiduParamEnum.CALLBACK_URL);
    }


    public static Map<LuyunParamEnum, XiaomiParamEnum> luyunXiaomiMap;

    static {
        luyunXiaomiMap = new HashMap<>();
        luyunXiaomiMap.put(APPID, XiaomiParamEnum.KEEP_APPID);
        luyunXiaomiMap.put(CHANNEL, XiaomiParamEnum.KEEP_CHANNEL);
        luyunXiaomiMap.put(IMEI, null);//需要特殊处理
        luyunXiaomiMap.put(IMEI_MD5, XiaomiParamEnum.IMEI);
        luyunXiaomiMap.put(OAID, XiaomiParamEnum.OAID);
        luyunXiaomiMap.put(OAID_MD5, null);
        luyunXiaomiMap.put(ANDROID_ID, XiaomiParamEnum.ANDROIDID);
        luyunXiaomiMap.put(IP, XiaomiParamEnum.IP);
        luyunXiaomiMap.put(ACCOUNT_ID, XiaomiParamEnum.ACCOUNT_ID);
        luyunXiaomiMap.put(UA, XiaomiParamEnum.UA);
        luyunXiaomiMap.put(CALLBACK, XiaomiParamEnum.CALLBACK);
    }


    public static Map<LuyunParamEnum, IQiyiParamEnum> luyunIQiyiMap;

    static {
        luyunIQiyiMap = new HashMap<>();
        luyunIQiyiMap.put(APPID, IQiyiParamEnum.KEEP_APPID);
        luyunIQiyiMap.put(CHANNEL, IQiyiParamEnum.KEEP_CHANNEL);
        luyunIQiyiMap.put(IMEI, null);//需要特殊处理
        luyunIQiyiMap.put(IMEI_MD5, IQiyiParamEnum.IMEI_MD5);
        luyunIQiyiMap.put(OAID, IQiyiParamEnum.OAID);
        luyunIQiyiMap.put(OAID_MD5, null);
        luyunIQiyiMap.put(ANDROID_ID, IQiyiParamEnum.ANDROID_ID_MD5);
        luyunIQiyiMap.put(IP, IQiyiParamEnum.IP);
        luyunIQiyiMap.put(ACCOUNT_ID, IQiyiParamEnum.ACCOUNT_ID);
        luyunIQiyiMap.put(UA, IQiyiParamEnum.UA);
        luyunIQiyiMap.put(CALLBACK, IQiyiParamEnum.CALLBACK_URL);
    }

}
