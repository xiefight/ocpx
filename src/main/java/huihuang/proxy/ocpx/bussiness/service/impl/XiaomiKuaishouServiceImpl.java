package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouAdsDTO;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouEventTypeEnum;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IKuaishouAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.bussiness.service.basechannel.XiaomiChannelFactory;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2XiaomiVO;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiCallbackDTO;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.KuaishouResponse;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.middle.factory.ChannelAdsFactory;
import huihuang.proxy.ocpx.util.tuple.Tuple2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * xiaomi-kuaishou
 *
 * @Author: xietao
 * @Date: 2023/5/16 11:47
 */
@Service("xkService")
public class XiaomiKuaishouServiceImpl extends XiaomiChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(XiaomiKuaishouServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IKuaishouAdsDao kuaishouAdsDao;
    @Autowired
    @Qualifier("kuaishouAdsXiaomiDao")
    private IKuaishouAdsDao kuaishouAdsXiaomiDao;
    @Autowired
    private BaseServiceInner baseServiceInner;

    String channelAdsKey = Constants.ChannelAdsKey.XIAOMI_KUAISHOU;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public KuaishouResponse adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        logger.info("adsCallBack {} 开始回调渠道  id:{}  parameterMap.size:{}", channelAdsKey, id, parameterMap.size());
        //根据id查询对应的点击记录
        Tuple2<IMarkDao, KuaishouAdsDTO> tuple2 = baseServiceInner.querySplitAdsObject(kuaishouAdsXiaomiDao, kuaishouAdsDao, "queryKuaishouAdsById", KuaishouAdsDTO.class, id);
        if (null == tuple2) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return new KuaishouResponse(1, "未根据找到对应的监测信息", id);
        }
        IKuaishouAdsDao ikuaishouAdsDao = (IKuaishouAdsDao) tuple2.getT1();
        KuaishouAdsDTO kuaishouAdsDTO = tuple2.getT2();

        Ads2XiaomiVO xiaomiVO = new Ads2XiaomiVO();

        //转化类型字段
        String eventType = parameterMap.get("actionType")[0];
        String eventTimes = String.valueOf(System.currentTimeMillis());

        xiaomiVO.setAdsId(id);
        xiaomiVO.setCallBackUrl(kuaishouAdsDTO.getCallback());
        xiaomiVO.setEventTimes(eventTimes);
        xiaomiVO.setEventType(KuaishouEventTypeEnum.kuaishouXiaomiEventTypeMap.get(eventType).getCode());
        if (Objects.isNull(kuaishouAdsDTO.getOaid())) {
            xiaomiVO.setImei(kuaishouAdsDTO.getImei());
        } else {
            xiaomiVO.setOaid(kuaishouAdsDTO.getOaid());
        }
//        if (KuaishouPath.XIAOMI_KUAISHOU_ADID.equals(kuaishouAdsDTO.getAdid())) {
        if (KuaishouPath.KUAISHOU_ADID6.equals(kuaishouAdsDTO.getAdid())) {
            xiaomiVO.setAdsName(KuaishouPath.KUAISHOU_ADS_NAME);
            xiaomiVO.setSecret(XiaomiPath.KUAISHOU_SECRET);
//        } else if (KuaishouPath.XIAOMI_KUAISHOUJISU_ADID.equals(kuaishouAdsDTO.getAdid())) {
        } else if (KuaishouPath.KUAISHOUJISU_ADID6.equals(kuaishouAdsDTO.getAdid())) {
            xiaomiVO.setAdsName(KuaishouPath.KUAISHOUJISU_ADS_NAME);
            xiaomiVO.setSecret(XiaomiPath.KUAISHOUJISU_SECRET);
        }

        Response response = super.baseAdsCallBack(xiaomiVO);
        XiaomiCallbackDTO data = (XiaomiCallbackDTO) response.getData();

        //更新回调状态
        KuaishouAdsDTO kuaishouAds = new KuaishouAdsDTO();
        kuaishouAds.setId(id);
        kuaishouAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));
        if (response.getCode() == 0) {
            kuaishouAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(kuaishouAds, ikuaishouAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return new KuaishouResponse(0, "", data.getId());
        } else {
            kuaishouAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(kuaishouAds, ikuaishouAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return new KuaishouResponse(1, data.getCallBackMes(), data.getId());
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
