package huihuang.proxy.ocpx.ads.dingyun;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.channel.baidu.BaiduEventTypeEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiEventTypeEnum;
import huihuang.proxy.ocpx.channel.iqiyi.IQiyiEventTypeEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiEventTypeEnum;

import java.util.Map;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-01-04 08:40
 **/
public enum DingyunEventTypeEnum {

    ACTIVATE("1", "新增"),
    DAY1RETENTION("2", "次日留存"),
//    NEW_LOGIN("2", "流失回传"),


    ;


    private String code;
    private String desc;

    DingyunEventTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static Map<String, BaiduEventTypeEnum> dingyunBaiduEventTypeMap;

    static {
        dingyunBaiduEventTypeMap = CollUtil.newHashMap();
        dingyunBaiduEventTypeMap.put(ACTIVATE.code, BaiduEventTypeEnum.ACTIVE);
        dingyunBaiduEventTypeMap.put(DAY1RETENTION.code, BaiduEventTypeEnum.RETAIN_1DAY);
    }

    public static Map<String, XiaomiEventTypeEnum> dingyunXiaomiEventTypeMap;

    static {
        dingyunXiaomiEventTypeMap = CollUtil.newHashMap();
        dingyunXiaomiEventTypeMap.put(ACTIVATE.code, XiaomiEventTypeEnum.APP_ACTIVE);
        //自定义新增激活
        dingyunXiaomiEventTypeMap.put(ACTIVATE.code + "new", XiaomiEventTypeEnum.APP_ACTIVE_NEW);
        dingyunXiaomiEventTypeMap.put(DAY1RETENTION.code, XiaomiEventTypeEnum.APP_RETENTION);
    }


    public static Map<String, HuaweiEventTypeEnum> dingyunHuaweiEventTypeMap;

    static {
        dingyunHuaweiEventTypeMap = CollUtil.newHashMap();
        dingyunHuaweiEventTypeMap.put(ACTIVATE.code, HuaweiEventTypeEnum.ACTIVE);
        dingyunHuaweiEventTypeMap.put(DAY1RETENTION.code, HuaweiEventTypeEnum.RETAIN);
    }


    public static Map<String, IQiyiEventTypeEnum> dingyunIQiyiEventTypeMap;

    static {
        dingyunIQiyiEventTypeMap = CollUtil.newHashMap();
        dingyunIQiyiEventTypeMap.put(ACTIVATE.code, IQiyiEventTypeEnum.ACTIVE);
        dingyunIQiyiEventTypeMap.put(DAY1RETENTION.code, IQiyiEventTypeEnum.RETENTION);
    }

}
