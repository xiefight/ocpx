package huihuang.proxy.ocpx.bussiness.service.basechannel.vo;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-06-02 16:36
 **/
public class Ads2WifiVO {

    //广告id
    private Integer adsId;
    //事件
    private String eventType;

    //广告侧名称
    private String adsName;

    //参数
    private String clientId;
    private String ts;
    private String extra;

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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "Ads2WifiVO{" +
                "adsId=" + adsId +
                ", eventType='" + eventType + '\'' +
                ", adsName='" + adsName + '\'' +
                ", clientId='" + clientId + '\'' +
                ", ts='" + ts + '\'' +
                ", extra='" + extra + '\'' +
                '}';
    }
}
