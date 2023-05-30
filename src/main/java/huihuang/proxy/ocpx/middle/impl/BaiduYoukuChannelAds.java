package huihuang.proxy.ocpx.middle.impl;

import cn.hutool.core.bean.BeanUtil;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoAdsDTO;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamField;
import huihuang.proxy.ocpx.ads.youku.YoukuPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.IYoukuAdsDao;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.BaiduLiangdamaoReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * baidu--youku
 *
 * @Author: xietao
 * @Date: 2023/5/10 22:28
 */
@Component("byChannelAds")
public class BaiduYoukuChannelAds extends BaiduLiangdamaoReportFactory {

    @Autowired
    private YoukuPath youkuPath;
    @Autowired
    private IYoukuAdsDao youkuAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_YOUKU;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_YOUKU;
    }

    @Override
    protected IMarkDao adsDao() {
        return youkuAdsDao;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        LiangdamaoParamField liangdamaoParamField = (LiangdamaoParamField) super.channelParamToAdsParam(parameterMap);
        liangdamaoParamField.setTp_adv_id(youkuPath.tpAdvId());
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, liangdamaoParamField);
        return liangdamaoParamField;
    }


    @Override
    protected Object saveOriginParamData(Object adsObj) {
        LiangdamaoParamField liangdamaoParamField = (LiangdamaoParamField) adsObj;
        LiangdamaoAdsDTO liangdamaoAdsDTO = new LiangdamaoAdsDTO();
        BeanUtil.copyProperties(liangdamaoParamField, liangdamaoAdsDTO);
        liangdamaoAdsDTO.setChannelName(BaiduPath.BAIDU_CHANNEL_NAME);
        youkuAdsDao.insert(liangdamaoAdsDTO);
        logger.info("clickReport {} 将原始参数保存数据库，返回数据库对象 saveOriginParamData:{}", channelAdsKey(), liangdamaoAdsDTO);
        return liangdamaoAdsDTO;
    }

}
