package huihuang.proxy.ocpx.ads.huihui;

import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.channel.guangdiantong.GuangdiantongParamEnum;
import huihuang.proxy.ocpx.channel.honor.HonorParamEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiParamEnum;
import huihuang.proxy.ocpx.channel.iqiyi.IQiyiParamEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiParamEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xietao
 * @Date: 2023/6/8 17:16
 */
public enum HuihuiParamEnum {
    AID("aid", "String", 1, "活动 id"),
    SID("sid", "String", 1, ""),
    CONV_EXT("conv_ext", "String", 1, ""),
    IDFA("idfa", "String", 1, "原值， 大写"),
    IDFA_MD5("idfa_md5", "String", 2, "原值大写后 md5 之后再转大写"),
    IMEI("imei", "String", 1, "md5 之后再转大写，对应渠道的imei_md5，如果只有imei，则先md5，再大写"),
    //    IMEI_MD5("imei_md5", "String", 1, "imei原生值的md5， 32位小写"),
    OAID("oaid", "String", 1, ""),
    OAID_MD5("oaid_md5", "String", 1, "android 广告标识 md5值， 32位大写"),
    CAID("caid", "String", 1, "原值"),
    CAID_MD5("caid_md5", "String", 1, "原值大写后 md5 之后再转大写"),

    REQ_ID("reqid", "String", 1, "原始请求 id；若为RTA 投放，需要回传RTA 接口的请求 id;RTA 投放时必填 "),
    AAID("aaid", "String", 2, "阿里设备标识符"),

    IP("ip", "String", 1, "点击 ip；IPV4:A.B.C.D (四段，以“.”分隔)IPV6:需要 encode 一次 "),
    UA("ua", "String", 1, "点 击 数 据 上 报 时http的 header 中的user_agent，一次urlencode 编码"),
    TS("ts", "String", 1, "点击时间，时间戳，单位毫秒"),

    OS("os", "String", 1, "android 或 ios；"),
    CALLBACK("callback", "String", 1, "渠道回调地址"),
    MODEL("model", "String", 1, "手机机型"),

    OCPX_ACCOUNT("ocpxAccount", "String", 1, ""),

    ;

    private String name;
    private String type;
    private Integer necessary;
    private String remark;

    HuihuiParamEnum(String name, String type, Integer necessary, String remark) {
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

    public static Map<HuihuiParamEnum, XiaomiParamEnum> huihuiXiaomiMap;

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
    }


    public static Map<HuihuiParamEnum, BaiduParamEnum> huihuiBaiduMap;

    static {
        huihuiBaiduMap = new HashMap<>();
        huihuiBaiduMap.put(AID, BaiduParamEnum.XIANYU_AID);
        huihuiBaiduMap.put(SID, BaiduParamEnum.XIANYU_SID);
        huihuiBaiduMap.put(CONV_EXT, BaiduParamEnum.XIANYU_CONV_EXT);
        huihuiBaiduMap.put(IMEI, BaiduParamEnum.IMEI_MD5);
        huihuiBaiduMap.put(OAID, BaiduParamEnum.OAID);
        huihuiBaiduMap.put(OAID_MD5, BaiduParamEnum.OAID_MD5);
        huihuiBaiduMap.put(IDFA, BaiduParamEnum.IDFA);
        huihuiBaiduMap.put(IDFA_MD5, null);
        huihuiBaiduMap.put(CAID, BaiduParamEnum.CAID);
        huihuiBaiduMap.put(CAID_MD5, null);
        huihuiBaiduMap.put(TS, BaiduParamEnum.TS);
        huihuiBaiduMap.put(OS, null);
        huihuiBaiduMap.put(IP, BaiduParamEnum.IP);
        huihuiBaiduMap.put(UA, BaiduParamEnum.UA);
        huihuiBaiduMap.put(CALLBACK, BaiduParamEnum.CALLBACK_URL);
        huihuiBaiduMap.put(REQ_ID, null);
        huihuiBaiduMap.put(AAID, null);
        huihuiBaiduMap.put(MODEL, null);
        huihuiBaiduMap.put(OCPX_ACCOUNT, BaiduParamEnum.ACCOUNT_ID);
    }


    public static Map<HuihuiParamEnum, HuaweiParamEnum> huihuiHuaweiMap;

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
    }


    public static Map<HuihuiParamEnum, IQiyiParamEnum> huihuiIQiyiMap;

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
    }

    public static Map<HuihuiParamEnum, GuangdiantongParamEnum> huihuiGDTMap;

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
    }

    public static Map<HuihuiParamEnum, HonorParamEnum> huihuiHonorMap;

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
    }



}
