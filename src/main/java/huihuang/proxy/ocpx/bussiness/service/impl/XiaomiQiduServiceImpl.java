package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.ads.qidu.QiduAdsDTO;
import huihuang.proxy.ocpx.ads.qidu.QiduEventTypeEnum;
import huihuang.proxy.ocpx.ads.qidu.QiduPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IQiduAdsDao;
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

@Service("xqiduService")
public class XiaomiQiduServiceImpl extends XiaomiChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(XiaomiQiduServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IQiduAdsDao qiduAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
//    @Autowired
//    private DongchediPath dongchediPath;


    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_QIDU;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("event_type")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  event:{}", channelAdsKey, id, eventType);
        if (QiduEventTypeEnum.ACTIVATE.getCode().equals(eventType)) {
            eventType = eventType + "new";
        }
        //根据id查询对应的点击记录
        QiduAdsDTO qiduAdsDTO = qiduAdsDao.queryQiduAdsById(id);
        if (null == qiduAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }
        Ads2XiaomiVO xiaomiVO = new Ads2XiaomiVO();
        xiaomiVO.setAdsId(id);
        xiaomiVO.setAdsName(QiduPath.ADS_NAME);
        xiaomiVO.setEventType(QiduEventTypeEnum.qiduXiaomiEventTypeMap.get(eventType).getCode());
        xiaomiVO.setEventTimes(String.valueOf(System.currentTimeMillis()));
        xiaomiVO.setCallBackUrl(qiduAdsDTO.getCallback());
        xiaomiVO.setOaid(qiduAdsDTO.getOaid());
        xiaomiVO.setImei(qiduAdsDTO.getImeiMd5());

        Response response = super.baseAdsCallBack(xiaomiVO);
        XiaomiCallbackDTO data = (XiaomiCallbackDTO) response.getData();

        //更新回调状态
        QiduAdsDTO qiduAds = new QiduAdsDTO();
        qiduAds.setId(id);
        qiduAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));

        if (response.getCode() == 0) {
            qiduAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(qiduAds, qiduAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            qiduAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(qiduAds, qiduAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }


}
