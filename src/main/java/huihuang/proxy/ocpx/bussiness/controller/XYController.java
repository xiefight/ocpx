package huihuang.proxy.ocpx.bussiness.controller;

import huihuang.proxy.ocpx.bussiness.service.IChannelAdsService;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * xiaomi-youku
 *
 * @Author: xietao
 * @Date: 2023/4/27 10:48
 */
@RestController
@RequestMapping("xyServer")
public class XYController {

    @Autowired
    @Qualifier("xyService")
    private IChannelAdsService xyService;

    /**
     * 监测地址
     */
    @RequestMapping("monitorAddress")
    public Response monitorAddress(HttpServletRequest request, @RequestBody String reqBody) {
        try {
            Map<String, Object> params = JsonParameterUtil.jsonToMap(reqBody, Exception.class);
            return xyService.monitorAddress(params);
        } catch (Exception e) {
            return BasicResult.getFailResponse("请求异常", e.getMessage());
        }
    }

    /**
     * 点击上报和回传
     */
    @RequestMapping("clickReport")
    public Response clickReport(HttpServletRequest request) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            return xyService.clickReport(parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResult.getFailResponse("请求异常", e.getMessage());
        }

    }

    /**
     * 客户侧回调
     */
    @GetMapping("adsCallBack/{id}")
    public Response adsCallBack(HttpServletRequest request, @PathVariable Integer id) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            return xyService.adsCallBack(id, parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResult.getFailResponse("请求异常", e.getMessage());
        }
    }


}