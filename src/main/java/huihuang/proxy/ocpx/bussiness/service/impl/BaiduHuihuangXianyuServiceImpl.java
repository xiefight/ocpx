package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.net.URLDecoder;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangFengmangEventTypeEnum;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianAdsDTO;
import huihuang.proxy.ocpx.ads.huihuangmingtian.ads.HuihuangFengmangXianyuPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuangFengmangXianyuAdsDao;
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

@Service("bhhxyService")
public class BaiduHuihuangXianyuServiceImpl extends BaiduChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(BaiduHuihuangXianyuServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IHuihuangFengmangXianyuAdsDao hhxyAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private HuihuangFengmangXianyuPath hhxyPath;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_HUIHUANG_XIANYU;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("event_type")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  eventType:{}", channelAdsKey, id, eventType);

        //根据id查询对应的点击记录
        HuihuangmingtianAdsDTO hhxyAdsDTO = hhxyAdsDao.queryHuihuangFengmangXianyuAdsById(id);
        if (null == hhxyAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }

        //百度-闲鱼1&4户 ：注册对应注册人数（激活不需要回传）
        //百度-闲鱼2&3户：注册对应激活人数（激活也不需要回传）

        if (!BaiduPath.BAIDU_HUIHUANG_XIANYU_ACCOUNT_07.equals(hhxyAdsDTO.getAccountId()) &&
                !BaiduPath.BAIDU_HUIHUANG_XIANYU_ACCOUNT_08.equals(hhxyAdsDTO.getAccountId()) &&
                eventType.equals(HuihuangFengmangEventTypeEnum.ACTIVATE.getCode())) {
            logger.error("{} 百度-闲鱼 {} 不需要激活事件 {}", channelAdsKey, hhxyAdsDTO.getAccountId(), id);
            return BasicResult.getFailResponse("百度-闲鱼不需要激活事件 " + id);
        }
        /*if (BaiduPath.BAIDU_HUIHUANG_XIANYU_ACCOUNT_01.equals(hhxyAdsDTO.getAccountId())
                || BaiduPath.BAIDU_HUIHUANG_XIANYU_ACCOUNT_03.equals(hhxyAdsDTO.getAccountId())) {
            if (eventType.equals(HuihuangFengmangEventTypeEnum.ACTIVATE.getCode())) {
                logger.error("{} 百度-闲鱼 {} 不需要激活事件 {}", channelAdsKey, hhxyAdsDTO.getAccountId(), id);
                return BasicResult.getFailResponse("小米-闲鱼不需要激活事件 " + id);
            }
        }*/

        if (BaiduPath.BAIDU_HUIHUANG_XIANYU_ACCOUNT_05.equals(hhxyAdsDTO.getAccountId()) ||
                BaiduPath.BAIDU_HUIHUANG_XIANYU_ACCOUNT_06.equals(hhxyAdsDTO.getAccountId())) {
            if (eventType.equals(HuihuangFengmangEventTypeEnum.REGISTER.getCode())) {
                eventType = HuihuangFengmangEventTypeEnum.ACTIVATE.getCode();
            }
        }

        String callback = hhxyAdsDTO.getCallbackUrl();
        String channelUrl = URLDecoder.decode(callback, StandardCharsets.UTF_8);

        Ads2BaiduVO baiduVO = new Ads2BaiduVO();
        baiduVO.setAdsId(id);
        baiduVO.setAdsName(hhxyPath.baseAdsName());
        baiduVO.setChannelUrl(channelUrl);
        baiduVO.setaType(HuihuangFengmangEventTypeEnum.huihuangmingtianBaiduEventTypeMap.get(eventType).getCode());
        baiduVO.setaValue(0);
        baiduVO.setCbEventTime(String.valueOf(System.currentTimeMillis()));
        baiduVO.setCbOaid(hhxyAdsDTO.getOaid());
        baiduVO.setCbOaidMd5(hhxyAdsDTO.getOaidMd5());
        baiduVO.setCbIdfa(hhxyAdsDTO.getIdfa());
        baiduVO.setCbImei(null);
        baiduVO.setCbImeiMd5(hhxyAdsDTO.getImeiMd5());
        baiduVO.setCbAndroidIdMd5(null);
        baiduVO.setCbIp(hhxyAdsDTO.getIp());

        if (BaiduPath.BAIDU_HUIHUANG_XIANYU_ACCOUNT_01.equals(hhxyAdsDTO.getAccountId())) {
            baiduVO.setSecret(BaiduPath.HUIHUANG_XIANYU_01_SECRET);
        } else if (BaiduPath.BAIDU_HUIHUANG_XIANYU_ACCOUNT_02.equals(hhxyAdsDTO.getAccountId())) {
            baiduVO.setSecret(BaiduPath.HUIHUANG_XIANYU_02_SECRET);
        } else if (BaiduPath.BAIDU_HUIHUANG_XIANYU_ACCOUNT_03.equals(hhxyAdsDTO.getAccountId())) {
            baiduVO.setSecret(BaiduPath.HUIHUANG_XIANYU_03_SECRET);
        } else if (BaiduPath.BAIDU_HUIHUANG_XIANYU_ACCOUNT_04.equals(hhxyAdsDTO.getAccountId())) {
            baiduVO.setSecret(BaiduPath.HUIHUANG_XIANYU_04_SECRET);
        } else if (BaiduPath.BAIDU_HUIHUANG_XIANYU_ACCOUNT_05.equals(hhxyAdsDTO.getAccountId())) {
            baiduVO.setSecret(BaiduPath.HUIHUANG_XIANYU_05_SECRET);
        } else if (BaiduPath.BAIDU_HUIHUANG_XIANYU_ACCOUNT_06.equals(hhxyAdsDTO.getAccountId())) {
            baiduVO.setSecret(BaiduPath.HUIHUANG_XIANYU_06_SECRET);
        } else if (BaiduPath.BAIDU_HUIHUANG_XIANYU_ACCOUNT_07.equals(hhxyAdsDTO.getAccountId())) {
            baiduVO.setSecret(BaiduPath.HUIHUANG_XIANYU_07_SECRET);
        } else if (BaiduPath.BAIDU_HUIHUANG_XIANYU_ACCOUNT_08.equals(hhxyAdsDTO.getAccountId())) {
            baiduVO.setSecret(BaiduPath.HUIHUANG_XIANYU_08_SECRET);
        } else if (BaiduPath.BAIDU_HUIHUANG_XIANYUSOUSUO_ACCOUNT_01.equals(hhxyAdsDTO.getAccountId())) {
            baiduVO.setSecret(BaiduPath.HUIHUANG_XIANYUSOUSUO_01_SECRET);
        } else if (BaiduPath.BAIDU_HUIHUANG_XIANYUSOUSUO_ACCOUNT_02.equals(hhxyAdsDTO.getAccountId())) {
            baiduVO.setSecret(BaiduPath.HUIHUANG_XIANYUSOUSUO_02_SECRET);
        } else if (BaiduPath.BAIDU_HUIHUANG_XIANYUSOUSUO_ACCOUNT_03.equals(hhxyAdsDTO.getAccountId())) {
            baiduVO.setSecret(BaiduPath.HUIHUANG_XIANYUSOUSUO_03_SECRET);
        }


        logger.info("adsCallBack {} 组装调用渠道参数:{}", channelAdsKey, baiduVO);

        Response response = baseAdsCallBack(baiduVO);
        BaiduCallbackDTO data = (BaiduCallbackDTO) response.getData();

        //更新回调状态
        HuihuangmingtianAdsDTO huihuangmingtianAds = new HuihuangmingtianAdsDTO();
        huihuangmingtianAds.setId(id);
        huihuangmingtianAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));
        if (response.getCode() == 0) {
            huihuangmingtianAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(huihuangmingtianAds, hhxyAdsDao);
            logger.info("adsCallBack {} 回调渠道成功：{}", channelAdsKey, data);
            return BasicResult.getSuccessResponse(data.getId());
        } else {
            huihuangmingtianAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(huihuangmingtianAds, hhxyAdsDao);
            logger.info("adsCallBack {} 回调渠道失败：{}", channelAdsKey, data);
            return BasicResult.getFailResponse(data.getCallBackMes());
        }
    }

}
