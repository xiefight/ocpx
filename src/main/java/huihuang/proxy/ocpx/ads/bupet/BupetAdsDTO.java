package huihuang.proxy.ocpx.ads.bupet;

import huihuang.proxy.ocpx.common.CommonColumn;
import huihuang.proxy.ocpx.marketinterface.IMarkDto;
import lombok.Data;
import lombok.ToString;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-01-04 09:02
 **/
@Data
@ToString
public class BupetAdsDTO extends CommonColumn implements IMarkDto {


    private String idfa;
    private String idfaMd5;
    private String imei;
    private String oaid;
    private String oaidMd5;
    private String androidId;
    private String androidIdMd5;
    private String gaid;
    private String ip;
    private String userAgent;
    private String model;
    private String callback;
    private String caid;
    private String reqId;
    private String accountId;

    private String extra;

}
