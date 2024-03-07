package huihuang.proxy.ocpx.util;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-06-13 15:24
 **/
@Component
public class RandomUtil {

    public static Random random = new Random();

    /**
     * 生成随机数 时间戳+6位随机数
     */
    public static String randomStamp(){
        return String.valueOf(System.currentTimeMillis()) + (RandomUtil.random.nextInt(900000) + 100000);
    }

}
