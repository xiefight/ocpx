package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.net.URLDecoder;
import huihuang.proxy.ocpx.ads.huihui.HuihuiAdsDTO;
import huihuang.proxy.ocpx.ads.huihui.HuihuiEventTypeEnum;
import huihuang.proxy.ocpx.ads.xianyu.XianyuPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IXianyuAdsDao;
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

/**
 * @Author: xietao
 * @Date: 2023/8/8 11:40
 */
@Service("bxyService")
public class BaiduXianyuServiceImpl extends BaiduChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(BaiduXianyuServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IXianyuAdsDao xianyuAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private XianyuPath xianyuPath;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_DONGCHEDI;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        logger.info("adsCallBack {} 开始回调渠道  id:{}  event:{}", channelAdsKey, id, parameterMap.get("conv_action")[0]);

        //根据id查询对应的点击记录
        HuihuiAdsDTO xianyuAdsDTO = xianyuAdsDao.queryXianyuAdsById(id);
        if (null == xianyuAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }
        String callback = xianyuAdsDTO.getCallback();
        String channelUrl = URLDecoder.decode(callback, StandardCharsets.UTF_8);

        Ads2BaiduVO baiduVO = new Ads2BaiduVO();
        baiduVO.setAdsId(id);
        baiduVO.setAdsName(xianyuPath.baseAdsName());
        baiduVO.setChannelUrl(channelUrl);
        baiduVO.setaType(HuihuiEventTypeEnum.huihuiBaiduEventTypeMap.get(parameterMap.get("conv_action")[0]).getCode());
        baiduVO.setaValue(0);
        baiduVO.setCbEventTime(String.valueOf(System.currentTimeMillis()));
        baiduVO.setCbOaid(xianyuAdsDTO.getOaid());
        baiduVO.setCbOaidMd5(xianyuAdsDTO.getOaid_md5());
        baiduVO.setCbIdfa(xianyuAdsDTO.getIdfa());
        baiduVO.setCbImei(xianyuAdsDTO.getImei());
//        baiduVO.setCbImeiMd5(xianyuAdsDTO.getImei_md5());
//        baiduVO.setCbAndroidIdMd5(xianyuAdsDTO.getAndroid_id_md5());
        baiduVO.setCbIp(xianyuAdsDTO.getIp());
        baiduVO.setSecret(BaiduPath.XIANYU_SECRET);
        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, baiduVO);

        Response response = baseAdsCallBack(baiduVO);
        BaiduCallbackDTO data = (BaiduCallbackDTO) response.getData();

        //更新回调状态
        HuihuiAdsDTO xianyuAds = new HuihuiAdsDTO();
        xianyuAds.setId(id);
        xianyuAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));
        if (response.getCode() == 0) {
            xianyuAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(xianyuAds, xianyuAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            xianyuAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(xianyuAds, xianyuAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }

}
