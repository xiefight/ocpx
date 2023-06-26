package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.net.URLDecoder;
import cn.hutool.crypto.digest.DigestUtil;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoAdsDTO;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoEventTypeEnum;
import huihuang.proxy.ocpx.ads.litianjingdong.LTJDPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.ILtjdAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.bussiness.service.basechannel.BdssChannelFactory;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2BaiduVO;
import huihuang.proxy.ocpx.channel.bdss.BdssCallbackDTO;
import huihuang.proxy.ocpx.channel.bdss.BdssPath;
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

/**
 * @Author: xietao
 * @Date: 2023/6/7 20:11
 */
@Service("bdssjdService")
public class BdssLtjdServiceImpl extends BdssChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(BdssLtjdServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private ILtjdAdsDao ltjdAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private LTJDPath ltjdPath;

    String channelAdsKey = Constants.ChannelAdsKey.BDSS_LTJD;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        logger.info("adsCallBack {} 开始回调渠道  id:{}  parameterMap.size:{}", channelAdsKey, id, parameterMap.size());

        //根据id查询对应的点击记录
        LiangdamaoAdsDTO ltjdAdsDTO = ltjdAdsDao.queryLtjdAdsById(id);
        if (null == ltjdAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }
        String callback = ltjdAdsDTO.getCallback_url();
        String channelUrl = URLDecoder.decode(callback, StandardCharsets.UTF_8);

        Ads2BaiduVO baiduVO = new Ads2BaiduVO();
        baiduVO.setAdsId(id);
        baiduVO.setAdsName(ltjdPath.baseAdsName());
        baiduVO.setChannelUrl(channelUrl);
        baiduVO.setaType(LiangdamaoEventTypeEnum.liangdamaoBaiduEventTypeMap.get(parameterMap.get("event_type")[0]).getCode());
        baiduVO.setaValue(0);
        baiduVO.setCbEventTime(String.valueOf(System.currentTimeMillis()));
        baiduVO.setCbOaid(ltjdAdsDTO.getOaid());
        baiduVO.setCbOaidMd5(ltjdAdsDTO.getOaid_md5());
        baiduVO.setCbIdfa(ltjdAdsDTO.getIdfa());
        baiduVO.setCbImei(ltjdAdsDTO.getImei());
        baiduVO.setCbImeiMd5(ltjdAdsDTO.getImei_md5());
        baiduVO.setCbAndroidIdMd5(ltjdAdsDTO.getAndroid_id_md5());
        baiduVO.setCbIp(ltjdAdsDTO.getIp());
        baiduVO.setSecret(BdssPath.LTJD_SECRET);
        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, baiduVO);

        Response response = baseAdsCallBack(baiduVO);
        BdssCallbackDTO data = (BdssCallbackDTO) response.getData();

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
