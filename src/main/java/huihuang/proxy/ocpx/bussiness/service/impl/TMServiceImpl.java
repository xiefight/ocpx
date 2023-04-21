package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.meituan.MeiTuanParamEnum;
import huihuang.proxy.ocpx.ads.meituan.MeiTuanParamField;
import huihuang.proxy.ocpx.bussiness.service.ITMService;
import huihuang.proxy.ocpx.channel.toutiao.ToutiaoParamEnum;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.middle.factory.ChannelAdsFactory;
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
public class TMServiceImpl implements ITMService {

    @Autowired
    private ChannelAdsFactory channelAdsFactory;

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
    public Response clickReport(Map<String, String[]> parameterMap) {
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
            return BasicResult.getFailResponse(MeiTuanParamEnum.SOURCE.getName() + "不能为空");
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

        //保存数据库

        return BasicResult.getSuccessResponse();
    }

}
