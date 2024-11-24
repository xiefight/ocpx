package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangFengmangEventTypeEnum;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianAdsDTO;
import huihuang.proxy.ocpx.ads.weibo.WeiboAdsDTO;
import huihuang.proxy.ocpx.ads.weibo.WeiboEventTypeEnum;
import huihuang.proxy.ocpx.ads.weibo.baiduwangpan.WeiboBaiduwangpanPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IWeiboBaiduwangpanAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.bussiness.service.basechannel.OppoChannelFactory;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2OppoVO;
import huihuang.proxy.ocpx.channel.oppo.OppoCallbackDTO;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.middle.factory.ChannelAdsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service("oppowbbdwpService")
public class OppoWeiboBaiduwangpanServiceImpl extends OppoChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(OppoWeiboBaiduwangpanServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IWeiboBaiduwangpanAdsDao wbbdwpAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private WeiboBaiduwangpanPath wbbdwpPath;

    String channelAdsKey = Constants.ChannelAdsKey.OPPO_WEIBO_BAIDUWANGPAN;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        //转化类型字段
        String eventType = parameterMap.get("action_type")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, eventType);
        //闲鱼13是激活
        if ("13".equals(eventType)) {
            eventType = HuihuangFengmangEventTypeEnum.ACTIVATE.getCode();
        }
        //根据id查询对应的点击记录
        WeiboAdsDTO weiboAdsDTO = wbbdwpAdsDao.queryWeiboBaiduwangpanAdsById(id);

        if (null == weiboAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        String adsName = wbbdwpPath.baseAdsName();
        //todo pkg包
//        String pkg = OppoPath.OPPO_HUIHUANG_XIANYU_PKG;

        long currentTime = System.currentTimeMillis();
        Ads2OppoVO oppoVO = new Ads2OppoVO();
        if (StrUtil.isNotEmpty(weiboAdsDTO.getImei_md5())) {
            oppoVO.setImei(encode(weiboAdsDTO.getImei_md5().getBytes(StandardCharsets.UTF_8)));
        }
        if (StrUtil.isNotEmpty(weiboAdsDTO.getOaid_md5())) {
            oppoVO.setOuId(encode(weiboAdsDTO.getOaid_md5().getBytes(StandardCharsets.UTF_8)));
        }

        oppoVO.setAdsId(id);
        oppoVO.setAdsName(adsName);
        oppoVO.setChannel(1);
        oppoVO.setTimestamp(currentTime);
//        oppoVO.setPkg(pkg);
        oppoVO.setDataType(WeiboEventTypeEnum.weiboOppoEventTypeMap.get(eventType).getCode());
        oppoVO.setAscribeType(0);
//        oppoVO.setAdId(Long.valueOf(weiboAdsDTO.getAdid()));
        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, oppoVO);

        Response response = baseAdsCallBack(oppoVO);
        OppoCallbackDTO data = (OppoCallbackDTO) response.getData();

        //更新回调状态
        WeiboAdsDTO weiboAds = new WeiboAdsDTO();
        weiboAds.setId(id);
        weiboAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));
        if (response.getCode() == 0) {
            weiboAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(weiboAds, wbbdwpAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            weiboAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(weiboAds, wbbdwpAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }


}
