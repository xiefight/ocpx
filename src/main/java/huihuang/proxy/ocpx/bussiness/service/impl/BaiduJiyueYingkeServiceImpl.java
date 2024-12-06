package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.net.URLDecoder;
import huihuang.proxy.ocpx.ads.bupet.BupetAdsDTO;
import huihuang.proxy.ocpx.ads.bupet.BupetEventTypeEnum;
import huihuang.proxy.ocpx.ads.jiyue.JiyueAdsDTO;
import huihuang.proxy.ocpx.ads.jiyue.JiyueEventTypeEnum;
import huihuang.proxy.ocpx.ads.jiyue.yingke.JiyueYingkePath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IJiyueYingkeAdsDao;
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

@Service("bdjyykService")
public class BaiduJiyueYingkeServiceImpl extends BaiduChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(BaiduJiyueYingkeServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IJiyueYingkeAdsDao jiyueYingkeAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private JiyueYingkePath jiyueYingkePath;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_JIYUE_YINGKE;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("conv_type")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, eventType);
        //根据id查询对应的点击记录
        JiyueAdsDTO jiyueAdsDTO = jiyueYingkeAdsDao.queryJiyueYingkeAdsById(id);
        if (null == jiyueAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        String callback = jiyueAdsDTO.getCallback();
        String channelUrl = URLDecoder.decode(callback, StandardCharsets.UTF_8);

        Ads2BaiduVO baiduVO = new Ads2BaiduVO();
        baiduVO.setAdsId(id);
        baiduVO.setAdsName(jiyueYingkePath.baseAdsName());
        baiduVO.setChannelUrl(channelUrl);
        baiduVO.setaType(JiyueEventTypeEnum.jiyueBaiduEventTypeMap.get(eventType).getCode());
        baiduVO.setaValue(0);
        baiduVO.setCbEventTime(String.valueOf(System.currentTimeMillis()));
        baiduVO.setCbOaid(jiyueAdsDTO.getOaid());
        baiduVO.setCbOaidMd5(jiyueAdsDTO.getOaidMd5());
        baiduVO.setCbIdfa(jiyueAdsDTO.getIdfa());
        baiduVO.setCbImei(null);
        baiduVO.setCbImeiMd5(null);
        baiduVO.setCbAndroidIdMd5(null);
        baiduVO.setCbIp(jiyueAdsDTO.getIp());
        if (BaiduPath.BAIDU_JIYUE_YINGKE_ACCOUNT_01.equals(jiyueAdsDTO.getAccountId())) {
            baiduVO.setSecret(BaiduPath.BAIDU_JIYUE_YINGKE_SECRET_01);
        }
        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, baiduVO);

        Response response = baseAdsCallBack(baiduVO);
        BaiduCallbackDTO data = (BaiduCallbackDTO) response.getData();

        //更新回调状态
        BupetAdsDTO dto = new BupetAdsDTO();
        dto.setId(id);
        dto.setCallBackTime(String.valueOf(System.currentTimeMillis()));

        if (response.getCode() == 0) {
            dto.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(dto, jiyueYingkeAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            dto.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(dto, jiyueYingkeAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }

}
