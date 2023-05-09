package huihuang.proxy.ocpx.middle.factory;

import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.middle.impl.MTChannelAds;
import huihuang.proxy.ocpx.middle.impl.WifiXiguaChannelAds;
import huihuang.proxy.ocpx.middle.impl.XJChannelAds;
import huihuang.proxy.ocpx.middle.impl.XYChannelAds;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-20 22:43
 **/
public class ChannelAdsConfig {

    protected static Map<String, IChannelAds> channelAdsMap = new HashMap<>();

    @Autowired
    private MTChannelAds mtChannelAds;
    @Autowired
    private XJChannelAds xjChannelAds;
    @Autowired
    private XYChannelAds xyChannelAds;
    @Autowired
    private WifiXiguaChannelAds wxChannelAds;

    @PostConstruct
    public void init() {
        channelAdsMap.put(Constants.ChannelAdsKey.TOUTIAO_MEITUAN, mtChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_LTJD, xjChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_YOUKU, xyChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.WIFI_XIGUA, wxChannelAds);
    }


}
