package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouAdsDTO;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouEventTypeEnum;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IKuaishouAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.bussiness.service.basechannel.IQiyiChannelFactory;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2IQiyiVO;
import huihuang.proxy.ocpx.channel.iqiyi.IQiyiCallbackDTO;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiCallbackDTO;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiPath;
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
import java.util.Set;

/**
 * @Author: xietao
 * @Date: 2023/6/29 21:55
 */
@Service("ikService")
public class IQiyiKuaishouServiceImpl extends IQiyiChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(IQiyiKuaishouServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IKuaishouAdsDao kuaishouAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;

    String channelAdsKey = Constants.ChannelAdsKey.IQIYI_KUAISHOU;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        logger.info("adsCallBack {} 开始回调渠道  id:{}  parameterMap.size:{}", channelAdsKey, id, parameterMap.size());
        //根据id查询对应的点击记录
        KuaishouAdsDTO kuaishouAdsDTO = kuaishouAdsDao.queryKuaishouAdsById(id);
        if (null == kuaishouAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }
        Ads2IQiyiVO iQiyiVO = new Ads2IQiyiVO();
        iQiyiVO.setAdsId(id);
        iQiyiVO.setEventType(KuaishouEventTypeEnum.kuaishouIQiyiEventTypeMap.get(parameterMap.get("actionType")[0]).getCode());
        iQiyiVO.setEventTime(Integer.valueOf(String.valueOf(System.currentTimeMillis() / 1000)));
        iQiyiVO.setChannelUrl(kuaishouAdsDTO.getCallback());

        if (KuaishouPath.IQIYI_KUAISHOU_ADID.equals(kuaishouAdsDTO.getAdid())) {
            iQiyiVO.setAdsName(KuaishouPath.KUAISHOU_ADS_NAME);
        } else if (KuaishouPath.IQIYI_KUAISHOUJISU_ADID.equals(kuaishouAdsDTO.getAdid())) {
            iQiyiVO.setAdsName(KuaishouPath.KUAISHOUJISU_ADS_NAME);
        }

        Response response = super.baseAdsCallBack(iQiyiVO);
        IQiyiCallbackDTO data = (IQiyiCallbackDTO) response.getData();

        //更新回调状态
        KuaishouAdsDTO kuaishouAds = new KuaishouAdsDTO();
        kuaishouAds.setId(id);
        kuaishouAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));

        if (response.getCode() == 0) {
            kuaishouAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(kuaishouAds, kuaishouAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            kuaishouAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(kuaishouAds, kuaishouAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }

    }

    //计算签名
    private String signature(Map<String, Object> json) {
        StringBuilder srcBuilder = new StringBuilder();
        Set<Map.Entry<String, Object>> entries = json.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            String key = entry.getKey();
            Object value = entry.getValue();
            srcBuilder.append(key).append("=").append(value).append("&");
        }
        String src = srcBuilder.substring(0, srcBuilder.length() - 1);
        String signatureStr = src + XiaomiPath.KUAISHOU_SECRET;
        String signature = DigestUtil.md5Hex(signatureStr).toLowerCase();
        json.put("sign", signature);
        logger.info("adsCallBack {} 原始:{}  签名:{}", channelAdsKey, signatureStr, signature);
        return signature;
    }


}
