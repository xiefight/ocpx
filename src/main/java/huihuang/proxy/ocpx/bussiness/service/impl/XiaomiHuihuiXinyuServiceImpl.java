package huihuang.proxy.ocpx.bussiness.service.impl;

import huihuang.proxy.ocpx.ads.huihui.HuihuiAdsDTO;
import huihuang.proxy.ocpx.ads.huihui.HuihuiEventTypeEnum;
import huihuang.proxy.ocpx.ads.huihui.xinyu.HuihuiXinyuPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuiXinyuAdsDao;
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
 * @Author: xietao
 * @Date: 2023/8/2 17:46
 */
@Service("xxyydService")
public class XiaomiHuihuiXinyuServiceImpl extends XiaomiChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(XiaomiHuihuiXinyuServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IHuihuiXinyuAdsDao xinyuyoudaoAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private HuihuiXinyuPath xinyuyoudaoPath;


    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_XINYU_YOUDAO;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        logger.info("adsCallBack {} 开始回调渠道  id:{}  event:{}", channelAdsKey, id, parameterMap.get("conv_action")[0]);
        //根据id查询对应的点击记录
        HuihuiAdsDTO huihuiAdsDTO = xinyuyoudaoAdsDao.queryXinyuYoudaoAdsById(id);
        if (null == huihuiAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }
        Ads2XiaomiVO xiaomiVO = new Ads2XiaomiVO();
        xiaomiVO.setAdsId(id);
        xiaomiVO.setAdsName(xinyuyoudaoPath.baseAdsName());
        //HuihuiEventTypeEnum.huihuiXiaomiEventTypeMap.get(parameterMap.get("conv_action")[0]).getCode()
        xiaomiVO.setEventType(HuihuiEventTypeEnum.huihuiXiaomiEventTypeMap.get(parameterMap.get("conv_action")[0]).getCode());
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
            baseServiceInner.updateAdsObject(huihuiAds, xinyuyoudaoAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            huihuiAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(huihuiAds, xinyuyoudaoAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }


}
