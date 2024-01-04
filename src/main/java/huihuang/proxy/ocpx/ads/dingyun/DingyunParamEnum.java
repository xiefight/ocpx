package huihuang.proxy.ocpx.ads.dingyun;

import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-01-04 08:44
 **/
public enum DingyunParamEnum {

    ADID("adid", "String", 1, "在代理平台中，渠道管理-渠道列表， 点击“推广位和投放链接”获取"),
    TYPE("type", "int", 1, "调用类型：1：rta，2：ocpx"),
    IMEI("imei", "String", 1, "android 设备 imei"),
    IMEI_MD5("imeiMd5", "String", 1, "android 设备 imeiMd5"),
    IDFA("idfa", "String", 1, "ios 设备 idfa"),
    IDFA_MD5("idfaMd5", "String", 1, "ios 设备 idfa的Md5"),
    OAID("oaid", "String", 1, "android 设备 oaid"),
    OAID_MD5("oaidMd5", "String", 1, "android 设备 oaid的Md5"),
    CALLBACK("callback", "String", 1, "回调地址"),
    ANDROID_ID("android_id", "String", 0, "android 设备 androidId"),
    MAC("mac", "String", 0, "mac 地址"),
    IMPRESSION_ID("impression_id", "String", 0, "曝光 id"),
    IP("ip", "String", 0, "点击用户的实际 ip"),
    USER_AGENT("user_agent", "String", 0, "用户浏览器 ua"),
    CAID_LIST("caid_list", "String", 0, "idfa 的替代（IOS14 之后未授权无法  获取 IDFA，使用 CAID 做为替代）格 式是json 数组的字符串，\n" +
            "[{\"caid\":\"9e4e58dfac3d8553292 71d25d3870b3d\",\"version\":\"100 1\"}]"),
    ACCOUNT_ID("account_id", "String", 0, "我们规定的账户id,用于同一pid区分不出来的情况下,使用accountId区分"),

    ;


    private String name;
    private String type;
    private Integer necessary;
    private String remark;

    DingyunParamEnum(String name, String type, Integer necessary, String remark) {
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


    public static Map<DingyunParamEnum, BaiduParamEnum> dingyunBaiduMap;

    static {
        dingyunBaiduMap = new HashMap<>();
        dingyunBaiduMap.put(ADID, BaiduParamEnum.DINGYUN_ADID);
        dingyunBaiduMap.put(TYPE, null);//上报时固定值 2
        dingyunBaiduMap.put(IMEI, null);
        dingyunBaiduMap.put(IMEI_MD5, BaiduParamEnum.IMEI_MD5);
        dingyunBaiduMap.put(IDFA, BaiduParamEnum.IDFA);
        dingyunBaiduMap.put(IDFA_MD5, null);
        dingyunBaiduMap.put(OAID, BaiduParamEnum.OAID);
        dingyunBaiduMap.put(OAID_MD5, BaiduParamEnum.OAID_MD5);
        dingyunBaiduMap.put(CALLBACK, BaiduParamEnum.CALLBACK_URL);
        dingyunBaiduMap.put(ANDROID_ID, BaiduParamEnum.ANDROID_ID_MD5);
        dingyunBaiduMap.put(MAC, BaiduParamEnum.MAC);
        dingyunBaiduMap.put(IMPRESSION_ID, null);
        dingyunBaiduMap.put(IP, BaiduParamEnum.IP);
        dingyunBaiduMap.put(USER_AGENT, BaiduParamEnum.UA);
        dingyunBaiduMap.put(CAID_LIST, BaiduParamEnum.CAID);
        dingyunBaiduMap.put(ACCOUNT_ID, BaiduParamEnum.ACCOUNT_ID);

    }
}
