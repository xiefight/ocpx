package huihuang.proxy.ocpx.channel.wifi;

import java.util.Date;

/**
 * @Author: xietao
 * @Date: 2023/5/9 20:42
 */
public class WifiCallbackDTO {

    private Integer id;
    private Integer adsId;

    private String clientId;
    private String eventType;
    private String ts;
    private String extra;

    private String adsName;
    private String callBackStatus;
    private String callBackMes;

    private Date createTime;
    private Date updateTime;

    public WifiCallbackDTO(Integer adsId, String clientId, String eventType, String ts, String adsName, String extra) {
        this.adsId = adsId;
        this.clientId = clientId;
        this.eventType = eventType;
        this.ts = ts;
        this.adsName = adsName;
        this.extra = extra;
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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
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

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }


    @Override
    public String toString() {
        return "WifiCallbackDTO{" +
                "id=" + id +
                ", adsId=" + adsId +
                ", clientId='" + clientId + '\'' +
                ", eventType='" + eventType + '\'' +
                ", ts='" + ts + '\'' +
                ", extra='" + extra + '\'' +
                ", adsName='" + adsName + '\'' +
                ", callBackStatus='" + callBackStatus + '\'' +
                ", callBackMes='" + callBackMes + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
