package huihuang.proxy.ocpx.ads.weibo;

import cn.hutool.core.annotation.Alias;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class WeiboParamField {

    private String uniq_id;
    private String os;
    private String os_ver;
    private String idfa_md5;
    private String caid;
    private String imei_md5;
    private String oaid_md5;
    private String model;
    private String brand;
    private String language;
    private String ip;
    private String ua;
    private String ts;
    private String cb;
    @Alias("accountId")
    private String account_id;

    private String extra;
    private String monitorType;


}
