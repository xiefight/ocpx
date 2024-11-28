package huihuang.proxy.ocpx.channel.huihuang;

import huihuang.proxy.ocpx.common.CommonColumn;
import huihuang.proxy.ocpx.marketinterface.IMarkDto;
import lombok.Data;
import lombok.ToString;

/**
 * @Author: xietao
 * @Date: 2023/6/8 17:21
 */
@ToString
@Data
public class HuihuangCallbackAdsDTO extends CommonColumn implements IMarkDto {

    private String chainCode;
    private String imeiMd5;
    private String oaid;
    private String oaidMd5;
    private String idfa;
    private String idfaMd5;
    private String taskId;
    private String aid;
    private String uid;
    private String cid;
    private String sid;
    private String eventType;
    private String campaignId;
    private String app;
    private String tms;
    private String os;
    private String ip;
    private String adAgent;
    private String callbackUrl;
    private String extra;
    private String source;
    private String advertisingSpaceId;
    private String channel;
    private String adid;

    private String accountId;

}
