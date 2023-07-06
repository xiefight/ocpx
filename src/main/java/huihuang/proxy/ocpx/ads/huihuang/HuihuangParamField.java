package huihuang.proxy.ocpx.ads.huihuang;

import lombok.Data;

/** 
 * 
 * @Author: xietao
 * @Date: 2023/7/6 16:23
 */ 
@Data
public class HuihuangParamField {

    private String chainCode;
    private String eventType;
    private String oaid;
    private String oaidMd5;
    private String idfa;
    private String idfaMd5;
    private String imeiMd5;
    private String aid;
    private String campaignId;
    private String tms;
    private String os;
    private String ip;
    private String adAgent;
    private String callbackUrl;
    private String uid;
    private String sid;
    private String cid;

    private String extra;

}
