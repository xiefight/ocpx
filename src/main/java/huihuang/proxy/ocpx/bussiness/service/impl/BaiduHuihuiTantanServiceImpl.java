package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.net.URLDecoder;
import huihuang.proxy.ocpx.ads.huihui.HuihuiAdsDTO;
import huihuang.proxy.ocpx.ads.huihui.HuihuiEventTypeEnum;
import huihuang.proxy.ocpx.ads.huihui.tantan.HuihuiTantanPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuiTantanAdsDao;
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

@Service("bdhhttService")
public class BaiduHuihuiTantanServiceImpl extends BaiduChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(BaiduHuihuiTantanServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IHuihuiTantanAdsDao tantanAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private HuihuiTantanPath tantanPath;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_HUIHUI_TANTAN;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("conv_action")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  event:{}", channelAdsKey, id, eventType);

        //根据id查询对应的点击记录
        HuihuiAdsDTO taobaoAdsDTO = tantanAdsDao.queryTantanAdsById(id);
        if (null == taobaoAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }
        String callback = taobaoAdsDTO.getCallback();
        String channelUrl = URLDecoder.decode(callback, StandardCharsets.UTF_8);

        //针对闲鱼aid=36490(原33936)的户，闲鱼注册事件对应百度激活事件，这里特殊处理一下，而闲鱼的激活事件暂时不处理
        if ("36490".equals(taobaoAdsDTO.getAid())) {
            if (eventType.equals(HuihuiEventTypeEnum.ANDROID_ACTIVATE.getCode())
                    || eventType.equals(HuihuiEventTypeEnum.IOS_ACTIVATE.getCode())) {
                return BasicResult.getFailResponse("aid=36490的闲鱼户不需要回传激活事件:" + id);
            }
            if (eventType.equals(HuihuiEventTypeEnum.ANDROID_REGISTER.getCode())) {
                eventType = HuihuiEventTypeEnum.ANDROID_ACTIVATE.getCode();
            }
            if (eventType.equals(HuihuiEventTypeEnum.IOS_REGISTER.getCode())) {
                eventType = HuihuiEventTypeEnum.IOS_ACTIVATE.getCode();
            }
        }

        Ads2BaiduVO baiduVO = new Ads2BaiduVO();
        baiduVO.setAdsId(id);
        baiduVO.setAdsName(tantanPath.baseAdsName());
        baiduVO.setChannelUrl(channelUrl);
        baiduVO.setaType(HuihuiEventTypeEnum.huihuiBaiduEventTypeMap.get(eventType).getCode());
        baiduVO.setaValue(0);
        baiduVO.setCbEventTime(String.valueOf(System.currentTimeMillis()));
        baiduVO.setCbOaid(taobaoAdsDTO.getOaid());
        baiduVO.setCbOaidMd5(taobaoAdsDTO.getOaid_md5());
        baiduVO.setCbIdfa(taobaoAdsDTO.getIdfa());
        baiduVO.setCbImei(taobaoAdsDTO.getImei());
//        baiduVO.setCbImeiMd5(taobaoAdsDTO.getImei_md5());
//        baiduVO.setCbAndroidIdMd5(taobaoAdsDTO.getAndroid_id_md5());
        baiduVO.setCbIp(taobaoAdsDTO.getIp());

        String ocpxAccount = taobaoAdsDTO.getOcpxAccount();
        if (BaiduPath.BAIDU_HUIHUI_TANTAN_ACCOUNT_01.equals(ocpxAccount)) {
            baiduVO.setSecret(BaiduPath.HUIHUI_TANTAN_01_SECRET);
        }


        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, baiduVO);

        Response response = baseAdsCallBack(baiduVO);
        BaiduCallbackDTO data = (BaiduCallbackDTO) response.getData();

        //更新回调状态
        HuihuiAdsDTO huihuiAds = new HuihuiAdsDTO();
        huihuiAds.setId(id);
        huihuiAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));
        if (response.getCode() == 0) {
            huihuiAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(huihuiAds, tantanAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            huihuiAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(huihuiAds, tantanAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }

}
