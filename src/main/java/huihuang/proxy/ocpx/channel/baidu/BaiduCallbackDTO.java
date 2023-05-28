package huihuang.proxy.ocpx.channel.baidu;

import java.util.Date;

/**
 * @Author: xietao
 * @Date: 2023/5/9 20:42
 */
public class BaiduCallbackDTO {

    private Integer id;
    private Integer adsId;
    private String eventType;

    private String cbIdfa;
    private String cbImei;
    private String cbImeiMd5;
    private String cbAndroidIdMd5;
    private String cbIp;
    private String cbEventTime;

    private String adsName;
    private String callBackStatus;
    private String callBackMes;

    private Date createTime;
    private Date updateTime;

    public BaiduCallbackDTO(Integer adsId, String eventType, String cbIdfa, String cbImei, String cbImeiMd5, String cbAndroidIdMd5, String cbIp, String cbEventTime, String adsName) {
        this.adsId = adsId;
        this.eventType = eventType;
        this.cbIdfa = cbIdfa;
        this.cbImei = cbImei;
        this.cbImeiMd5 = cbImeiMd5;
        this.cbAndroidIdMd5 = cbAndroidIdMd5;
        this.cbIp = cbIp;
        this.cbEventTime = cbEventTime;
        this.adsName = adsName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getCbIdfa() {
        return cbIdfa;
    }

    public void setCbIdfa(String cbIdfa) {
        this.cbIdfa = cbIdfa;
    }

    public String getCbImei() {
        return cbImei;
    }

    public void setCbImei(String cbImei) {
        this.cbImei = cbImei;
    }

    public String getCbImeiMd5() {
        return cbImeiMd5;
    }

    public void setCbImeiMd5(String cbImeiMd5) {
        this.cbImeiMd5 = cbImeiMd5;
    }

    public String getCbAndroidIdMd5() {
        return cbAndroidIdMd5;
    }

    public void setCbAndroidIdMd5(String cbAndroidIdMd5) {
        this.cbAndroidIdMd5 = cbAndroidIdMd5;
    }

    public String getCbIp() {
        return cbIp;
    }

    public void setCbIp(String cbIp) {
        this.cbIp = cbIp;
    }

    public String getCbEventTime() {
        return cbEventTime;
    }

    public void setCbEventTime(String cbEventTime) {
        this.cbEventTime = cbEventTime;
    }

    public String getAdsName() {
        return adsName;
    }

    public void setAdsName(String adsName) {
        this.adsName = adsName;
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

    @Override
    public String toString() {
        return "BaiduCallbackDTO{" +
                "id=" + id +
                ", adsId=" + adsId +
                ", eventType='" + eventType + '\'' +
                ", cbIdfa='" + cbIdfa + '\'' +
                ", cbImei='" + cbImei + '\'' +
                ", cbImeiMd5='" + cbImeiMd5 + '\'' +
                ", cbAndroidIdMd5='" + cbAndroidIdMd5 + '\'' +
                ", cbIp='" + cbIp + '\'' +
                ", cbEventTime='" + cbEventTime + '\'' +
                ", adsName='" + adsName + '\'' +
                ", callBackStatus='" + callBackStatus + '\'' +
                ", callBackMes='" + callBackMes + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
