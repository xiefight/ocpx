package huihuang.proxy.ocpx.ads.xiguavideo;

import huihuang.proxy.ocpx.ads.litianjingdong.LTJDAdsDTO;

/**
 * xigua使用的也是力天京东的对接文档
 *
 * @Author: xietao
 * @Date: 2023/5/9 20:26
 */
public class XiguaAdsDTO extends LTJDAdsDTO {

    //回传各渠道时，需要携带的参数（广告侧已有对应的，则不需要出现在这里，如：imei，oaid）
    // 广告测没有对应的，但是还要在回传时使用的，需要出现在这里，如：wifi万能钥匙侧的广告检索ID，广告创意ID，广告检索时间等需要保存
    private String extra;

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
