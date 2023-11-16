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
    private XiaomiJingdongChannelAds xiaomiJingdongChannelAds;
    @Autowired
    private XiaomiYoukuChannelAds xiaomiYoukuChannelAds;
    @Autowired
    private XiaomiKuaishouChannelAds xkChannelAds;
    @Autowired
    private XiaomiXinyuChannelAds xiaomiXinyuChannelAds;
    @Autowired
    private XiaomiTantanChannelAds xiaomiTantanChannelAds;
    @Autowired
    private XiaomiXianyuChannelAds xiaomiXianyuChannelAds;
    @Autowired
    private XiaomiXinyuYoudaoChannelAds xiaomiXinyuYoudaoChannelAds;
    @Autowired
    private XiaomiDongchediChannelAds xiaomiDongchediChannelAds;
    @Autowired
    private XiaomiXiaohongshuChannelAds xiaomiXhsChannelAds;
    @Autowired
    private XiaomiQuannengFanqieChannelAds xiaomiQuannengFanqieChannelAds;
    @Autowired
    private XiaomiIQiyiChannelAds xiaomiiqyChannelAds;
    @Autowired
    private XiaomiQuannengXiguaVideoChannelAds xiaomiQuannengXiguaChannelAds;
    @Autowired
    private XiaomiQuannengDouyinjisuChannelAds xqdyjsChannelAds;
    @Autowired
    private XiaomiQuannengDouyinhuoshanChannelAds xqdyhsChannelAds;
    @Autowired
    private XiaomiQuannengJinritoutiaoChannelAds xqjrttChannelAds;
    @Autowired
    private XiaomiQuannengBaidujisuChannelAds xqbdjsChannelAds;
    @Autowired
    private XiaomiQuannengXiaohongshuChannelAds xqxhsChannelAds;
    @Autowired
    private XiaomiDouyinChannelAds xiaomiDouyinChannelAds;

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
    private BdssKuaishouChannelAds bdssksChannelAds;
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
    private HuaweiDouyinChannelAds hdyChannelAds;
    @Autowired
    private HuaweiHuihuangChannelAds hhChannelAds;
    @Autowired
    private HuaweiTuhuChannelAds htChannelAds;
    @Autowired
    private HuaweiXianyuChannelAds hxyChannelAds;
    @Autowired
    private HuaweiIQiyiChannelAds hiqyChannelAds;
    @Autowired
    private HuaweiDiantaoChannelAds hdtChannelAds;
    @Autowired
    private HuaweiQuannengFanqieChannelAds hqfChannelAds;
    @Autowired
    private HuaweiQuannengDouyinjisuChannelAds hqdyjsChannelAds;
    @Autowired
    private HuaweiQuannengDouyinhuoshanChannelAds hqdyhsChannelAds;
    @Autowired
    private HuaweiQuannengXiguaVideoChannelAds hqxvChannelAds;
    @Autowired
    private HuaweiQuannengBaidujisuChannelAds hqbaidujisuChannelAds;
    @Autowired
    private HuaweiQuannengJinritoutiaoChannelAds hqjrttChannelAds;
    @Autowired
    private HuaweiQuannengIQiyiChannelAds hqiqyChannelAds;


    @Autowired
    private BaiduKuaishouChannelAds bkChannelAds;
    @Autowired
    private BaiduFanqieChannelAds bfChannelAds;
    @Autowired
    private BaiduXianyuChannelAds baiduxianyuChannelAds;
    @Autowired
    private BaiduDongchediChannelAds bdcdChannelAds;
    @Autowired
    private BaiduJdjrChannelAds bjdjrChannelAds;
    @Autowired
    private BaiduJdssChannelAds bjdssChannelAds;
    @Autowired
    private BaiduDiantaoChannelAds bdtChannelAds;
    @Autowired
    private BaiduDouyinChannelAds bdyChannelAds;
    @Autowired
    private BaiduQuannengXiguaVideoChannelAds bqxvChannelAds;
    @Autowired
    private BaiduQuannengDouyinjisuChannelAds bqdyjsChannelAds;
    @Autowired
    private BaiduQuannengJinritoutiaoChannelAds bqjrttChannelAds;
    @Autowired
    private BaiduQuannengFanqieChannelAds bqfanqieChannelAds;
    @Autowired
    private BaiduQuannengFanqiechangtingChannelAds bqfqctChannelAds;
    @Autowired
    private BaiduQuannengDouyinhuoshanChannelAds bqdyhsChannelAds;
    @Autowired
    private BaiduHuihuangTianmaoChannelAds bhhtmChannelAds;

    @Autowired
    private OppoKuaishouChannelAds oppoKuaishouChannelAds;
    @Autowired
    private IQiyiKuaishouChannelAds ikChannelAds;

    @PostConstruct
    public void init() {
        channelAdsMap.put(Constants.ChannelAdsKey.TOUTIAO_MEITUAN, mtChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_LTJD, xiaomiJingdongChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_YOUKU, xiaomiYoukuChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_KUAISHOU, xkChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_XINYU, xiaomiXinyuChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_TANTAN, xiaomiTantanChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_XINYU_YOUDAO, xiaomiXinyuYoudaoChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_DONGCHEDI, xiaomiDongchediChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_XIANYU, xiaomiXianyuChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_XIAOHONGSHU, xiaomiXhsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_QUANNENG_FANQIE, xiaomiQuannengFanqieChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_QUANNENG_XIGUA_VIDEO, xiaomiQuannengXiguaChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_QUANNENG_JINRITOUTIAO, xqjrttChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_QUANNENG_DOUYINJISU, xqdyjsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_QUANNENG_DOUYINHUOSHAN, xqdyhsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_QUANNENG_BAIDUJISU, xqbdjsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_QUANNENG_XIAOHONGSHU, xqxhsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_IQIYI, xiaomiiqyChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_DOUYIN, xiaomiDouyinChannelAds);

        channelAdsMap.put(Constants.ChannelAdsKey.WIFI_XIGUA, wxChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.WIFI_FANQIE, wfChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_YOUKU, byChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_LTJD, bjChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BDSS_LTJD, bdssjdChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BDSS_KUAISHOU, bdssksChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_TIANMAO, btChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_KUAISHOU, hkChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_LTJD, hjChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_YOUKU, hyChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_FANQIE, hfChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_DOUYIN, hdyChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_HUIHUANG, hhChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_TUHU, htChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_XIANYU, hxyChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_IQIYI, hiqyChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_DIANTAO, hdtChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_QUANNENG_FANQIE, hqfChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_QUANNENG_DOUYIN_JISU, hqdyjsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_QUANNENG_DOUYIN_HUOSHAN, hqdyhsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_QUANNENG_XIGUA_VIDEO, hqxvChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_QUANNENG_BAIDU_JISU, hqbaidujisuChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_QUANNENG_JINRITOUTIAO, hqjrttChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_QUANNENG_IQIYI, hqiqyChannelAds);

        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_KUAISHOU, bkChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_FANQIE, bfChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_DONGCHEDI, bdcdChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_XIANYU, baiduxianyuChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_JDJR, bjdjrChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_JDSS, bjdssChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_DIANTAO, bdtChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_HUIHUANG_TIANMAO, bhhtmChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_DOUYIN, bdyChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_QUANNENG_XIGUA_VIDEO, bqxvChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_QUANNENG_DOUYIN_JISU, bqdyjsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_QUANNENG_JINRITOUTIAO, bqjrttChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_QUANNENG_FANQIE, bqfanqieChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_QUANNENG_FANQIE_CHANGTING, bqfqctChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_QUANNENG_DOUYIN_HUOSHAN, bqdyhsChannelAds);

        channelAdsMap.put(Constants.ChannelAdsKey.OPPO_KUAISHOU, oppoKuaishouChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.IQIYI_KUAISHOU, ikChannelAds);
    }


}
