package huihuang.proxy.ocpx.middle;

import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.common.IConfigService;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.common.ResultStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-21 13:44
 **/
public abstract class BaseSupport {

    protected Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private BaseServiceInner baseServiceInner;
    @Autowired
    private IConfigService configService;

    /**
     * config中查询服务地址
     */
    protected String queryServerPath() {
        return configService.queryServerPath();
    }

    /**
     * 点击上报和回传
     */
    public Response clickReport(Map<String, String[]> parameterMap) throws Exception {
        Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
        for (Map.Entry<String, String[]> entry : entries) {
            logger.info("点击入参： key:{} value:{}", entry.getKey(), entry.getValue());
        }
        //1.将媒体侧请求的监测链接中的参数，转化成广告侧的参数对象
        Object adsObj = channelParamToAdsParam(parameterMap);
        //2.特殊参数进行转换（如设备参数转换）
        convertParams(adsObj);
        //3.对特殊参数进行校验（一些参数不能为空）
        Response response = judgeParams(adsObj);
        if (ResultStatus.success.status != response.getCode()) {
            return response;
        }
        //4.将原始参数保存数据库，返回数据库对象
        Object adsDtoObj = saveOriginParamData(adsObj);
        //5.将回调参数替换成我们的，之后广告侧有回调请求，是通知我们
        replaceCallbackUrl(adsObj, adsDtoObj);
        //6.初始化广告侧请求url
        String adsUrl = initAdsUrl();
        adsUrl = null == adsUrl ? initAdsUrl(adsObj) : adsUrl;
        String completeAdsUrl = baseServiceInner.initAdsUrlAndParam(adsUrl, adsObj);
        //7.调用广告侧上报接口
        return reportAds(completeAdsUrl, adsDtoObj);
    }

    protected abstract Object channelParamToAdsParam(Map<String, String[]> parameterMap);

    protected abstract void convertParams(Object adsObj);

    protected abstract Response judgeParams(Object adsObj) throws Exception;

    protected abstract Object saveOriginParamData(Object adsObj);

    protected abstract void replaceCallbackUrl(Object adsObj, Object adsDtoObj);

    /**
     * 如果不需要判断返回多个url，则直接使用该方法
     */
     protected abstract String initAdsUrl();
    //由于该方法无法根据参数判断具体要使用的url，所以要想实现根据类型返回不同的监测链接，需要配合下面的重载方法，即：子类实现该方法返回null，再重写下面的方法

    //这个方法不强制子类实现，在模板中，先沿用原有的url逻辑，如果原url为空，再使用该方法
    protected String initAdsUrl(Object adsObj){
        return null;
    }

    protected abstract Response reportAds(String adsUrl, Object adsDtoObj) throws Exception;


}
