package huihuang.proxy.ocpx.bussiness.controller;

import huihuang.proxy.ocpx.bussiness.service.ITMService;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.util.JsonParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private ITMService tmService;

    /**
     * 监测地址
     */
    @RequestMapping("monitorAddress")
    public Response monitorAddress(HttpServletRequest request, @RequestBody String reqBody) {
        try {
            Map<String, Object> params = JsonParameterUtil.jsonToMap(reqBody, Exception.class);
            return tmService.monitorAddress(params);
        } catch (Exception e) {
            return BasicResult.getFailResponse(e.getMessage());
        }
    }

    /**
     * 点击上报和回传
     */
    @RequestMapping("clickReport")
    public Response monitorAddress(HttpServletRequest request) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            return tmService.clickReport(parameterMap);
        } catch (Exception e) {
            return BasicResult.getFailResponse(e.getMessage());
        }
    }

}
