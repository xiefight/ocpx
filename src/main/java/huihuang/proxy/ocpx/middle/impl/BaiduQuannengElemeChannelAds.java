package huihuang.proxy.ocpx.middle.impl;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongAdsDTO;
import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.IQuannengElemeAdsDao;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.quannenghudong.BaiduQuannengHudongReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("bqelemeChannelAds")
public class BaiduQuannengElemeChannelAds extends BaiduQuannengHudongReportFactory {

    private static String REGEX = "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$";
    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_QUANNENG_ELEME;

    @Autowired
    private IQuannengElemeAdsDao elemeAdsDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_QUANNENG_ELEME;
    }

    @Override
    protected String channelName() {
        return BaiduPath.BAIDU_CHANNEL_NAME;
    }

    @Override
    protected IMarkDao adsDao() {
        return elemeAdsDao;
    }

    @Override
    protected Object saveOriginParamData(Object adsObj) {
        QuannengHudongParamField quannengHudongParamField = (QuannengHudongParamField) adsObj;
//        quannengHudongParamField.setPid("865");
        String ip = quannengHudongParamField.getIp();
        logger.info("saveOriginParamData 当前ip是：{}", ip);
        if (StrUtil.isNotEmpty(ip) && ip.length() <= 16) {
            logger.info("saveOriginParamData ip地址格式正确，进行上报");
            return super.saveOriginParamData(quannengHudongParamField);
        } else {
            logger.info("saveOriginParamData ip地址格式不正确，不进行上报");
            return adsObj;
        }
    }

    @Override
    protected void replaceCallbackUrl(Object adsObj, Object adsDtoObj) {
        QuannengHudongParamField quannengHudongParamField = (QuannengHudongParamField) adsObj;
        String ip = quannengHudongParamField.getIp();
        logger.info("replaceCallbackUrl 当前ip是：{}", ip);
        if (StrUtil.isNotEmpty(ip) && ip.length() <= 16) {
            logger.info("replaceCallbackUrl ip地址格式正确，进行上报");
            super.replaceCallbackUrl(adsObj, adsDtoObj);
        } else {
            logger.info("replaceCallbackUrl ip地址格式不正确，不进行上报");
        }
    }


    @Override
    protected Response reportAds(String adsUrl, Object adsDtoObj) throws Exception {
        try {
            QuannengHudongAdsDTO quannengHudongAdsDTO = (QuannengHudongAdsDTO) adsDtoObj;
            String ip = quannengHudongAdsDTO.getIp();
            logger.info("reportAds 当前ip是：{}", ip);
            if (StrUtil.isNotEmpty(ip) && ip.length() <= 16) {
                logger.info("reportAds ip地址格式正确，进行上报");
                return super.reportAds(adsUrl, quannengHudongAdsDTO);
            } else {
                logger.info("reportAds ip地址格式不正确，不进行上报");
                return BasicResult.getFailResponse("ip地址格式不正确，不进行上报", 0);
            }
        } catch (Exception e) {
            logger.info("reportAds ip地址格式不正确，不进行上报");
            return BasicResult.getFailResponse("reportAds ip地址格式不正确，不进行上报", 0);
        }

    }

    public static void main(String[] args) {
//        String ip = "112.137.235.150";
//        System.out.println(REGEX.matches(ip));

        String ip = "223.104.124.176";
        if (StrUtil.isNotEmpty(ip) && ip.length() <= 16) {
            System.out.println("ip地址格式正确，进行上报");
        } else {
            System.out.println("ip地址格式不正确，不进行上报");
        }

    }


}
