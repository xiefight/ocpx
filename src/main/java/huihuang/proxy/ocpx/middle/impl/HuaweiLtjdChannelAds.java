package huihuang.proxy.ocpx.middle.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoAdsDTO;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.ILtjdAdsDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.channel.huawei.HuaweiParamEnum;
import huihuang.proxy.ocpx.channel.huawei.HuaweiPath;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.HuaweiLiangdamaoReportFactory;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * huawei-jingdong
 *
 * @Author: xietao
 * @Date: 2023/5/28 16:02
 */
@Component("hjChannelAds")
public class HuaweiLtjdChannelAds extends HuaweiLiangdamaoReportFactory {

    @Autowired
    private ILtjdAdsDao ltjdAdsDao;

    String channelAdsKey = Constants.ChannelAdsKey.HUAWEI_LTJD;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.HUAWEI_LTJD;
    }

    @Override
    protected IMarkDao adsDao() {
        return ltjdAdsDao;
    }


    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        LiangdamaoParamField liangdamaoParamField = (LiangdamaoParamField) super.channelParamToAdsParam(parameterMap);
        //存储华为这边必有而广告侧（快手、粮大猫）这不必有的参数，回传可能会用到
        String extras = fitExtras(parameterMap,
                HuaweiParamEnum.CONTENT_ID.getParam(),
                HuaweiParamEnum.EVENT_TYPE.getParam(),
                HuaweiParamEnum.TRACE_TIME.getParam(),
                HuaweiParamEnum.TRACKING_ENABLED.getParam(),
                HuaweiParamEnum.CAMPAIGN_ID.getParam());
        if (extras.length() > 0) {
            liangdamaoParamField.setExtra(extras);
        }
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, liangdamaoParamField);
        return liangdamaoParamField;
    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        LiangdamaoParamField liangdamaoParamField = (LiangdamaoParamField) adsObj;
        LiangdamaoAdsDTO liangdamaoAdsDTO = new LiangdamaoAdsDTO();
        BeanUtil.copyProperties(liangdamaoParamField, liangdamaoAdsDTO);
        liangdamaoAdsDTO.setChannelName(HuaweiPath.HUAWEI_CHANNEL_NAME);
        ltjdAdsDao.insert(liangdamaoAdsDTO);
        logger.info("clickReport {} 将原始参数保存数据库，返回数据库对象 saveOriginParamData:{}", channelAdsKey, liangdamaoAdsDTO);
        return liangdamaoAdsDTO;
    }

}
