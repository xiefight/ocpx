package huihuang.proxy.ocpx.middle.factory;

import huihuang.proxy.ocpx.middle.IChannelAds;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-20 22:48
 **/
@Component
public class ChannelAdsFactory extends ChannelAdsConfig {

    public IChannelAds findChannelAds(String key) {
        return channelAdsMap.get(key);
    }

}
