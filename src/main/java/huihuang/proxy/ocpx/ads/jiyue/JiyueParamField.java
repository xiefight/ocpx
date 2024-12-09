package huihuang.proxy.ocpx.ads.jiyue;

import cn.hutool.core.annotation.Alias;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JiyueParamField {

    private String os;
    private String imei;
    private String oaid;
    @Alias("oaidMd5")
    private String oaidmd5;
    private String idfa;
    @Alias("idfaMd5")
    private String idfamd5;
    private String caid;
    private String ip;
    private String ua;
    @Alias("androidId")
    private String androidid;
    private String mac;
    private String ts;
    private String callback;
    private String extra;
    //jiyue家的产品type
    private String type;
    @Alias("accountId")
    private String account_id;
    private String token;
}
