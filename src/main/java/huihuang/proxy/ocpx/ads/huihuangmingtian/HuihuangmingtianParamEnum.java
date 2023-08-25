package huihuang.proxy.ocpx.ads.huihuangmingtian;

import huihuang.proxy.ocpx.channel.huawei.HuaweiParamEnum;

import java.util.HashMap;
import java.util.Map;

public enum HuihuangmingtianParamEnum {
    CHAIN_CODE("chainCode", "String", 1, "链路编码,正式编码由辉煌明天提供"),
    SOURCE("source", "String", 2, "资源号，由辉煌明天提供"),
    IDFA("idfa", "String", 1, "idfa 的原生值"),
    IDFA_MD5("idfaMd5", "String", 2, "iOS 设备广告标识 idfa md5值 小写"),
    IMEI_MD5("imeiMd5", "String", 1, "安卓设备广告标识 IMEI md5值 小写"),
    OAID("oaid", "String", 1, "oaid 的原生值"),
    OAID_MD5("oaidMd5", "String", 1, "安卓设备广告标识 oaid md5值 小写"),
    TASKID("taskId", "String", 1, "任务 id，由辉煌明天提供"),
    APP("app", "String", 1, "App 号，14 飞猪,9 点淘"),

    IP("ip", "String", 2, "点击 ip"),
    UA("adAgent", "String", 2, "点 击 数 据 上 报 时http的 header 中的user_agent，一次urlencode 编码"),
    TMS("tms", "String", 1, "点击时间，时间戳，单位毫秒"),

    AID("aid", "String", 1, "广告数据 id"),
    CAMPAIGN_ID("campaignId", "String", 1, "广告数据计划 id"),
    OS("os", "String", 1, "0:android 1:ios"),
    CALLBACK_URL("callbackUrl", "String", 1, "渠道回调地址"),
    ADVERTISING_SPACE_ID("advertisingSpaceId", "String", 2, "广告位 id，由辉煌明天提供"),

    ;

    private String name;
    private String type;
    private Integer necessary;
    private String remark;

    HuihuangmingtianParamEnum(String name, String type, Integer necessary, String remark) {
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

    public static Map<HuihuangmingtianParamEnum, HuaweiParamEnum> huihuangmingtianHuaweiMap;

    static {
        huihuangmingtianHuaweiMap = new HashMap<>();
        huihuangmingtianHuaweiMap.put(CHAIN_CODE, HuaweiParamEnum.HUIHUANG_CHAIN_CODE);
        huihuangmingtianHuaweiMap.put(TASKID, HuaweiParamEnum.HUIHUANG_TASK_ID);
        huihuangmingtianHuaweiMap.put(APP, HuaweiParamEnum.HUIHUANG_APP);
        huihuangmingtianHuaweiMap.put(SOURCE, HuaweiParamEnum.HUIHUANG_APP);
        huihuangmingtianHuaweiMap.put(ADVERTISING_SPACE_ID, HuaweiParamEnum.HUIHUANG_ADVERTISING_SPACE_ID);
        huihuangmingtianHuaweiMap.put(AID, HuaweiParamEnum.HUIHUANG_AID);
        huihuangmingtianHuaweiMap.put(IMEI_MD5, HuaweiParamEnum.ID_TYPE);
        huihuangmingtianHuaweiMap.put(OAID, HuaweiParamEnum.OAID);
        huihuangmingtianHuaweiMap.put(OAID_MD5, null);
        huihuangmingtianHuaweiMap.put(IDFA, null);
        huihuangmingtianHuaweiMap.put(IDFA_MD5, null);

        huihuangmingtianHuaweiMap.put(TMS, HuaweiParamEnum.TRACE_TIME);
        huihuangmingtianHuaweiMap.put(OS, HuaweiParamEnum.OS_VERSION);
        huihuangmingtianHuaweiMap.put(IP, HuaweiParamEnum.IP);
        huihuangmingtianHuaweiMap.put(UA, HuaweiParamEnum.USER_AGENT);
        huihuangmingtianHuaweiMap.put(CALLBACK_URL, HuaweiParamEnum.CALLBACK);
    }


}
