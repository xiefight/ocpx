package huihuang.proxy.ocpx.ads2channel.service;

import huihuang.proxy.ocpx.common.Response;

import java.util.Map;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-06-27 09:19
 **/
public interface ILiangdamaoService {


    Response clickReport(Map<String, String[]> parameterMap) throws Exception;

    /**
     * 客户将用户行为回调给渠道
     */
    Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception;
}
