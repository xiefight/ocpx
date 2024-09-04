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
    private XiaomiHuihuiTantanChannelAds xiaomiHuihuiTantanChannelAds;
    @Autowired
    private XiaomiHuihuiXianyuChannelAds xiaomiHuihuiXianyuChannelAds;
    @Autowired
    private XiaomiHuihuiTaobaoChannelAds xmhhtbChannelAds;
    @Autowired
    private XiaomiXinyuYoudaoChannelAds xiaomiXinyuYoudaoChannelAds;
    @Autowired
    private XiaomiTTYoudaoChannelAds xiaomiTTYoudaoChannelAds;
    @Autowired
    private XiaomiQimaoYoudaoChannelAds xiaomiQimaoYoudaoChannelAds;
    @Autowired
    private XiaomiHuihuiKuakeChannelAds xiaomiHuihuiKuakeChannelAds;
    @Autowired
    private XiaomiHuihuiGuaziChannelAds xmhhgzChannelAds;
    @Autowired
    private XiaomiDongchediChannelAds xiaomiDongchediChannelAds;
    @Autowired
    private XiaomiXiaohongshuChannelAds xiaomiXhsChannelAds;
    @Autowired
    private XiaomiQuannengFanqieChannelAds xiaomiQuannengFanqieChannelAds;
    @Autowired
    private XiaomiQuannengFanqiechangtingChannelAds xqfqctChannelAds;
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
    private XiaomiQuannengIQiyiChannelAds xqiqyChannelAds;
    @Autowired
    private XiaomiQuannengHgdjChannelAds xqhgdjChannelAds;
    @Autowired
    private XiaomiQuannengPipixiaChannelAds xqppxChannelAds;
    @Autowired
    private XiaomiQuannengYoukuChannelAds xqykChannelAds;
    @Autowired
    private XiaomiQuannengUCChannelAds xqucChannelAds;
    @Autowired
    private XiaomiQuannengSoulChannelAds xqsoulChannelAds;
    @Autowired
    private XiaomiQuannengElemeChannelAds xqelemeChannelAds;

    @Autowired
    private XiaomiDouyinChannelAds xiaomiDouyinChannelAds;
    @Autowired
    private XiaomiDouyinhuoshanChannelAds xiaomiDouyinhuoshanChannelAds;
    @Autowired
    private XiaomiQiduChannelAds xiaomiQiduChannelAds;
    @Autowired
    private XiaomiHuihuangDouyinhuoshanChannelAds xhhdyhsChannelAds;
    @Autowired
    private XiaomiHuihuangHongguoduanjuChannelAds xhhhgdjChannelAds;
    @Autowired
    private XiaomiHuihuangFanqiechangtingChannelAds xhhfqctChannelAds;
    @Autowired
    private XiaomiHuihuangXiguavideoChannelAds xhhxgChannelAds;
    @Autowired
    private XiaomiHuihuangToutiaojisuChannelAds xhhttjsChannelAds;
    @Autowired
    private XiaomiHuihuangYitaoChannelAds xhhytChannelAds;
    @Autowired
    private XiaomiHuihuangFengmangYitaoChannelAds xhhfmytChannelAds;
    @Autowired
    private XiaomiHuihuangYoukuChannelAds xhhykChannelAds;
    @Autowired
    private XiaomiHuihuangJingdongChannelAds xhhjdChannelAds;
    @Autowired
    private XiaomiHuihuangFengmangXianyuChannelAds xhhfmxyChannelAds;
    @Autowired
    private XiaomiHuihuangAiliaoChannelAds xhhalChannelAds;

    @Autowired
    private XiaomiDingyunDouyinhuoshanChannelAds xdydyhsChannelAds;
    @Autowired
    private XiaomiDingyunXiguavideoChannelAds xdyxgChannelAds;
    @Autowired
    private XiaomiDingyunFanqiechangtingChannelAds xdyfqctChannelAds;
    @Autowired
    private XiaomiDingyunYoushiChannelAds xdyysChannelAds;

    @Autowired
    private XiaomiKeepChannelAds xiaomiKeepChannelAds;
    @Autowired
    private XiaomiLuyunPaipaiChannelAds xiaomiLuyunPaipaiChannelAds;
    @Autowired
    private XiaomiLuyunXiaohongshuChannelAds xiaomiLuyunXiaohongshuChannelAds;
    @Autowired
    private XiaomiLuyunKuaikanmanhuaChannelAds xiaomiLuyunKuaikanmanhuaChannelAds;

    @Autowired
    private XiaomiBupetBiliChannelAds xmbupetbiliChannelAds;

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
    private HuaweiHuihuiTantanChannelAds hhhttChannelAds;
    @Autowired
    private HuaweiHuihuiTaobaoChannelAds hhhtbChannelAds;
    @Autowired
    private HuaweiHuihuiQQReadChannelAds hhhqqrChannelAds;
    @Autowired
    private HuaweiHuihuiGuaziChannelAds hhhgzChannelAds;
    @Autowired
    private HuaweiHuihuiTongchengChannelAds hhhtcChannelAds;
    @Autowired
    private HuaweiHuihuiXingyeChannelAds hhhxingyeChannelAds;

    @Autowired
    private HuaweiIQiyiChannelAds hiqyChannelAds;
    @Autowired
    private HuaweiDiantaoChannelAds hdtChannelAds;
    @Autowired
    private HuaweiQuannengFanqieChannelAds hqfChannelAds;
    @Autowired
    private HuaweiQuannengFanqiechangtingChannelAds hqfqctChannelAds;
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
    private HuaweiQuannengHemajuchangChannelAds hqhemajuchangChannelAds;
    @Autowired
    private HuaweiQuannengXiaohongshuChannelAds hqxhsChannelAds;
    @Autowired
    private HuaweiQuannengYoushiChannelAds hqysChannelAds;
    @Autowired
    private HuaweiQuannengHongguoduanjuChannelAds hqhgdjChannelAds;
    @Autowired
    private HuaweiQuannengPipixiaChannelAds hqppxChannelAds;
    @Autowired
    private HuaweiQuannengUCChannelAds hqucChannelAds;

    @Autowired
    private HuaweiHuihuangDouyinhuoshanChannelAds hhhdyhsChannelAds;
    @Autowired
    private HuaweiHuihuangXiguavideoChannelAds hhhxgChannelAds;
    @Autowired
    private HuaweiHuihuangFanqiechangtingChannelAds hhhfqctChannelAds;
    @Autowired
    private HuaweiHuihuangHongguoduanjuChannelAds hhhhgdjChannelAds;
    @Autowired
    private HuaweiHuihuangToutiaojisuChannelAds hhhttjsChannelAds;
    @Autowired
    private HuaweiHuihuangYoukuChannelAds hhhykChannelAds;
    @Autowired
    private HuaweiHuihuangJingdongChannelAds hhhjdChannelAds;
    @Autowired
    private HuaweiHuihuangFengmangXianyuChannelAds hhhxyChannelAds;
    @Autowired
    private HuaweiHuihuangAiliaoChannelAds hhhalChannelAds;
    @Autowired
    private HuaweiHuihuangJingdongjinrongChannelAds hhhjdjrChannelAds;

    @Autowired
    private HuaweiDingyunDouyinhuoshanChannelAds hdydyhsChannelAds;
    @Autowired
    private HuaweiDingyunXiguavideoChannelAds hdyxgChannelAds;
    @Autowired
    private HuaweiDingyunFanqiechangtingChannelAds hdyfqctChannelAds;

    @Autowired
    private HuaweiKeepChannelAds hkeepChannelAds;


    @Autowired
    private BaiduKuaishouChannelAds bkChannelAds;
    @Autowired
    private BaiduFanqieChannelAds bfChannelAds;
    @Autowired
    private BaiduHuihuiXianyuChannelAds baiduxianyuChannelAds;
    @Autowired
    private BaiduHuihuiTaobaoChannelAds bdhhtbChannelAds;
    @Autowired
    private BaiduHuihuiTantanChannelAds bdhhttChannelAds;
    @Autowired
    private BaiduHuihuiTongchengChannelAds bdhhtcChannelAds;
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
    private BaiduQuannengTengxunshipinChannelAds bqtxspChannelAds;
    @Autowired
    private BaiduQuannengPipixiaChannelAds bqppxChannelAds;
    @Autowired
    private BaiduQuannengYoushiChannelAds bqysChannelAds;
    @Autowired
    private BaiduQuannengUCChannelAds bqucChannelAds;

    @Autowired
    private BaiduHuihuangTianmaoChannelAds bhhtmChannelAds;
    @Autowired
    private BaiduHuihuangDouyinhuoshanChannelAds bhhdyhsChannelAds;
    @Autowired
    private BaiduHuihuangXiguavideoChannelAds bhhxgChannelAds;
    @Autowired
    private BaiduHuihuangFanqiechangtingChannelAds bhhfqctChannelAds;
    @Autowired
    private BaiduHuihuangJinritoutiaoChannelAds bhhjrttChannelAds;
    @Autowired
    private BaiduHuihuangYitaoChannelAds bhhytChannelAds;
    @Autowired
    private BaiduHuihuangToutiaojisuChannelAds bhhttjsChannelAds;
    @Autowired
    private BaiduHuihuangHongguoduanjuChannelAds bhhhgdjChannelAds;
    @Autowired
    private BaiduHuihuangPipixiaChannelAds bhhppxChannelAds;
    @Autowired
    private BaiduHuihuangXianyuChannelAds bhhxyChannelAds;
    @Autowired
    private BaiduHuihuangYoukuChannelAds bhhyoukuChannelAds;
    @Autowired
    private BaiduHuihuangZhifubaoChannelAds bhhzfbChannelAds;
    @Autowired
    private BaiduHuihuangYingkeChannelAds bhhyingkeChannelAds;

    @Autowired
    private BaiduDingyunDouyinhuoshanChannelAds bdydyhsChannelAds;
    @Autowired
    private BaiduDingyunXiguavideoChannelAds bdyxgChannelAds;
    @Autowired
    private BaiduDingyunFanqiechangtingChannelAds bdyfqctChannelAds;
    @Autowired
    private BaiduDingyunYoushiChannelAds bdyysChannelAds;

    @Autowired
    private BaiduKeepChannelAds bkeepChannelAds;
    @Autowired
    private BaiduLuyunPaipaiChannelAds blyppChannelAds;
    @Autowired
    private BaiduLuyunKuaikanmanhuaChannelAds blykkmhChannelAds;

    @Autowired
    private OppoKuaishouChannelAds oppoKuaishouChannelAds;
    @Autowired
    private OppoHuihuangYitaoChannelAds oppohhytChannelAds;
    @Autowired
    private OppoHuihuangXianyunChannelAds oppohhxyChannelAds;
    @Autowired
    private IQiyiKuaishouChannelAds ikChannelAds;
    @Autowired
    private IQiyiHuihuangFanqieChannelAds ihhfqChannelAds;
    @Autowired
    private IQiyiHuihuangXiguavideoChannelAds ihhxgChannelAds;
    @Autowired
    private IQiyiHuihuangFengmangXianyuChannelAds ihhxyChannelAds;
    @Autowired
    private IQiyiHuihuangFengmangJingdongChannelAds ihhjdChannelAds;
    @Autowired
    private IQiyiDingyunXiguavideoChannelAds idyxgChannelAds;
    @Autowired
    private IQiyiQuannengXiguavideoChannelAds iqnxgChannelAds;
    @Autowired
    private IQiyiLuyunKeepChannelAds ilykeepChannelAds;
    @Autowired
    private IQiyiHuihuiXianyuChannelAds iqyxyChannelAds;
    @Autowired
    private IQiyiLuyunXiaohongshuChannelAds ilyxhsChannelAds;

    @Autowired
    private HonorHuihuangJingdongChannelAds honorhhjdChannelAds;
    @Autowired
    private HonorHuihuangFengmangXianyuChannelAds honorhhxyChannelAds;

    @Autowired
    private GDTHuihuiXianyuChannelAds gdthhxyChannelAds;
    @Autowired
    private GDTKuaishouChannelAds gdtksChannelAds;
    @Autowired
    private GdtHuihuangZhitouXianyuChannelAds gdthhfmxyChannelAds;

    @PostConstruct
    public void init() {
        channelAdsMap.put(Constants.ChannelAdsKey.TOUTIAO_MEITUAN, mtChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_LTJD, xiaomiJingdongChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_YOUKU, xiaomiYoukuChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_KUAISHOU, xkChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_XINYU, xiaomiXinyuChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_TANTAN, xiaomiHuihuiTantanChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_XINYU_YOUDAO, xiaomiXinyuYoudaoChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_TT_YOUDAO, xiaomiTTYoudaoChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_QIMAO_YOUDAO, xiaomiQimaoYoudaoChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_DONGCHEDI, xiaomiDongchediChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_XIANYU, xiaomiHuihuiXianyuChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_HUIHUI_KUAKE, xiaomiHuihuiKuakeChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_HUIHUI_TAOBAO, xmhhtbChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_HUIHUI_GUAZI, xmhhgzChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_XIAOHONGSHU, xiaomiXhsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_QUANNENG_FANQIE, xiaomiQuannengFanqieChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_QUANNENG_FANQIECHANGTING, xqfqctChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_QUANNENG_XIGUA_VIDEO, xiaomiQuannengXiguaChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_QUANNENG_JINRITOUTIAO, xqjrttChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_QUANNENG_DOUYINJISU, xqdyjsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_QUANNENG_DOUYINHUOSHAN, xqdyhsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_QUANNENG_BAIDUJISU, xqbdjsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_QUANNENG_XIAOHONGSHU, xqxhsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_QUANNENG_IQIYI, xqiqyChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_QUANNENG_HGDJ, xqhgdjChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_QUANNENG_PIPIXIA, xqppxChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_QUANNENG_YOUKU, xqykChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_QUANNENG_UC, xqucChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_QUANNENG_SOUL, xqsoulChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_QUANNENG_ELEME, xqelemeChannelAds);

        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_IQIYI, xiaomiiqyChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_DOUYIN, xiaomiDouyinChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_DOUYINHUOSHAN, xiaomiDouyinhuoshanChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_QIDU, xiaomiQiduChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_HUIHUANG_DOUYINHUOSHAN, xhhdyhsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_HUIHUANG_HONGGUODUANJU, xhhhgdjChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_HUIHUANG_FANQIECHANGTING, xhhfqctChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_HUIHUANG_XIGUAVIDEO, xhhxgChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_HUIHUANG_TOUTIAOJISU, xhhttjsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_HUIHUANG_YITAO, xhhytChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_HUIHUANG_FENGMANG_YITAO, xhhfmytChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_HUIHUANG_YOUKU, xhhykChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_HUIHUANG_JINGDONG, xhhjdChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_HUIHUANG_FENGMANG_XIANYU, xhhfmxyChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_HUIHUANG_AILIAO, xhhalChannelAds);

        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_DINGYUN_DOUYINHUOSHAN, xdydyhsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_DINGYUN_XIGUAVIDEO, xdyxgChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_DINGYUN_FANQIECHANGTING, xdyfqctChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_DINGYUN_YOUSHI, xdyysChannelAds);

        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_KEEP, xiaomiKeepChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_LUYUN_PAIPAI, xiaomiLuyunPaipaiChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_LUYUN_XIAOHONGSHU, xiaomiLuyunXiaohongshuChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_LUYUN_KUAIKANMANHUA, xiaomiLuyunKuaikanmanhuaChannelAds);

        channelAdsMap.put(Constants.ChannelAdsKey.XIAOMI_BUPET_BILI, xmbupetbiliChannelAds);

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
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_HUIHUI_TANTAN, hhhttChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_HUIHUI_TAOBAO, hhhtbChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_HUIHUI_QQREAD, hhhqqrChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_HUIHUI_GUAZI, hhhgzChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_HUIHUI_TONGCHENG, hhhtcChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_HUIHUI_XINGYE, hhhxingyeChannelAds);

        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_IQIYI, hiqyChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_DIANTAO, hdtChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_QUANNENG_FANQIE, hqfChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_QUANNENG_FANQIECHANGTING, hqfqctChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_QUANNENG_DOUYIN_JISU, hqdyjsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_QUANNENG_DOUYIN_HUOSHAN, hqdyhsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_QUANNENG_XIGUA_VIDEO, hqxvChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_QUANNENG_BAIDU_JISU, hqbaidujisuChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_QUANNENG_JINRITOUTIAO, hqjrttChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_QUANNENG_IQIYI, hqiqyChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_QUANNENG_HEMAJUCHANG, hqhemajuchangChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_QUANNENG_XIAOHONGSHU, hqxhsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_QUANNENG_YOUSHI, hqysChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_QUANNENG_HONGGUODUANJU, hqhgdjChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_QUANNENG_PIPIXIA, hqppxChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_QUANNENG_UC, hqucChannelAds);

        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_HUIHUANG_DOUYINHUOSHAN, hhhdyhsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_HUIHUANG_XIGUAVIDEO, hhhxgChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_HUIHUANG_FANQIECHANGTING, hhhfqctChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_HUIHUANG_HONGGUODUANJU, hhhhgdjChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_HUIHUANG_TOUTIAOJISU, hhhttjsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_HUIHUANG_YOUKU, hhhykChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_HUIHUANG_JINGDONG, hhhjdChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_HUIHUANG_JINGDONGJINRONG, hhhjdjrChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_HUIHUANG_XIANYU, hhhxyChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_HUIHUANG_AILIAO, hhhalChannelAds);

        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_DINGYUN_DOUYINHUOSHAN, hdydyhsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_DINGYUN_XIGUAVIDEO, hdyxgChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_DINGYUN_FANQIECHANGTING, hdyfqctChannelAds);

        channelAdsMap.put(Constants.ChannelAdsKey.HUAWEI_KEEP, hkeepChannelAds);

        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_KUAISHOU, bkChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_FANQIE, bfChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_DONGCHEDI, bdcdChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_HUIHUI_XIANYU, baiduxianyuChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_HUIHUI_TAOBAO, bdhhtbChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_HUIHUI_TANTAN, bdhhttChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_HUIHUI_TONGCHENG, bdhhtcChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_JDJR, bjdjrChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_JDSS, bjdssChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_DIANTAO, bdtChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_HUIHUANG_TIANMAO, bhhtmChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_HUIHUANG_DOUYINHUOSHAN, bhhdyhsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_HUIHUANG_XIGUAVIDEO, bhhxgChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_HUIHUANG_FANQIECHANGTING, bhhfqctChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_HUIHUANG_JINRITOUTIAO, bhhjrttChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_HUIHUANG_YITAO, bhhytChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_HUIHUANG_TOUTIAOJISU, bhhttjsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_HUIHUANG_HONGGUODUANJU, bhhhgdjChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_HUIHUANG_PIPIXIA, bhhppxChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_HUIHUANG_XIANYU, bhhxyChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_HUIHUANG_YOUKU, bhhyoukuChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_HUIHUANG_ZHIFUBAO, bhhzfbChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_HUIHUANG_YINGKE, bhhyingkeChannelAds);

        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_DOUYIN, bdyChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_QUANNENG_XIGUA_VIDEO, bqxvChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_QUANNENG_DOUYIN_JISU, bqdyjsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_QUANNENG_JINRITOUTIAO, bqjrttChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_QUANNENG_FANQIE, bqfanqieChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_QUANNENG_FANQIE_CHANGTING, bqfqctChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_QUANNENG_DOUYIN_HUOSHAN, bqdyhsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_QUANNENG_TENGXUNSHIPIN, bqtxspChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_QUANNENG_PIPIXIA, bqppxChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_QUANNENG_YOUSHI, bqysChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_QUANNENG_UC, bqucChannelAds);

        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_DINGYUN_DOUYINHUOSHAN, bdydyhsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_DINGYUN_XIGUAVIDEO, bdyxgChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_DINGYUN_FANQIECHANGTING, bdyfqctChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_DINGYUN_YOUSHI, bdyysChannelAds);

        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_KEEP, bkeepChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_LUYUN_PAIPAI, blyppChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.BAIDU_LUYUN_KUAIKANMANHUA, blykkmhChannelAds);

        channelAdsMap.put(Constants.ChannelAdsKey.OPPO_KUAISHOU, oppoKuaishouChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.OPPO_HUIHUANG_YITAO, oppohhytChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.OPPO_HUIHUANG_XIANYU, oppohhxyChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.IQIYI_KUAISHOU, ikChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.IQIYI_HUIHUANG_FANQIE, ihhfqChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.IQIYI_HUIHUANG_XIGUAVIDEO, ihhxgChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.IQIYI_HUIHUANG_XIANYU, ihhxyChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.IQIYI_HUIHUANG_JINGDONG, ihhjdChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.IQIYI_DINGYUN_XIGUAVIDEO, idyxgChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.IQIYI_QUANNENG_XIGUAVIDEO, iqnxgChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.IQIYI_LUYUN_KEEP, ilykeepChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.IQIYI_LUYUN_XIAOHONGSHU, ilyxhsChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.IQIYI_HUIHUI_XIANYU, iqyxyChannelAds);


        channelAdsMap.put(Constants.ChannelAdsKey.HONOR_HUIHUANG_JINGDONG, honorhhjdChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.HONOR_HUIHUANG_XIANYU, honorhhxyChannelAds);

        channelAdsMap.put(Constants.ChannelAdsKey.GDT_HUIHUI_XIANYU, gdthhxyChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.GDT_KUAISHOU, gdtksChannelAds);
        channelAdsMap.put(Constants.ChannelAdsKey.GDT_HUIHUANG_XIANYU, gdthhfmxyChannelAds);

    }


}
