package huihuang.proxy.ocpx.ads.huihui;

/**
 * @Author: xietao
 * @Date: 2023/6/8 17:21
 */
public class HuihuiParamField {

    private String aid;
    private String sid;
    private String req_id;
    private String imei;
    private String oaid;
    private String oaid_md5;
    private String idfa;
    private String idfa_md5;
    private String caid;
    private String caid_md5;
    private String aaid;
    private String ts;
    private String os;
    private String ip;
    private String ua;
    private String callback;
    private String model;
    private String extra;
    private String redirect;
    private String conv_ext;

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getReq_id() {
        return req_id;
    }

    public void setReq_id(String req_id) {
        this.req_id = req_id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
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

    public String getCaid() {
        return caid;
    }

    public void setCaid(String caid) {
        this.caid = caid;
    }

    public String getCaid_md5() {
        return caid_md5;
    }

    public void setCaid_md5(String caid_md5) {
        this.caid_md5 = caid_md5;
    }

    public String getAaid() {
        return aaid;
    }

    public void setAaid(String aaid) {
        this.aaid = aaid;
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

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getConv_ext() {
        return conv_ext;
    }

    public void setConv_ext(String conv_ext) {
        this.conv_ext = conv_ext;
    }

    @Override
    public String toString() {
        return "HuihuiParamField{" +
                "req_id='" + req_id + '\'' +
                ", imei='" + imei + '\'' +
                ", oaid='" + oaid + '\'' +
                ", oaid_md5='" + oaid_md5 + '\'' +
                ", idfa='" + idfa + '\'' +
                ", idfa_md5='" + idfa_md5 + '\'' +
                ", caid='" + caid + '\'' +
                ", caid_md5='" + caid_md5 + '\'' +
                ", aaid='" + aaid + '\'' +
                ", ts='" + ts + '\'' +
                ", os='" + os + '\'' +
                ", ip='" + ip + '\'' +
                ", ua='" + ua + '\'' +
                ", callback='" + callback + '\'' +
                ", extra='" + extra + '\'' +
                '}';
    }
}
