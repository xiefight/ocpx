package huihuang.proxy.ocpx.bussiness.service.basechannel;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.bussiness.dao.channel.IBaiduCallbackDao;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2BaiduVO;
import huihuang.proxy.ocpx.channel.baidu.BaiduCallbackDTO;
import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.util.CommonUtil;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @Description: 抽象出baidu渠道的回调，不掺杂广告侧的逻辑，不用管广告侧是谁
 * @Author: xietao
 * @Date: 2023-05-28 09:29
 **/
public class BaiduChannelFactory {

    @Autowired
    private IBaiduCallbackDao baiduCallbackDao;

    protected Response baseAdsCallBack(Ads2BaiduVO baiduVO) throws Exception {

        String channelUrl = baiduVO.getChannelUrl();
//        String callback = URLEncoder.createQuery().encode(feedbackUrl, StandardCharsets.UTF_8);
        channelUrl = channelUrl.replace("a_type={{ATYPE}}", "").replace("a_value={{AVALUE}}", "").replace("&&", "&");
        //回传到渠道
        JSONObject json = new JSONObject();
        json.put("a_type", baiduVO.getaType());
        json.put("a_value", baiduVO.getaValue());
        json.put("cb_event_time", baiduVO.getCbEventTime());
        if (CommonUtil.strEmpty(baiduVO.getCbOaid(), BaiduParamEnum.OAID.getMacro())) {
            json.put("cb_oaid", baiduVO.getCbOaid());
        }
        if (CommonUtil.strEmpty(baiduVO.getCbOaidMd5(), BaiduParamEnum.OAID_MD5.getMacro())) {
            json.put("cb_oaid_md5", baiduVO.getCbOaidMd5());
        }
        if (CommonUtil.strEmpty(baiduVO.getCbIdfa(), BaiduParamEnum.IDFA.getMacro())) {
            json.put("cb_idfa", baiduVO.getCbIdfa());
        }
        if (CommonUtil.strEmpty(baiduVO.getCbImei(), null)) {
            json.put("cb_imei", baiduVO.getCbImei());
        }
        if (CommonUtil.strEmpty(baiduVO.getCbImeiMd5(), BaiduParamEnum.IMEI_MD5.getMacro())) {
            json.put("cb_imei_md5", baiduVO.getCbImeiMd5());
        }
        if (CommonUtil.strEmpty(baiduVO.getCbAndroidIdMd5(), BaiduParamEnum.ANDROID_ID_MD5.getMacro())) {
            json.put("cb_android_id_md5", baiduVO.getCbAndroidIdMd5());
        }
        if (CommonUtil.strEmpty(baiduVO.getCbIp(), BaiduParamEnum.IP.getMacro())) {
            json.put("cb_ip", baiduVO.getCbIp());
        }


        StringBuilder url = new StringBuilder(channelUrl);
        Set<Map.Entry<String, Object>> entries = json.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            url.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
//        String signature = signature(json);
//        url.append("sign=").append(signature);
        HttpResponse response = HttpRequest.get(url.toString()).execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);

        //保存转化事件回调信息
        BaiduCallbackDTO baiduCallbackDTO = new BaiduCallbackDTO(baiduVO.getAdsId(), baiduVO.getEventType(), baiduVO.getCbIdfa(),
                baiduVO.getCbImei(), baiduVO.getCbImeiMd5(), baiduVO.getCbAndroidIdMd5(), baiduVO.getCbIp(), baiduVO.getCbEventTime(), baiduVO.getAdsName());
        if (HttpStatus.HTTP_OK == response.getStatus() && Objects.requireNonNull(responseBodyMap).get("error_code").equals(0)) {
            baiduCallbackDTO.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            baiduCallbackDTO.setCallBackMes(String.valueOf(responseBodyMap.get("code")));
            baiduCallbackDao.insert(baiduCallbackDTO);
            return BasicResult.getSuccessResponse(baiduCallbackDTO);
        } else {
            baiduCallbackDTO.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            baiduCallbackDTO.setCallBackMes(responseBodyMap.get("error_code") + "  " + responseBodyMap.get("error_msg"));
            baiduCallbackDao.insert(baiduCallbackDTO);
            return BasicResult.getFailResponse(baiduCallbackDTO.getCallBackMes(), baiduCallbackDTO);
        }
    }

}
