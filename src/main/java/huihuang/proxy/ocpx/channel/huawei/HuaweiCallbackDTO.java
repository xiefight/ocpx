package huihuang.proxy.ocpx.channel.huawei;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: xietao
 * @Date: 2023/5/9 20:42
 */
@Data
@ToString
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

}
