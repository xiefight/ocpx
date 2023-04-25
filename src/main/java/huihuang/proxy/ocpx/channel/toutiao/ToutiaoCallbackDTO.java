package huihuang.proxy.ocpx.channel.toutiao;

import huihuang.proxy.ocpx.marketinterface.IMarkDto;

import java.util.Date;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-22 15:26
 **/
public class ToutiaoCallbackDTO implements IMarkDto {

    private Integer id;
    private Integer adsId;
    private String callback;
    private String imei;
    private String idfa;
    private String muid;
    private String oaid;
    private String oaidMd5;
    private String os;
    private String source;
    private String convTime;
    private String eventType;

    private String callBackStatus;
    private String callBackMes;

    private Date createTime;
    private Date updateTime;

    public ToutiaoCallbackDTO() {
    }

    public ToutiaoCallbackDTO(Integer adsId, String callback, String imei, String idfa, String muid, String oaid, String oaidMd5, String os, String source, String convTime, String eventType) {
        this.adsId = adsId;
        this.callback = callback;
        this.imei = imei;
        this.idfa = idfa;
        this.muid = muid;
        this.oaid = oaid;
        this.oaidMd5 = oaidMd5;
        this.os = os;
        this.source = source;
        this.convTime = convTime;
        this.eventType = eventType;
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

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getIdfa() {
        return idfa;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa;
    }

    public String getMuid() {
        return muid;
    }

    public void setMuid(String muid) {
        this.muid = muid;
    }

    public String getOaid() {
        return oaid;
    }

    public void setOaid(String oaid) {
        this.oaid = oaid;
    }

    public String getOaidMd5() {
        return oaidMd5;
    }

    public void setOaidMd5(String oaidMd5) {
        this.oaidMd5 = oaidMd5;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getConvTime() {
        return convTime;
    }

    public void setConvTime(String convTime) {
        this.convTime = convTime;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
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

    public Integer getAdsId() {
        return adsId;
    }

    public void setAdsId(Integer adsId) {
        this.adsId = adsId;
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
}
