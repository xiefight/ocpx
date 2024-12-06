package huihuang.proxy.ocpx.ads.jiyue;

import huihuang.proxy.ocpx.common.CommonColumn;
import huihuang.proxy.ocpx.marketinterface.IMarkDto;
import lombok.Data;
import lombok.ToString;

/**
 * @Author: xietao
 * @Date: 2023/6/8 17:21
 */
@Data
@ToString
public class JiyueAdsDTO extends CommonColumn implements IMarkDto {

    private String os;
    private String imei;
    private String oaid;
    private String oaidMd5;
    private String idfa;
    private String idfaMd5;
    private String caid;
    private String ip;
    private String ua;
    private String androidId;
    private String mac;
    private String ts;
    private String callback;
    private String extra;

    private String accountId;
    //jiyue家的产品type
    private String type;
}
