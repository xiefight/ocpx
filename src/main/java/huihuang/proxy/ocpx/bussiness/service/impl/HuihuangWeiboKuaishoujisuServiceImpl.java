package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.net.URLDecoder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import huihuang.proxy.ocpx.ads.weibo.WeiboAdsDTO;
import huihuang.proxy.ocpx.ads.weibo.kuaishou.WeiboKuaishoujisuPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IWeiboKuaishoujisuAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
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

@Service("hhwbksjsService")
public class HuihuangWeiboKuaishoujisuServiceImpl implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(HuihuangWeiboKuaishoujisuServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IWeiboKuaishoujisuAdsDao weiboKuaishoujisuAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private WeiboKuaishoujisuPath weiboKuaishoujisuPath;

    String channelAdsKey = Constants.ChannelAdsKey.HUIHUANG_WEIBO_KUAISHOUJISU;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("action_type")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, eventType);
        //根据id查询对应的点击记录
        WeiboAdsDTO weiboAdsDTO = weiboKuaishoujisuAdsDao.queryWeiboKuaishoujisuAdsById(id);
        if (null == weiboAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        String callback = weiboAdsDTO.getCb();
        String channelUrl = URLDecoder.decode(callback, StandardCharsets.UTF_8);
        //辉煌的链接，将事件拼接上
        channelUrl = channelUrl + "&event_type=" + eventType;
        HttpResponse result = HttpRequest.get(channelUrl).execute();
        //todo 回调辉煌的结果暂不保存数据库

        //更新回调状态
        WeiboAdsDTO dto = new WeiboAdsDTO();
        dto.setId(id);
        dto.setCallBackTime(String.valueOf(System.currentTimeMillis()));

        if (HttpStatus.HTTP_OK == result.getStatus()) {
            dto.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(dto, weiboKuaishoujisuAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, id);
            return BasicResult.getSuccessResponse(id);
        } else {
            dto.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(dto, weiboKuaishoujisuAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, id);
            return BasicResult.getFailResponse("回调渠道失败:"+id);
        }
    }

}
