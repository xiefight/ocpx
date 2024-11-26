package huihuang.proxy.ocpx.ads.weibo;

import huihuang.proxy.ocpx.common.CommonColumn;
import huihuang.proxy.ocpx.marketinterface.IMarkDto;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class WeiboAdsDTO extends CommonColumn implements IMarkDto {


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
    private String accountId;
    private String extra;

    private String monitorType;

}
