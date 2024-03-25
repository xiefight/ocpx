package huihuang.proxy.ocpx.bussiness.service.basechannel.vo;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Ads2HonorVO {
    //广告id
    private Integer adsId;
    //事件
    private String eventType;

    //广告侧名称
    private String adsName;
    //密钥
    private String secret;

    //参数
    private String trackId;
    private String conversionId;
    private String conversionTime;
    private String advertiserId;
    private String oaid;

    //回调渠道的url
    private String callbackUrl;
    //时间
    private String timestamp;

}
