package huihuang.proxy.ocpx.channel.huawei;

import java.util.Date;

/**
 * @Author: xietao
 * @Date: 2023/5/9 20:42
 */
public class HuaweiCallbackDTO {

    private Integer id;
    private Integer adsId;
    private String callback;
    private String contentId;//素材id
    private String campaignId;//计划id
    private String oaid;//设备标识符，明文，没有传空字符
    private String trackingEnabled;//广告主接收到的tracking_enable字段
    private String ip;//IP地址，明文
    private String conversionType;//事件的类型
    private String conversionTime;//事件发生的时间 秒
    private String timestamp;//本请求发起的时间戳
    private String conversionCount;//转 化 数 量
    private String conversionPrice;//转化价格
    private String eventId;//事件id

    private String adsName;
    private String callBackStatus;
    private String callBackMes;

    private Date createTime;
    private Date updateTime;

    public HuaweiCallbackDTO(Integer adsId, String callback, String contentId, String campaignId, String oaid, String trackingEnabled, String conversionType, String conversionTime, String timestamp, String adsName) {
        this.adsId = adsId;
        this.callback = callback;
        this.contentId = contentId;
        this.campaignId = campaignId;
        this.oaid = oaid;
        this.trackingEnabled = trackingEnabled;
        this.conversionType = conversionType;
        this.conversionTime = conversionTime;
        this.timestamp = timestamp;
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

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getOaid() {
        return oaid;
    }

    public void setOaid(String oaid) {
        this.oaid = oaid;
    }

    public String getTrackingEnabled() {
        return trackingEnabled;
    }

    public void setTrackingEnabled(String trackingEnabled) {
        this.trackingEnabled = trackingEnabled;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getConversionCount() {
        return conversionCount;
    }

    public void setConversionCount(String conversionCount) {
        this.conversionCount = conversionCount;
    }

    public String getConversionPrice() {
        return conversionPrice;
    }

    public void setConversionPrice(String conversionPrice) {
        this.conversionPrice = conversionPrice;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
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
}
