package huihuang.proxy.ocpx.ads.meituan;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.channel.toutiao.ToutiaoEventTypeEnum;

import java.util.Map;

/**
 * @Description: 美团事件类型枚举
 * @Author: xietao
 * @Date: 2023-04-24 09:53
 **/
public enum MeiTuanEventTypeEnum {

    ACTIVE("1","激活"),
    ORDER("2","用户下单"),
    FIRST_DAU("3","用户当日首次 DAU"),
    OBTAIN_CUSTOM("4","有效获客"),
    PAY("5","付费"),
    UV("6","意向 uv"),
    KEY_BEHAVIOR("7","关键行为"),

    ;

    private String code;
    private String desc;

    MeiTuanEventTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static Map<String, ToutiaoEventTypeEnum> eventTypeMap;

    static {
        eventTypeMap = CollUtil.newHashMap();
        //激活
        eventTypeMap.put(ACTIVE.code, ToutiaoEventTypeEnum.ACTIVE);
        //用户下单
        eventTypeMap.put(ORDER.code, ToutiaoEventTypeEnum.APP_ORDER);
        //用户当日首次DAU
        eventTypeMap.put(FIRST_DAU.code, ToutiaoEventTypeEnum.APP_ACCESS);
        //有效获客
        eventTypeMap.put(OBTAIN_CUSTOM.code, null);
        //付费
        eventTypeMap.put(PAY.code, ToutiaoEventTypeEnum.PAY);
        //意向uv
        eventTypeMap.put(UV.code, null);
        //关键行为
        eventTypeMap.put(KEY_BEHAVIOR.code, ToutiaoEventTypeEnum.KEY_BEHAVIOR);
    }
}
