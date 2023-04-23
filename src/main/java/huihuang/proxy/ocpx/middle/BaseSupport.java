package huihuang.proxy.ocpx.middle;

import cn.hutool.core.collection.CollUtil;
import huihuang.proxy.ocpx.bussiness.dao.IConfigDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.common.ResultStatus;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-21 13:44
 **/
public abstract class BaseSupport {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IConfigDao configDao;

    @Autowired
    private BaseServiceInner baseServiceInner;

    /**
     * config中查询服务地址
     */
    protected String queryServerPath() {
        String config = configDao.queryConfig();
        Map<String, Object> configMap = CollUtil.newHashMap();
        try {
            configMap = JsonParameterUtil.jsonToMap(config, Exception.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (String) configMap.get("serverPath");
    }

    /**
     * 点击上报和回传
     */
    public Response clickReport(Map<String, String[]> parameterMap) throws Exception {
        //1.将媒体侧请求的监测链接中的参数，转化成广告侧的参数对象
        Object adsObj = channelParamToAdsParam(parameterMap);
        //2.对特殊参数进行校验（一些参数不能为空）
        Response response = judgeParams(adsObj);
        if (ResultStatus.success.status != response.getCode()) {
            return response;
        }
        //3.特殊参数进行转换（如设备参数转换）
        convertParams(adsObj);
        //4.将原始参数保存数据库，返回数据库对象
        Object adsDtoObj = saveOriginParamData(adsObj);
        //5.将回调参数替换成我们的，之后广告侧有回调请求，是通知我们
        replaceCallbackUrl(adsObj, adsDtoObj);
        //6.初始化广告侧请求url
        String adsUrl = initAdsUrl(adsObj, adsDtoObj);
        baseServiceInner.initAdsUrlAndParam(adsUrl, adsObj);
        //7.调用广告侧上报接口
        return reportAds(adsUrl, adsDtoObj);
    }

    protected abstract Object channelParamToAdsParam(Map<String, String[]> parameterMap);

    protected abstract Response judgeParams(Object adsObj) throws Exception;

    protected abstract void convertParams(Object adsObj);

    protected abstract Object saveOriginParamData(Object adsObj);

    protected abstract void replaceCallbackUrl(Object adsObj, Object adsDtoObj);

    protected abstract String initAdsUrl(Object adsObj, Object adsDtoObj);

    protected abstract Response reportAds(String adsUrl, Object adsDtoObj) throws Exception;


    /**
     * 客户侧回调
     */
    public void adsCallBack() {

    }

}
