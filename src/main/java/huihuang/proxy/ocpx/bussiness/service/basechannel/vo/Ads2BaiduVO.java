package huihuang.proxy.ocpx.bussiness.service.basechannel.vo;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-05-28 09:34
 **/
public class Ads2BaiduVO {

    //广告id
    private Integer adsId;
    //回调渠道的decode的url
    private String channelUrl;
    //参数
    //事件
    private String aType;
    private Integer aValue;
    private String cbOaid;
    private String cbOaidMd5;
    private String cbEventTime;
    private String cbIdfa;
    private String cbImei;
    private String cbImeiMd5;
    private String cbAndroidIdMd5;
    private String cbIp;

    //广告侧名称
    private String adsName;
    //密钥
    private String secret;

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

    public String getaType() {
        return aType;
    }

    public void setaType(String aType) {
        this.aType = aType;
    }

    public Integer getaValue() {
        return aValue;
    }

    public void setaValue(Integer aValue) {
        this.aValue = aValue;
    }

    public String getCbOaid() {
        return cbOaid;
    }

    public void setCbOaid(String cbOaid) {
        this.cbOaid = cbOaid;
    }

    public String getCbOaidMd5() {
        return cbOaidMd5;
    }

    public void setCbOaidMd5(String cbOaidMd5) {
        this.cbOaidMd5 = cbOaidMd5;
    }

    public String getCbEventTime() {
        return cbEventTime;
    }

    public void setCbEventTime(String cbEventTime) {
        this.cbEventTime = cbEventTime;
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

    public String getAdsName() {
        return adsName;
    }

    public void setAdsName(String adsName) {
        this.adsName = adsName;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public String toString() {
        return "Ads2BaiduVO{" +
                "adsId=" + adsId +
                ", channelUrl='" + channelUrl + '\'' +
                ", aType='" + aType + '\'' +
                ", aValue=" + aValue +
                ", cbOaid='" + cbOaid + '\'' +
                ", cbOaidMd5='" + cbOaidMd5 + '\'' +
                ", cbEventTime='" + cbEventTime + '\'' +
                ", cbIdfa='" + cbIdfa + '\'' +
                ", cbImei='" + cbImei + '\'' +
                ", cbImeiMd5='" + cbImeiMd5 + '\'' +
                ", cbAndroidIdMd5='" + cbAndroidIdMd5 + '\'' +
                ", cbIp='" + cbIp + '\'' +
                ", adsName='" + adsName + '\'' +
                '}';
    }
}
