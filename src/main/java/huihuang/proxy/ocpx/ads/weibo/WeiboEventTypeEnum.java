package huihuang.proxy.ocpx.ads.weibo;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.channel.baidu.BaiduEventTypeEnum;
import huihuang.proxy.ocpx.channel.honor.HonorEventTypeEnum;
import huihuang.proxy.ocpx.channel.huihuang.HuihuangChannelEventTypeEnum;
import huihuang.proxy.ocpx.channel.oppo.OppoEventTypeEnum;

import java.util.Map;

public enum WeiboEventTypeEnum {

    ACTIVATE("1", "新增"),
    ORDER("2", "下单"),
    REGISTER("3", "注册"),
    PAY("4", "付费"),
    DAY1RETENTION("7", "次日留存"),

    ;


    private String code;
    private String desc;

    WeiboEventTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static Map<String, BaiduEventTypeEnum> weiboBaiduEventTypeMap;

    static {
        weiboBaiduEventTypeMap = CollUtil.newHashMap();
        weiboBaiduEventTypeMap.put(ACTIVATE.code, BaiduEventTypeEnum.ACTIVE);
        weiboBaiduEventTypeMap.put(DAY1RETENTION.code, BaiduEventTypeEnum.RETAIN_1DAY);
        weiboBaiduEventTypeMap.put(ORDER.code, BaiduEventTypeEnum.EC_BUY);
        weiboBaiduEventTypeMap.put(PAY.code, BaiduEventTypeEnum.ORDERS);
        weiboBaiduEventTypeMap.put(REGISTER.code, BaiduEventTypeEnum.REGISTER);
    }


    public static Map<String, OppoEventTypeEnum> weiboOppoEventTypeMap;

    static {
        weiboOppoEventTypeMap = CollUtil.newHashMap();
        weiboOppoEventTypeMap.put(ACTIVATE.code, OppoEventTypeEnum.ACTIVE);
        weiboOppoEventTypeMap.put(REGISTER.code, OppoEventTypeEnum.REGISTER);
        weiboOppoEventTypeMap.put(DAY1RETENTION.code, OppoEventTypeEnum.RETAIN_2DAY);
        weiboOppoEventTypeMap.put(ORDER.code, OppoEventTypeEnum.APPLICATION_ORDER);
        weiboOppoEventTypeMap.put(PAY.code, OppoEventTypeEnum.DEEP_PAGE_ACCESS);
    }


    public static Map<String, HuihuangChannelEventTypeEnum> weiboHuihuangEventTypeMap;

    static {
        weiboHuihuangEventTypeMap = CollUtil.newHashMap();
        weiboHuihuangEventTypeMap.put(ACTIVATE.code, HuihuangChannelEventTypeEnum.ACTIVATE);
        weiboHuihuangEventTypeMap.put(REGISTER.code, null);
        weiboHuihuangEventTypeMap.put(DAY1RETENTION.code, HuihuangChannelEventTypeEnum.DAY1RETENTION);
        weiboHuihuangEventTypeMap.put(ORDER.code, HuihuangChannelEventTypeEnum.ORDER);
        weiboHuihuangEventTypeMap.put(PAY.code, HuihuangChannelEventTypeEnum.PAID);
    }


    public static Map<String, HonorEventTypeEnum> weiboHonorEventTypeMap;

    static {
        weiboHonorEventTypeMap = CollUtil.newHashMap();
        weiboHonorEventTypeMap.put(ACTIVATE.code, HonorEventTypeEnum.ACTIVE);
        weiboHonorEventTypeMap.put(DAY1RETENTION.code, HonorEventTypeEnum.RETAIN);
        weiboHonorEventTypeMap.put(ORDER.code, HonorEventTypeEnum.PRE_ORDER);
        weiboHonorEventTypeMap.put(PAY.code, HonorEventTypeEnum.PAID);
        weiboHonorEventTypeMap.put(REGISTER.code, HonorEventTypeEnum.REGISTER);
    }


}
