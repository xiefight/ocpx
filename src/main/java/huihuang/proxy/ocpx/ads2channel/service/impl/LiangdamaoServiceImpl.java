package huihuang.proxy.ocpx.ads2channel.service.impl;

import cn.hutool.core.net.URLDecoder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoAdsDTO;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamEnum;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamField;
import huihuang.proxy.ocpx.ads2channel.dao.ILiangdamaoDao;
import huihuang.proxy.ocpx.ads2channel.service.ILiangdamaoService;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import huihuang.proxy.ocpx.middle.baseadsreport.liangdamao.BaseLiangdamaoReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-06-27 09:20
 **/
@Service("ldmService")
public class LiangdamaoServiceImpl extends BaseLiangdamaoReportFactory implements ILiangdamaoService {

    @Autowired
    private ILiangdamaoDao liangdamaoDao;

    private String channelName;

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    @Override
    protected String channelAdsKey() {
        return "liangdamao";
    }

    @Override
    protected String serverPathKey() {
        return Constants.AdsForChannel.LIANGDAMAO;
    }

    @Override
    protected IMarkDao adsDao() {
        return liangdamaoDao;
    }

    @Override
    protected String channelName() {
        return channelName;
    }

    @Override
    public Response clickReport(Map<String, String[]> parameterMap) throws Exception {
        return super.clickReport(parameterMap);
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        LiangdamaoParamField liangdamaoParamField = new LiangdamaoParamField();

        LiangdamaoParamEnum[] values = LiangdamaoParamEnum.values();
        List<String> ldmFields = Arrays.stream(values).map(LiangdamaoParamEnum::getName).collect(Collectors.toList());

        ldmFields.forEach(ldmField -> {
            String[] ldmValue = parameterMap.getOrDefault(ldmField, new String[]{});
            if (Objects.isNull(ldmValue) || ldmValue.length == 0) return;
            if ("null".equals(ldmValue[0]) || "NULL".equals(ldmValue[0])) return;
            if (ldmValue[0].startsWith("__") && ldmValue[0].endsWith("__")) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(ldmField, liangdamaoParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(liangdamaoParamField, ldmValue[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        setChannelName(parameterMap.get("channel_name")[0]);
//        liangdamaoParamField.setAccess_id(LiangdamaoPath.ACCESS_ID);
        return liangdamaoParamField;
    }

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        return null;
    }

    /**
     * 客户将用户行为回调给渠道
     */
    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        logger.info("adsCallBack 开始回调渠道  id:{}  parameterMap.size:{}", id, parameterMap.size());

        //根据id查询对应的点击记录
        LiangdamaoAdsDTO liangdamaoDTO = liangdamaoDao.queryLdmAdsById(id);
        if (null == liangdamaoDTO) {
            logger.error("未根据{}找到对应的监测信息", id);
            return BasicResult.getFailResponse("未找到对应的监测信息 " + id);
        }
        String callback = liangdamaoDTO.getCallback_url();
        String channelUrl = URLDecoder.decode(callback, StandardCharsets.UTF_8);

        String event_type = parameterMap.get("event_type")[0];
        StringBuilder str = new StringBuilder(channelUrl);
        if (str.toString().contains("?")){
            str.append("&event_type=").append(event_type);
        }else {
            str.append("?event_type=").append(event_type);
        }
        logger.info("adsCallBack 回调渠道URL：{}", str);

        HttpResponse response = HttpRequest.get(str.toString()).execute();

        //更新回调状态
        LiangdamaoAdsDTO ldm = new LiangdamaoAdsDTO();
        ldm.setId(id);
        ldm.setCallBackTime(String.valueOf(System.currentTimeMillis()));
        if (HttpStatus.HTTP_OK == response.getStatus()) {
            ldm.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baseServiceInner.updateAdsObject(ldm, liangdamaoDao);
            logger.info("adsCallBack 回调渠道成功");
            return BasicResult.getSuccessResponse(id);
        } else {
            ldm.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baseServiceInner.updateAdsObject(ldm, liangdamaoDao);
            logger.info("adsCallBack 回调渠道失败");
            return BasicResult.getFailResponse(String.valueOf(id));
        }
    }


}
