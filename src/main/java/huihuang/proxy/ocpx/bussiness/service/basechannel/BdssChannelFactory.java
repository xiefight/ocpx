package huihuang.proxy.ocpx.bussiness.service.basechannel;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import huihuang.proxy.ocpx.bussiness.dao.channel.IBdssCallbackDao;
import huihuang.proxy.ocpx.bussiness.service.basechannel.vo.Ads2BaiduVO;
import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.channel.bdss.BdssCallbackDTO;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.util.CommonUtil;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @Description: 抽象出baidu渠道的回调，不掺杂广告侧的逻辑，不用管广告侧是谁
 * @Author: xietao
 * @Date: 2023-05-28 09:29
 **/
public abstract class BdssChannelFactory {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IBdssCallbackDao bdssCallbackDao;

    protected Response baseAdsCallBack(Ads2BaiduVO baiduVO) throws Exception {

        String channelUrl = baiduVO.getChannelUrl();
//        String callback = URLEncoder.createQuery().encode(feedbackUrl, StandardCharsets.UTF_8);
        channelUrl = channelUrl.replace("a_type={{ATYPE}}", "").replace("a_value={{AVALUE}}", "").replace("&&", "&");
        //回传到渠道
        JSONObject json = new JSONObject();
        json.put("a_type", baiduVO.getaType());
        json.put("a_value", baiduVO.getaValue());
        json.put("cb_event_time", baiduVO.getCbEventTime());
        json.put("join_type", "caid");
        if (CommonUtil.strEmpty(baiduVO.getCbOaid(), BaiduParamEnum.OAID.getMacro())) {
            json.put("cb_oaid", baiduVO.getCbOaid());
            json.put("join_type", "oaid");
        }
        if (CommonUtil.strEmpty(baiduVO.getCbOaidMd5(), BaiduParamEnum.OAID_MD5.getMacro())) {
            json.put("cb_oaid_md5", baiduVO.getCbOaidMd5());
            json.put("join_type", "oaid");
        }
        if (CommonUtil.strEmpty(baiduVO.getCbIdfa(), BaiduParamEnum.IDFA.getMacro())) {
            json.put("cb_idfa", baiduVO.getCbIdfa());
            json.put("join_type", "idfa");
        }
        if (CommonUtil.strEmpty(baiduVO.getCbImei(), null)) {
            json.put("cb_imei", baiduVO.getCbImei());
            json.put("join_type", "imei");
        }
        if (CommonUtil.strEmpty(baiduVO.getCbImeiMd5(), BaiduParamEnum.IMEI_MD5.getMacro())) {
            json.put("cb_imei_md5", baiduVO.getCbImeiMd5());
            json.put("join_type", "imei");
        }
        if (CommonUtil.strEmpty(baiduVO.getCbAndroidIdMd5(), BaiduParamEnum.ANDROID_ID_MD5.getMacro())) {
            json.put("cb_android_id_md5", baiduVO.getCbAndroidIdMd5());
            json.put("join_type", "android_id");
        }
        if (CommonUtil.strEmpty(baiduVO.getCbIp(), BaiduParamEnum.IP.getMacro())) {
            json.put("cb_ip", baiduVO.getCbIp());
            json.put("join_type", "ip");
        }


        StringBuilder url = new StringBuilder(channelUrl);
        Set<Map.Entry<String, Object>> entries = json.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            url.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
        signature(url, baiduVO.getSecret());
        logger.info("baseAdsCallBack 回传渠道url：{}", url);
        HttpResponse response = HttpRequest.get(url.toString()).execute();
        Map<String, Object> responseBodyMap = JsonParameterUtil.jsonToMap(response.body(), Exception.class);

        //保存转化事件回调信息
        BdssCallbackDTO bdssCallbackDTO = new BdssCallbackDTO(baiduVO.getAdsId(), baiduVO.getaType(), baiduVO.getCbIdfa(),
                baiduVO.getCbImei(), baiduVO.getCbImeiMd5(), baiduVO.getCbAndroidIdMd5(), baiduVO.getCbIp(), baiduVO.getCbEventTime(), baiduVO.getAdsName());
        if (HttpStatus.HTTP_OK == response.getStatus() && Objects.requireNonNull(responseBodyMap).get("error_code").equals(0)) {
            bdssCallbackDTO.setCallBackStatus(Constants.CallBackStatus.SUCCESS.getCode());
            bdssCallbackDTO.setCallBackMes(String.valueOf(responseBodyMap.get("code")));
            bdssCallbackDao.insert(bdssCallbackDTO);
            return BasicResult.getSuccessResponse(bdssCallbackDTO);
        } else {
            bdssCallbackDTO.setCallBackStatus(Constants.CallBackStatus.FAIL.getCode());
            bdssCallbackDTO.setCallBackMes(responseBodyMap.get("error_code") + "  " + responseBodyMap.get("error_msg"));
            bdssCallbackDao.insert(bdssCallbackDTO);
            return BasicResult.getFailResponse(bdssCallbackDTO.getCallBackMes(), bdssCallbackDTO);
        }
    }

    //计算签名
    protected void signature(StringBuilder url, String secret) {
        String signatureStr = url + secret;
        url.append("&sign=").append(DigestUtil.md5Hex(signatureStr).toLowerCase());
    }

}
