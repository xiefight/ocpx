package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.net.URLDecoder;
import huihuang.proxy.ocpx.ads.weibo.WeiboAdsDTO;
import huihuang.proxy.ocpx.ads.weibo.WeiboEventTypeEnum;
import huihuang.proxy.ocpx.ads.weibo.wannianli.WeiboWannianliPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IWeiboWannianliAdsDao;
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

@Service("bdwbwnlService")
public class BaiduWeiboWannianliServiceImpl extends BaiduChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(BaiduWeiboWannianliServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IWeiboWannianliAdsDao weiboWannianliAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private WeiboWannianliPath weiboWannianliPath;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_WEIBO_WANNIANLI;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("action_type")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, eventType);
        //根据id查询对应的点击记录
        WeiboAdsDTO weiboAdsDTO = weiboWannianliAdsDao.queryWeiboWannianliAdsById(id);
        if (null == weiboAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        String callback = weiboAdsDTO.getCb();
        String channelUrl = URLDecoder.decode(callback, StandardCharsets.UTF_8);

        Ads2BaiduVO baiduVO = new Ads2BaiduVO();
        baiduVO.setAdsId(id);
        baiduVO.setAdsName(weiboWannianliPath.baseAdsName());
        baiduVO.setChannelUrl(channelUrl);
        baiduVO.setaType(WeiboEventTypeEnum.weiboBaiduEventTypeMap.get(eventType).getCode());
        baiduVO.setaValue(0);
        baiduVO.setCbEventTime(String.valueOf(System.currentTimeMillis()));
//        baiduVO.setCbOaid(weiboAdsDTO.getOaid());
        baiduVO.setCbOaidMd5(weiboAdsDTO.getOaid_md5());
        baiduVO.setCbIdfa(weiboAdsDTO.getIdfa_md5());
        baiduVO.setCbImei(null);
        baiduVO.setCbImeiMd5(weiboAdsDTO.getImei_md5());
        baiduVO.setCbAndroidIdMd5(null);
        baiduVO.setCbIp(weiboAdsDTO.getIp());
        if (BaiduPath.BAIDU_WEIBO_WANNIANLI_ACCOUNT_01.equals(weiboAdsDTO.getAccountId())) {
            baiduVO.setSecret(BaiduPath.BAIDU_WEIBO_WANNIANLI_SECRET_01);
        }
        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, baiduVO);

        Response response = baseAdsCallBack(baiduVO);
        BaiduCallbackDTO data = (BaiduCallbackDTO) response.getData();

        //更新回调状态
        WeiboAdsDTO dto = new WeiboAdsDTO();
        dto.setId(id);
        dto.setCallBackTime(String.valueOf(System.currentTimeMillis()));

        if (response.getCode() == 0) {
            dto.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(dto, weiboWannianliAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            dto.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(dto, weiboWannianliAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }

}
