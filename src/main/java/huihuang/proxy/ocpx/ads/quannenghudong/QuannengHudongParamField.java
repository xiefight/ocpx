package huihuang.proxy.ocpx.ads.quannenghudong;

import cn.hutool.core.annotation.Alias;
import lombok.Data;

@Data
public class QuannengHudongParamField {

    private String pid;
    private String idfa;
    private String imei;
    private String oaid;
    private String caid;
    private String ip;
    private String androidId;
    private String aaid;
    private String ua;
    private String os;
    private String model;
    private String callback;
    @Alias("uniqueId")
    private String uniqueid;
    @Alias("accountId")
    private String account_id;

    private String extra;

}
