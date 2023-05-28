package huihuang.proxy.ocpx.ads.liangdamao;

/**
 *
 * @Author: xietao
 * @Date: 2023/5/28 12:09
 */
public class LiangdamaoParamField {

    private String tp_adv_id;
    private String access_id;
    private String request_id;
    private String imei;
    private String imei_md5;
    private String oaid;
    private String oaid_md5;
    private String idfa;
    private String idfa_md5;
    private String advertiser_id;
    private String android_id_md5;
    private String mac_md5;
    private String deep_link;
    private String ts;
    private String os;
    private String ip;
    private String ip_md5;
    private String ua;
    private String callback_url;
    //拼接参数时，签名放最后
    private String signature;

    private String extra;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTp_adv_id() {
        return tp_adv_id;
    }

    public void setTp_adv_id(String tp_adv_id) {
        this.tp_adv_id = tp_adv_id;
    }

    public String getAccess_id() {
        return access_id;
    }

    public void setAccess_id(String access_id) {
        this.access_id = access_id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImei_md5() {
        return imei_md5;
    }

    public void setImei_md5(String imei_md5) {
        this.imei_md5 = imei_md5;
    }

    public String getOaid() {
        return oaid;
    }

    public void setOaid(String oaid) {
        this.oaid = oaid;
    }

    public String getOaid_md5() {
        return oaid_md5;
    }

    public void setOaid_md5(String oaid_md5) {
        this.oaid_md5 = oaid_md5;
    }

    public String getIdfa() {
        return idfa;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa;
    }

    public String getIdfa_md5() {
        return idfa_md5;
    }

    public void setIdfa_md5(String idfa_md5) {
        this.idfa_md5 = idfa_md5;
    }

    public String getAdvertiser_id() {
        return advertiser_id;
    }

    public void setAdvertiser_id(String advertiser_id) {
        this.advertiser_id = advertiser_id;
    }

    public String getAndroid_id_md5() {
        return android_id_md5;
    }

    public void setAndroid_id_md5(String android_id_md5) {
        this.android_id_md5 = android_id_md5;
    }

    public String getMac_md5() {
        return mac_md5;
    }

    public void setMac_md5(String mac_md5) {
        this.mac_md5 = mac_md5;
    }

    public String getDeep_link() {
        return deep_link;
    }

    public void setDeep_link(String deep_link) {
        this.deep_link = deep_link;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp_md5() {
        return ip_md5;
    }

    public void setIp_md5(String ip_md5) {
        this.ip_md5 = ip_md5;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getCallback_url() {
        return callback_url;
    }

    public void setCallback_url(String callback_url) {
        this.callback_url = callback_url;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "LiangdamaoParamField{" +
                "tp_adv_id='" + tp_adv_id + '\'' +
                ", request_id='" + request_id + '\'' +
                ", imei='" + imei + '\'' +
                ", imei_md5='" + imei_md5 + '\'' +
                ", oaid='" + oaid + '\'' +
                ", oaid_md5='" + oaid_md5 + '\'' +
                ", idfa='" + idfa + '\'' +
                ", idfa_md5='" + idfa_md5 + '\'' +
                ", advertiser_id='" + advertiser_id + '\'' +
                ", android_id_md5='" + android_id_md5 + '\'' +
                ", mac_md5='" + mac_md5 + '\'' +
                ", deep_link='" + deep_link + '\'' +
                ", ts='" + ts + '\'' +
                ", os='" + os + '\'' +
                ", ip='" + ip + '\'' +
                ", ip_md5='" + ip_md5 + '\'' +
                ", ua='" + ua + '\'' +
                ", callback_url='" + callback_url + '\'' +
                ", signature='" + signature + '\'' +
                ", extra='" + extra + '\'' +
                '}';
    }
}
