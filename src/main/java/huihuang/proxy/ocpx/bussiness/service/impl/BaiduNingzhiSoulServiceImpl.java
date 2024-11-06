package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.net.URLDecoder;
import huihuang.proxy.ocpx.ads.ningzhi.NingzhiAdsDTO;
import huihuang.proxy.ocpx.ads.ningzhi.NingzhiEventTypeEnum;
import huihuang.proxy.ocpx.ads.ningzhi.soul.NingzhiSoulPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.INingzhiSoulAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.bussiness.service.basechannel.BaiduChannelFactory;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2BaiduVO;
import huihuang.proxy.ocpx.channel.baidu.BaiduCallbackDTO;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
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

@Service("bdningzhisoulService")
public class BaiduNingzhiSoulServiceImpl extends BaiduChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(BaiduNingzhiSoulServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private INingzhiSoulAdsDao ningzhiSoulAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private NingzhiSoulPath ningzhisoulPath;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_NINGZHI_SOUL;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("event")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, eventType);
        //根据id查询对应的点击记录
        NingzhiAdsDTO ningzhiAdsDTO = ningzhiSoulAdsDao.queryNingzhiSoulAdsById(id);
        if (null == ningzhiAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        //bdningzhisoul02广告主注册伪装激活回传
//        if (ningzhiAdsDTO.getAccountId().equals(BaiduPath.BAIDU_NINGZHI_SOUL_ACCOUNT_02) && NingzhiEventTypeEnum.REGISTER.getCode().equals(eventType)) {
//            eventType = NingzhiEventTypeEnum.ACTIVATE.getCode();
//        }

        String callback = ningzhiAdsDTO.getCallback();
        String channelUrl = URLDecoder.decode(callback, StandardCharsets.UTF_8);

        Ads2BaiduVO baiduVO = new Ads2BaiduVO();
        baiduVO.setAdsId(id);
        baiduVO.setAdsName(ningzhisoulPath.baseAdsName());
        baiduVO.setChannelUrl(channelUrl);
        baiduVO.setaType(NingzhiEventTypeEnum.ningzhiBaiduEventTypeMap.get(eventType).getCode());
        baiduVO.setaValue(0);
        baiduVO.setCbEventTime(String.valueOf(System.currentTimeMillis()));
        baiduVO.setCbOaid(ningzhiAdsDTO.getOaid());
        baiduVO.setCbOaidMd5(ningzhiAdsDTO.getOaid());
        baiduVO.setCbIdfa(ningzhiAdsDTO.getIdfa());
        baiduVO.setCbImei(null);
        baiduVO.setCbImeiMd5(null);
        baiduVO.setCbAndroidIdMd5(null);
        baiduVO.setCbIp(ningzhiAdsDTO.getIp());
        if (BaiduPath.BAIDU_NINGZHI_SOUL_ACCOUNT_01.equals(ningzhiAdsDTO.getAccountId())) {
            baiduVO.setSecret(BaiduPath.BAIDU_NINGZHI_SOUL_SECRET_01);
        } else if (BaiduPath.BAIDU_NINGZHI_SOUL_ACCOUNT_02.equals(ningzhiAdsDTO.getAccountId())) {
            baiduVO.setSecret(BaiduPath.BAIDU_NINGZHI_SOUL_SECRET_02);
        }
        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, baiduVO);

        Response response = baseAdsCallBack(baiduVO);
        BaiduCallbackDTO data = (BaiduCallbackDTO) response.getData();

        //更新回调状态
        NingzhiAdsDTO dto = new NingzhiAdsDTO();
        dto.setId(id);
        dto.setCallBackTime(String.valueOf(System.currentTimeMillis()));

        if (response.getCode() == 0) {
            dto.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(dto, ningzhiSoulAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            dto.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(dto, ningzhiSoulAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }

}
