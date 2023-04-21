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

    @RequestMapping("monitorAddress")
    public Response monitorAddress(HttpServletRequest request, @RequestBody String reqBody) {
        try {
            Map<String, Object> params = JsonParameterUtil.jsonToMap(reqBody, Exception.class);
            return tmService.monitorAddress(params);
        } catch (Exception e) {
            return BasicResult.getFailResponse(e.getMessage());
        }
    }

    @RequestMapping("queryConfig")
    public Response queryConfig(){
        return tmService.queryConfig();
    }

}
