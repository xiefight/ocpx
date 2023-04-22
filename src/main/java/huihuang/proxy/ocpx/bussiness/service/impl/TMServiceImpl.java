package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import huihuang.proxy.ocpx.ads.meituan.MeiTuanAdsDTO;
import huihuang.proxy.ocpx.ads.meituan.MeiTuanParamEnum;
import huihuang.proxy.ocpx.ads.meituan.MeiTuanParamField;
import huihuang.proxy.ocpx.ads.meituan.MeiTuanPath;
import huihuang.proxy.ocpx.bussiness.dao.IMeiTuanAdsDao;
import huihuang.proxy.ocpx.bussiness.service.ITMService;
import huihuang.proxy.ocpx.channel.toutiao.ToutiaoParamEnum;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.middle.factory.ChannelAdsFactory;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-20 21:33
 **/
@Service
public class TMServiceImpl implements ITMService {

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IMeiTuanAdsDao meiTuanAdsDao;

    @Override
    public Response monitorAddress(Map<String, Object> params) {

        String channel = (String) params.get("channel");
        String ads = (String) params.get("ads");

        String key = channel + "-" + ads;
        IChannelAds channelAds = channelAdsFactory.findChannelAds(key);
        if (Objects.isNull(channelAds)) {
            return BasicResult.getFailResponse("未找到对应渠道：" + channel + " 和广告商：" + ads + "的监测地址");
        }
        String monitorAddress = channelAds.findMonitorAddress();
        if (StrUtil.isEmpty(monitorAddress)) {
            return BasicResult.getFailResponse("生成监测地址失败，渠道：" + channel + " 和广告商：" + ads + "");
        }

        return BasicResult.getSuccessResponse(monitorAddress);
    }

    @Override
    public Response clickReport(Map<String, String[]> parameterMap) throws Exception {
        MeiTuanParamField meiTuanParamField = new MeiTuanParamField();

        Set<Map.Entry<MeiTuanParamEnum, ToutiaoParamEnum>> tmSet = MeiTuanParamEnum.tmMap.entrySet();
        tmSet.stream().filter(tm -> Objects.nonNull(tm.getValue())).forEach(tm -> {
            MeiTuanParamEnum meituan = tm.getKey();
            ToutiaoParamEnum toutiao = tm.getValue();
            //美团的字段名
            String meituanField = meituan.getName();
            //头条的字段名
            String toutiaoParam = toutiao.getParam();
            //头条的参数值
            String[] value = parameterMap.get(toutiaoParam);
            if (Objects.isNull(value) || value.length == 0) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(meituanField, meiTuanParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(meiTuanParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        //对特殊参数进行校验
        if (Objects.isNull(meiTuanParamField.getSource())) {
            meiTuanParamField.setSource("123");
//            return BasicResult.getFailResponse(MeiTuanParamEnum.SOURCE.getName() + "不能为空");
        }
        if (Objects.isNull(meiTuanParamField.getAction_time())) {
            return BasicResult.getFailResponse(MeiTuanParamEnum.ACTION_TIME.getName() + "不能为空");
        }
        if (Objects.isNull(meiTuanParamField.getApp_type())) {
            return BasicResult.getFailResponse(MeiTuanParamEnum.APP_TYPE.getName() + "不能为空");
        }
        if (Objects.isNull(meiTuanParamField.getFeedback_url())) {
            return BasicResult.getFailResponse(MeiTuanParamEnum.FEEDBACK_URL.getName() + "不能为空");
        }
        //如果ios设备为空，则判断安卓设备
        if (Objects.isNull(meiTuanParamField.getMd5_idfa())) {
            if (Objects.isNull(meiTuanParamField.getMd5_imei()) || Objects.isNull(meiTuanParamField.getOaid()) || Objects.isNull(meiTuanParamField.getMd5_oaid())) {
                return BasicResult.getFailResponse(MeiTuanParamEnum.MD5_IMEI.getName() + "、" + MeiTuanParamEnum.OAID.getName() + "、" + MeiTuanParamEnum.MD5_OAID.getName() + "不能同时为空");
            }
        }

        //特殊参数进行转换
        meiTuanParamField.setApp_type(convertAppType(meiTuanParamField.getApp_type()));

        //保存数据库
        MeiTuanAdsDTO meiTuanAdsDTO = new MeiTuanAdsDTO();
        BeanUtil.copyProperties(meiTuanParamField, meiTuanAdsDTO);
        meiTuanAdsDao.insert(meiTuanAdsDTO);

        //将回调参数替换成我们的，之后美团侧有回调请求，是通知我们


        String adsUrl = initAdsUrl(meiTuanParamField);
        //调用广告侧美团上报接口
        reportAds(meiTuanAdsDTO.getId(), adsUrl);

        return BasicResult.getSuccessResponse(adsUrl);
    }


    /**
     * 上报给广告侧
     */
    private void reportAds(Integer id, String adsUrl) throws Exception {
        HttpResponse response = HttpRequest.get(adsUrl).timeout(20000).header("token", "application/json").execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);
        //上报成功
        if (HttpStatus.HTTP_OK == response.getStatus() && responseBodyMap.get("ret").equals(0)) {
            updateReportStatus(id, Constants.ReportStatus.SUCCESS.getCode());
        } else {
            updateReportStatus(id, Constants.ReportStatus.FAIL.getCode());
        }
    }

    /**
     * 更新上报状态
     */
    private void updateReportStatus(Integer id, String status) {
        MeiTuanAdsDTO meiTuanAdsDTO = new MeiTuanAdsDTO();
        meiTuanAdsDTO.setId(id);
        meiTuanAdsDTO.setReportStatus(status);
        int update = meiTuanAdsDao.update(meiTuanAdsDTO);
        System.out.println("update:" + update);
    }

    /**
     * 整理广告侧的url
     *
     * @param meiTuanParamField
     * @return
     */
    private String initAdsUrl(MeiTuanParamField meiTuanParamField) {
        StringBuilder adsUrl = new StringBuilder(MeiTuanPath.BASIC_URI + MeiTuanPath.VERIFY);
        //将参数拼接到url中以发送get请求
        Field[] declaredFields = meiTuanParamField.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            String fieldName = field.getName();
            PropertyDescriptor descriptor;
            try {
                descriptor = new PropertyDescriptor(fieldName, meiTuanParamField.getClass());
                Method getMethod = descriptor.getReadMethod();
                Object fieldValue = getMethod.invoke(meiTuanParamField);
                if (Objects.nonNull(fieldValue)) {
                    adsUrl.append("&").append(fieldName).append("=").append(fieldValue);
                }
            } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return adsUrl.toString();
    }


    private String convertAppType(String os) {
        switch (os) {
            case "0":
                return "android";
            case "1":
                return "ios";
        }
        return "";
    }

}
