package huihuang.proxy.ocpx.channel.iqiyi;

import java.util.Date;

/** 
 * 
 * @Author: xietao
 * @Date: 2023/6/29 21:56
 */ 
public class IQiyiCallbackDTO {

    private Integer id;
    private Integer adsId;
    private String eventType;

    private String eventTime;

    private String adsName;
    private String callBackStatus;
    private String callBackMes;

    private Date createTime;
    private Date updateTime;

    public IQiyiCallbackDTO(Integer adsId, String eventType, String eventTime, String adsName) {
        this.adsId = adsId;
        this.eventType = eventType;
        this.eventTime = eventTime;
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

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
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
        return "IQiyiCallbackDTO{" +
                "id=" + id +
                ", adsId=" + adsId +
                ", eventType='" + eventType + '\'' +
                ", eventTime='" + eventTime + '\'' +
                ", adsName='" + adsName + '\'' +
                ", callBackStatus='" + callBackStatus + '\'' +
                ", callBackMes='" + callBackMes + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
