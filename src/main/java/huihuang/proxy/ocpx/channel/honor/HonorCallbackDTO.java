package huihuang.proxy.ocpx.channel.honor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-03-25 13:50
 **/
@Data
@ToString
public class HonorCallbackDTO {

    private Integer id;
    private Integer adsId;

    private String conversionId;
    //10004(付费)，10025(当日付费)时该字段必填
    private String oaid;
    private String advertiserId;
    private String conversionTime;

    private String adsName;
    private String callBackStatus;
    private String callBackMes;

    private Date createTime;
    private Date updateTime;


    public HonorCallbackDTO(Integer adsId, String conversionId, String oaid, String advertiserId, String conversionTime, String adsName) {
        this.adsId = adsId;
        this.conversionId = conversionId;
        this.oaid = oaid;
        this.advertiserId = advertiserId;
        this.conversionTime = conversionTime;
        this.adsName = adsName;
    }
}
