package huihuang.proxy.ocpx.ads.dingyun;

import cn.hutool.core.annotation.Alias;
import lombok.Data;
import lombok.ToString;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-01-04 08:51
 **/
@Data
@ToString
public class DingyunParamField {

    private String adid;
    private String type;
    private String imei;
    private String imeiMd5;
    private String idfa;
    private String idfaMd5;
    private String oaid;
    private String oaidMd5;
    private String callback;
    @Alias("androidId")
    private String android_id;
    private String mac;
    @Alias("impressionId")
    private String impression_id;
    private String ip;
    @Alias("userAgent")
    private String user_agent;
    @Alias("caidList")
    private String caid_list;
    @Alias("accountId")
    private String account_id;

    private String extra;


}
