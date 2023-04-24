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
 * @Description: 头条--美团
 * @Author: xietao
 * @Date: 2023-04-20 21:14
 **/
@RestController
@RequestMapping("tmServer")
public class TMController {

    @Autowired
    @Qualifier("tmService")
    private IChannelAdsService tmService;

    /**
     * 监测地址
     */
    @RequestMapping("monitorAddress")
    public Response monitorAddress(HttpServletRequest request, @RequestBody String reqBody) {
        try {
            Map<String, Object> params = JsonParameterUtil.jsonToMap(reqBody, Exception.class);
            return tmService.monitorAddress(params);
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
            return tmService.clickReport(parameterMap);
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
            return tmService.adsCallBack(id, parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
            return BasicResult.getFailResponse("请求异常", e.getMessage());
        }
    }


}
