package huihuang.proxy.ocpx.ads.quannenghudong;

import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.channel.honor.HonorParamEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiParamEnum;
import huihuang.proxy.ocpx.channel.iqiyi.IQiyiParamEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiParamEnum;

import java.util.HashMap;
import java.util.Map;

public enum QuannengHudongParamEnum {

    PID("pid", "String", 1, "广告商分配的标识"),
    IDFA("idfa", "String", 1, "iOS 设备广告标识 idfa（iOS必填）"),
    IMEI("imei", "String", 1, "imei原生值的md5， 32位小写"),
    OAID("oaid", "String", 1, "安卓设备广告标识 oaid 原值"),
    CAID("caid", "String", 1, "iOS：caid  原值;  需将值和版本号   使 用”_”拼接(若无版 本号使用 0，多个使用”,”拼接)"),
    IP("ip", "String", 0, "媒体投放系统获取的⽤户终端的公共IP地址"),
    ANDROID_ID("android_id", "String", 0, "请求设备android id"),
    AAID("aaid", "String", 0, "阿里设备标识符"),
    UA("ua", "String", 1, "点 击 数 据 上 报 时http的 header 中的user_agent，一次urlencode 编码"),
    OS("os", "String", 1, "设备类型 ios android"),
    MODEL("model", "String", 1, "手机机型"),
    CALLBACK("callback", "String", 1, "渠道回调地址"),
    UNIQUE_ID("uniqueid", "String", 1, "本次请求的唯一id"),
    ACCOUNT_ID("account_id", "String", 0, "我们规定的账户id,用于同一pid区分不出来的情况下,使用accountId区分"),

    ;

    private String name;
    private String type;
    private Integer necessary;
    private String remark;

    QuannengHudongParamEnum(String name, String type, Integer necessary, String remark) {
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


    public static Map<QuannengHudongParamEnum, HuaweiParamEnum> quannengHudongHuaweiMap;

    static {
        quannengHudongHuaweiMap = new HashMap<>();
        quannengHudongHuaweiMap.put(PID, HuaweiParamEnum.PID);
        quannengHudongHuaweiMap.put(IDFA, null);
        quannengHudongHuaweiMap.put(IMEI, HuaweiParamEnum.ID_TYPE);//需要md5
        quannengHudongHuaweiMap.put(OAID, HuaweiParamEnum.OAID);
        quannengHudongHuaweiMap.put(CAID, null);
        quannengHudongHuaweiMap.put(OS, HuaweiParamEnum.OS_VERSION);
        quannengHudongHuaweiMap.put(IP, HuaweiParamEnum.IP);
        quannengHudongHuaweiMap.put(ANDROID_ID, null);
        quannengHudongHuaweiMap.put(AAID, null);
        quannengHudongHuaweiMap.put(UA, HuaweiParamEnum.USER_AGENT);
        quannengHudongHuaweiMap.put(MODEL, null);
        quannengHudongHuaweiMap.put(CALLBACK, HuaweiParamEnum.CALLBACK);
        quannengHudongHuaweiMap.put(UNIQUE_ID, HuaweiParamEnum.QUANNENGHUDONG_UNIQUEID);
        quannengHudongHuaweiMap.put(ACCOUNT_ID, HuaweiParamEnum.ACCOUNT_ID);
    }


    public static Map<QuannengHudongParamEnum, XiaomiParamEnum> quannengHudongXiaomiMap;

    static {
        quannengHudongXiaomiMap = new HashMap<>();
        quannengHudongXiaomiMap.put(PID, XiaomiParamEnum.PID);
        quannengHudongXiaomiMap.put(IDFA, null);
        quannengHudongXiaomiMap.put(IMEI, XiaomiParamEnum.IMEI);//需要md5
        quannengHudongXiaomiMap.put(OAID, XiaomiParamEnum.OAID);
        quannengHudongXiaomiMap.put(CAID, null);
        quannengHudongXiaomiMap.put(OS, null);
        quannengHudongXiaomiMap.put(IP, XiaomiParamEnum.IP);
        quannengHudongXiaomiMap.put(ANDROID_ID, null);
        quannengHudongXiaomiMap.put(AAID, null);
        quannengHudongXiaomiMap.put(UA, XiaomiParamEnum.UA);
        quannengHudongXiaomiMap.put(MODEL, null);
        quannengHudongXiaomiMap.put(CALLBACK, XiaomiParamEnum.CALLBACK);
        quannengHudongXiaomiMap.put(UNIQUE_ID, XiaomiParamEnum.QUANNENGHUDONG_UNIQUEID);
        quannengHudongXiaomiMap.put(ACCOUNT_ID, XiaomiParamEnum.ACCOUNT_ID);
    }


    public static Map<QuannengHudongParamEnum, BaiduParamEnum> quannengHudongBaiduMap;

    static {
        quannengHudongBaiduMap = new HashMap<>();
        quannengHudongBaiduMap.put(PID, BaiduParamEnum.QUANNENGHUDONG_PID);
        quannengHudongBaiduMap.put(IDFA, BaiduParamEnum.IDFA);
        quannengHudongBaiduMap.put(IMEI, BaiduParamEnum.IMEI_MD5);//需要md5
        quannengHudongBaiduMap.put(OAID, BaiduParamEnum.OAID);
        quannengHudongBaiduMap.put(CAID, BaiduParamEnum.CAID);
        quannengHudongBaiduMap.put(OS, BaiduParamEnum.OS_TYPE);
        quannengHudongBaiduMap.put(IP, BaiduParamEnum.IP);
        quannengHudongBaiduMap.put(ANDROID_ID, BaiduParamEnum.ANDROID_ID_MD5);
        quannengHudongBaiduMap.put(AAID, null);
        quannengHudongBaiduMap.put(UA, BaiduParamEnum.UA);
        quannengHudongBaiduMap.put(MODEL, null);
        quannengHudongBaiduMap.put(CALLBACK, BaiduParamEnum.CALLBACK_URL);
        quannengHudongBaiduMap.put(UNIQUE_ID, BaiduParamEnum.QUANNENGHUDONG_UNIQUEID);
        quannengHudongBaiduMap.put(ACCOUNT_ID, BaiduParamEnum.ACCOUNT_ID);
    }


public static Map<QuannengHudongParamEnum, IQiyiParamEnum> quannengHudongIQiyiMap;

    static {
        quannengHudongIQiyiMap = new HashMap<>();
        quannengHudongIQiyiMap.put(PID, IQiyiParamEnum.QUANNENGHUDONG_PID);
        quannengHudongIQiyiMap.put(IDFA, IQiyiParamEnum.IDFA);
        quannengHudongIQiyiMap.put(IMEI, IQiyiParamEnum.IMEI_MD5);//需要md5
        quannengHudongIQiyiMap.put(OAID, IQiyiParamEnum.OAID);
        quannengHudongIQiyiMap.put(CAID, null);
        quannengHudongIQiyiMap.put(OS, IQiyiParamEnum.OS);
        quannengHudongIQiyiMap.put(IP, IQiyiParamEnum.IP);
        quannengHudongIQiyiMap.put(ANDROID_ID, IQiyiParamEnum.ANDROID_ID_MD5);
        quannengHudongIQiyiMap.put(AAID, null);
        quannengHudongIQiyiMap.put(UA, IQiyiParamEnum.UA);
        quannengHudongIQiyiMap.put(MODEL, null);
        quannengHudongIQiyiMap.put(CALLBACK, IQiyiParamEnum.CALLBACK_URL);
        quannengHudongIQiyiMap.put(UNIQUE_ID, IQiyiParamEnum.QUANNENGHUDONG_UNIQUEID);
        quannengHudongIQiyiMap.put(ACCOUNT_ID, IQiyiParamEnum.ACCOUNT_ID);
    }

    public static Map<QuannengHudongParamEnum, HonorParamEnum> quannengHudongHonorMap;

    static {
        quannengHudongHonorMap = new HashMap<>();
        quannengHudongHonorMap.put(PID, HonorParamEnum.QUANNENGHUDONG_PID);
        quannengHudongHonorMap.put(IDFA, null);
        quannengHudongHonorMap.put(IMEI, null);//需要md5
        quannengHudongHonorMap.put(OAID, HonorParamEnum.OAID);
        quannengHudongHonorMap.put(CAID, null);
        quannengHudongHonorMap.put(OS, HonorParamEnum.OS);
        quannengHudongHonorMap.put(IP, HonorParamEnum.IP);
        quannengHudongHonorMap.put(ANDROID_ID, null);
        quannengHudongHonorMap.put(AAID, null);
        quannengHudongHonorMap.put(UA, HonorParamEnum.UA);
        quannengHudongHonorMap.put(MODEL, null);
        quannengHudongHonorMap.put(CALLBACK, null);
        quannengHudongHonorMap.put(UNIQUE_ID, HonorParamEnum.QUANNENGHUDONG_UNIQUEID);
        quannengHudongHonorMap.put(ACCOUNT_ID, HonorParamEnum.ACCOUNT_ID);
    }


}
