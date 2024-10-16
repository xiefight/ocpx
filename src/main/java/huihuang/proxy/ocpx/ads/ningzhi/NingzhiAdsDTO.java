package huihuang.proxy.ocpx.ads.ningzhi;

import huihuang.proxy.ocpx.common.CommonColumn;
import huihuang.proxy.ocpx.marketinterface.IMarkDto;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NingzhiAdsDTO extends CommonColumn implements IMarkDto {

    private String idfa;
    private String imei;
    private String oaid;
    private String ip;
    private String ts;
    private String ua;
    private String os;
    private String model;
    private String callback;

    private String yyq;

    private String accountId;

    private String extra;

}
