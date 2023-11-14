package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoAdsDTO;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoEventTypeEnum;
import huihuang.proxy.ocpx.ads.litianjingdong.LTJDPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.ILtjdAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.bussiness.service.basechannel.XiaomiChannelFactory;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2XiaomiVO;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiCallbackDTO;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.middle.factory.ChannelAdsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description: xiaomi-ltjd
 * @Author: xietao
 * @Date: 2023-04-24 17:42
 **/
@Service("xjService")
public class XiaomiJingdongServiceImpl extends XiaomiChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(XiaomiJingdongServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private ILtjdAdsDao ltjdAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private LTJDPath ltjdPath;


    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_LTJD;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("event_type")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, eventType);
        //根据id查询对应的点击记录
        LiangdamaoAdsDTO ltjdAdsDTO = ltjdAdsDao.queryLtjdAdsById(id);
        if (null == ltjdAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        if (eventType.equals(LiangdamaoEventTypeEnum.ACTIVE.getCode())) {
            eventType = eventType + "new";
        }

        Ads2XiaomiVO xiaomiVO = new Ads2XiaomiVO();
        xiaomiVO.setAdsId(id);
        xiaomiVO.setAdsName(ltjdPath.baseAdsName());
        xiaomiVO.setEventType(LiangdamaoEventTypeEnum.liangdamaoXiaomiEventTypeMap.get(eventType).getCode());
        xiaomiVO.setEventTimes(String.valueOf(System.currentTimeMillis()));
        xiaomiVO.setCallBackUrl(ltjdAdsDTO.getCallback_url());
        xiaomiVO.setOaid(ltjdAdsDTO.getOaid());
        xiaomiVO.setImei(ltjdAdsDTO.getImei_md5());

        Response response = super.baseAdsCallBack(xiaomiVO);
        XiaomiCallbackDTO data = (XiaomiCallbackDTO) response.getData();

        //更新回调状态
        LiangdamaoAdsDTO ltjdAds = new LiangdamaoAdsDTO();
        ltjdAds.setId(id);
        ltjdAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));

        if (response.getCode() == 0) {
            ltjdAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(ltjdAds, ltjdAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            ltjdAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(ltjdAds, ltjdAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }




}
