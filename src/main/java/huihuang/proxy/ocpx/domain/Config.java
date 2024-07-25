package huihuang.proxy.ocpx.domain;

/**
 * @Description: 动态配置
 * @Author: xietao
 * @Date: 2023-04-21 10:32
 **/
public class Config {



    public static final String SERVER_PATH = "serverPath";

    /**
     * 百度-快手根据accountId分表,需要规定每个户的表的起始id,在此作映射关系
     */
    public static final String BAIDU_KUAISHOU_ACCOUNT_ID_MAP = "bdksacMap";

}
