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

    public static final String XIAOMI_CHANNEL_NAME = "xiaomi";

    public static final String CALLBACK_URL = "http://trail.e.mi.com/api/callback?";
}
