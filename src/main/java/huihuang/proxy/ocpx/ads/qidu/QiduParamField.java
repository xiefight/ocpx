package huihuang.proxy.ocpx.ads.qidu;

import cn.hutool.core.annotation.Alias;
import lombok.Data;

@Data
public class QiduParamField {

    @Alias("appId")
    private String appid;
    private String channel;
    private String idfa;
    private String imei;
    private String imei_md5;
    private String oaid;
    private String oaid_md5;
    @Alias("androidId")
    private String androidid;
    private String ua;
    private String ip;
    private String ts;
    @Alias("clickId")
    private String clickid;
    private String callback;

    private String extra;

}
