package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.net.URLDecoder;
import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongAdsDTO;
import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongEventTypeEnum;
import huihuang.proxy.ocpx.ads.quannenghudong.xiguavideo.QuannengXiguaVideoPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IQuannengXiguaVideoAdsDao;
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

@Service("bqxvService")
public class BaiduQuannengXiguaServiceImpl extends BaiduChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(BaiduQuannengXiguaServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IQuannengXiguaVideoAdsDao xiguaAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private QuannengXiguaVideoPath xiguaVideoPath;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_QUANNENG_XIGUA_VIDEO;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("action_type")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, eventType);

        //根据id查询对应的点击记录
        QuannengHudongAdsDTO xiguaAdsDTO = xiguaAdsDao.queryQuannengXiguaVideoAdsById(id);
        if (null == xiguaAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }
        String callback = xiguaAdsDTO.getCallback();
        String channelUrl = URLDecoder.decode(callback, StandardCharsets.UTF_8);

        Ads2BaiduVO baiduVO = new Ads2BaiduVO();
        baiduVO.setAdsId(id);
        baiduVO.setAdsName(xiguaVideoPath.baseAdsName());
        baiduVO.setChannelUrl(channelUrl);
        baiduVO.setaType(QuannengHudongEventTypeEnum.quannengHudongBaiduEventTypeMap.get(eventType).getCode());
        baiduVO.setaValue(0);
        baiduVO.setCbEventTime(String.valueOf(System.currentTimeMillis()));
        baiduVO.setCbOaid(xiguaAdsDTO.getOaid());
        baiduVO.setCbOaidMd5(xiguaAdsDTO.getOaid());
        baiduVO.setCbIdfa(xiguaAdsDTO.getIdfa());
        baiduVO.setCbImei(null);
        baiduVO.setCbImeiMd5(xiguaAdsDTO.getImei());
        baiduVO.setCbAndroidIdMd5(null);
        baiduVO.setCbIp(xiguaAdsDTO.getIp());
        baiduVO.setSecret(BaiduPath.QUANNENG_XIGUA_VIDEO_SECRET);
        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, baiduVO);

        Response response = baseAdsCallBack(baiduVO);
        BaiduCallbackDTO data = (BaiduCallbackDTO) response.getData();

        //更新回调状态
        QuannengHudongAdsDTO quannengdongAds = new QuannengHudongAdsDTO();
        quannengdongAds.setId(id);
        quannengdongAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));
        if (response.getCode() == 0) {
            quannengdongAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(quannengdongAds, xiguaAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            quannengdongAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(quannengdongAds, xiguaAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }

}