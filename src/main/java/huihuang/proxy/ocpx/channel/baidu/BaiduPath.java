package huihuang.proxy.ocpx.channel.baidu;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-25 20:36
 **/
public class BaiduPath {

    /*
        账户：原生-智道上海35-B23KA01379
        密码：JJyku234
        id：47395819
        akey:NDczOTU4MTk=
        token:FmRTMwCwoTuOUqz1mOdaeQXShmPkTt6I@BvWbBznj5ZOvsZz37HbNpCsrqNAhCKLo
     */

    /*
        账户：原生-智道未来93-B23KA00816
        密码：JINdcj8701
        ID：47716744
        Akey：NDc3MTY3NDQ=
     */

    /** BAIDU提供给YOUKU的秘钥----回传渠道时使用 */
    public static final String YOUKU_SECRET = "NDczOTU4MTk=";
    public static final String LTJD_SECRET = "NDc5NzE1MDk=";//"NDc3MTY3NDQ=";
    public static final String TIANMAO_SECRET = "NDc5NzI3MDk=";
    public static final String FANQIE_SECRET = "NDU3OTc0NDg=";//"NDU3OTc0NTE=";
    public static final String FANQIE2_SECRET = "NDc5NzEyMzE=";
    public static final String JDJR_SECRET = "NDc4NDc5Njk=";
    public static final String JDSS_SECRET = "NDc4NDc5NzA=";

    public static final String XIANYU_SECRET = "?";

    public static final String KUAISHOU_SECRET = "NDc5MTg5NDc=";
    public static final String KUAISHOU2_SECRET = "NDc5MTkyNDA=";
    public static final String KUAISHOUJISU_SECRET = "NDc5MTg5NTc=";
    public static final String KUAISHOUJISU2_SECRET = "NDc5MTkyNjg=";
    //在用
    public static final String KUAISHOU3_SECRET = "NDg1NDA1Nzc=";
    //在用
    public static final String KUAISHOUJISU3_SECRET = "NDg1NDA3OTI=";

    public static final String BAIDU_CHANNEL_NAME = "baidu";

    public static final String CALLBACK_URL = "http://als.baidu.com/cb/actionCb?";
}
