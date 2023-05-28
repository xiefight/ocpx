package huihuang.proxy.ocpx.middle.factory;

import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.middle.impl.*;
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
    private XiaomiKuaishouChannelAds xkChannelAds;
    @Autowired
    private WifiXiguaChannelAds wxChannelAds;
    @Autowired
    private BaiduYoukuChannelAds byChannelAds;
    @Autowired
    private BaiduLtjdChannelAds bjChannelAds;
    @Autowired
    private BaiduTianmaoChannelAds btChannelAds;
    @Autowired
    private HuaweiKuaishouChannelAds hkChannelAds;
    @Autowired
    private HuaweiLtjdChannelAds hjChannelAds;
    @Autowired
    private BaiduKuaishouChannelAds bkChannelAds;
    @Autowired
    private BaiduFanqieChannelAds bfChannelAds;

    @PostConstruct
    public void init() {
        channelAdsMap.put(Constants.ChannelAdsKey.TOUTIAO_MEITUAN, mtChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_LTJD, xjChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_YOUKU, xyChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_KUAISHOU, xkChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.WIFI_XIGUA, wxChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_YOUKU, byChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_LTJD, bjChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_TIANMAO, btChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_KUAISHOU, hkChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_LTJD, hjChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_KUAISHOU, bkChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_FANQIE, bfChannelAds);
    }


}
