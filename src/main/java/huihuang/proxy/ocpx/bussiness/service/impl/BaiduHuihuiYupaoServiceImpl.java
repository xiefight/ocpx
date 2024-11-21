package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.net.URLDecoder;
import huihuang.proxy.ocpx.ads.huihui.HuihuiAdsDTO;
import huihuang.proxy.ocpx.ads.huihui.HuihuiEventTypeEnum;
import huihuang.proxy.ocpx.ads.huihui.yupao.HuihuiYupaoPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuiYupaoAdsDao;
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

@Service("bdhhyupaoService")
public class BaiduHuihuiYupaoServiceImpl extends BaiduChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(BaiduHuihuiYupaoServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IHuihuiYupaoAdsDao yupaoAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private HuihuiYupaoPath huihuiYupaoPath;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_HUIHUI_YUPAO;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("conv_action")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  event:{}", channelAdsKey, id, eventType);

        //根据id查询对应的点击记录
        HuihuiAdsDTO huihuiAdsDTO = yupaoAdsDao.queryYupaoAdsById(id);
        if (null == huihuiAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }
        String callback = huihuiAdsDTO.getCallback();
        String channelUrl = URLDecoder.decode(callback, StandardCharsets.UTF_8);

        String ocpxAccount = huihuiAdsDTO.getOcpxAccount();

        Ads2BaiduVO baiduVO = new Ads2BaiduVO();
        baiduVO.setAdsId(id);
        baiduVO.setAdsName(huihuiYupaoPath.baseAdsName());
        baiduVO.setChannelUrl(channelUrl);
        baiduVO.setaType(HuihuiEventTypeEnum.huihuiBaiduEventTypeMap.get(eventType).getCode());
        baiduVO.setaValue(0);
        baiduVO.setCbEventTime(String.valueOf(System.currentTimeMillis()));
        baiduVO.setCbOaid(huihuiAdsDTO.getOaid());
        baiduVO.setCbOaidMd5(huihuiAdsDTO.getOaid_md5());
        baiduVO.setCbIdfa(huihuiAdsDTO.getIdfa());
        baiduVO.setCbImei(huihuiAdsDTO.getImei());
//        baiduVO.setCbImeiMd5(huihuiAdsDTO.getImei_md5());
//        baiduVO.setCbAndroidIdMd5(huihuiAdsDTO.getAndroid_id_md5());
        baiduVO.setCbIp(huihuiAdsDTO.getIp());


        if (BaiduPath.BAIDU_HUIHUI_YUPAO_ACCOUNT_01.equals(ocpxAccount)) {
            baiduVO.setSecret(BaiduPath.BAIDU_HUIHUI_YUPAO_SECRET_01);
        } else if (BaiduPath.BAIDU_HUIHUI_YUPAO_ACCOUNT_02.equals(ocpxAccount)) {
            baiduVO.setSecret(BaiduPath.BAIDU_HUIHUI_YUPAO_SECRET_02);
        } else if (BaiduPath.BAIDU_HUIHUI_YUPAO_ACCOUNT_03.equals(ocpxAccount)) {
            baiduVO.setSecret(BaiduPath.BAIDU_HUIHUI_YUPAO_SECRET_03);
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
            baseServiceInner.updateAdsObject(huihuiAds, yupaoAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            huihuiAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(huihuiAds, yupaoAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }

}
