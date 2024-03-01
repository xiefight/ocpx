package huihuang.proxy.ocpx.ads.huihuangmingtian;

import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiParamEnum;
import huihuang.proxy.ocpx.channel.iqiyi.IQiyiParamEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiParamEnum;

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
    CHANNEL("channel", "String", 2, "渠道标识"),
    ADID("adid", "String", 2, "渠道标识"),
    UID("uid", "String", 2, "来自优酷 pid，拉新需要，拉活不需要"),


    ACCOUNT_ID("account_id", "String", 1, ""),

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
        huihuangmingtianHuaweiMap.put(SOURCE, HuaweiParamEnum.HUIHUANG_SOURCE);
        huihuangmingtianHuaweiMap.put(ADVERTISING_SPACE_ID, HuaweiParamEnum.HUIHUANG_ADVERTISING_SPACE_ID);
        huihuangmingtianHuaweiMap.put(AID, HuaweiParamEnum.HUIHUANG_AID);
        huihuangmingtianHuaweiMap.put(CHANNEL, HuaweiParamEnum.HUIHUANG_CHANNEL);
        huihuangmingtianHuaweiMap.put(ADID, HuaweiParamEnum.HUIHUANG_ADID);
        huihuangmingtianHuaweiMap.put(IMEI_MD5, HuaweiParamEnum.ID_TYPE);
        huihuangmingtianHuaweiMap.put(OAID, HuaweiParamEnum.OAID);
        huihuangmingtianHuaweiMap.put(OAID_MD5, null);
        huihuangmingtianHuaweiMap.put(IDFA, null);
        huihuangmingtianHuaweiMap.put(IDFA_MD5, null);
        huihuangmingtianHuaweiMap.put(CAMPAIGN_ID, HuaweiParamEnum.CAMPAIGN_ID);

        huihuangmingtianHuaweiMap.put(TMS, HuaweiParamEnum.TRACE_TIME);
        huihuangmingtianHuaweiMap.put(OS, HuaweiParamEnum.OS_VERSION);
        huihuangmingtianHuaweiMap.put(IP, HuaweiParamEnum.IP);
        huihuangmingtianHuaweiMap.put(UA, HuaweiParamEnum.USER_AGENT);
        huihuangmingtianHuaweiMap.put(CALLBACK_URL, HuaweiParamEnum.CALLBACK);

        huihuangmingtianHuaweiMap.put(ACCOUNT_ID, HuaweiParamEnum.ACCOUNT_ID);

    }

    public static Map<HuihuangmingtianParamEnum, BaiduParamEnum> huihuangmingtianBaiduMap;

    static {
        huihuangmingtianBaiduMap = new HashMap<>();
        huihuangmingtianBaiduMap.put(CHAIN_CODE, BaiduParamEnum.HUIHUANG_CHAIN_CODE);
        huihuangmingtianBaiduMap.put(TASKID, BaiduParamEnum.HUIHUANG_TASK_ID);
        huihuangmingtianBaiduMap.put(APP, BaiduParamEnum.HUIHUANG_APP);
        huihuangmingtianBaiduMap.put(SOURCE, BaiduParamEnum.HUIHUANG_SOURCE);
        huihuangmingtianBaiduMap.put(ADVERTISING_SPACE_ID, BaiduParamEnum.HUIHUANG_ADVERTISING_SPACE_ID);
        huihuangmingtianBaiduMap.put(AID, BaiduParamEnum.HUIHUANG_AID);
        huihuangmingtianBaiduMap.put(CHANNEL, BaiduParamEnum.HUIHUANG_CHANNEL);
        huihuangmingtianBaiduMap.put(ADID, BaiduParamEnum.HUIHUANG_ADID);
        huihuangmingtianBaiduMap.put(IMEI_MD5, BaiduParamEnum.IMEI_MD5);
        huihuangmingtianBaiduMap.put(OAID, BaiduParamEnum.OAID);
        huihuangmingtianBaiduMap.put(OAID_MD5, BaiduParamEnum.OAID_MD5);
        huihuangmingtianBaiduMap.put(IDFA, BaiduParamEnum.IDFA);
        huihuangmingtianBaiduMap.put(IDFA_MD5, null);
        huihuangmingtianBaiduMap.put(CAMPAIGN_ID, null);

        huihuangmingtianBaiduMap.put(TMS, BaiduParamEnum.TS);
        huihuangmingtianBaiduMap.put(OS, BaiduParamEnum.OS_TYPE);
        huihuangmingtianBaiduMap.put(IP, BaiduParamEnum.IP);
        huihuangmingtianBaiduMap.put(UA, BaiduParamEnum.UA);
        huihuangmingtianBaiduMap.put(CALLBACK_URL, BaiduParamEnum.CALLBACK_URL);

        huihuangmingtianBaiduMap.put(ACCOUNT_ID, BaiduParamEnum.ACCOUNT_ID);
    }


    public static Map<HuihuangmingtianParamEnum, XiaomiParamEnum> huihuangmingtianXiaomiMap;

    static {
        huihuangmingtianXiaomiMap = new HashMap<>();
        huihuangmingtianXiaomiMap.put(CHAIN_CODE, XiaomiParamEnum.HUIHUANG_CHAIN_CODE);
        huihuangmingtianXiaomiMap.put(TASKID, XiaomiParamEnum.HUIHUANG_TASK_ID);
        huihuangmingtianXiaomiMap.put(APP, XiaomiParamEnum.HUIHUANG_APP);
        huihuangmingtianXiaomiMap.put(SOURCE, XiaomiParamEnum.HUIHUANG_SOURCE);
        huihuangmingtianXiaomiMap.put(ADVERTISING_SPACE_ID, XiaomiParamEnum.HUIHUANG_ADVERTISING_SPACE_ID);
        huihuangmingtianXiaomiMap.put(AID, XiaomiParamEnum.HUIHUANG_AID);
        huihuangmingtianXiaomiMap.put(CHANNEL, XiaomiParamEnum.HUIHUANG_CHANNEL);
        huihuangmingtianXiaomiMap.put(ADID, XiaomiParamEnum.HUIHUANG_ADID);
        huihuangmingtianXiaomiMap.put(IMEI_MD5, XiaomiParamEnum.IMEI);
        huihuangmingtianXiaomiMap.put(OAID, XiaomiParamEnum.OAID);
        huihuangmingtianXiaomiMap.put(OAID_MD5, null);
        huihuangmingtianXiaomiMap.put(IDFA, null);
        huihuangmingtianXiaomiMap.put(IDFA_MD5, null);
        huihuangmingtianXiaomiMap.put(CAMPAIGN_ID, XiaomiParamEnum.CAMPAIGNID);
        huihuangmingtianXiaomiMap.put(UID, XiaomiParamEnum.HUIHUANG_UID);

        huihuangmingtianXiaomiMap.put(TMS, XiaomiParamEnum.TS);
        huihuangmingtianXiaomiMap.put(OS, null);
        huihuangmingtianXiaomiMap.put(IP, XiaomiParamEnum.IP);
        huihuangmingtianXiaomiMap.put(UA, XiaomiParamEnum.UA);
        huihuangmingtianXiaomiMap.put(CALLBACK_URL, XiaomiParamEnum.CALLBACK);

        huihuangmingtianXiaomiMap.put(ACCOUNT_ID, XiaomiParamEnum.ACCOUNT_ID);

    }



    public static Map<HuihuangmingtianParamEnum, IQiyiParamEnum> huihuangmingtianIQiyiMap;

    static {
        huihuangmingtianIQiyiMap = new HashMap<>();
        huihuangmingtianIQiyiMap.put(CHAIN_CODE, IQiyiParamEnum.HUIHUANG_CHAIN_CODE);
        huihuangmingtianIQiyiMap.put(TASKID, IQiyiParamEnum.HUIHUANG_TASK_ID);
        huihuangmingtianIQiyiMap.put(APP, IQiyiParamEnum.HUIHUANG_APP);
        huihuangmingtianIQiyiMap.put(SOURCE, IQiyiParamEnum.HUIHUANG_SOURCE);
        huihuangmingtianIQiyiMap.put(ADVERTISING_SPACE_ID, IQiyiParamEnum.HUIHUANG_ADVERTISING_SPACE_ID);
        huihuangmingtianIQiyiMap.put(AID, IQiyiParamEnum.HUIHUANG_AID);
        huihuangmingtianIQiyiMap.put(CHANNEL, IQiyiParamEnum.HUIHUANG_CHANNEL);
        huihuangmingtianIQiyiMap.put(ADID, IQiyiParamEnum.HUIHUANG_ADID);
        huihuangmingtianIQiyiMap.put(IMEI_MD5, IQiyiParamEnum.IMEI_MD5);
        huihuangmingtianIQiyiMap.put(OAID, IQiyiParamEnum.OAID);
        huihuangmingtianIQiyiMap.put(OAID_MD5, null);
        huihuangmingtianIQiyiMap.put(IDFA, IQiyiParamEnum.IDFA);
        huihuangmingtianIQiyiMap.put(IDFA_MD5, null);
        huihuangmingtianIQiyiMap.put(CAMPAIGN_ID, null);

        huihuangmingtianIQiyiMap.put(TMS, IQiyiParamEnum.TS);
        huihuangmingtianIQiyiMap.put(OS, IQiyiParamEnum.OS);
        huihuangmingtianIQiyiMap.put(IP, IQiyiParamEnum.IP);
        huihuangmingtianIQiyiMap.put(UA, IQiyiParamEnum.UA);
        huihuangmingtianIQiyiMap.put(CALLBACK_URL, IQiyiParamEnum.CALLBACK_URL);

        huihuangmingtianIQiyiMap.put(ACCOUNT_ID, IQiyiParamEnum.ACCOUNT_ID);
    }

}
