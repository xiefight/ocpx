package huihuang.proxy.ocpx.ads.huihuang;

import huihuang.proxy.ocpx.channel.huawei.HuaweiParamEnum;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @Author: xietao
 * @Date: 2023/7/6 16:23
 */
public enum HuihuangParamEnum {

    CHAIN_CODE("chainCode", "String", 1, "链路编码"),
    UID("uid", "String", 0, "来自优酷 pid，拉新需要，拉活不需要"),
    SID("sid", "String", 0, ""),
    EVENT_TYPE("eventType", "String", 1, "目标 1-拉活 2-拉新"),
    CID("cid", "String", 1, ""),
    IMEI_MD5("imeiMd5", "String", 1, "imei原生值的md5， 32位小写"),
    OAID("oaid", "String", 1, "安卓设备广告标识 oaid 原值"),
    OAID_MD5("oaidMd5", "String", 1, "安卓设备广告标识 oaid md5 值"),
    IDFA("idfa", "String", 1, "iOS 设备广告标识 idfa（iOS必填）"),
    IDFA_MD5("idfaMd5", "String", 1, "iOS 设备广告标识 idfa md5 值"),
    TMS("tms", "String", 1, "点击时间，时间戳，单位毫秒"),
    OS("os", "String", 1, "设备类型 1-ios 2-android"),
    IP("ip", "String", 0, "媒体投放系统获取的⽤户终端的公共IP地址"),
    AID("aid", "String", 0, "数据渠道号，拉新传上述 uid，拉活由辉煌明天提供"),
    AD_AGENT("adAgent", "String", 0, "用户代理(User Agent)，urlencode 编码"),
    CALLBACK_URL("callbackUrl", "String", 1, "渠道回调地址"),
    CAMPAIGN_ID("campaignId", "String", 0, "广告计划 ID"),

    ;

    private String name;
    private String type;
    private Integer necessary;
    private String remark;

    HuihuangParamEnum(String name, String type, Integer necessary, String remark) {
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
    public static Map<HuihuangParamEnum, HuaweiParamEnum> huihuangHuaweiMap;

    static {
        huihuangHuaweiMap = new HashMap<>();
        huihuangHuaweiMap.put(CHAIN_CODE, HuaweiParamEnum.HUIHUANG_CHAIN_CODE);
        huihuangHuaweiMap.put(UID, null);
        huihuangHuaweiMap.put(SID, null);
        huihuangHuaweiMap.put(EVENT_TYPE, null);
        huihuangHuaweiMap.put(CID, null);
        huihuangHuaweiMap.put(IMEI_MD5, HuaweiParamEnum.ID_TYPE);//需要md5
        huihuangHuaweiMap.put(OAID, HuaweiParamEnum.OAID);
        huihuangHuaweiMap.put(OAID_MD5, null);
        huihuangHuaweiMap.put(IDFA, null);
        huihuangHuaweiMap.put(IDFA_MD5, null);
        huihuangHuaweiMap.put(TMS, null);
        huihuangHuaweiMap.put(OS, HuaweiParamEnum.OS_VERSION);
        huihuangHuaweiMap.put(IP, HuaweiParamEnum.IP);
        huihuangHuaweiMap.put(AD_AGENT, HuaweiParamEnum.USER_AGENT);
        huihuangHuaweiMap.put(CALLBACK_URL, HuaweiParamEnum.CALLBACK);
        huihuangHuaweiMap.put(CAMPAIGN_ID, HuaweiParamEnum.CAMPAIGN_ID);
    }


}
