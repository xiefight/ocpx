package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.net.URLDecoder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.ads.meituan.MeiTuanAdsDTO;
import huihuang.proxy.ocpx.ads.meituan.MeiTuanEventTypeEnum;
import huihuang.proxy.ocpx.bussiness.dao.ads.IMeiTuanAdsMarkDao;
import huihuang.proxy.ocpx.bussiness.dao.channel.IToutiaoCallbackDao;
import huihuang.proxy.ocpx.bussiness.service.BaseServiceInner;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.channel.toutiao.ToutiaoCallbackDTO;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.middle.factory.ChannelAdsFactory;
import huihuang.proxy.ocpx.util.CommonUtil;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-20 21:33
 **/
@Service("tmService")
public class TMServiceImpl implements IChannelAdsService {

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IMeiTuanAdsMarkDao meiTuanAdsDao;
    @Autowired
    private IToutiaoCallbackDao toutiaoCallbackDao;
    @Autowired
    private BaseServiceInner baseServiceInner;


    @Override
    public IChannelAds channelAds() {
        return channelAdsFactory.findChannelAds(Constants.ChannelAdsKey.TOUTIAO_MEITUAN);
    }

    @Override
    public Response adsCallBack(Integer id, Map<String, String[]> parameterMap) throws Exception {
        //转化类型字段
        String eventType = parameterMap.get("event_type")[0];
        String eventTimes = parameterMap.get("event_time")[0];

        //根据id查询对应的点击记录
        MeiTuanAdsDTO meiTuanAdsDTO = meiTuanAdsDao.queryMeiTuanAdsById(id);
        String feedbackUrl = meiTuanAdsDTO.getFeedback_url();
        String callbackUrl = URLDecoder.decode(feedbackUrl, StandardCharsets.UTF_8);

        String[] split = callbackUrl.split("\\?");
        String channelUrl = split[0];
        Map<String, String> paramMap = CommonUtil.convertGetParamToMap(split[1]);
        //回传到字节
        String os = baseServiceInner.convertOs(meiTuanAdsDTO.getApp_type());
        JSONObject json = new JSONObject();
        json.put("callback", paramMap.get("callback"));
        json.put("conv_time", eventTimes);
        json.put("event_type", MeiTuanEventTypeEnum.eventTypeMap.get(eventType).getCode());
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
        baseServiceInner.updateAdsObject(meiTuanAds, meiTuanAdsDao);

        return BasicResult.getSuccessResponse();
    }

}
