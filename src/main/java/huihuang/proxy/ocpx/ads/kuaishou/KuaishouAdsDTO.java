package huihuang.proxy.ocpx.ads.kuaishou;

import huihuang.proxy.ocpx.common.CommonColumn;
import huihuang.proxy.ocpx.marketinterface.IMarkDto;
import lombok.ToString;

/**
 * 快手
 * @Author: xietao
 * @Date: 2023/5/14 10:51
 */
@ToString
public class KuaishouAdsDTO extends CommonColumn  implements IMarkDto {

    private String adid;
    private String imei;
    private String idfa;
    private String oaid;
    private String androidId;
    private String mac;
    private String ip;
    private String userAgent;
    private String accountId;
    private String clickId;
    private String campaignId;
    private String adgroupId;
    private String creativeId;
    private String advertiserId;
    private String rtaId;
    private String caidList;
    private String callback;
    private String extra;

    private String tableName;

    public String getAdid() {
        return adid;
    }

    public void setAdid(String adid) {
        this.adid = adid;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getIdfa() {
        return idfa;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa;
    }

    public String getOaid() {
        return oaid;
    }

    public void setOaid(String oaid) {
        this.oaid = oaid;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getClickId() {
        return clickId;
    }

    public void setClickId(String clickId) {
        this.clickId = clickId;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getAdgroupId() {
        return adgroupId;
    }

    public void setAdgroupId(String adgroupId) {
        this.adgroupId = adgroupId;
    }

    public String getCreativeId() {
        return creativeId;
    }

    public void setCreativeId(String creativeId) {
        this.creativeId = creativeId;
    }

    public String getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(String advertiserId) {
        this.advertiserId = advertiserId;
    }

    public String getRtaId() {
        return rtaId;
    }

    public void setRtaId(String rtaId) {
        this.rtaId = rtaId;
    }

    public String getCaidList() {
        return caidList;
    }

    public void setCaidList(String caidList) {
        this.caidList = caidList;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
