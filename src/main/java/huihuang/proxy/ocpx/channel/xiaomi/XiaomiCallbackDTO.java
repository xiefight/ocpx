package huihuang.proxy.ocpx.channel.xiaomi;

import java.util.Date;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-25 20:38
 **/
public class XiaomiCallbackDTO {

    private Integer id;
    private Integer adsId;
    private String callback;
    private String eventType;
    private String eventTime;
    private String imei;
    private String oaid;
    private String signature;

    private String adsName;

    private String callBackStatus;
    private String callBackMes;

    private Date createTime;
    private Date updateTime;

    public XiaomiCallbackDTO(Integer adsId, String callback, String eventType, String eventTime, String imei, String oaid, String signature, String adsName) {
        this.adsId = adsId;
        this.callback = callback;
        this.eventType = eventType;
        this.eventTime = eventTime;
        this.imei = imei;
        this.oaid = oaid;
        this.signature = signature;
        this.adsName = adsName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getOaid() {
        return oaid;
    }

    public void setOaid(String oaid) {
        this.oaid = oaid;
    }

    public String getCallBackStatus() {
        return callBackStatus;
    }

    public void setCallBackStatus(String callBackStatus) {
        this.callBackStatus = callBackStatus;
    }

    public String getCallBackMes() {
        return callBackMes;
    }

    public void setCallBackMes(String callBackMes) {
        this.callBackMes = callBackMes;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getAdsId() {
        return adsId;
    }

    public void setAdsId(Integer adsId) {
        this.adsId = adsId;
    }

    public String getAdsName() {
        return adsName;
    }

    public void setAdsName(String adsName) {
        this.adsName = adsName;
    }

    @Override
    public String toString() {
        return "XiaomiCallbackDTO{" +
                "id=" + id +
                ", adsId=" + adsId +
                ", callback='" + callback + '\'' +
                ", eventType='" + eventType + '\'' +
                ", eventTime='" + eventTime + '\'' +
                ", imei='" + imei + '\'' +
                ", oaid='" + oaid + '\'' +
                ", signature='" + signature + '\'' +
                ", adsName='" + adsName + '\'' +
                ", callBackStatus='" + callBackStatus + '\'' +
                ", callBackMes='" + callBackMes + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
