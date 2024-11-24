package huihuang.proxy.ocpx.ads.weibo;

import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.channel.oppo.OppoParamEnum;

import java.util.HashMap;
import java.util.Map;

public enum WeiboParamEnum {

    UNIQUE_ID("uniq_id", "String", 1, "曝光唯一id，用于串联整个投放链路"),
    OS("os", "String", 1, "设备操作系统 0：安卓 1：ios"),
    OSVERSION("os_ver", "String", 1, "设备操作系统版本"),
    IDFA_MD5("idfa_md5", "String", 1, "ios设备号"),
    CAID("caid", "String", 1, "ios必须"),
    IMEI_MD5("imei_md5", "String", 1, "IMEI转小写，MD5后转大写"),
    OAID_MD5("oaid_md5", "String", 1, "安卓必须，原值MD5后转大写"),
    MODEL("model", "String", 1, "设备机型"),
    BRAND("brand", "String", 1, "设备品牌"),
    LANGUAGE("language", "String", 1, "设备语言 zh_CN 或 zh-hans-CN 自己上报"),
    IPV4("ip", "String", 1, "仅获取用户设备ipv4"),
    UA("ua", "String", 1, "user agent"),
    TS("ts", "String", 1, "毫秒时间戳 自己上报"),
    CALLBACK("cb", "String", 1, "转化回调地址 需URL ENCODE 处理"),

    ACCOUNT_ID("account_id", "String", 0, "我们规定的账户id,用于同一pid区分不出来的情况下,使用accountId区分"),

    ;


    private String name;
    private String type;
    private Integer necessary;
    private String remark;

    WeiboParamEnum(String name, String type, Integer necessary, String remark) {
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


    public static Map<WeiboParamEnum, BaiduParamEnum> weiboBaiduMap;

    static {
        weiboBaiduMap = new HashMap<>();
        weiboBaiduMap.put(IMEI_MD5, BaiduParamEnum.IMEI_MD5);
        weiboBaiduMap.put(OAID_MD5, BaiduParamEnum.OAID_MD5);
        weiboBaiduMap.put(IDFA_MD5, null);
        weiboBaiduMap.put(CAID, BaiduParamEnum.CAID);
        weiboBaiduMap.put(MODEL, BaiduParamEnum.MODEL);
        weiboBaiduMap.put(BRAND, null);
        weiboBaiduMap.put(LANGUAGE, null);

        weiboBaiduMap.put(TS, BaiduParamEnum.TS);
        weiboBaiduMap.put(OS, BaiduParamEnum.OS_TYPE);
        weiboBaiduMap.put(OSVERSION, BaiduParamEnum.OS_VERSION);
        weiboBaiduMap.put(IPV4, BaiduParamEnum.IP);
        weiboBaiduMap.put(UA, BaiduParamEnum.UA);
        weiboBaiduMap.put(CALLBACK, BaiduParamEnum.CALLBACK_URL);

        weiboBaiduMap.put(ACCOUNT_ID, BaiduParamEnum.ACCOUNT_ID);
    }

    public static Map<WeiboParamEnum, OppoParamEnum> weiboOppoMap;

    static {
        weiboOppoMap = new HashMap<>();
        weiboOppoMap.put(IMEI_MD5, OppoParamEnum.IMEI);
        weiboOppoMap.put(OAID_MD5, OppoParamEnum.OAID);
        weiboOppoMap.put(IDFA_MD5, null);
        weiboOppoMap.put(CAID, null);
        weiboOppoMap.put(MODEL, OppoParamEnum.MODEL);
        weiboOppoMap.put(BRAND, null);
        weiboOppoMap.put(LANGUAGE, OppoParamEnum.LANG);

        weiboOppoMap.put(TS, OppoParamEnum.TS);
        weiboOppoMap.put(OS, OppoParamEnum.OS);
        weiboOppoMap.put(OSVERSION, OppoParamEnum.OS_VERSION);
        weiboOppoMap.put(IPV4, OppoParamEnum.IP);
        weiboOppoMap.put(UA, OppoParamEnum.USERAGENT);
        weiboOppoMap.put(CALLBACK, null);

        weiboOppoMap.put(ACCOUNT_ID, OppoParamEnum.ACCOUNT_ID);
    }

}
