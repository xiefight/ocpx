package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoAdsDTO;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoEventTypeEnum;
import huihuang.proxy.ocpx.ads.youku.YoukuPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IYoukuAdsDao;
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
 * xiaomi-youku
 *
 * @Author: xietao
 * @Date: 2023/4/27 10:47
 */
@Service("xyService")
public class XiaomiYoukuServiceImpl extends XiaomiChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(XiaomiYoukuServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IYoukuAdsDao youkuAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private YoukuPath youkuPath;

    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_YOUKU;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        logger.info("adsCallBack {} 开始回调渠道  id:{}  parameterMap.size:{}", channelAdsKey, id, parameterMap.size());
        //根据id查询对应的点击记录
        LiangdamaoAdsDTO youkuAdsDTO = youkuAdsDao.queryYoukuAdsById(id);
        if (null == youkuAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }
        Ads2XiaomiVO xiaomiVO = new Ads2XiaomiVO();
        xiaomiVO.setAdsId(id);
        xiaomiVO.setAdsName(youkuPath.baseAdsName());
        xiaomiVO.setEventType(LiangdamaoEventTypeEnum.liangdamaoXiaomiEventTypeMap.get(parameterMap.get("event_type")[0]).getCode());
        xiaomiVO.setEventTimes(String.valueOf(System.currentTimeMillis()));
        xiaomiVO.setCallBackUrl(youkuAdsDTO.getCallback_url());
        xiaomiVO.setOaid(youkuAdsDTO.getOaid());
        xiaomiVO.setImei(youkuAdsDTO.getImei_md5());

        Response response = super.baseAdsCallBack(xiaomiVO);
        XiaomiCallbackDTO data = (XiaomiCallbackDTO) response.getData();

        //更新回调状态
        LiangdamaoAdsDTO youkuAds = new LiangdamaoAdsDTO();
        youkuAds.setId(id);
        youkuAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));

        if (response.getCode() == 0) {
            youkuAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(youkuAds, youkuAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            youkuAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(youkuAds, youkuAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }

}
