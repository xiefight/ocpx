package huihuang.proxy.ocpx.ads.luyun;

import cn.hutool.core.annotation.Alias;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LuyunParamField {

    @Alias("appId")
    private String appid;
    private String channel;
    private String imei;
    @Alias("imeiMd5")
    private String imei_md5;
    private String oaid;
    @Alias("oaidMd5")
    private String oaid_md5;
    @Alias("androidId")
    private String android_id;
    private String ip;
    private String ua;
    @Alias("accountId")
    private String account_id;
    private String callback;

    //回传各渠道时，需要携带的参数（广告侧已有对应的，则不需要出现在这里，如：imei，oaid）
    // 广告测没有对应的，但是还要在回传时使用的，需要出现在这里，如：素材ID、事件类型、事件发生的时间等需要保存
    private String extra;

}
