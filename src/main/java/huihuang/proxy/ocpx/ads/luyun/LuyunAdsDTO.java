package huihuang.proxy.ocpx.ads.luyun;

import huihuang.proxy.ocpx.common.CommonColumn;
import huihuang.proxy.ocpx.marketinterface.IMarkDto;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class LuyunAdsDTO extends CommonColumn  implements IMarkDto {

    private String appId;
    private String channel;
    private String oaid;
    private String oaidMd5;
    private String imei;
    private String imeiMd5;
    private String androidId;
    private String ip;
    private String ua;
    private String accountId;
    private String callback;
    private String extra;

}
