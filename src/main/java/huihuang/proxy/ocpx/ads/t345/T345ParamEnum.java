package huihuang.proxy.ocpx.ads.t345;

import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiParamEnum;
import huihuang.proxy.ocpx.channel.iqiyi.IQiyiParamEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiParamEnum;

import java.util.HashMap;
import java.util.Map;

public enum T345ParamEnum {

    ADID("adId", "String", 1, "广告id, DSP无须关心，且不能更改"),
    CAMPAIGN_ID("campaign_id", "String", 1, "与该事件匹配的计划id"),
    CONTENT_ID("content_id", "String", 1, "素材id，与事件匹配的素材id"),
    MODEL("model", "String", 0, "客户端机型, 例：EVA- AL10"),
    EVENT_ID("event_id", "String", 1, "点击事件唯一标识，可以是时间戳"),
    IP("ip", "String", 1, "客户端 IP 地址, IPV4地址"),
    UA("ua", "String", 1, "终端设备的UserAgent"),
    TS("ts", "integer", 1, "本请求发起的时间戳 unix 时间戳(ms)"),
    OS("os", "integer", 1, "操作系统类型 1:IOS;2:Android; 3鸿蒙"),
    MAC("mac", "String", 0, "mac 地址，Android 设备, 无mac时传空"),
    IMEI("imei", "String", 1, "android 设备 imei"),
    IMEI_MD5("imei_md5", "String", 1, "android 设备 imeiMd5"),
    OAID("oaid", "String", 1, "android 设备 oaid"),
    OAID_MD5("oaid_md5", "String", 1, "android 设备 oaid的Md5"),
    ANDROID_ID("android_id", "String", 0, "Android 设备标识，无android_id时传空"),
    CALLBACK("callback", "String", 1, "callback，将在转化行为数据回传接口中原样带回"),
    ACCOUNT_ID("account_id", "String", 0, "我们规定的账户id,用于同一pid区分不出来的情况下,使用accountId区分"),

    ;


    private String name;
    private String type;
    private Integer necessary;
    private String remark;

    T345ParamEnum(String name, String type, Integer necessary, String remark) {
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


    public static Map<T345ParamEnum, BaiduParamEnum> t345BaiduMap;

    static {
        t345BaiduMap = new HashMap<>();
        t345BaiduMap.put(ADID, BaiduParamEnum.T345_ADID);
        t345BaiduMap.put(CAMPAIGN_ID, BaiduParamEnum.T345_CAMPAIGN_ID);//2345提供
        t345BaiduMap.put(CONTENT_ID, BaiduParamEnum.T345_CONTENT_ID);//2345提供
        t345BaiduMap.put(IMEI, null);
        t345BaiduMap.put(IMEI_MD5, BaiduParamEnum.IMEI_MD5);
        t345BaiduMap.put(OAID, BaiduParamEnum.OAID);
        t345BaiduMap.put(OAID_MD5, BaiduParamEnum.OAID_MD5);
        t345BaiduMap.put(CALLBACK, BaiduParamEnum.CALLBACK_URL);
        t345BaiduMap.put(ANDROID_ID, BaiduParamEnum.ANDROID_ID_MD5); //记得要在converParam方法中进行md5转换
        t345BaiduMap.put(MAC, BaiduParamEnum.MAC);
        t345BaiduMap.put(OS, BaiduParamEnum.OS_TYPE);  //在converParam方法中进行转换
        t345BaiduMap.put(IP, BaiduParamEnum.IP);
        t345BaiduMap.put(UA, BaiduParamEnum.UA);
        t345BaiduMap.put(MODEL, BaiduParamEnum.CAID);
        t345BaiduMap.put(ACCOUNT_ID, BaiduParamEnum.ACCOUNT_ID);

    }

    /*public static Map<T345ParamEnum, XiaomiParamEnum> t345XiaomiMap;

    static {
        t345XiaomiMap = new HashMap<>();
        t345XiaomiMap.put(ADID, XiaomiParamEnum.DINGYUN_ADID);
        t345XiaomiMap.put(TYPE, null);//上报时固定值 2
        t345XiaomiMap.put(IMEI, null);
        t345XiaomiMap.put(IMEI_MD5, XiaomiParamEnum.IMEI);
        t345XiaomiMap.put(IDFA, null);
        t345XiaomiMap.put(IDFA_MD5, null);
        t345XiaomiMap.put(OAID, XiaomiParamEnum.OAID);
        t345XiaomiMap.put(OAID_MD5, null);
        t345XiaomiMap.put(CALLBACK, XiaomiParamEnum.CALLBACK);
        t345XiaomiMap.put(ANDROID_ID, XiaomiParamEnum.ANDROIDID);
        t345XiaomiMap.put(MAC, null);
        t345XiaomiMap.put(IMPRESSION_ID, null);
        t345XiaomiMap.put(IP, XiaomiParamEnum.IP);
        t345XiaomiMap.put(USER_AGENT, XiaomiParamEnum.UA);
        t345XiaomiMap.put(CAID_LIST, null);
        t345XiaomiMap.put(ACCOUNT_ID, XiaomiParamEnum.ACCOUNT_ID);

    }

    public static Map<T345ParamEnum, HuaweiParamEnum> t345HuaweiMap;

    static {
        t345HuaweiMap = new HashMap<>();
        t345HuaweiMap.put(ADID, HuaweiParamEnum.DINGYUN_ADID);
        t345HuaweiMap.put(TYPE, null);//上报时固定值 2
        t345HuaweiMap.put(IMEI, null);
        t345HuaweiMap.put(IMEI_MD5, null);
        t345HuaweiMap.put(IDFA, null);
        t345HuaweiMap.put(IDFA_MD5, null);
        t345HuaweiMap.put(OAID, HuaweiParamEnum.OAID);
        t345HuaweiMap.put(OAID_MD5, null);
        t345HuaweiMap.put(CALLBACK, HuaweiParamEnum.CALLBACK);
        t345HuaweiMap.put(ANDROID_ID, null);
        t345HuaweiMap.put(MAC, null);
        t345HuaweiMap.put(IMPRESSION_ID, null);
        t345HuaweiMap.put(IP, HuaweiParamEnum.IP);
        t345HuaweiMap.put(USER_AGENT, HuaweiParamEnum.USER_AGENT);
        t345HuaweiMap.put(CAID_LIST, null);
        t345HuaweiMap.put(ACCOUNT_ID, HuaweiParamEnum.ACCOUNT_ID);

    }


    public static Map<T345ParamEnum, IQiyiParamEnum> t345IQiyiMap;

    static {
        t345IQiyiMap = new HashMap<>();
        t345IQiyiMap.put(ADID, IQiyiParamEnum.DINGYUN_ADID);
        t345IQiyiMap.put(TYPE, null);//上报时固定值 2
        t345IQiyiMap.put(IMEI, null);
        t345IQiyiMap.put(IMEI_MD5, IQiyiParamEnum.IMEI_MD5);
        t345IQiyiMap.put(IDFA, IQiyiParamEnum.IDFA);
        t345IQiyiMap.put(IDFA_MD5, null);
        t345IQiyiMap.put(OAID, IQiyiParamEnum.OAID);
        t345IQiyiMap.put(OAID_MD5, null);
        t345IQiyiMap.put(CALLBACK, IQiyiParamEnum.CALLBACK_URL);
        t345IQiyiMap.put(ANDROID_ID, IQiyiParamEnum.ANDROID_ID_MD5);
        t345IQiyiMap.put(MAC, IQiyiParamEnum.MAC);
        t345IQiyiMap.put(IMPRESSION_ID, null);
        t345IQiyiMap.put(IP, IQiyiParamEnum.IP);
        t345IQiyiMap.put(USER_AGENT, IQiyiParamEnum.UA);
        t345IQiyiMap.put(CAID_LIST, null);
        t345IQiyiMap.put(ACCOUNT_ID, IQiyiParamEnum.ACCOUNT_ID);

    }*/

}
