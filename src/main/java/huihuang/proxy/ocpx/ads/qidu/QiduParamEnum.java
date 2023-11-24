package huihuang.proxy.ocpx.ads.qidu;

import huihuang.proxy.ocpx.channel.xiaomi.XiaomiParamEnum;

import java.util.HashMap;
import java.util.Map;

public enum QiduParamEnum {

    APPID("appid", "String", 1, "广告主的appid"),
    CHANNEL("channel", "String", 1, "渠道标识"),
    IDFA("idfa", "String", 1, "iOS设备的idfa原值"),
    IMEI("imei", "String", 1, "安卓设备的imei原值"),
    IMEI_MD5("imei_md5", "String", 0, "安卓设备的imei原值md5加密小写值"),
    OAID("oaid", "String", 1, "安卓设备oaid原值"),
    OAID_MD5("oaid_md5", "String", 0, "安卓设备oaid原值md5加密小写值"),
    ANDROID_ID("androidid", "String", 1, "安卓设备androidid原值"),
    IP("ip", "String", 1, "客户端ip"),
    UA("ua", "String", 0, "客户端设备ua，当ios设备获取不到idfa值时，需要传此值"),
    CLICK_ID("clickid", "String", 0, "点击id"),
    TS("ts", "String", 0, "请求时间（毫秒单位）"),
    CALLBACK("callback", "String", 1, "渠道回调地址"),


    ;

    private String name;
    private String type;
    private Integer necessary;
    private String remark;

    QiduParamEnum(String name, String type, Integer necessary, String remark) {
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


    public static Map<QiduParamEnum, XiaomiParamEnum> qiduXiaomiMap;

    static {
        qiduXiaomiMap = new HashMap<>();
        qiduXiaomiMap.put(APPID, XiaomiParamEnum.QIDU_APPID);
        qiduXiaomiMap.put(CHANNEL, XiaomiParamEnum.QIDU_CHANNEL);
        qiduXiaomiMap.put(IDFA, null);
        qiduXiaomiMap.put(IMEI, null);//
        qiduXiaomiMap.put(IMEI_MD5, XiaomiParamEnum.IMEI);//需要md5
        qiduXiaomiMap.put(OAID, XiaomiParamEnum.OAID);
        qiduXiaomiMap.put(OAID_MD5, XiaomiParamEnum.OAID);
        qiduXiaomiMap.put(IP, XiaomiParamEnum.IP);
        qiduXiaomiMap.put(ANDROID_ID, null);
        qiduXiaomiMap.put(UA, XiaomiParamEnum.UA);
        qiduXiaomiMap.put(CALLBACK, XiaomiParamEnum.CALLBACK);
        qiduXiaomiMap.put(CLICK_ID, null);
        qiduXiaomiMap.put(TS, XiaomiParamEnum.TS);
    }


}
