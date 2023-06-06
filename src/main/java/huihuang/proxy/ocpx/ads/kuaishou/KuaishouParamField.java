package huihuang.proxy.ocpx.ads.kuaishou;

import cn.hutool.core.annotation.Alias;

/**
 *
 * @Author: xietao
 * @Date: 2023/5/14 11:44
 */
public class KuaishouParamField {

    private String adid;
    private String imei;
    private String idfa;
    private String oaid;
    @Alias("androidId")
    private String android_id;
    private String mac;
    private String ip;
    @Alias("userAgent")
    private String user_agent;
    @Alias("accountId")
    private String account_id;
    @Alias("clickId")
    private String click_id;
    @Alias("campaignId")
    private String campaign_id;
    @Alias("adgroupId")
    private String adgroup_id;
    @Alias("creativeId")
    private String creative_id;
    @Alias("advertiserId")
    private String advertiser_id;
    @Alias("rtaId")
    private String rta_id;
    @Alias("caidList")
    private String caid_list;
    private String callback;

    //回传各渠道时，需要携带的参数（广告侧已有对应的，则不需要出现在这里，如：imei，oaid）
    // 广告测没有对应的，但是还要在回传时使用的，需要出现在这里，如：素材ID、事件类型、事件发生的时间等需要保存
    private String extra;

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

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

    public String getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(String android_id) {
        this.android_id = android_id;
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

    public String getUser_agent() {
        return user_agent;
    }

    public void setUser_agent(String user_agent) {
        this.user_agent = user_agent;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getClick_id() {
        return click_id;
    }

    public void setClick_id(String click_id) {
        this.click_id = click_id;
    }

    public String getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(String campaign_id) {
        this.campaign_id = campaign_id;
    }

    public String getAdgroup_id() {
        return adgroup_id;
    }

    public void setAdgroup_id(String adgroup_id) {
        this.adgroup_id = adgroup_id;
    }

    public String getCreative_id() {
        return creative_id;
    }

    public void setCreative_id(String createive_id) {
        this.creative_id = createive_id;
    }

    public String getAdvertiser_id() {
        return advertiser_id;
    }

    public void setAdvertiser_id(String advertiser_id) {
        this.advertiser_id = advertiser_id;
    }

    public String getRta_id() {
        return rta_id;
    }

    public void setRta_id(String rta_id) {
        this.rta_id = rta_id;
    }

    public String getCaid_list() {
        return caid_list;
    }

    public void setCaid_list(String caid_list) {
        this.caid_list = caid_list;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    @Override
    public String toString() {
        return "KuaishouParamField{" +
                "adid='" + adid + '\'' +
                ", imei='" + imei + '\'' +
                ", idfa='" + idfa + '\'' +
                ", oaid='" + oaid + '\'' +
                ", android_id='" + android_id + '\'' +
                ", mac='" + mac + '\'' +
                ", ip='" + ip + '\'' +
                ", user_agent='" + user_agent + '\'' +
                ", account_id='" + account_id + '\'' +
                ", click_id='" + click_id + '\'' +
                ", campaign_id='" + campaign_id + '\'' +
                ", adgroup_id='" + adgroup_id + '\'' +
                ", creative_id='" + creative_id + '\'' +
                ", advertiser_id='" + advertiser_id + '\'' +
                ", rta_id='" + rta_id + '\'' +
                ", caid_list='" + caid_list + '\'' +
                ", callback='" + callback + '\'' +
                ", extra='" + extra + '\'' +
                '}';
    }
}
