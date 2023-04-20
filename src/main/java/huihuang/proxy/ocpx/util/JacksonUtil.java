package huihuang.proxy.ocpx.util;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-20 21:42
 **/
public class JacksonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T jsonToJavaBean(String jsonString, Class<T> _class) throws Exception {
        try {
            return (T)mapper.readValue(jsonString, _class);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

}
