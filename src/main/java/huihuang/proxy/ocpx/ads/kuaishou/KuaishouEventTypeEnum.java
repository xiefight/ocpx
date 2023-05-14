package huihuang.proxy.ocpx.ads.kuaishou;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.channel.huawei.HuaweiEventTypeEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiEventTypeEnum;

import java.util.Map;

/**
 *
 * @Author: xietao
 * @Date: 2023/5/9 20:26
 */
public enum KuaishouEventTypeEnum {

    ACTIVE("1", "新增"),
    SECOND_OPEN("2", "次日回访，次日留存"),
    LEAVE_BACK("4","流失回流（暂不回传）")

    ;

    private String code;
    private String desc;

    KuaishouEventTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static Map<String, HuaweiEventTypeEnum> kuaishouHuaweiEventTypeMap;

    static {
        kuaishouHuaweiEventTypeMap = CollUtil.newHashMap();
        //新增
        kuaishouHuaweiEventTypeMap.put(ACTIVE.code, HuaweiEventTypeEnum.ACTIVE);
        //次日回访
        kuaishouHuaweiEventTypeMap.put(SECOND_OPEN.code, HuaweiEventTypeEnum.RETAIN);
    }


}
