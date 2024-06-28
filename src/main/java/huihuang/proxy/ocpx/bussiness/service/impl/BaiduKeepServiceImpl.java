package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.net.URLDecoder;
import huihuang.proxy.ocpx.ads.luyun.LuyunAdsDTO;
import huihuang.proxy.ocpx.ads.luyun.LuyunEventTypeEnum;
import huihuang.proxy.ocpx.ads.luyun.keep.KeepPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IKeepAdsDao;
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

@Service("bkeepService")
public class BaiduKeepServiceImpl extends BaiduChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(BaiduKeepServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IKeepAdsDao keepAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private KeepPath keepPath;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_KEEP;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("event_type")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, eventType);

        //根据id查询对应的点击记录
        LuyunAdsDTO keepAdsDTO = keepAdsDao.queryKeepAdsById(id);
        if (null == keepAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }
        String callback = keepAdsDTO.getCallback();
        String channelUrl = URLDecoder.decode(callback, StandardCharsets.UTF_8);

        Ads2BaiduVO baiduVO = new Ads2BaiduVO();
        baiduVO.setAdsId(id);
        baiduVO.setAdsName(keepPath.baseAdsName());
        baiduVO.setChannelUrl(channelUrl);
        baiduVO.setaType(LuyunEventTypeEnum.luyunBaiduEventTypeMap.get(eventType).getCode());
        baiduVO.setaValue(0);
        baiduVO.setCbEventTime(String.valueOf(System.currentTimeMillis()));
        baiduVO.setCbOaid(keepAdsDTO.getOaid());
        baiduVO.setCbOaidMd5(keepAdsDTO.getOaidMd5());
//        baiduVO.setCbIdfa(keepAdsDTO.getIdfa());
        baiduVO.setCbImei(null);
        baiduVO.setCbImeiMd5(keepAdsDTO.getImeiMd5());
        baiduVO.setCbAndroidIdMd5(null);
        baiduVO.setCbIp(keepAdsDTO.getIp());
        if (BaiduPath.BAIDU_LUYUN_KEEP_ACCOUNT_02.equals(keepAdsDTO.getAccountId())) {
            baiduVO.setSecret(BaiduPath.KEEP_02_SECRET);
        }else if (BaiduPath.BAIDU_LUYUN_KEEP_SOUSUO_ACCOUNT_01.equals(keepAdsDTO.getAccountId())) {
            baiduVO.setSecret(BaiduPath.KEEP_SOUSUO_SECRET_01);
        } else {
            baiduVO.setSecret(BaiduPath.KEEP_SECRET);
        }
        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, baiduVO);

        Response response = baseAdsCallBack(baiduVO);
        BaiduCallbackDTO data = (BaiduCallbackDTO) response.getData();

        //更新回调状态
        LuyunAdsDTO keepAds = new LuyunAdsDTO();
        keepAds.setId(id);
        keepAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));
        if (response.getCode() == 0) {
            keepAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(keepAds, keepAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            keepAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(keepAds, keepAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }

}
