package huihuang.proxy.ocpx.ads.huihuangmingtian.ads;

import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianPath;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-04-03 16:43
 **/
public class HuihuangKuakePath extends HuihuangmingtianPath {
    @Override
    protected String baseAdsName() {
        return "huihuang-kuake";
    }

    /**
     * 点击上报及转化数据回调接口
     */
    public static final String BASIC_URI = "http://hh-ocpx.yoqu.net/v2/ocpx/media/022078/click?";

}
