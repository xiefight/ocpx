package huihuang.proxy.ocpx.ads.huihuang;

import huihuang.proxy.ocpx.common.CommonColumn;
import huihuang.proxy.ocpx.marketinterface.IMarkDto;

/**
 *
 * @Author: xietao
 * @Date: 2023/7/6 16:23
 */
public class HuihuangAdsDTO extends CommonColumn implements IMarkDto {

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

    public String getChainCode() {
        return chainCode;
    }

    public void setChainCode(String chainCode) {
        this.chainCode = chainCode;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getOaid() {
        return oaid;
    }

    public void setOaid(String oaid) {
        this.oaid = oaid;
    }

    public String getOaidMd5() {
        return oaidMd5;
    }

    public void setOaidMd5(String oaidMd5) {
        this.oaidMd5 = oaidMd5;
    }

    public String getIdfa() {
        return idfa;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa;
    }

    public String getIdfaMd5() {
        return idfaMd5;
    }

    public void setIdfaMd5(String idfaMd5) {
        this.idfaMd5 = idfaMd5;
    }

    public String getImeiMd5() {
        return imeiMd5;
    }

    public void setImeiMd5(String imeiMd5) {
        this.imeiMd5 = imeiMd5;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getTms() {
        return tms;
    }

    public void setTms(String tms) {
        this.tms = tms;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAdAgent() {
        return adAgent;
    }

    public void setAdAgent(String adAgent) {
        this.adAgent = adAgent;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "HuihuangAdsDTO{" +
                "chainCode='" + chainCode + '\'' +
                ", eventType='" + eventType + '\'' +
                ", oaid='" + oaid + '\'' +
                ", oaidMd5='" + oaidMd5 + '\'' +
                ", idfa='" + idfa + '\'' +
                ", idfaMd5='" + idfaMd5 + '\'' +
                ", imeiMd5='" + imeiMd5 + '\'' +
                ", aid='" + aid + '\'' +
                ", campaignId='" + campaignId + '\'' +
                ", tms='" + tms + '\'' +
                ", os='" + os + '\'' +
                ", ip='" + ip + '\'' +
                ", adAgent='" + adAgent + '\'' +
                ", callbackUrl='" + callbackUrl + '\'' +
                ", uid='" + uid + '\'' +
                ", sid='" + sid + '\'' +
                ", cid='" + cid + '\'' +
                ", extra='" + extra + '\'' +
                '}';
    }
}
