package huihuang.proxy.ocpx.domain;

/**
 * @Description: 动态配置
 * @Author: xietao
 * @Date: 2023-04-21 10:32
 **/
public class Config {

    //配置字段
    private String dynamic_config;

    public String getDynamic_config() {
        return dynamic_config;
    }

    public void setDynamic_config(String dynamic_config) {
        this.dynamic_config = dynamic_config;
    }

    public static final String SERVER_PATH = "serverPath";
}
