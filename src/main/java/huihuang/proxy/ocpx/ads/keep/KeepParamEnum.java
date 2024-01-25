package huihuang.proxy.ocpx.ads.keep;

import huihuang.proxy.ocpx.channel.huawei.HuaweiParamEnum;

import java.util.HashMap;
import java.util.Map;

public enum KeepParamEnum {

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

    KeepParamEnum(String name, String type, Integer necessary, String remark) {
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
     * huawei-kuaishou
     */
    public static Map<KeepParamEnum, HuaweiParamEnum> keepHuaweiMap;

    static {
        keepHuaweiMap = new HashMap<>();
        keepHuaweiMap.put(APPID, HuaweiParamEnum.KEEP_APPID);
        keepHuaweiMap.put(CHANNEL, HuaweiParamEnum.KEEP_CHANNEL);
        keepHuaweiMap.put(IMEI, HuaweiParamEnum.ID_TYPE);//需要特殊处理
        keepHuaweiMap.put(IMEI_MD5, null);
        keepHuaweiMap.put(OAID, HuaweiParamEnum.OAID);
        keepHuaweiMap.put(OAID_MD5, null);
        keepHuaweiMap.put(ANDROID_ID, null);
        keepHuaweiMap.put(IP, HuaweiParamEnum.IP);
        keepHuaweiMap.put(ACCOUNT_ID, HuaweiParamEnum.ACCOUNT_ID);
        keepHuaweiMap.put(UA, HuaweiParamEnum.USER_AGENT);
        keepHuaweiMap.put(CALLBACK, HuaweiParamEnum.CALLBACK);
    }

}
