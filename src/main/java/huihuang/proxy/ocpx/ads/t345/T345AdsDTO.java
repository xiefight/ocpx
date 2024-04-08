package huihuang.proxy.ocpx.ads.t345;

import huihuang.proxy.ocpx.common.CommonColumn;
import huihuang.proxy.ocpx.marketinterface.IMarkDto;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class T345AdsDTO extends CommonColumn implements IMarkDto {


    private String adid;
    private String type;
    private String imei;
    private String imeiMd5;
    private String idfa;
    private String idfaMd5;
    private String oaid;
    private String oaidMd5;
    private String callback;
    private String androidId;
    private String mac;
    private String impressionId;
    private String ip;
    private String userAgent;
    private String caidList;
    private String accountId;
    private String extra;

}
