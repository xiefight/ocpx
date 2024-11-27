package huihuang.proxy.ocpx.channel.xiaomi;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-25 20:36
 **/
public class XiaomiPath {

    /** xiaomi提供给ltjd的秘钥，后期做成配置，动态获取 */
    public static final String LTJD_SECRET = "saOdakCiYeOXXCTX";// "toLBaIvbRMKXUoTL";

    /** xiaomi提供给youku的秘钥 */
    public static final String YOUKU_SECRET = "FBvBCjprfNhRyLFd";//"jiwTWEHPQjZySweS"
    /**
     * xiaomi提供给kuaishou的秘钥 */
    public static final String KUAISHOU_SECRET = "uMhzEwzBvUvcTLqL";

    public static final String KUAISHOUJISU_SECRET = "qcNfrgHbmDGcyvUc";

    public static final String XIANYU_SECRET = "uISNMKFpPUyXsJzy";

    public static final String DOUYIN_SECRET = "lcfSAqakADyIZKna";

    public static final String XIAOMI_CHANNEL_NAME = "xiaomi";

    public static final String CALLBACK_URL = "https://trail.e.mi.com/api/callback?";


    //1户新增激活  02户和03户都是pid都是356   02户新增激活  03户、04户激活  要区分
    public static final String XM_QUANNENG_FANQIE_ACCOUNT_02 = "xmqnfq02";
    public static final String XM_QUANNENG_FANQIE_ACCOUNT_03 = "xmqnfq03";
    public static final String XM_QUANNENG_FANQIE_ACCOUNT_04 = "xmqnfq04";

}
