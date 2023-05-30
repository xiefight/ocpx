package huihuang.proxy.ocpx.middle.impl;

import cn.hutool.core.bean.BeanUtil;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoAdsDTO;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamField;
import huihuang.proxy.ocpx.ads.litianjingdong.LTJDPath;
import huihuang.proxy.ocpx.bussiness.dao.ads.ILtjdAdsDao;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.BaiduLiangdamaoReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * baidu--ltjd
 *
 * @Author: xietao
 * @Date: 2023/5/10 22:28
 */
@Component("bjChannelAds")
public class BaiduLtjdChannelAds extends BaiduLiangdamaoReportFactory {

    @Autowired
    private LTJDPath ltjdPath;
    @Autowired
    private ILtjdAdsDao ltjdAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_LTJD;


    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_LTJD;
    }

    @Override
    protected IMarkDao adsDao() {
        return ltjdAdsDao;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        LiangdamaoParamField liangdamaoParamField = (LiangdamaoParamField) super.channelParamToAdsParam(parameterMap);
        liangdamaoParamField.setTp_adv_id(ltjdPath.tpAdvId());
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, liangdamaoParamField);
        return liangdamaoParamField;
    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        LiangdamaoParamField liangdamaoParamField = (LiangdamaoParamField) adsObj;
        LiangdamaoAdsDTO liangdamaoAdsDTO = new LiangdamaoAdsDTO();
        BeanUtil.copyProperties(liangdamaoParamField, liangdamaoAdsDTO);
        liangdamaoAdsDTO.setChannelName(BaiduPath.BAIDU_CHANNEL_NAME);
        ltjdAdsDao.insert(liangdamaoAdsDTO);
        logger.info("clickReport {} 将原始参数保存数据库，返回数据库对象 saveOriginParamData:{}", channelAdsKey(), liangdamaoAdsDTO);
        return liangdamaoAdsDTO;
    }

}
