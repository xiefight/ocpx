package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.net.URLDecoder;
import cn.hutool.crypto.digest.DigestUtil;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouAdsDTO;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouEventTypeEnum;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IKuaishouAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.bussiness.service.basechannel.BaiduChannelFactory;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2BaiduVO;
import huihuang.proxy.ocpx.channel.baidu.BaiduCallbackDTO;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
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

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

/**
 * baidu-kuaishou
 *
 * @Author: xietao
 * @Date: 2023/5/15 20:28
 */
@Service("bkService")
public class BaiduKuaishouServiceImpl extends BaiduChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(BaiduKuaishouServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IKuaishouAdsDao kuaishouAdsDao;
    @Autowired
    @Qualifier("kuaishouAdsBaiduDao")
    private IKuaishouAdsDao kuaishouAdsBaiduDao;
    @Autowired
    private BaseServiceInner baseServiceInner;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_KUAISHOU;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        logger.info("adsCallBack {} 开始回调渠道  id:{}  parameterMap.size:{}", channelAdsKey, id, parameterMap.size());
        //转化类型字段
        String eventType = parameterMap.get("actionType")[0];
        //根据id查询对应的点击记录
        Tuple2<IMarkDao, KuaishouAdsDTO> tuple2 = baseServiceInner.querySplitAdsObject(kuaishouAdsBaiduDao, kuaishouAdsDao, "queryKuaishouAdsById", KuaishouAdsDTO.class, id);

        if (null == tuple2) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }
        IKuaishouAdsDao ikuaishouAdsDao = (IKuaishouAdsDao) tuple2.getT1();
        KuaishouAdsDTO kuaishouAdsDTO = tuple2.getT2();

        String callback = kuaishouAdsDTO.getCallback();
        String channelUrl = URLDecoder.decode(callback, StandardCharsets.UTF_8);

        Ads2BaiduVO baiduVO = new Ads2BaiduVO();
        baiduVO.setAdsId(id);

        baiduVO.setChannelUrl(channelUrl);
        baiduVO.setaType(KuaishouEventTypeEnum.kuaishouBaiduEventTypeMap.get(eventType).getCode());
        baiduVO.setaValue(0);
        baiduVO.setCbEventTime(String.valueOf(System.currentTimeMillis()));
        baiduVO.setCbOaid(kuaishouAdsDTO.getOaid());
        baiduVO.setCbOaidMd5(kuaishouAdsDTO.getOaid());
        baiduVO.setCbIdfa(kuaishouAdsDTO.getIdfa());
        baiduVO.setCbImei(kuaishouAdsDTO.getImei());
        baiduVO.setCbImeiMd5(kuaishouAdsDTO.getImei());
        baiduVO.setCbAndroidIdMd5(kuaishouAdsDTO.getAndroidId());
        baiduVO.setCbIp(kuaishouAdsDTO.getIp());
        if (KuaishouPath.BAIDU_KUAISHOU_ADID.equals(kuaishouAdsDTO.getAdid())) {
            baiduVO.setAdsName(KuaishouPath.KUAISHOU_ADS_NAME);
            if (BaiduPath.BAIDU_KUAISHOU_ACCOUNT_01.equals(kuaishouAdsDTO.getAccountId())) {
                baiduVO.setSecret(BaiduPath.KUAISHOU_21666_01_SECRET);
            } else {
                baiduVO.setSecret(BaiduPath.KUAISHOU_21666_SECRET);
            }
        } else if (KuaishouPath.KUAISHOUJISU_ADID6.equals(kuaishouAdsDTO.getAdid())) {
            baiduVO.setAdsName(KuaishouPath.KUAISHOUJISU_ADS_NAME);
            baiduVO.setSecret(BaiduPath.KUAISHOUJISU_SECRET);
        } else if (KuaishouPath.KUAISHOU_ADID_21749.equals(kuaishouAdsDTO.getAdid())) {
            baiduVO.setAdsName(KuaishouPath.KUAISHOU_ADS_NAME);
            baiduVO.setSecret(BaiduPath.KUAISHOU_7_SECRET);
        } else if (KuaishouPath.KUAISHOUJISU_ADID_21756.equals(kuaishouAdsDTO.getAdid())) {
            baiduVO.setAdsName(KuaishouPath.KUAISHOUJISU_ADS_NAME);
            if (kuaishouAdsDTO.getAccountId().equals(BaiduPath.BAIDU_KUAISHOUJISU_ACCOUNT_01)) {
                baiduVO.setSecret(BaiduPath.KUAISHOUJISU_7_01_SECRET);
            } else {
                baiduVO.setSecret(BaiduPath.KUAISHOUJISU_7_SECRET);
            }
        }
        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, baiduVO);

        Response response = baseAdsCallBack(baiduVO);
        BaiduCallbackDTO data = (BaiduCallbackDTO) response.getData();

        //更新回调状态
        KuaishouAdsDTO kuaishouAds = new KuaishouAdsDTO();
        kuaishouAds.setId(id);
        kuaishouAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));
        if (response.getCode() == 0) {
            kuaishouAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(kuaishouAds, ikuaishouAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            kuaishouAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(kuaishouAds, ikuaishouAdsDao);
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
        String signatureStr = src + BaiduPath.KUAISHOU_21666_SECRET;
        String signature = DigestUtil.md5Hex(signatureStr).toLowerCase();
        json.put("sign", signature);
        logger.info("adsCallBack {} 原始:{}  签名:{}", channelAdsKey, signatureStr, signature);
        return signature;
    }
}
