package huihuang.proxy.ocpx.ads.xiguavideo;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.channel.wifi.WifiEventTypeEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiEventTypeEnum;

import java.util.Map;

/**
 *
 * @Author: xietao
 * @Date: 2023/5/9 20:26
 */
public enum XiguaEventTypeEnum {

    ACTIVE("0", "激活"),
    REGISTER("1", "注册"),
    FIRST_BUY("2", "当日首购-一般指用户的后链路购买/下单等行为"),
    FIRST_WAKEUP("3", "首唤-广告外投deeplink拉端的行为，当日首次唤醒"),
    SECOND_OPEN("4", "次日回访，次日留存"),

    ;

    private String code;
    private String desc;

    XiguaEventTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * xigua - xiaomi 暂无对接
     */
    public static Map<String, XiaomiEventTypeEnum> xiguaXiaomiEventTypeMap;

    static {
        xiguaXiaomiEventTypeMap = CollUtil.newHashMap();
        //激活
        xiguaXiaomiEventTypeMap.put(ACTIVE.code, XiaomiEventTypeEnum.APP_ACTIVE_NEW);
        //注册
        xiguaXiaomiEventTypeMap.put(REGISTER.code, XiaomiEventTypeEnum.APP_REGISTER);
        //当日首购
        xiguaXiaomiEventTypeMap.put(FIRST_BUY.code, XiaomiEventTypeEnum.APP_FIRST_PAY);
        //首唤
        xiguaXiaomiEventTypeMap.put(FIRST_WAKEUP.code, null);
        //次日回访
        xiguaXiaomiEventTypeMap.put(SECOND_OPEN.code, null);
    }


    public static Map<String, WifiEventTypeEnum> xiguaWifiEventTypeMap;

    static {
        xiguaWifiEventTypeMap = CollUtil.newHashMap();
        //激活
        xiguaWifiEventTypeMap.put(ACTIVE.code, WifiEventTypeEnum.APP_ACTIVE);
        //注册
        xiguaWifiEventTypeMap.put(REGISTER.code, WifiEventTypeEnum.APP_REGISTER);
        //当日首购
        xiguaWifiEventTypeMap.put(FIRST_BUY.code, WifiEventTypeEnum.APP_PAY);
        //首唤
        xiguaWifiEventTypeMap.put(FIRST_WAKEUP.code, null);
        //次日回访
        xiguaWifiEventTypeMap.put(SECOND_OPEN.code, WifiEventTypeEnum.APP_RETENTION);
    }

}
