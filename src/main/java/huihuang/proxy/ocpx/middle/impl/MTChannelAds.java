package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.meituan.MeiTuanParamEnum;
import huihuang.proxy.ocpx.channel.toutiao.ToutiaoParamEnum;
import huihuang.proxy.ocpx.middle.BaseSupport;
import huihuang.proxy.ocpx.middle.IChannelAds;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-20 22:42
 **/
@Component
public class MTChannelAds extends BaseSupport implements IChannelAds {


    @Override
    public String findMonitorAddress() {

        StringBuilder macro = new StringBuilder();
        //1.遍历meituan查找tuotiao对应的宏参数
        Set<MeiTuanParamEnum> meiTuanParamEnums = MeiTuanParamEnum.tmMap.keySet();
        for (MeiTuanParamEnum meiTuan : meiTuanParamEnums) {
            ToutiaoParamEnum toutiao = MeiTuanParamEnum.tmMap.get(meiTuan);
            if (Objects.isNull(toutiao)) {
                continue;
            }
            macro.append(toutiao.getParam()).append("=").append(toutiao.getMacro()).append("&");
        }
        String macroStr = macro.toString();
        if (macroStr.endsWith("&")) {
            macroStr = macroStr.substring(0, macroStr.length() - 1);
        }
        //2.config中查找服务地址
        String serverPath = queryServerPath();
        //3.拼接监测地址
        return serverPath + "?" + macroStr;
    }
}
