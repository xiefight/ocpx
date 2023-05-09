package huihuang.proxy.ocpx.bussiness.service;

import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.util.CommonUtil;

import java.util.Map;
import java.util.Set;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-20 21:32
 **/
public interface IChannelAdsService {

    /**
     * 获取渠道-客户的业务工厂
     *
     * @return
     */
    IChannelAds channelAds();

    /**
     * 生成监测地址
     */
    default Response monitorAddress(Map<String, Object> params) {
        IChannelAds channelAds = channelAds();
        String monitorAddress = channelAds.findMonitorAddress();
        monitorAddress = CommonUtil.appendAddressParam(monitorAddress, params);
        return BasicResult.getSuccessResponse(monitorAddress);
    }

    default Response clickReport(Map<String, String[]> parameterMap) throws Exception {
        IChannelAds channelAds = channelAds();
        return channelAds.clickReport(parameterMap);
    }

    /**
     * 渠道点击上报给客户
     */
//    Response clickReport(Map<String, String[]> parameterMap) throws Exception;

    /**
     * 客户将用户行为回调给渠道
     */
    Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception;


}
