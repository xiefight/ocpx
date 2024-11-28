package huihuang.proxy.ocpx.main;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-11-28 11:37
 **/
@RestController
@RequestMapping("/dataFix")
public class DataFixController {

    @RequestMapping("/fix/{id}")
    public void fix(@PathVariable("id") Integer id) {
        String url = "http://localhost:5015/honorhhjdServer/adsCallBack/"+id+"?event_type=1";

        HttpResponse execute = HttpRequest.get(url).execute();
        System.out.println("id:"+id+"  -  "+execute.getStatus()+"  -  "+execute.body());
    }

}
