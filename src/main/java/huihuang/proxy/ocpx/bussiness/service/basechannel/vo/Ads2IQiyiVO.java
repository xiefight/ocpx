package huihuang.proxy.ocpx.bussiness.service.basechannel.vo;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-06-29 22:21
 **/
public class Ads2IQiyiVO {

    //广告id
    private Integer adsId;
    //回调渠道的decode的url
    private String channelUrl;
    //参数
    //事件
    private Integer eventType;
    private Integer eventTime;

    //广告侧名称
    private String adsName;

    public Integer getAdsId() {
        return adsId;
    }

    public void setAdsId(Integer adsId) {
        this.adsId = adsId;
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public Integer getEventTime() {
        return eventTime;
    }

    public void setEventTime(Integer eventTime) {
        this.eventTime = eventTime;
    }

    public String getAdsName() {
        return adsName;
    }

    public void setAdsName(String adsName) {
        this.adsName = adsName;
    }

    @Override
    public String toString() {
        return "Ads2BaiduVO{" +
                "adsId=" + adsId +
                ", channelUrl='" + channelUrl + '\'' +
                ", eventType='" + eventType + '\'' +
                ", eventTime='" + eventTime + '\'' +
                ", adsName='" + adsName + '\'' +
                '}';
    }

}
