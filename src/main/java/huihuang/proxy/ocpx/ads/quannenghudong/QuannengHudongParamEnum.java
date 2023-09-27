package huihuang.proxy.ocpx.ads.quannenghudong;

import huihuang.proxy.ocpx.channel.huawei.HuaweiParamEnum;

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


    /**
     * huawei-huihuang
     */
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
    }


}
