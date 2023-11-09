package huihuang.proxy.ocpx.ads.kuaishou;

/**
 * xigua
 *
 * @Author: xietao
 * @Date: 2023/5/9 20:26
 */
public class KuaishouPath {

    public static final String KUAISHOU_ADS_NAME = "kuaishou";
    public static final String KUAISHOUJISU_ADS_NAME = "kuaishoujisu";
    public static final String KUAISHOUJISU2_ADS_NAME = "kuaishoujisu2";
    public static final String KUAISHOUJISU3_ADS_NAME = "kuaishoujisu3";

//    public static final String CLIENT_ID = "dc557d524fdd1b29d684ea348890fc17";
//    public static final String SECRET = "333DUFA6oZmi09QQ";

    //小米-快手的adid 21666
    //小米-快手极速的adid 21756
    //华为-快手的adid 21749
    //华为-快手极速的adid 21750

    public static final String XIAOMI_KUAISHOU_ADID = "21749";//"21666";
    public static final String XIAOMI_KUAISHOUJISU_ADID = "21756";//"21756";
    public static final String HUAWEI_KUAISHOU_ADID = "21749";
    public static final String HUAWEI_KUAISHOU2_ADID = "22037";
    public static final String HUAWEI_KUAISHOUJISU_ADID = "21756";//"21750";//激活+次留
    public static final String HUAWEI_KUAISHOUJISU2_ADID = "21756";//激活+次留
    public static final String HUAWEI_KUAISHOUJISU3_ADID = "21750";//激活+次留

    public static final String OPPO_KUAISHOU_ADID = "21666"; //弃用
    public static final String OPPO_KUAISHOUJISU_ADID = "21756";  //弃用

    public static final String BAIDU_KUAISHOU_ADID = "21666";
    public static final String BAIDU_KUAISHOUJISU_ADID = "22038";

    public static final String BDSS_KUAISHOU_ADID = "21749";
    public static final String BDSS_KUAISHOUJISU_ADID = "";

    public static final String IQIYI_KUAISHOU_ADID = "21749";
    public static final String IQIYI_KUAISHOUJISU_ADID = "21756";

    public static final String KUAISHOU_ADID5 = "22126";
    public static final String KUAISHOUJISU_ADID5 = "22125";
    public static final String KUAISHOU_ADID6 = "22173";
    public static final String KUAISHOUJISU_ADID6 = "22115";

    public static final String KUAISHOU_ADID_21749 = "21749";
    public static final String KUAISHOUJISU_ADID_21756 = "21756";


    //快手包名，oppo用到
    public static final String OPPO_KUAISHOU_PKG = "com.smile.gifmaker";
    public static final String OPPO_KUAISHOUJISU_PKG = "com.kuaishou.nebula";


    //生产域名
    public static final String URI = "https://promotion-partner.kuaishou.com";
    //曝光接受路径
    public static final String IMPRESSION = "/rest/n/promotion/impression?";
    //
    public static final String PROMOTION = "/rest/n/promotion/p?";

}
