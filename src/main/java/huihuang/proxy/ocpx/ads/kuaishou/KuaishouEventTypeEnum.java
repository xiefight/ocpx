package huihuang.proxy.ocpx.ads.kuaishou;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.channel.baidu.BaiduEventTypeEnum;
import huihuang.proxy.ocpx.channel.guangdiantong.GuangdiantongEventTypeEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiEventTypeEnum;
import huihuang.proxy.ocpx.channel.iqiyi.IQiyiEventTypeEnum;
import huihuang.proxy.ocpx.channel.oppo.OppoEventTypeEnum;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiEventTypeEnum;

import java.util.Map;

/**
 * @Author: xietao
 * @Date: 2023/5/9 20:26
 */
public enum KuaishouEventTypeEnum {

    ACTIVE("1", "新增"),
    SECOND_OPEN("2", "次日回访，次日留存"),
    LEAVE_BACK("4", "流失回流（暂不回传）");

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

    public static Map<String, XiaomiEventTypeEnum> kuaishouXiaomiEventTypeMap;

    static {
        kuaishouXiaomiEventTypeMap = CollUtil.newHashMap();
        //新增
        kuaishouXiaomiEventTypeMap.put(ACTIVE.code, XiaomiEventTypeEnum.APP_ACTIVE_NEW);
        //次日回访
        kuaishouXiaomiEventTypeMap.put(SECOND_OPEN.code, XiaomiEventTypeEnum.APP_RETENTION);
    }

    public static Map<String, BaiduEventTypeEnum> kuaishouBaiduEventTypeMap;

    static {
        kuaishouBaiduEventTypeMap = CollUtil.newHashMap();
        //新增
        kuaishouBaiduEventTypeMap.put(ACTIVE.code, BaiduEventTypeEnum.ACTIVE);
        //次日回访
        kuaishouBaiduEventTypeMap.put(SECOND_OPEN.code, BaiduEventTypeEnum.RETAIN_1DAY);
    }


    public static Map<String, OppoEventTypeEnum> kuaishouOppoEventTypeMap;

    static {
        kuaishouOppoEventTypeMap = CollUtil.newHashMap();
        //新增
        kuaishouOppoEventTypeMap.put(ACTIVE.code, OppoEventTypeEnum.ACTIVE);
        //次日回访
        kuaishouOppoEventTypeMap.put(SECOND_OPEN.code, OppoEventTypeEnum.RETAIN_2DAY);
    }



    public static Map<String, IQiyiEventTypeEnum> kuaishouIQiyiEventTypeMap;

    static {
        kuaishouIQiyiEventTypeMap = CollUtil.newHashMap();
        //新增
        kuaishouIQiyiEventTypeMap.put(ACTIVE.code, IQiyiEventTypeEnum.ACTIVE);
    }

    public static Map<String, GuangdiantongEventTypeEnum> kuaishouGuangdiantongEventTypeMap;

    static {
        kuaishouGuangdiantongEventTypeMap = CollUtil.newHashMap();
        //新增
        kuaishouGuangdiantongEventTypeMap.put(ACTIVE.code, GuangdiantongEventTypeEnum.ACTIVE);
        //次日回访
        kuaishouGuangdiantongEventTypeMap.put(SECOND_OPEN.code, GuangdiantongEventTypeEnum.START_APP);
    }

}
