package huihuang.proxy.ocpx.ads.huihui;

import lombok.Data;

/**
 * @Author: xietao
 * @Date: 2023/6/8 17:21
 */
@Data
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

    //did
    private String developer_id;

    private String ocpxAccount;


    @Override
    public String toString() {
        return "HuihuiParamField{" +
                "aid='" + aid + '\'' +
                ", sid='" + sid + '\'' +
                ", req_id='" + req_id + '\'' +
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
                ", model='" + model + '\'' +
                ", extra='" + extra + '\'' +
                ", redirect='" + redirect + '\'' +
                ", conv_ext='" + conv_ext + '\'' +
                ", ocpxAccount='" + ocpxAccount + '\'' +
                '}';
    }
}
