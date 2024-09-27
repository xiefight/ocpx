package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.net.URLDecoder;
import huihuang.proxy.ocpx.ads.huihui.HuihuiAdsDTO;
import huihuang.proxy.ocpx.ads.huihui.HuihuiEventTypeEnum;
import huihuang.proxy.ocpx.ads.huihui.xianyu.HuihuiXianyuPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IHuihuiXianyuAdsDao;
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
public class BaiduHuihuiXianyuServiceImpl extends BaiduChannelFactory implements IChannelAdsService {

    protected Logger logger = LoggerFactory.getLogger(BaiduHuihuiXianyuServiceImpl.class);

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IHuihuiXianyuAdsDao xianyuAdsDao;
    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private HuihuiXianyuPath huihuiXianyuPath;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_HUIHUI_XIANYU;

    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(channelAdsKey);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        String eventType = parameterMap.get("conv_action")[0];
        logger.info("adsCallBack {} 开始回调渠道  id:{}  event:{}", channelAdsKey, id, eventType);

        //根据id查询对应的点击记录
        HuihuiAdsDTO xianyuAdsDTO = xianyuAdsDao.queryXianyuAdsById(id);
        if (null == xianyuAdsDTO) {
            logger.error("{} 未根据{}找到对应的监测信息", channelAdsKey, id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }
        String callback = xianyuAdsDTO.getCallback();
        String channelUrl = URLDecoder.decode(callback, StandardCharsets.UTF_8);

        String ocpxAccount = xianyuAdsDTO.getOcpxAccount();
        //针对闲鱼aid=36490(原33936)的户，闲鱼注册事件对应百度激活事件，这里特殊处理一下，而闲鱼的激活事件暂时不处理
        //通过账户进行特殊处理，不再通过aid进行特殊处理
//        if ("36490".equals(xianyuAdsDTO.getAid())) {
        if (BaiduPath.BAIDU_XIANYU_ACCOUNT_02.equals(ocpxAccount)
                || BaiduPath.BAIDU_XIANYU_ACCOUNT_03.equals(ocpxAccount)
                || BaiduPath.BAIDU_XIANYU_ACCOUNT_06.equals(ocpxAccount)
                || BaiduPath.BAIDU_XIANYU_ACCOUNT_07.equals(ocpxAccount)) {
            if (eventType.equals(HuihuiEventTypeEnum.ANDROID_ACTIVATE.getCode())
                    || eventType.equals(HuihuiEventTypeEnum.IOS_ACTIVATE.getCode())) {
                return BasicResult.getFailResponse(ocpxAccount + "的闲鱼户不需要回传激活事件:" + id);
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
        baiduVO.setAdsName(huihuiXianyuPath.baseAdsName());
        baiduVO.setChannelUrl(channelUrl);
        baiduVO.setaType(HuihuiEventTypeEnum.huihuiBaiduEventTypeMap.get(eventType).getCode());
        baiduVO.setaValue(0);
        baiduVO.setCbEventTime(String.valueOf(System.currentTimeMillis()));
        baiduVO.setCbOaid(xianyuAdsDTO.getOaid());
        baiduVO.setCbOaidMd5(xianyuAdsDTO.getOaid_md5());
        baiduVO.setCbIdfa(xianyuAdsDTO.getIdfa());
        baiduVO.setCbImei(xianyuAdsDTO.getImei());
//        baiduVO.setCbImeiMd5(xianyuAdsDTO.getImei_md5());
//        baiduVO.setCbAndroidIdMd5(xianyuAdsDTO.getAndroid_id_md5());
        baiduVO.setCbIp(xianyuAdsDTO.getIp());


        if (BaiduPath.BAIDU_XIANYU_ACCOUNT_01.equals(ocpxAccount)) {
            baiduVO.setSecret(BaiduPath.XIANYU_01_SECRET);
        } else if (BaiduPath.BAIDU_XIANYU_ACCOUNT_02.equals(ocpxAccount)) {
            baiduVO.setSecret(BaiduPath.XIANYU_02_SECRET);
        } else if (BaiduPath.BAIDU_XIANYU_ACCOUNT_03.equals(ocpxAccount)) {
            baiduVO.setSecret(BaiduPath.XIANYU_03_SECRET);
        } else if (BaiduPath.BAIDU_XIANYU_ACCOUNT_04.equals(ocpxAccount)) {
            baiduVO.setSecret(BaiduPath.XIANYU_04_SECRET);
        } else if (BaiduPath.BAIDU_XIANYU_ACCOUNT_05.equals(ocpxAccount)) {
            baiduVO.setSecret(BaiduPath.XIANYU_05_SECRET);
        } else if (BaiduPath.BAIDU_XIANYU_ACCOUNT_06.equals(ocpxAccount)) {
            baiduVO.setSecret(BaiduPath.XIANYU_06_SECRET);
        } else if (BaiduPath.BAIDU_XIANYU_ACCOUNT_07.equals(ocpxAccount)) {
            baiduVO.setSecret(BaiduPath.XIANYU_07_SECRET);
        } else if (BaiduPath.BAIDU_XIANYU_ACCOUNT_08.equals(ocpxAccount)) {
            baiduVO.setSecret(BaiduPath.XIANYU_08_SECRET);
        } else if (BaiduPath.BAIDU_XIANYUSOUSUO_ACCOUNT_01.equals(ocpxAccount)) {
            baiduVO.setSecret(BaiduPath.XIANYUSOUSUO_01_SECRET);
        } else if (BaiduPath.BAIDU_XIANYUSOUSUO_ACCOUNT_02.equals(ocpxAccount)) {
            baiduVO.setSecret(BaiduPath.XIANYUSOUSUO_02_SECRET);
        } else if (BaiduPath.BAIDU_XIANYUSOUSUO_ACCOUNT_03.equals(ocpxAccount)) {
            baiduVO.setSecret(BaiduPath.XIANYUSOUSUO_03_SECRET);
        } else {
            //默认
            baiduVO.setSecret(BaiduPath.XIANYU_01_SECRET);
        }


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
