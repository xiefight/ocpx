package huihuang.proxy.ocpx.channel.guangdiantong;

import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-05-07 15:45
 **/
@Data
public class GuangdiantongCallbackDTO {

    private Integer id;
    private Integer adsId;
    private Integer hashImei; //IMEI设备号保持小写，进行MD5编码字段长度为32字节
    private String hashIdfa;//IOS必填 Android不填 IDFA设备号保持大写，进行MD5编码字段长度为32字节

    private String oaid; //安卓oaid原值
    private String hashOaid; //安卓oaid原值md5编码

    private String actionType;

    private String adsName;
    private String callBackStatus;
    private String callBackMes;

    private Date createTime;
    private Date updateTime;

    public GuangdiantongCallbackDTO(Integer adsId, String actionType, String adsName) {
        this.adsId = adsId;
        this.actionType = actionType;
        this.adsName = adsName;
    }
}
