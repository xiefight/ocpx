package huihuang.proxy.ocpx.ads.huihuangmingtian.field;

import cn.hutool.core.annotation.Alias;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianParamField;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class HuihuangmingtianParamFieldWphExposure extends HuihuangmingtianParamField {

    private String monitor_spot_code;
    private String app_tracking_code;
    private String type;


}
