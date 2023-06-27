package huihuang.proxy.ocpx.ads2channel.controller;

import huihuang.proxy.ocpx.ads2channel.service.ILiangdamaoService;
import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.common.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-06-27 09:00
 **/
@RestController
@RequestMapping(Constants.AdsForChannel.LIANGDAMAO)
public class LiangdamaoController {

    @Autowired
    @Qualifier("ldmService")
    private ILiangdamaoService ldmService;

    /**
     * 点击上报和回传
     */
    @RequestMapping(Constants.ServerPath.CLICK_REPORT)
    public Response clickReport(HttpServletRequest request) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            return ldmService.clickReport(parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResult.getFailResponse("请求异常", e.getMessage());
        }
    }

    /**
     * 客户侧回调
     */
    @GetMapping(Constants.ServerPath.ADS_CALLBACK_ID)
    public Response adsCallBack(HttpServletRequest request, @PathVariable Integer id) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            return ldmService.adsCallBack(id, parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResult.getFailResponse("请求异常", e.getMessage());
        }
    }

}
