package huihuang.proxy.ocpx.ads.dingyun;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.channel.baidu.BaiduEventTypeEnum;

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

}
