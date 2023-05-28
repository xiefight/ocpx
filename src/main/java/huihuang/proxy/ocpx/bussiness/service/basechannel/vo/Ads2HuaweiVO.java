package huihuang.proxy.ocpx.bussiness.service.basechannel.vo;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-05-28 13:04
 **/
public class Ads2HuaweiVO {
    //广告id
    private Integer adsId;
    //事件
    private String eventType;


    //广告侧名称
    private String adsName;
    //密钥
    private String secret;

    //参数
    private String campaignId;
    private String contentId;
    private String trackingEnabled;
    private String conversionType;
    private String conversionTime;
    private String oaid;

    //回调渠道的url
    private String callbackUrl;
    //时间
    private String timestamp;

    public Integer getAdsId() {
        return adsId;
    }

    public void setAdsId(Integer adsId) {
        this.adsId = adsId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getAdsName() {
        return adsName;
    }

    public void setAdsName(String adsName) {
        this.adsName = adsName;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getTrackingEnabled() {
        return trackingEnabled;
    }

    public void setTrackingEnabled(String trackingEnabled) {
        this.trackingEnabled = trackingEnabled;
    }

    public String getConversionType() {
        return conversionType;
    }

    public void setConversionType(String conversionType) {
        this.conversionType = conversionType;
    }

    public String getConversionTime() {
        return conversionTime;
    }

    public void setConversionTime(String conversionTime) {
        this.conversionTime = conversionTime;
    }

    public String getOaid() {
        return oaid;
    }

    public void setOaid(String oaid) {
        this.oaid = oaid;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Ads2HuaweiVO{" +
                "adsId=" + adsId +
                ", eventType='" + eventType + '\'' +
                ", adsName='" + adsName + '\'' +
                ", campaignId='" + campaignId + '\'' +
                ", contentId='" + contentId + '\'' +
                ", trackingEnabled='" + trackingEnabled + '\'' +
                ", conversionType='" + conversionType + '\'' +
                ", conversionTime='" + conversionTime + '\'' +
                ", oaid='" + oaid + '\'' +
                ", callbackUrl='" + callbackUrl + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
