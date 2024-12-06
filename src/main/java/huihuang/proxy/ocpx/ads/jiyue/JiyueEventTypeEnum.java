package huihuang.proxy.ocpx.ads.jiyue;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.channel.baidu.BaiduEventTypeEnum;

import java.util.Map;

/**
 * @Author: xietao
 * @Date: 2023/6/8 17:21
 */
public enum JiyueEventTypeEnum {

    //安卓
    ACTIVED("actived", "激活"),
    REGISTER("register", "注册"),
    COMMIT_MSG("commit_msg", "授信"),
    PAY("pay", "付费"),
    RETAIND("retained", "次留"),
    OPPN_APP("open_app", "唤起应用"),

    ;

    private String code;
    private String desc;

    JiyueEventTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static Map<String, BaiduEventTypeEnum> jiyueBaiduEventTypeMap;

    static {
        jiyueBaiduEventTypeMap = CollUtil.newHashMap();
        //下载
        //激活
        jiyueBaiduEventTypeMap.put(ACTIVED.code, BaiduEventTypeEnum.ACTIVE);
        //注册
        jiyueBaiduEventTypeMap.put(REGISTER.code, BaiduEventTypeEnum.REGISTER);
        //次日留存
        jiyueBaiduEventTypeMap.put(RETAIND.code, BaiduEventTypeEnum.RETAIN_1DAY);
        //授信
        jiyueBaiduEventTypeMap.put(COMMIT_MSG.code, null);
        //付费
        jiyueBaiduEventTypeMap.put(PAY.code, BaiduEventTypeEnum.ORDERS);
        jiyueBaiduEventTypeMap.put(OPPN_APP.code, BaiduEventTypeEnum.FEED_DEEPLINK);

    }


}
