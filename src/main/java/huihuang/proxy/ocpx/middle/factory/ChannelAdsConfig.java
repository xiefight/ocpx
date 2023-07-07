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
    private XiaomiXinyuChannelAds xiaomiXinyuChannelAds;
    @Autowired
    private XiaomiTantanChannelAds xiaomiTantanChannelAds;
    @Autowired
    private WifiXiguaChannelAds wxChannelAds;
    @Autowired
    private WifiFanqieChannelAds wfChannelAds;
    @Autowired
    private BaiduYoukuChannelAds byChannelAds;
    @Autowired
    private BaiduLtjdChannelAds bjChannelAds;
    @Autowired
    private BdssLtjdChannelAds bdssjdChannelAds;
    @Autowired
    private BaiduTianmaoChannelAds btChannelAds;
    @Autowired
    private HuaweiKuaishouChannelAds hkChannelAds;
    @Autowired
    private HuaweiLtjdChannelAds hjChannelAds;
    @Autowired
    private HuaweiYoukuChannelAds hyChannelAds;
    @Autowired
    private HuaweiFanqieChannelAds hfChannelAds;
    @Autowired
    private HuaweiHuihuangChannelAds hhChannelAds;
    @Autowired
    private BaiduKuaishouChannelAds bkChannelAds;
    @Autowired
    private BaiduFanqieChannelAds bfChannelAds;
    @Autowired
    private BaiduJdjrChannelAds bjdjrChannelAds;
    @Autowired
    private BaiduJdssChannelAds bjdssChannelAds;
    @Autowired
    private XiaomiXiaohongshuChannelAds xiaomiXhsChannelAds;
    @Autowired
    private OppoKuaishouChannelAds oppoKuaishouChannelAds;
    @Autowired
    private IQiyiKuaishouChannelAds ikChannelAds;

    @PostConstruct
    public void init() {
        channelAdsMap.put(Constants.ChannelAdsKey.TOUTIAO_MEITUAN, mtChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_LTJD, xjChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_YOUKU, xyChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_KUAISHOU, xkChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_XINYU, xiaomiXinyuChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_TANTAN, xiaomiTantanChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.WIFI_XIGUA, wxChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.WIFI_FANQIE, wfChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_YOUKU, byChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_LTJD, bjChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BDSS_LTJD, bdssjdChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_TIANMAO, btChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_KUAISHOU, hkChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_LTJD, hjChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_YOUKU, hyChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_FANQIE, hfChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_HUIHUANG, hhChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_KUAISHOU, bkChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_FANQIE, bfChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_JDJR, bjdjrChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_JDSS, bjdssChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_XIAOHONGSHU, xiaomiXhsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.OPPO_KUAISHOU, oppoKuaishouChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.IQIYI_KUAISHOU, ikChannelAds);
    }


}
