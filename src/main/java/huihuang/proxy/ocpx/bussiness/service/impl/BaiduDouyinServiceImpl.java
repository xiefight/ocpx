package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.net.URLDecoder;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoAdsDTO;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoEventTypeEnum;
import huihuang.proxy.ocpx.ads.liangdamao.douyin.DouyinPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IDouyinAdsDao;
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

@Service("bdyService")
public class BaiduDouyinServiceImpl extends BaiduChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(BaiduDouyinServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IDouyinAdsDao douyinAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private DouyinPath douyinPath;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_DOUYIN;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("event_type")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, eventType);

        //根据id查询对应的点击记录
        LiangdamaoAdsDTO douyinAdsDTO = douyinAdsDao.queryDouyinAdsById(id);
        if (null == douyinAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }
        String callback = douyinAdsDTO.getCallback_url();
        String channelUrl = URLDecoder.decode(callback, StandardCharsets.UTF_8);

        Ads2BaiduVO baiduVO = new Ads2BaiduVO();
        baiduVO.setAdsId(id);
        baiduVO.setAdsName(douyinPath.baseAdsName());
        baiduVO.setChannelUrl(channelUrl);
        baiduVO.setaType(LiangdamaoEventTypeEnum.liangdamaoBaiduEventTypeMap.get(eventType).getCode());
        baiduVO.setaValue(0);
        baiduVO.setCbEventTime(String.valueOf(System.currentTimeMillis()));
        baiduVO.setCbOaid(douyinAdsDTO.getOaid());
        baiduVO.setCbOaidMd5(douyinAdsDTO.getOaid_md5());
        baiduVO.setCbIdfa(douyinAdsDTO.getIdfa());
        baiduVO.setCbImei(douyinAdsDTO.getImei());
        baiduVO.setCbImeiMd5(douyinAdsDTO.getImei_md5());
        baiduVO.setCbAndroidIdMd5(douyinAdsDTO.getAndroid_id_md5());
        baiduVO.setCbIp(douyinAdsDTO.getIp());
        baiduVO.setSecret(BaiduPath.DOUYIN_SECRET);
        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, baiduVO);

        Response response = baseAdsCallBack(baiduVO);
        BaiduCallbackDTO data = (BaiduCallbackDTO) response.getData();

        //更新回调状态
        LiangdamaoAdsDTO douyinAds = new LiangdamaoAdsDTO();
        douyinAds.setId(id);
        douyinAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));
        if (response.getCode() == 0) {
            douyinAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(douyinAds, douyinAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            douyinAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(douyinAds, douyinAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }

}
