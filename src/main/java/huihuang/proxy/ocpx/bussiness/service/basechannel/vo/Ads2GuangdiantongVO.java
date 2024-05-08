package huihuang.proxy.ocpx.bussiness.service.basechannel.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-05-07 16:06
 **/
@Data
@ToString
public class Ads2GuangdiantongVO {

    //user_id下
    private String hashImei;
    private String hashIdfa;
    private String oaid;
    private String hashOaid;

    //行为类型
    private String actionType;
    //次留时，行为参数 action_param 中 length_of_stay=1
    private String lengthOfStay;

    private String channelUrl;

    private Integer adsId;
    private String adsName;

}
