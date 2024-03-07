package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.ads.huihui.HuihuiAdsDTO;
import huihuang.proxy.ocpx.ads.huihui.HuihuiEventTypeEnum;
import huihuang.proxy.ocpx.ads.huihui.taobao.HuihuiTaobaoPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuiTaobaoAdsDao;
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

@Service("xhhtbService")
public class XiaomiHuihuiTaobaoServiceImpl extends XiaomiChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(XiaomiHuihuiTaobaoServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IHuihuiTaobaoAdsDao taobaoAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private HuihuiTaobaoPath huihuiTaobaoPath;


    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_HUIHUI_TAOBAO;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("conv_action")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, eventType);
        //根据id查询对应的点击记录
        HuihuiAdsDTO huihuiAdsDTO = taobaoAdsDao.queryTaobaoYoudaoAdsById(id);
        if (null == huihuiAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }
        Ads2XiaomiVO xiaomiVO = new Ads2XiaomiVO();
        xiaomiVO.setAdsId(id);
        xiaomiVO.setAdsName(huihuiTaobaoPath.baseAdsName());
        //HuihuiEventTypeEnum.huihuiXiaomiEventTypeMap.get(parameterMap.get("conv_action")[0]).getCode()
        xiaomiVO.setEventType(HuihuiEventTypeEnum.huihuiXiaomiEventTypeMap.get(eventType).getCode());
        xiaomiVO.setEventTimes(String.valueOf(System.currentTimeMillis()));
        xiaomiVO.setCallBackUrl(huihuiAdsDTO.getCallback());
        xiaomiVO.setOaid(huihuiAdsDTO.getOaid());
        xiaomiVO.setImei(huihuiAdsDTO.getImei());

        Response response = super.baseAdsCallBack(xiaomiVO);
        XiaomiCallbackDTO data = (XiaomiCallbackDTO) response.getData();

        //更新回调状态
        HuihuiAdsDTO huihuiAds = new HuihuiAdsDTO();
        huihuiAds.setId(id);
        huihuiAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));

        if (response.getCode() == 0) {
            huihuiAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(huihuiAds, taobaoAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            huihuiAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(huihuiAds, taobaoAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }


}
