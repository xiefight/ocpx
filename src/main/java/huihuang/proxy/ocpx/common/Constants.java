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

    public class ServerPath{

        public static final String XIAOMI_LTJD = "/xjServer";
        public static final String XIAOMI_YOUKU = "/xyServer";
        public static final String WIFI_XIGUA = "/wxServer";
        public static final String BAIDU_YOUKU = "/byServer";

        public static final String MONITOR_ADDRESS = "/monitorAddress";
        public static final String CLICK_REPORT = "/clickReport";
        public static final String ADS_CALLBACK = "/adsCallBack";
        public static final String ADS_CALLBACK_ID = "/adsCallBack/{id}";


    }

    public class ChannelAdsKey{
        public static final String XIAOMI_LTJD = "xiaomi-ltjd";
        public static final String XIAOMI_YOUKU = "xiaomi-youku";
        public static final String TOUTIAO_MEITUAN = "toutiao-meituan";
        public static final String WIFI_XIGUA = "wifi-xigua";
        public static final String BAIDU_YOUKU = "baidu-youku";
    }

}
