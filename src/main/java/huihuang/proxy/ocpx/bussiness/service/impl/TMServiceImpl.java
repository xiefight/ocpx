package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.meituan.MeiTuanAdsDTO;
import huihuang.proxy.ocpx.ads.meituan.MeiTuanParamEnum;
import huihuang.proxy.ocpx.ads.meituan.MeiTuanParamField;
import huihuang.proxy.ocpx.bussiness.dao.IMeiTuanAdsDao;
import huihuang.proxy.ocpx.bussiness.dao.IToutiaoCallbackDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.ITMService;
import huihuang.proxy.ocpx.channel.toutiao.ToutiaoCallbackDTO;
import huihuang.proxy.ocpx.channel.toutiao.ToutiaoParamEnum;
import huihuang.proxy.ocpx.channel.toutiao.ToutiaoPath;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.middle.BaseSupport;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.middle.factory.ChannelAdsFactory;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
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
public class TMServiceImpl extends BaseSupport implements ITMService {

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IMeiTuanAdsDao meiTuanAdsDao;
    @Autowired
    private IToutiaoCallbackDao toutiaoCallbackDao;
    @Autowired
    private BaseServiceInner baseServiceInner;

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
            meiTuanParamField.setSource("agroup_bmarketing_conline_dmeituanunion_xinlianlu_roihhmt_ta_5");
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
        meiTuanParamField.setApp_type(baseServiceInner.convertAppType(meiTuanParamField.getApp_type()));

        //保存数据库
        MeiTuanAdsDTO meiTuanAdsDTO = new MeiTuanAdsDTO();
        BeanUtil.copyProperties(meiTuanParamField, meiTuanAdsDTO);
        meiTuanAdsDao.insert(meiTuanAdsDTO);

        //将回调参数替换成我们的，之后美团侧有回调请求，是通知我们
        String ocpxUrl = queryServerPath() + "/tmServer/adsCallBack/" + meiTuanAdsDTO.getId();
        meiTuanParamField.setFeedback_url(ocpxUrl);
        String adsUrl = baseServiceInner.initAdsUrl(meiTuanParamField);
        //调用广告侧美团上报接口
        baseServiceInner.reportAds(meiTuanAdsDTO.getId(), adsUrl);

        return BasicResult.getSuccessResponse(adsUrl);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        //转化类型字段
        String eventType = parameterMap.get("event_type")[0];
        String eventTimes = parameterMap.get("event_time")[0];

        //根据id查询对应的点击记录
        MeiTuanAdsDTO meiTuanAdsDTO = meiTuanAdsDao.queryMeiTuanAdsById(id);
        String feedbackUrl = meiTuanAdsDTO.getFeedback_url();
        //回传到字节
        String channelUrl = ToutiaoPath.BASIC_URI;
//        HttpResponse response = HttpRequest.get(channelUrl).timeout(20000).execute();
        String os = baseServiceInner.convertOs(meiTuanAdsDTO.getApp_type());
        JSONObject json = new JSONObject();
        json.put("callback", feedbackUrl);
        json.put("conv_time", eventTimes);
        json.put("event_type", MeiTuanParamEnum.eventTypeMap.get(eventType));
        json.put("os", os);
        json.put("idfa", meiTuanAdsDTO.getMd5_idfa());
        json.put("oaid", meiTuanAdsDTO.getOaid());
        json.put("imei", meiTuanAdsDTO.getMd5_imei());
        json.put("muid", os.equals("1") ? meiTuanAdsDTO.getMd5_idfa() : meiTuanAdsDTO.getMd5_imei());
        json.put("source", meiTuanAdsDTO.getSource());

        HttpResponse response = HttpRequest.post(channelUrl)
                .timeout(20000).form(json)
                .header("content-type", "application/json")
                .execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);

        //保存转化事件回调信息
        ToutiaoCallbackDTO toutiaoCallbackDTO = new ToutiaoCallbackDTO(id, feedbackUrl, meiTuanAdsDTO.getMd5_imei(),
                meiTuanAdsDTO.getMd5_idfa(), (String) json.get("muid"), meiTuanAdsDTO.getOaid(), null, os, meiTuanAdsDTO.getSource(), eventTimes, eventType);
        //更新回调状态
        MeiTuanAdsDTO meiTuanAds = new MeiTuanAdsDTO();
        meiTuanAds.setId(id);
        meiTuanAds.setCallBackTime(String.valueOf(System.currentTimeMillis()));
        if (HttpStatus.HTTP_OK == response.getStatus() && responseBodyMap.get("code").equals(0)) {
            toutiaoCallbackDTO.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            meiTuanAds.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
        } else {
            toutiaoCallbackDTO.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            meiTuanAds.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
        }
        toutiaoCallbackDTO.setCallBackMes((String) responseBodyMap.get("msg"));
        toutiaoCallbackDao.insert(toutiaoCallbackDTO);
        baseServiceInner.updateMeiTuanAds(meiTuanAds);

        return BasicResult.getSuccessResponse();
    }


}
