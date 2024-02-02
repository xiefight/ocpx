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
        public static final String XIAOMI_TT_YOUDAO = "/xttydServer";
        public static final String XIAOMI_DONGCHEDI = "/xdcdServer";
        public static final String XIAOMI_XIANYU = "/xxyServer";
        public static final String XIAOMI_QUANNENG_FANQIE = "/xqfServer";
        public static final String XIAOMI_QUANNENG_FANQIECHANGTING = "/xqfqctServer";
        public static final String XIAOMI_QUANNENG_XIGUA_VIDEO = "/xqxvServer";
        public static final String XIAOMI_QUANNENG_JINRITOUTIAO = "/xqjrttServer";
        public static final String XIAOMI_QUANNENG_DOUYINJISU = "/xqdyjsServer";
        public static final String XIAOMI_QUANNENG_DOUYINHUOSHAN = "/xqdyhsServer";
        public static final String XIAOMI_QUANNENG_BAIDUJISU = "/xqbdjsServer";
        public static final String XIAOMI_QUANNENG_XIAOHONGSHU = "/xqxhsServer";
        public static final String XIAOMI_QUANNENG_IQIYI = "/xqiqyServer";
        public static final String XIAOMI_QUANNENG_HGDJ = "/xqhgdjServer";
        public static final String XIAOMI_IQIYI = "/xiqyServer";
        public static final String XIAOMI_DOUYIN = "/xdyServer";
        public static final String XIAOMI_DOUYINHUOSHAN = "/xdyhsServer";
        public static final String XIAOMI_HUIHUANG_DOUYINHUOSHAN = "/xhhdyhsServer";
        public static final String XIAOMI_HUIHUANG_HONGGUODUANJU = "/xhhhgdjServer";
        public static final String XIAOMI_HUIHUANG_FANQIECHANGTING = "/xhhfqctServer";
        public static final String XIAOMI_HUIHUANG_XIGUAVIDEO = "/xhhxgServer";
        public static final String XIAOMI_HUIHUANG_TOUTIAOJISU = "/xhhttjsServer";
        public static final String XIAOMI_HUIHUANG_YITAO = "/xhhytServer";
        public static final String XIAOMI_HUIHUANG_FENGMANG_YITAO = "/xhhfmytServer";

        public static final String XIAOMI_DINGYUN_DOUYINHUOSHAN = "/xdydyhsServer";
        public static final String XIAOMI_DINGYUN_XIGUAVIDEO = "/xdyxgServer";
        public static final String XIAOMI_DINGYUN_FANQIECHANGTING = "/xdyfqctServer";
        public static final String XIAOMI_DINGYUN_YOUSHI = "/xdyysServer";

        public static final String XIAOMI_KEEP = "/xkeepServer";
        public static final String XIAOMI_LUYUN_PAIPAI = "/xluppServer";
        public static final String XIAOMI_QIDU = "/xqiduServer";


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
        public static final String BAIDU_DIANTAO = "/bdtServer";
        public static final String BAIDU_HUIHUANG_TIANMAO = "/bhhtmServer";
        public static final String BAIDU_HUIHUANG_DOUYINHUOSHAN = "/bhhdyhsServer";
        public static final String BAIDU_HUIHUANG_FANQIECHANGTING = "/bhhfqctServer";
        public static final String BAIDU_HUIHUANG_XIGUAVIDEO = "/bhhxgServer";
        public static final String BAIDU_HUIHUANG_JINRITOUTIAO = "/bhhjrttServer";
        public static final String BAIDU_HUIHUANG_TOUTIAOJISU = "/bhhttjsServer";
        public static final String BAIDU_HUIHUANG_HONGGUODUANJU = "/bhhhgdjServer";
        public static final String BAIDU_HUIHUANG_YITAO = "/bhhytServer";
        public static final String BAIDU_HUIHUANG_PIPIXIA = "/bhhppxServer";
        public static final String BDSS_LTJD = "/bdssjdServer";
        public static final String BDSS_KUAISHOU = "/bdssksServer";
        public static final String BAIDU_DOUYIN = "/bdyServer";
        public static final String BAIDU_QUANNENG_XIGUA_VIDEO = "/bqxvServer";
        public static final String BAIDU_QUANNENG_DOUYIN_JISU = "/bqdyjsServer";
        public static final String BAIDU_QUANNENG_JINRITOUTIAO = "/bqjrttServer";
        public static final String BAIDU_QUANNENG_FANQIE = "/bqfanqieServer";
        public static final String BAIDU_QUANNENG_FANQIE_CHANGTING = "/bqfqctServer";
        public static final String BAIDU_QUANNENG_DOUYIN_HUOSHAN = "/bqdyhsServer";
        public static final String BAIDU_QUANNENG_TENGXUNSHIPIN = "/bqtxspServer";
        public static final String BAIDU_QUANNENG_PIPIXIA = "/bqppxServer";
        public static final String BAIDU_QUANNENG_YOUSHI = "/bqysServer";

        public static final String BAIDU_DINGYUN_DOUYINHUOSHAN = "/bdydyhsServer";
        public static final String BAIDU_DINGYUN_XIGUAVIDEO = "/bdyxgServer";
        public static final String BAIDU_DINGYUN_FANQIECHANGTING = "/bdyfqctServer";
        public static final String BAIDU_DINGYUN_YOUSHI = "/bdyysServer";

        public static final String BAIDU_KEEP = "/bkeepServer";
        public static final String BAIDU_LUYUN_PAIPAI = "/blyppServer";



        public static final String HUAWEI_KUAISHOU = "/hkServer";
        public static final String HUAWEI_LTJD = "/hjServer";
        public static final String HUAWEI_YOUKU = "/hyServer";
        public static final String HUAWEI_FANQIE = "/hfServer";
        public static final String HUAWEI_DOUYIN = "/hdyServer";
        public static final String HUAWEI_HUIHUANG = "/hhServer";
        public static final String HUAWEI_TUHU = "/htServer";
        public static final String HUAWEI_XIANYU = "/hxyServer";
        public static final String HUAWEI_IQIYI = "/hiqyServer";
        public static final String HUAWEI_DIANTAO = "/hdtServer";
        public static final String HUAWEI_HUIHUI_TANTAN = "/hhhttServer";
        public static final String HUAWEI_HUIHUANG_DOUYINHUOSHAN = "/hhhdyhsServer";
        public static final String HUAWEI_HUIHUANG_XIGUAVIDEO = "/hhhxgServer";
        public static final String HUAWEI_HUIHUANG_FANQIECHANGTING = "/hhhfqctServer";
        public static final String HUAWEI_HUIHUANG_HONGGUODUANJU = "/hhhhgdjServer";
        public static final String HUAWEI_HUIHUANG_TOUTIAOJISU = "/hhhttjsServer";
        public static final String HUAWEI_QUANNENG_FANQIE = "/hqfServer";
        public static final String HUAWEI_QUANNENG_FANQIECHANGTING = "/hqfqctServer";
        public static final String HUAWEI_QUANNENG_DOUYIN_JISU = "/hqdyjsServer";
        public static final String HUAWEI_QUANNENG_DOUYIN_HUOSHAN = "/hqdyhsServer";
        public static final String HUAWEI_QUANNENG_XIGUA_VIDEO = "/hqxvServer";
        public static final String HUAWEI_QUANNENG_BAIDU_JISU = "/hqbaidujisuServer";
        public static final String HUAWEI_QUANNENG_JINRITOUTIAO = "/hqjrttServer";
        public static final String HUAWEI_QUANNENG_IQIYI = "/hqiqyServer";
        public static final String HUAWEI_QUANNENG_HEMAJUCHANG = "/hqhmjcServer";
        public static final String HUAWEI_QUANNENG_XIAOHONGSHU = "/hqxhsServer";
        public static final String HUAWEI_QUANNENG_YOUSHI = "/hqysServer";

        public static final String HUAWEI_DINGYUN_DOUYINHUOSHAN = "/hdydyhsServer";
        public static final String HUAWEI_DINGYUN_XIGUAVIDEO = "/hdyxgServer";
        public static final String HUAWEI_DINGYUN_FANQIECHANGTING = "/hdyfqctServer";

        public static final String HUAWEI_KEEP = "/hkeepServer";

        public static final String OPPO_KUAISHOU = "/okServer";


        public static final String IQIYI_KUAISHOU = "/ikServer";
        public static final String IQIYI_HUIHUANG_FANQIE = "/ihhfqServer";
        public static final String IQIYI_HUIHUANG_XIGUAVIDEO = "/ihhxgServer";
        public static final String IQIYI_QUANNENG_XIGUAVIDEO = "/iqnxgServer";
        public static final String IQIYI_DINGYUN_XIGUAVIDEO = "/idyxgServer";
        public static final String IQIYI_LUYUN_KEEP = "/ilykeepServer";


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
        public static final String XIAOMI_TT_YOUDAO = "xiaomi-tt-youdao";
        public static final String XIAOMI_DONGCHEDI = "xiaomi-dongchedi";
        public static final String XIAOMI_XIANYU = "xiaomi-xianyu";
        public static final String XIAOMI_IQIYI = "xiaomi-iqiyi";
        public static final String XIAOMI_DOUYIN = "xiaomi-douyin";
        public static final String XIAOMI_DOUYINHUOSHAN = "xiaomi-douyinhuoshan";
        public static final String XIAOMI_QUANNENG_FANQIE = "xiaomi-quanneng-fanqie";
        public static final String XIAOMI_QUANNENG_FANQIECHANGTING = "xiaomi-quanneng-fanqiechangting";
        public static final String XIAOMI_QUANNENG_XIGUA_VIDEO = "xiaomi-quanneng-xiguavideo";
        public static final String XIAOMI_QUANNENG_JINRITOUTIAO = "xiaomi-quanneng-jinritoutiao";
        public static final String XIAOMI_QUANNENG_DOUYINJISU = "xiaomi-quanneng-douyinjisu";
        public static final String XIAOMI_QUANNENG_DOUYINHUOSHAN = "xiaomi-quanneng-douyinhuoshan";
        public static final String XIAOMI_QUANNENG_BAIDUJISU = "xiaomi-quanneng-baidujisu";
        public static final String XIAOMI_QUANNENG_XIAOHONGSHU = "xiaomi-quanneng-xiaohongshu";
        public static final String XIAOMI_QUANNENG_IQIYI = "xiaomi-quanneng-iqiyi";
        public static final String XIAOMI_QUANNENG_HGDJ = "xiaomi-quanneng-hgdj";
        public static final String XIAOMI_HUIHUANG_DOUYINHUOSHAN = "xiaomi-huihuang-douyinhuoshan";
        public static final String XIAOMI_HUIHUANG_HONGGUODUANJU = "xiaomi-huihuang-hongguoduanju";
        public static final String XIAOMI_HUIHUANG_FANQIECHANGTING = "xiaomi-huihuang-fanqiechangting";
        public static final String XIAOMI_HUIHUANG_XIGUAVIDEO = "xiaomi-huihuang-xiguavideo";
        public static final String XIAOMI_HUIHUANG_TOUTIAOJISU = "xiaomi-huihuang-toutiaojisu";
        public static final String XIAOMI_HUIHUANG_YITAO = "xiaomi-huihuang-yitao";
        public static final String XIAOMI_HUIHUANG_FENGMANG_YITAO = "xiaomi-huihuangfengmang-yitao";

        public static final String XIAOMI_DINGYUN_DOUYINHUOSHAN = "xiaomi-dingyun-douyinhuoshan";
        public static final String XIAOMI_DINGYUN_XIGUAVIDEO = "xiaomi-dingyun-xiguavideo";
        public static final String XIAOMI_DINGYUN_FANQIECHANGTING = "xiaomi-dingyun-fanqiechangting";
        public static final String XIAOMI_DINGYUN_YOUSHI = "xiaomi-dingyun-youshi";

        public static final String XIAOMI_QIDU = "xiaomi-qidu";
        public static final String XIAOMI_KEEP = "xiaomi-keep";
        public static final String XIAOMI_LUYUN_PAIPAI = "xiaomi-luyun-paipai";

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
        public static final String BAIDU_DIANTAO = "baidu-diantao";
        public static final String BDSS_LTJD = "bdss-ltjd";
        public static final String BDSS_KUAISHOU = "bdss-kuaishou";
        public static final String BAIDU_DOUYIN = "baidu-douyin";
        public static final String BAIDU_HUIHUANG_TIANMAO = "baidu-huihuang-tianmao";
        public static final String BAIDU_HUIHUANG_DOUYINHUOSHAN = "baidu-huihuang-douyinhuoshan";
        public static final String BAIDU_HUIHUANG_XIGUAVIDEO = "baidu-huihuang-xiguavideo";
        public static final String BAIDU_HUIHUANG_FANQIECHANGTING = "baidu-huihuang-fanqiechangting";
        public static final String BAIDU_HUIHUANG_JINRITOUTIAO = "baidu-huihuang-jinritoutiao";
        public static final String BAIDU_HUIHUANG_TOUTIAOJISU = "baidu-huihuang-toutiaojisu";
        public static final String BAIDU_HUIHUANG_HONGGUODUANJU = "baidu-huihuang-hongguoduanju";
        public static final String BAIDU_HUIHUANG_YITAO = "baidu-huihuang-yitao";
        public static final String BAIDU_HUIHUANG_PIPIXIA = "baidu-huihuang-pipixia";
        public static final String BAIDU_QUANNENG_XIGUA_VIDEO = "baidu-quanneng-xiguavideo";
        public static final String BAIDU_QUANNENG_DOUYIN_JISU = "baidu-quanneng-douyinjisu";
        public static final String BAIDU_QUANNENG_JINRITOUTIAO = "baidu-quanneng-jinritoutiao";
        public static final String BAIDU_QUANNENG_FANQIE = "baidu-quanneng-fanqie";
        public static final String BAIDU_QUANNENG_FANQIE_CHANGTING = "baidu-quanneng-fanqiechangting";
        public static final String BAIDU_QUANNENG_DOUYIN_HUOSHAN = "baidu-quanneng-douyinhuoshan";
        public static final String BAIDU_QUANNENG_TENGXUNSHIPIN = "baidu-quanneng-tengxunshipin";
        public static final String BAIDU_QUANNENG_PIPIXIA = "baidu-quanneng-pipixia";
        public static final String BAIDU_QUANNENG_YOUSHI = "baidu-quanneng-youshi";

        public static final String BAIDU_DINGYUN_DOUYINHUOSHAN = "baidu-dingyun-douyinhuoshan";
        public static final String BAIDU_DINGYUN_XIGUAVIDEO = "baidu-dingyun-xiguavideo";
        public static final String BAIDU_DINGYUN_FANQIECHANGTING = "baidu-dingyun-fanqiechangting";
        public static final String BAIDU_DINGYUN_YOUSHI = "baidu-dingyun-youshi";

        public static final String BAIDU_KEEP = "baidu-keep";
        public static final String BAIDU_LUYUN_PAIPAI = "baidu-luyun-paipai";

        public static final String HUAWEI_KUAISHOU = "huawei-kuaishou";
        public static final String HUAWEI_LTJD = "huawei-ltjd";
        public static final String HUAWEI_YOUKU = "huawei-youku";
        public static final String HUAWEI_FANQIE = "huawei-fanqie";
        public static final String HUAWEI_DOUYIN = "huawei-douyin";
        public static final String HUAWEI_HUIHUANG = "huawei-huihuang";
        public static final String HUAWEI_TUHU = "huawei-tuhu";
        public static final String HUAWEI_XIANYU = "huawei-xianyu";
        public static final String HUAWEI_IQIYI = "huawei-iqiyi";
        public static final String HUAWEI_DIANTAO = "huawei-diantao";
        public static final String HUAWEI_HUIHUI_TANTAN = "huawei-huihui-tantan";
        public static final String HUAWEI_HUIHUANG_DOUYINHUOSHAN = "huawei-huihuang_douyinhuoshan";
        public static final String HUAWEI_HUIHUANG_XIGUAVIDEO = "huawei-huihuang_xiguavideo";
        public static final String HUAWEI_HUIHUANG_FANQIECHANGTING = "huawei-huihuang_fanqiechangting";
        public static final String HUAWEI_HUIHUANG_HONGGUODUANJU = "huawei-huihuang_hongguoduanju";
        public static final String HUAWEI_HUIHUANG_TOUTIAOJISU = "huawei-huihuang_toutiaojisu";
        public static final String HUAWEI_QUANNENG_FANQIE = "huawei-quanneng-fanqie";
        public static final String HUAWEI_QUANNENG_FANQIECHANGTING = "huawei-quanneng-fanqiechangting";
        public static final String HUAWEI_QUANNENG_DOUYIN_JISU = "huawei-quanneng-douyinjisu";
        public static final String HUAWEI_QUANNENG_DOUYIN_HUOSHAN = "huawei-quanneng-douyinhuoshan";
        public static final String HUAWEI_QUANNENG_XIGUA_VIDEO = "huawei-quanneng-xiguavideo";
        public static final String HUAWEI_QUANNENG_BAIDU_JISU = "huawei-quanneng-baidujisu";
        public static final String HUAWEI_QUANNENG_JINRITOUTIAO = "huawei-quanneng-jinritoutiao";
        public static final String HUAWEI_QUANNENG_IQIYI = "huawei-quanneng-iqiyi";
        public static final String HUAWEI_QUANNENG_HEMAJUCHANG = "huawei-quanneng-hemajuchang";
        public static final String HUAWEI_QUANNENG_XIAOHONGSHU = "huawei-quanneng-xiaohongshu";
        public static final String HUAWEI_QUANNENG_YOUSHI = "huawei-quanneng-youshi";

        public static final String HUAWEI_DINGYUN_DOUYINHUOSHAN = "huawei-dingyun-douyinhuoshan";
        public static final String HUAWEI_DINGYUN_XIGUAVIDEO = "huawei-dingyun-xiguavideo";
        public static final String HUAWEI_DINGYUN_FANQIECHANGTING = "huawei-dingyun-fanqiechangting";

        public static final String HUAWEI_KEEP = "huawei-keep";

        public static final String OPPO_KUAISHOU = "oppo-kuaishou";

        public static final String IQIYI_KUAISHOU = "iqiyi-kuaishou";
        public static final String IQIYI_HUIHUANG_FANQIE = "iqiyi-huihuang-fanqie";
        public static final String IQIYI_HUIHUANG_XIGUAVIDEO = "iqiyi-huihuang-xiguavideo";
        public static final String IQIYI_QUANNENG_XIGUAVIDEO = "iqiyi-quanneng-xiguavideo";
        public static final String IQIYI_DINGYUN_XIGUAVIDEO = "iqiyi-dingyun-xiguavideo";
        public static final String IQIYI_LUYUN_KEEP = "iqiyi-luyun-keep";
    }

    public class AdsForChannel {
        public static final String OCPX = "/ocpx/v1";
        public static final String LIANGDAMAO = OCPX + "/ldm";
    }

}
