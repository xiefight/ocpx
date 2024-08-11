package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianAdsDTO;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianEventTypeEnum;
import huihuang.proxy.ocpx.ads.huihuangmingtian.ads.HuihuangYitaoPath;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangYitaoAdsDao;
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

@Service("oppohhytService")
public class OppoHuihuangYitaoServiceImpl extends OppoChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(OppoHuihuangYitaoServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IHuihuangYitaoAdsDao hhytAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private HuihuangYitaoPath hhytPath;

    String channelAdsKey = Constants.ChannelAdsKey.OPPO_HUIHUANG_YITAO;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        logger.info("adsCallBack {} 开始回调渠道  id:{}  parameterMap.size:{}", channelAdsKey, id, parameterMap.size());
        //根据id查询对应的点击记录
        HuihuangmingtianAdsDTO hhtmAdsDTO = hhytAdsDao.queryHuihuangYitaoAdsById(id);

        if (null == hhtmAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        String oppoSecret = "";
        String adsName = hhytPath.baseAdsName();
        Long adId = OppoPath.KUAISHOU_ADID;
        String pkg = OppoPath.OPPO_HUIHUANG_YITAO_PKG;
        if (KuaishouPath.OPPO_KUAISHOU_ADID.equals(hhtmAdsDTO.getAdid())) {
            oppoSecret = OppoPath.KUAISHOU_SECRET;
            adsName = KuaishouPath.KUAISHOU_ADS_NAME;
            pkg = KuaishouPath.OPPO_KUAISHOU_PKG;
        }
        if (KuaishouPath.OPPO_KUAISHOUJISU_ADID.equals(hhtmAdsDTO.getAdid())) {
            oppoSecret = OppoPath.KUAISHOUJISU_SECRET;
            adsName = KuaishouPath.KUAISHOUJISU_ADS_NAME;
            adId = OppoPath.KUAISHOUJISU_ADID;
            pkg = KuaishouPath.OPPO_KUAISHOUJISU_PKG;
        }

        //转化类型字段
        String eventType = parameterMap.get("event_type")[0];
        long currentTime = System.currentTimeMillis();
        Ads2OppoVO oppoVO = new Ads2OppoVO();
        if (StrUtil.isNotEmpty(hhtmAdsDTO.getImeiMd5())) {
            oppoVO.setImei(encode(hhtmAdsDTO.getImeiMd5().getBytes(StandardCharsets.UTF_8)));
        }
        if (StrUtil.isNotEmpty(hhtmAdsDTO.getOaid())) {
            oppoVO.setOuId(encode(hhtmAdsDTO.getOaid().getBytes(StandardCharsets.UTF_8)));
        }

        oppoVO.setAdsId(id);
        oppoVO.setAdsName(adsName);
        oppoVO.setChannel(1);
        oppoVO.setTimestamp(currentTime);
        oppoVO.setPkg(pkg);
        oppoVO.setDataType(HuihuangmingtianEventTypeEnum.huihuangmingtianOppoEventTypeMap.get(eventType).getCode());
        oppoVO.setAscribeType(0);
        oppoVO.setAdId(Long.valueOf(hhtmAdsDTO.getAid()));
        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, oppoVO);

        Response response = baseAdsCallBack(oppoVO);
        OppoCallbackDTO data = (OppoCallbackDTO) response.getData();

        //更新回调状态
        HuihuangmingtianAdsDTO huihuangmingtianAds = new HuihuangmingtianAdsDTO();
        huihuangmingtianAds.setId(id);
        huihuangmingtianAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));
        if (response.getCode() == 0) {
            huihuangmingtianAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(huihuangmingtianAds, hhytAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            huihuangmingtianAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(huihuangmingtianAds, hhytAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }


}
