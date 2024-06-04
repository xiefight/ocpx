package huihuang.proxy.ocpx.ads.kuaishou;

import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.channel.guangdiantong.GuangdiantongParamEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiParamEnum;
import huihuang.proxy.ocpx.channel.iqiyi.IQiyiParamEnum;
import huihuang.proxy.ocpx.channel.oppo.OppoParamEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiParamEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xietao
 * @Date: 2023/5/9 20:26
 */
public enum KuaishouParamEnum {

    ADID("adid", "String", 1, "投放渠道标识，向快手商务申请"),
    IMEI("imei", "String", 1, "设备 imei，支持原值，md5"),
    IDFA("idfa", "String", 1, "设备 idfa，支持原值，md5"),
    OAID("oaid", "String", 1, "设备 oaid，支持原值，md5"),
    ANDROID_ID("android_id", "String", 0, "androidId"),
    MAC("mac", "String", 0, "mac地址"),
    IP("ip", "String", 0, "媒体投放系统获取的⽤户终端的公共IP地址"),
    USER_AGENT("user_agent", "String", 0, "用户浏览器 userAgent"),
    ACCOUNT_ID("account_id", "String", 0, "媒体账户id"),
    CLICK_ID("click_id", "String", 0, "媒体点击id"),
    CAMPAIGN_ID("campaign_id", "String", 0, "计划id"),
    ADGROUP_ID("adgroup_id", "String", 0, "广告组id"),
    CREATEIVE_ID("creative_id", "String", 0, "创意id"),
    ADVERTISER_ID("advertiser_id", "String", 0, "广告id"),
    RTA_ID("rta_id", "String", 0, "请求rta时的request_id"),
    CAID_LIST("caid_list", "String", 0, "idfa 的替代,格式是 json 数组的字符串，[{\"caid\":\"9e4e58dfac3d855329271d25d3870b3d\",\"version\":\"1001\"}]"),


    CALLBACK("callback", "String", 0, "回调url或参数"),

    ;

    private String name;
    private String type;
    private Integer necessary;
    private String remark;

    KuaishouParamEnum(String name, String type, Integer necessary, String remark) {
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
    public static Map<KuaishouParamEnum, HuaweiParamEnum> kuaishouHuaweiMap;

    static {
        kuaishouHuaweiMap = new HashMap<>();
        kuaishouHuaweiMap.put(ADID, HuaweiParamEnum.KUAISHOU_ADID);
        kuaishouHuaweiMap.put(IMEI, HuaweiParamEnum.ID_TYPE);//需要特殊处理
        kuaishouHuaweiMap.put(IDFA, null);
        kuaishouHuaweiMap.put(OAID, HuaweiParamEnum.OAID);
        kuaishouHuaweiMap.put(ANDROID_ID, null);
        kuaishouHuaweiMap.put(MAC, null);
        kuaishouHuaweiMap.put(IP, HuaweiParamEnum.IP);
        kuaishouHuaweiMap.put(ACCOUNT_ID, HuaweiParamEnum.ACCOUNT_ID);
        kuaishouHuaweiMap.put(CLICK_ID, null);//由中间层生成时间戳
        kuaishouHuaweiMap.put(CAMPAIGN_ID, HuaweiParamEnum.CAMPAIGN_ID);
        kuaishouHuaweiMap.put(ADGROUP_ID, HuaweiParamEnum.ADGROUP_ID);
        kuaishouHuaweiMap.put(CREATEIVE_ID, null);
        kuaishouHuaweiMap.put(ADVERTISER_ID, null);
        kuaishouHuaweiMap.put(RTA_ID, null);
        kuaishouHuaweiMap.put(CAID_LIST, null);
        kuaishouHuaweiMap.put(USER_AGENT, HuaweiParamEnum.USER_AGENT);
        kuaishouHuaweiMap.put(CALLBACK, HuaweiParamEnum.CALLBACK);
    }

    /**
     * xiaomi-kuaishou
     */
    public static Map<KuaishouParamEnum, XiaomiParamEnum> kuaishouXiaomiMap;

    static {
        kuaishouXiaomiMap = new HashMap<>();
        kuaishouXiaomiMap.put(ADID, XiaomiParamEnum.KUAISHOU_ADID);
        kuaishouXiaomiMap.put(IMEI, XiaomiParamEnum.IMEI);
        kuaishouXiaomiMap.put(IDFA, null);
        kuaishouXiaomiMap.put(OAID, XiaomiParamEnum.OAID);//需要特殊处理
        kuaishouXiaomiMap.put(ANDROID_ID, XiaomiParamEnum.ANDROIDID);
        kuaishouXiaomiMap.put(MAC, null);
        kuaishouXiaomiMap.put(IP, XiaomiParamEnum.IP);
        kuaishouXiaomiMap.put(ACCOUNT_ID, null);
        kuaishouXiaomiMap.put(CLICK_ID, null);//由中间层生成时间戳
        kuaishouXiaomiMap.put(CAMPAIGN_ID, XiaomiParamEnum.CAMPAIGNID);
        kuaishouXiaomiMap.put(ADGROUP_ID, null);//XiaomiParamEnum.CUSTOMERID
        kuaishouXiaomiMap.put(CREATEIVE_ID, null);//XiaomiParamEnum.ADID
        kuaishouXiaomiMap.put(ADVERTISER_ID, null);//XiaomiParamEnum.CUSTOMERID
        kuaishouXiaomiMap.put(RTA_ID, null);
        kuaishouXiaomiMap.put(CAID_LIST, null);
        kuaishouXiaomiMap.put(USER_AGENT, XiaomiParamEnum.UA);
        kuaishouXiaomiMap.put(CALLBACK, XiaomiParamEnum.CALLBACK);
    }


    /**
     * baidu-kuaishou
     */
    public static Map<KuaishouParamEnum, BaiduParamEnum> kuaishouBaiduMap;

    static {
        kuaishouBaiduMap = new HashMap<>();
        kuaishouBaiduMap.put(ADID, BaiduParamEnum.KUAISHOU_ADID);
        kuaishouBaiduMap.put(IMEI, BaiduParamEnum.IMEI_MD5);
        kuaishouBaiduMap.put(IDFA, BaiduParamEnum.IDFA);
        kuaishouBaiduMap.put(OAID, BaiduParamEnum.OAID);//需要特殊处理
        kuaishouBaiduMap.put(ANDROID_ID, BaiduParamEnum.ANDROID_ID_MD5);
        kuaishouBaiduMap.put(MAC, BaiduParamEnum.MAC);
        kuaishouBaiduMap.put(IP, BaiduParamEnum.IP);
        kuaishouBaiduMap.put(ACCOUNT_ID, BaiduParamEnum.ACCOUNT_ID);
        kuaishouBaiduMap.put(CLICK_ID, null);//由中间层生成时间戳
        kuaishouBaiduMap.put(CAMPAIGN_ID, BaiduParamEnum.PID);
        kuaishouBaiduMap.put(ADGROUP_ID, BaiduParamEnum.UID);
        kuaishouBaiduMap.put(CREATEIVE_ID, null);
        kuaishouBaiduMap.put(ADVERTISER_ID, null);
        kuaishouBaiduMap.put(RTA_ID, null);
        kuaishouBaiduMap.put(CAID_LIST, null);
        kuaishouBaiduMap.put(USER_AGENT, BaiduParamEnum.UA);
        kuaishouBaiduMap.put(CALLBACK, BaiduParamEnum.CALLBACK_URL);
    }


    /**
     * oppo-kuaishou
     */
    public static Map<KuaishouParamEnum, OppoParamEnum> kuaishouOppoMap;

    static {
        kuaishouOppoMap = new HashMap<>();
        kuaishouOppoMap.put(ADID, OppoParamEnum.KUAISHOU_ADID);
        kuaishouOppoMap.put(IMEI, OppoParamEnum.IMEI);
        kuaishouOppoMap.put(IDFA, null);
        kuaishouOppoMap.put(OAID, OppoParamEnum.OAID);//需要特殊处理
        kuaishouOppoMap.put(ANDROID_ID, null);
        kuaishouOppoMap.put(MAC, null);
        kuaishouOppoMap.put(IP, null);
        kuaishouOppoMap.put(ACCOUNT_ID, null);
        kuaishouOppoMap.put(CLICK_ID, null);//由中间层生成时间戳
        kuaishouOppoMap.put(CAMPAIGN_ID, null);
        kuaishouOppoMap.put(ADGROUP_ID, null);
        kuaishouOppoMap.put(CREATEIVE_ID, null);
        kuaishouOppoMap.put(ADVERTISER_ID, OppoParamEnum.ADID);
        kuaishouOppoMap.put(RTA_ID, null);
        kuaishouOppoMap.put(CAID_LIST, null);
        kuaishouOppoMap.put(USER_AGENT, OppoParamEnum.USERAGENT);
        kuaishouOppoMap.put(CALLBACK, null);
    }


    /**
     * iqiyi-kuaishou
     */
    public static Map<KuaishouParamEnum, IQiyiParamEnum> kuaishouIQiyiMap;

    static {
        kuaishouIQiyiMap = new HashMap<>();
        kuaishouIQiyiMap.put(ADID, IQiyiParamEnum.KUAISHOU_ADID);
        kuaishouIQiyiMap.put(IMEI, IQiyiParamEnum.IMEI_MD5);
        kuaishouIQiyiMap.put(IDFA, IQiyiParamEnum.IDFA);
        kuaishouIQiyiMap.put(OAID, IQiyiParamEnum.OAID);//需要特殊处理
        kuaishouIQiyiMap.put(ANDROID_ID, IQiyiParamEnum.ANDROID_ID_MD5);
        kuaishouIQiyiMap.put(MAC, IQiyiParamEnum.MAC);
        kuaishouIQiyiMap.put(IP, IQiyiParamEnum.IP);
        kuaishouIQiyiMap.put(ACCOUNT_ID, IQiyiParamEnum.ACCOUNT_ID);
        kuaishouIQiyiMap.put(CLICK_ID, null);//由中间层生成时间戳
        kuaishouIQiyiMap.put(CAMPAIGN_ID, null);
        kuaishouIQiyiMap.put(ADGROUP_ID, null);//XiaomiParamEnum.CUSTOMERID
        kuaishouIQiyiMap.put(CREATEIVE_ID, null);//XiaomiParamEnum.ADID
        kuaishouIQiyiMap.put(ADVERTISER_ID, null);//XiaomiParamEnum.CUSTOMERID
        kuaishouIQiyiMap.put(RTA_ID, null);
        kuaishouIQiyiMap.put(CAID_LIST, null);
        kuaishouIQiyiMap.put(USER_AGENT, IQiyiParamEnum.UA);
        kuaishouIQiyiMap.put(CALLBACK, IQiyiParamEnum.CALLBACK_URL);
    }


    /**
     * guangdiantong-kuaishou
     */
    public static Map<KuaishouParamEnum, GuangdiantongParamEnum> kuaishouGudangdiantongMap;

    static {
        kuaishouGudangdiantongMap = new HashMap<>();
        kuaishouGudangdiantongMap.put(ADID, GuangdiantongParamEnum.KUAISHOU_ADID);
        kuaishouGudangdiantongMap.put(IMEI, GuangdiantongParamEnum.MUID);
        //由于imei和idfa都使用muid，监测链接中会重复，这里如果有muid，我们先给imei，然后在后续填充对象时，再判断是将muid赋值给imei还是idfa
//        kuaishouGudangdiantongMap.put(IDFA, GuangdiantongParamEnum.MUID);
        kuaishouGudangdiantongMap.put(OAID, GuangdiantongParamEnum.HASH_OAID);
        kuaishouGudangdiantongMap.put(ANDROID_ID, null);
        kuaishouGudangdiantongMap.put(MAC, null);
        kuaishouGudangdiantongMap.put(IP, GuangdiantongParamEnum.IP);
        kuaishouGudangdiantongMap.put(ACCOUNT_ID, GuangdiantongParamEnum.ACCOUNT_ID_OWN);
        kuaishouGudangdiantongMap.put(CLICK_ID, null);//由中间层生成时间戳
        kuaishouGudangdiantongMap.put(CAMPAIGN_ID, GuangdiantongParamEnum.CAMPAIGN_ID);
        kuaishouGudangdiantongMap.put(ADGROUP_ID, null);
        kuaishouGudangdiantongMap.put(CREATEIVE_ID, GuangdiantongParamEnum.AD_ID);
        kuaishouGudangdiantongMap.put(ADVERTISER_ID, GuangdiantongParamEnum.ADGROUP_ID);
        kuaishouGudangdiantongMap.put(RTA_ID, null);
        kuaishouGudangdiantongMap.put(CAID_LIST, null);
        kuaishouGudangdiantongMap.put(USER_AGENT, GuangdiantongParamEnum.USER_AGENT);
        kuaishouGudangdiantongMap.put(CALLBACK, GuangdiantongParamEnum.CALLBACK);
    }

}
