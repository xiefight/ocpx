package huihuang.proxy.ocpx.common;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-20 22:45
 **/
public class Constants {

    /**
     * 上报状态
     */
    public enum ReportStatus {
        INIT("0", "初始化"),
        SUCCESS("1", "成功"),
        FAIL("2", "失败");

        private String code;
        private String info;

        ReportStatus(String code, String info) {
            this.code = code;
            this.info = info;
        }

        public String getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }

    /**
     * 回调状态
     */
    public enum CallBackStatus {
        INIT("0", "未回调"),
        SUCCESS("1", "回调成功"),
        FAIL("2", "回调失败");

        private String code;
        private String info;

        CallBackStatus(String code, String info) {
            this.code = code;
            this.info = info;
        }

        public String getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }

    public class ServerPath {

        public static final String XIAOMI_LTJD = "/xjServer";
        public static final String XIAOMI_YOUKU = "/xyServer";
        public static final String XIAOMI_KUAISHOU = "/xkServer";
        public static final String XIAOMI_XIAOHONGSHU = "/xxhsServer";
        public static final String XIAOMI_XINYU = "/xxinyuServer";
        public static final String XIAOMI_TANTAN = "/xtServer";
        public static final String XIAOMI_XINYU_YOUDAO = "/xxyydServer";
        public static final String XIAOMI_DONGCHEDI = "/xdcdServer";

        public static final String WIFI_XIGUA = "/wxServer";
        public static final String WIFI_FANQIE = "/wfServer";

        public static final String BAIDU_YOUKU = "/byServer";
        public static final String BAIDU_LTJD = "/bjServer";
        public static final String BAIDU_JDSS = "/bjdssServer";
        public static final String BAIDU_JDJR = "/bjdjrServer";
        public static final String BAIDU_KUAISHOU = "/bkServer";
        public static final String BAIDU_TIANMAO = "/btServer";
        public static final String BAIDU_FANQIE = "/bfServer";
        public static final String BAIDU_DONGCHEDI = "/bdcdServer";
        public static final String BAIDU_XIANYU = "/bxyServer";
        public static final String BDSS_LTJD = "/bdssjdServer";

        public static final String HUAWEI_KUAISHOU = "/hkServer";
        public static final String HUAWEI_LTJD = "/hjServer";
        public static final String HUAWEI_YOUKU = "/hyServer";
        public static final String HUAWEI_FANQIE = "/hfServer";
        public static final String HUAWEI_HUIHUANG = "/hhServer";
        public static final String HUAWEI_TUHU = "/htServer";

        public static final String OPPO_KUAISHOU = "/okServer";


        public static final String IQIYI_KUAISHOU = "/ikServer";


        public static final String MONITOR_ADDRESS = "/monitorAddress";
        public static final String CLICK_REPORT = "/clickReport";
        public static final String ADS_CALLBACK = "/adsCallBack";
        public static final String ADS_CALLBACK_ID = "/adsCallBack/{id}";


    }

    public class ChannelAdsKey {
        public static final String XIAOMI_LTJD = "xiaomi-ltjd";
        public static final String XIAOMI_YOUKU = "xiaomi-youku";
        public static final String XIAOMI_KUAISHOU = "xiaomi-kuaishou";
        public static final String XIAOMI_XIAOHONGSHU = "xiaomi-xiaohongshu";
        public static final String XIAOMI_XINYU = "xiaomi-xinyu";
        public static final String XIAOMI_TANTAN = "xiaomi-tantan";
        public static final String XIAOMI_XINYU_YOUDAO = "xiaomi-xinyu-youdao";
        public static final String XIAOMI_DONGCHEDI = "xiaomi-dongchedi";

        public static final String TOUTIAO_MEITUAN = "toutiao-meituan";

        public static final String WIFI_XIGUA = "wifi-xigua";
        public static final String WIFI_FANQIE = "wifi-fanqie";

        public static final String BAIDU_YOUKU = "baidu-youku";
        public static final String BAIDU_LTJD = "baidu-ltjd";
        public static final String BAIDU_JDSS = "baidu-jdss";
        public static final String BAIDU_JDJR = "baidu-jdjr";
        public static final String BAIDU_KUAISHOU = "baidu-kuaishou";
        public static final String BAIDU_TIANMAO = "baidu-tianmao";
        public static final String BAIDU_FANQIE = "baidu-fanqie";
        public static final String BAIDU_DONGCHEDI = "baidu-dongchedi";
        public static final String BAIDU_XIANYU = "baidu-xianyu";
        public static final String BDSS_LTJD = "bdss-ltjd";

        public static final String HUAWEI_KUAISHOU = "huawei-kuaishou";
        public static final String HUAWEI_LTJD = "huawei-ltjd";
        public static final String HUAWEI_YOUKU = "huawei-youku";
        public static final String HUAWEI_FANQIE = "huawei-fanqie";
        public static final String HUAWEI_HUIHUANG = "huawei-huihuang";
        public static final String HUAWEI_TUHU = "huawei-tuhu";

        public static final String OPPO_KUAISHOU = "oppo-kuaishou";

        public static final String IQIYI_KUAISHOU = "iqiyi-kuaishou";
    }

    public class AdsForChannel {
        public static final String OCPX = "/ocpx/v1";
        public static final String LIANGDAMAO = OCPX + "/ldm";
    }

}
