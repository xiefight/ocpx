package huihuang.proxy.ocpx.ads.jiyue;

import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;

import java.util.HashMap;
import java.util.Map;

public enum JiyueParamEnum {

    OS("os", "String", 1, "android 或 ios；"),
    IMEI("imei", "String", 1, "imei转小写后32位 MD5无符号 "),
    OAID("oaid", "String", 1, "OAID明文 优先"),
    OAID_MD5("oaidmd5", "String", 1, "OAID  明文直接取 32 位MD5 值"),
    IDFA("idfa", "String", 1, "IOS idfa明文设备号 优先"),
    IDFA_MD5("idfamd5", "String", 2, "IDFA 转大写后取 32 位MD5 值"),
    CAID("caid", "String", 1, "CAID明文  idfa和caid至少一项必填"),
    IP("ip", "String", 1, "IP地址，明文"),
    UA("ua", "String", 1, "User Agent信息，明文"),
    ANDROID_ID("androidid", "String", 1, "androidid原值进行MD5，32位无符号"),
    MAC("mac", "String", 1, "移动设备 mac 地址,转换成大写后,取md5，32 位无符号"),
    TS("ts", "String", 1, "点击时间，时间戳，单位毫秒"),
    CALLBACK("callback", "String", 1, "渠道回调地址"),

    ACCOUNT_ID("account_id", "String", 1, ""),

    JIYUE_TYPE("type", "String", 1, ""),
    JIYUE_TOKEN("token", "String", 1, ""),

    ;

    private String name;
    private String type;
    private Integer necessary;
    private String remark;

    JiyueParamEnum(String name, String type, Integer necessary, String remark) {
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

    /*public static Map<JiyueParamEnum, XiaomiParamEnum> huihuiXiaomiMap;

    static {
        huihuiXiaomiMap = new HashMap<>();
        huihuiXiaomiMap.put(AID, XiaomiParamEnum.TANTAN_AID);
        huihuiXiaomiMap.put(SID, XiaomiParamEnum.TANTAN_SID);
        huihuiXiaomiMap.put(CONV_EXT, XiaomiParamEnum.TANTAN_CONV_EXT);
        huihuiXiaomiMap.put(IMEI, XiaomiParamEnum.IMEI);
        huihuiXiaomiMap.put(OAID, XiaomiParamEnum.OAID);
        huihuiXiaomiMap.put(OAID_MD5, null);
        huihuiXiaomiMap.put(IDFA, null);// 小米无idfa
        huihuiXiaomiMap.put(IDFA_MD5, null);
        huihuiXiaomiMap.put(CAID, null);
        huihuiXiaomiMap.put(CAID_MD5, null);
        huihuiXiaomiMap.put(TS, XiaomiParamEnum.TS);//注意：ltjd是秒，xiaomi是毫秒
        huihuiXiaomiMap.put(OS, null);// 未确定
        huihuiXiaomiMap.put(IP, XiaomiParamEnum.IP);
        huihuiXiaomiMap.put(UA, XiaomiParamEnum.UA);
        huihuiXiaomiMap.put(CALLBACK, XiaomiParamEnum.CALLBACK);
        huihuiXiaomiMap.put(REQ_ID, null);
        huihuiXiaomiMap.put(AAID, null);
        huihuiXiaomiMap.put(MODEL, null);//小米机型？
        huihuiXiaomiMap.put(OCPX_ACCOUNT, XiaomiParamEnum.ACCOUNT_ID);
    }*/


    public static Map<JiyueParamEnum, BaiduParamEnum> jiyueBaiduMap;

    static {
        jiyueBaiduMap = new HashMap<>();
        jiyueBaiduMap.put(IMEI, BaiduParamEnum.IMEI_MD5);
        jiyueBaiduMap.put(OAID, BaiduParamEnum.OAID);
        jiyueBaiduMap.put(OAID_MD5, BaiduParamEnum.OAID_MD5);
        jiyueBaiduMap.put(IDFA, BaiduParamEnum.IDFA);
        jiyueBaiduMap.put(IDFA_MD5, null);
        jiyueBaiduMap.put(CAID, BaiduParamEnum.CAID);
        jiyueBaiduMap.put(TS, BaiduParamEnum.TS);
        jiyueBaiduMap.put(OS, BaiduParamEnum.OS_TYPE);
        jiyueBaiduMap.put(IP, BaiduParamEnum.IP);
        jiyueBaiduMap.put(UA, BaiduParamEnum.UA);
        jiyueBaiduMap.put(CALLBACK, BaiduParamEnum.CALLBACK_URL);
        jiyueBaiduMap.put(ACCOUNT_ID, BaiduParamEnum.ACCOUNT_ID);
        jiyueBaiduMap.put(JIYUE_TYPE, BaiduParamEnum.JIYUE_TYPE);
        jiyueBaiduMap.put(JIYUE_TOKEN, BaiduParamEnum.JIYUE_TOKEN);
    }


    /*public static Map<JiyueParamEnum, HuaweiParamEnum> huihuiHuaweiMap;

    static {
        huihuiHuaweiMap = new HashMap<>();
        huihuiHuaweiMap.put(AID, HuaweiParamEnum.TUHU_AID);
        huihuiHuaweiMap.put(SID, HuaweiParamEnum.TUHU_SID);
        huihuiHuaweiMap.put(CONV_EXT, HuaweiParamEnum.TUHU_CONV_EXT);
        huihuiHuaweiMap.put(IMEI, HuaweiParamEnum.ID_TYPE);
        huihuiHuaweiMap.put(OAID, HuaweiParamEnum.OAID);
        huihuiHuaweiMap.put(OAID_MD5, null);
        huihuiHuaweiMap.put(IDFA, null);
        huihuiHuaweiMap.put(IDFA_MD5, null);
        huihuiHuaweiMap.put(CAID, null);
        huihuiHuaweiMap.put(CAID_MD5, null);
        huihuiHuaweiMap.put(TS, null);
        huihuiHuaweiMap.put(OS, null);
        huihuiHuaweiMap.put(IP, HuaweiParamEnum.IP);
        huihuiHuaweiMap.put(UA, HuaweiParamEnum.USER_AGENT);
        huihuiHuaweiMap.put(CALLBACK, HuaweiParamEnum.CALLBACK);
        huihuiHuaweiMap.put(REQ_ID, null);
        huihuiHuaweiMap.put(AAID, null);
        huihuiHuaweiMap.put(MODEL, null);
        huihuiHuaweiMap.put(OCPX_ACCOUNT, HuaweiParamEnum.HUIHUI_ACCOUNT);
    }*/


    /*public static Map<JiyueParamEnum, IQiyiParamEnum> huihuiIQiyiMap;

    static {
        huihuiIQiyiMap = new HashMap<>();
        huihuiIQiyiMap.put(AID, IQiyiParamEnum.HUIHUIYOUDAO_AID);
        huihuiIQiyiMap.put(SID, IQiyiParamEnum.HUIHUIYOUDAO_SID);
        huihuiIQiyiMap.put(CONV_EXT, IQiyiParamEnum.HUIHUIYOUDAO_CONV_EXT);
        huihuiIQiyiMap.put(IMEI, IQiyiParamEnum.IMEI_MD5);
        huihuiIQiyiMap.put(OAID, IQiyiParamEnum.OAID);
        huihuiIQiyiMap.put(OAID_MD5, null);
        huihuiIQiyiMap.put(IDFA, IQiyiParamEnum.IDFA);
        huihuiIQiyiMap.put(IDFA_MD5, null);
        huihuiIQiyiMap.put(CAID, null);
        huihuiIQiyiMap.put(CAID_MD5, null);
        huihuiIQiyiMap.put(TS, IQiyiParamEnum.TS);
        huihuiIQiyiMap.put(OS, IQiyiParamEnum.OS);
        huihuiIQiyiMap.put(IP, IQiyiParamEnum.IP);
        huihuiIQiyiMap.put(UA, IQiyiParamEnum.UA);
        huihuiIQiyiMap.put(CALLBACK, IQiyiParamEnum.CALLBACK_URL);
        huihuiIQiyiMap.put(REQ_ID, null);
        huihuiIQiyiMap.put(AAID, null);
        huihuiIQiyiMap.put(MODEL, null);
        huihuiIQiyiMap.put(OCPX_ACCOUNT, IQiyiParamEnum.ACCOUNT_ID);
    }*/

    /*public static Map<JiyueParamEnum, GuangdiantongParamEnum> huihuiGDTMap;

    static {
        huihuiGDTMap = new HashMap<>();
        huihuiGDTMap.put(AID, GuangdiantongParamEnum.HUIHUIYOUDAO_AID);
        huihuiGDTMap.put(SID, GuangdiantongParamEnum.HUIHUIYOUDAO_SID);
        huihuiGDTMap.put(CONV_EXT, GuangdiantongParamEnum.HUIHUIYOUDAO_CONV_EXT);
        huihuiGDTMap.put(IMEI, GuangdiantongParamEnum.MUID);
        huihuiGDTMap.put(OAID, null);
        huihuiGDTMap.put(OAID_MD5, GuangdiantongParamEnum.HASH_OAID);
        huihuiGDTMap.put(IDFA, null);
        huihuiGDTMap.put(IDFA_MD5, GuangdiantongParamEnum.MUID);
        huihuiGDTMap.put(CAID, null);
        huihuiGDTMap.put(CAID_MD5, null);
        huihuiGDTMap.put(TS, GuangdiantongParamEnum.CLICK_TIME);
        huihuiGDTMap.put(OS, GuangdiantongParamEnum.DEVICE_OS_TYPE);
        huihuiGDTMap.put(IP, GuangdiantongParamEnum.IP);
        huihuiGDTMap.put(UA, GuangdiantongParamEnum.USER_AGENT);
        huihuiGDTMap.put(CALLBACK, GuangdiantongParamEnum.CALLBACK);
        huihuiGDTMap.put(REQ_ID, GuangdiantongParamEnum.REQUEST_ID);
        huihuiGDTMap.put(AAID, null);
        huihuiGDTMap.put(MODEL, null);
        huihuiGDTMap.put(OCPX_ACCOUNT, GuangdiantongParamEnum.ACCOUNT_ID_OWN);
    }*/

    /*public static Map<JiyueParamEnum, HonorParamEnum> huihuiHonorMap;

    static {
        huihuiHonorMap = new HashMap<>();
        huihuiHonorMap.put(AID, HonorParamEnum.HUIHUIYOUDAO_AID);
        huihuiHonorMap.put(SID, HonorParamEnum.HUIHUIYOUDAO_SID);
        huihuiHonorMap.put(CONV_EXT, HonorParamEnum.HUIHUIYOUDAO_CONV_EXT);
        huihuiHonorMap.put(IMEI, null);
        huihuiHonorMap.put(OAID, HonorParamEnum.OAID);
        huihuiHonorMap.put(OAID_MD5, null);
        huihuiHonorMap.put(IDFA, null);
        huihuiHonorMap.put(IDFA_MD5, null);
        huihuiHonorMap.put(CAID, null);
        huihuiHonorMap.put(CAID_MD5, null);
        huihuiHonorMap.put(TS, HonorParamEnum.TIME);
        huihuiHonorMap.put(OS, HonorParamEnum.OS);
        huihuiHonorMap.put(IP, HonorParamEnum.IP);
        huihuiHonorMap.put(UA, HonorParamEnum.UA);
//        huihuiHonorMap.put(CALLBACK, HonorParamEnum.CALLBACK);
        huihuiHonorMap.put(REQ_ID, null);
        huihuiHonorMap.put(AAID, null);
        huihuiHonorMap.put(MODEL, null);
        huihuiHonorMap.put(OCPX_ACCOUNT, HonorParamEnum.ACCOUNT_ID);
    }*/


}
