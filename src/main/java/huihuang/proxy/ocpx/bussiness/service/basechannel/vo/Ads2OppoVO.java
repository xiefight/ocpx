package huihuang.proxy.ocpx.bussiness.service.basechannel.vo;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-06-04 16:21
 **/
public class Ads2OppoVO {

    //广告id
    private Integer adsId;

    private String imei;
    private String ouId;
    private String pkg;
    private Integer dataType;
    private Integer channel;
    private Integer ascribeType;
    private Long adId;

    //时间
    private Long timestamp;
    //广告侧名称
    private String adsName;

    public Integer getAdsId() {
        return adsId;
    }

    public void setAdsId(Integer adsId) {
        this.adsId = adsId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getOuId() {
        return ouId;
    }

    public void setOuId(String ouId) {
        this.ouId = ouId;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getAscribeType() {
        return ascribeType;
    }

    public void setAscribeType(Integer ascribeType) {
        this.ascribeType = ascribeType;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getAdsName() {
        return adsName;
    }

    public void setAdsName(String adsName) {
        this.adsName = adsName;
    }

    @Override
    public String toString() {
        return "Ads2OppoVO{" +
                "adsId=" + adsId +
                ", imei='" + imei + '\'' +
                ", ouId='" + ouId + '\'' +
                ", pkg='" + pkg + '\'' +
                ", dataType=" + dataType +
                ", channel=" + channel +
                ", ascribeType=" + ascribeType +
                ", adId=" + adId +
                ", timestamp='" + timestamp + '\'' +
                ", adsName='" + adsName + '\'' +
                '}';
    }
}
