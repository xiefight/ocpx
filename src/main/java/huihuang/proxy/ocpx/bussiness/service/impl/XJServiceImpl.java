package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.middle.factory.ChannelAdsFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description: xiaomi-ltjd
 * @Author: xietao
 * @Date: 2023-04-24 17:42
 **/
@Service("xjService")
public class XJServiceImpl implements IChannelAdsService {

    @Autowired
    private ChannelAdsFactory channelAdsFactory;

    @Override
    public IChannelAds channelAds() {
        String channelAdsKey = "xiaomi-ltjd";
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response clickReport(Map<String, String[]> parameterMap) throws Exception {
        IChannelAds channelAds = channelAds();
        return channelAds.clickReport(parameterMap);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        return null;
    }
}
