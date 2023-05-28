package huihuang.proxy.ocpx.bussiness.service.basechannel.vo;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-05-28 09:34
 **/
public class Ads2XiaomiVO {

    //广告id
    private Integer adsId;
    //事件
    private String eventType;
    //时间
    private String eventTimes;
    //回调渠道的decode的url
    private String callBackUrl;
    //参数
    private String oaid;
    private String imei;

    //广告侧名称
    private String adsName;
    //密钥
    private String secret;

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

    public String getEventTimes() {
        return eventTimes;
    }

    public void setEventTimes(String eventTimes) {
        this.eventTimes = eventTimes;
    }

    public String getCallBackUrl() {
        return callBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }

    public String getOaid() {
        return oaid;
    }

    public void setOaid(String oaid) {
        this.oaid = oaid;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
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

    @Override
    public String toString() {
        return "Ads2XiaomiVO{" +
                "adsId=" + adsId +
                ", eventType='" + eventType + '\'' +
                ", eventTimes='" + eventTimes + '\'' +
                ", callBackUrl='" + callBackUrl + '\'' +
                ", oaid='" + oaid + '\'' +
                ", imei='" + imei + '\'' +
                ", adsName='" + adsName + '\'' +
                '}';
    }
}
