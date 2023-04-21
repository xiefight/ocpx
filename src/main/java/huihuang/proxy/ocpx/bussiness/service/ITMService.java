package huihuang.proxy.ocpx.bussiness.service;

import huihuang.proxy.ocpx.common.Response;

import java.util.Map;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-20 21:32
 **/
public interface ITMService {

    Response monitorAddress(Map<String, Object> params);

    Response clickReport(Map<String, String[]> parameterMap);
}
