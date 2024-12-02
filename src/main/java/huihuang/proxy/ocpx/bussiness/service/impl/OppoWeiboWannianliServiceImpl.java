package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangFengmangEventTypeEnum;
import huihuang.proxy.ocpx.ads.weibo.WeiboAdsDTO;
import huihuang.proxy.ocpx.ads.weibo.WeiboEventTypeEnum;
import huihuang.proxy.ocpx.ads.weibo.wannianli.WeiboWannianliPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IWeiboWannianliAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.bussiness.service.basechannel.OppoChannelFactory;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2OppoVO;
import huihuang.proxy.ocpx.channel.oppo.OppoCallbackDTO;
import huihuang.proxy.ocpx.channel.oppo.OppoPath;
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

@Service("oppowbwnlService")
public class OppoWeiboWannianliServiceImpl extends OppoChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(OppoWeiboWannianliServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IWeiboWannianliAdsDao wbwnlAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private WeiboWannianliPath wbwnlPath;

    String channelAdsKey = Constants.ChannelAdsKey.OPPO_WEIBO_WANNIANLI;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        //转化类型字段
        String eventType = parameterMap.get("action_type")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, eventType);
        //根据id查询对应的点击记录
        WeiboAdsDTO weiboAdsDTO = wbwnlAdsDao.queryWeiboWannianliAdsById(id);

        if (null == weiboAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        String adsName = wbwnlPath.baseAdsName();
        String pkg = OppoPath.OPPO_WEIBO_WANNIANLI_PKG;

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
        oppoVO.setPkg(pkg);
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
            baseServiceInner.updateAdsObject(weiboAds, wbwnlAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            weiboAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(weiboAds, wbwnlAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }


}
