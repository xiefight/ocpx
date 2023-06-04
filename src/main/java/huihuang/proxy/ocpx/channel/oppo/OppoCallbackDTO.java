package huihuang.proxy.ocpx.channel.oppo;

import java.util.Date;

/**
 * @Author: xietao
 * @Date: 2023/5/9 20:42
 */
public class OppoCallbackDTO {

    private Integer id;
    private Integer adsId;

    private String imei;//客户端 imei 经过 AES 加密后的值   二选一，优先imei
    private String ouId;//开发者 ID 经过 AES 加密后的值   oaid
    private String requestId;//请求 id   否
    private String mac;//客户端 mac 经过 AES 加密后的值   否
    private String clientIp;//客户端 ip   否
    private String timestamp;//事件发生时间戳   是
    //com.oppo.test，要填投放应用的包名，不要依赖监测链接上报的 pkg 字段
    private String pkg;//包名，如 com.xxx 如果是快应用，要填快应用 id，如 100137；；；是
    private Integer dataType;//是
    private String payId;//付费交易 Id   否
    private Integer customType;//否
    private Integer channel;//渠道：1、OPPO，2、一加，0、其他   是   默认1
    private Integer type;//0： 无加密（默认为 0）1：imei md5 加密   2：oaid md5 加密   是  默认1
    private Integer appType;//应用类别：1 应用 2 游戏3 快应用 0 其他，默认 1应用   否  默认1
    private Long payAmount;//付费金额（单位：分）    否
    private Integer ascribeType;//  归因类型：1：广告主归因，0：OPPO 归因（默认或者不填即为 0），2：助攻归因    是
    private Long adId;//广告主回传转化数据时，附带已经归因好的广告 id    是

    private String adsName;
    private String callBackStatus;
    private String callBackMes;

    private Date createTime;
    private Date updateTime;

    public OppoCallbackDTO(Integer adsId, String imei, String ouId, String timestamp, String pkg, Integer dataType,
                           Integer channel, Integer type, Integer ascribeType, Long adId, String adsName) {
        this.adsId = adsId;
        this.imei = imei;
        this.ouId = ouId;
        this.timestamp = timestamp;
        this.pkg = pkg;
        this.dataType = dataType;
        this.channel = channel;
        this.type = type;
        this.ascribeType = ascribeType;
        this.adId = adId;
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

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public Integer getCustomType() {
        return customType;
    }

    public void setCustomType(Integer customType) {
        this.customType = customType;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public Long getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Long payAmount) {
        this.payAmount = payAmount;
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
        return "OppoCallbackDTO{" +
                "id=" + id +
                ", adsId=" + adsId +
                ", imei='" + imei + '\'' +
                ", ouId='" + ouId + '\'' +
                ", requestId='" + requestId + '\'' +
                ", mac='" + mac + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", pkg='" + pkg + '\'' +
                ", dataType=" + dataType +
                ", payId='" + payId + '\'' +
                ", customType=" + customType +
                ", channel=" + channel +
                ", type=" + type +
                ", appType=" + appType +
                ", payAmount=" + payAmount +
                ", ascribeType=" + ascribeType +
                ", adId=" + adId +
                ", adsName='" + adsName + '\'' +
                ", callBackStatus='" + callBackStatus + '\'' +
                ", callBackMes='" + callBackMes + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
