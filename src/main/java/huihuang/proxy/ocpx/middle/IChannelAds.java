package huihuang.proxy.ocpx.middle;

import huihuang.proxy.ocpx.common.Response;

import java.util.Map;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-20 22:39
 **/
public interface IChannelAds {

    /**
     * 生成监测链接
     */
    String findMonitorAddress();

    /**
     * 点击上报和回传
     */
    Response clickReport(Map<String, String[]> parameterMap) throws Exception;

}
