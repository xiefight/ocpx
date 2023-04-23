package huihuang.proxy.ocpx.bussiness.service;

import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.middle.IChannelAds;

import java.util.Map;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-20 21:32
 **/
public interface IChannelAdsService {

    IChannelAds channelAds();

    Response monitorAddress(Map<String, Object> params);

    Response clickReport(Map<String, String[]> parameterMap) throws Exception;

    Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception;
}
