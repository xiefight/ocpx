package huihuang.proxy.ocpx.ads.liangdamao;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.channel.baidu.BaiduEventTypeEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiEventTypeEnum;
import huihuang.proxy.ocpx.channel.wifi.WifiEventTypeEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiEventTypeEnum;

import java.util.Map;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-24 16:50
 **/
public enum LiangdamaoEventTypeEnum {

    ACTIVE("0", "激活"),
    REGISTER("1", "注册"),
    FIRST_BUY("2", "当日首购-一般指用户的后链路购买/下单等行为"),
    FIRST_WAKEUP("3", "首唤-广告外投deeplink拉端的行为，当日首次唤醒"),
    SECOND_OPEN("4", "次日回访，次日留存"),

    ;

    private String code;
    private String desc;

    LiangdamaoEventTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static Map<String, XiaomiEventTypeEnum> liangdamaoXiaomiEventTypeMap;

    static {
        liangdamaoXiaomiEventTypeMap = CollUtil.newHashMap();
        //激活
        liangdamaoXiaomiEventTypeMap.put(ACTIVE.code, XiaomiEventTypeEnum.APP_ACTIVE);
        //自定义新增激活
        liangdamaoXiaomiEventTypeMap.put(ACTIVE.code + "new", XiaomiEventTypeEnum.APP_ACTIVE_NEW);
        //注册
        liangdamaoXiaomiEventTypeMap.put(REGISTER.code, XiaomiEventTypeEnum.APP_REGISTER);
        //当日首购
        liangdamaoXiaomiEventTypeMap.put(FIRST_BUY.code, XiaomiEventTypeEnum.APP_FIRST_PAY);
        //首唤
        liangdamaoXiaomiEventTypeMap.put(FIRST_WAKEUP.code, null);
        //次日回访
        liangdamaoXiaomiEventTypeMap.put(SECOND_OPEN.code, XiaomiEventTypeEnum.APP_RETENTION);
    }

    public static Map<String, BaiduEventTypeEnum> liangdamaoBaiduEventTypeMap;

    static {
        liangdamaoBaiduEventTypeMap = CollUtil.newHashMap();
        //激活
        liangdamaoBaiduEventTypeMap.put(ACTIVE.code, BaiduEventTypeEnum.ACTIVE);
        //注册
        liangdamaoBaiduEventTypeMap.put(REGISTER.code, BaiduEventTypeEnum.REGISTER);
        //当日首购
        liangdamaoBaiduEventTypeMap.put(FIRST_BUY.code, BaiduEventTypeEnum.ORDERS);
        //首唤
        liangdamaoBaiduEventTypeMap.put(FIRST_WAKEUP.code, null);
        //次日回访
        liangdamaoBaiduEventTypeMap.put(SECOND_OPEN.code, BaiduEventTypeEnum.RETAIN_1DAY);
    }

    public static Map<String, WifiEventTypeEnum> liangdamaoWifiEventTypeMap;

    static {
        liangdamaoWifiEventTypeMap = CollUtil.newHashMap();
        //激活
        liangdamaoWifiEventTypeMap.put(ACTIVE.code, WifiEventTypeEnum.APP_ACTIVE);
        //注册
        liangdamaoWifiEventTypeMap.put(REGISTER.code, WifiEventTypeEnum.APP_REGISTER);
        //当日首购
        liangdamaoWifiEventTypeMap.put(FIRST_BUY.code, WifiEventTypeEnum.APP_PAY);
        //首唤
        liangdamaoWifiEventTypeMap.put(FIRST_WAKEUP.code, null);
        //次日回访
        liangdamaoWifiEventTypeMap.put(SECOND_OPEN.code, WifiEventTypeEnum.APP_RETENTION);
    }

    public static Map<String, HuaweiEventTypeEnum> liangdamaoHuaweiEventTypeMap;

    static {
        liangdamaoHuaweiEventTypeMap = CollUtil.newHashMap();
        //激活
        liangdamaoHuaweiEventTypeMap.put(ACTIVE.code, HuaweiEventTypeEnum.ACTIVE);
        //注册
        liangdamaoHuaweiEventTypeMap.put(REGISTER.code, HuaweiEventTypeEnum.REGISTER);
        //当日首购
        liangdamaoHuaweiEventTypeMap.put(FIRST_BUY.code, HuaweiEventTypeEnum.PAID);
        //首唤
        liangdamaoHuaweiEventTypeMap.put(FIRST_WAKEUP.code, HuaweiEventTypeEnum.RE_ENGAGE);
        //次日回访
        liangdamaoHuaweiEventTypeMap.put(SECOND_OPEN.code, HuaweiEventTypeEnum.RETAIN);
    }

}
