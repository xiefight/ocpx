package huihuang.proxy.ocpx.ads.quannenghudong;

import huihuang.proxy.ocpx.common.CommonColumn;
import huihuang.proxy.ocpx.marketinterface.IMarkDto;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class QuannengHudongAdsDTO extends CommonColumn implements IMarkDto {

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
    private String uniqueId;
    private String accountId;

    private String extra;

}
