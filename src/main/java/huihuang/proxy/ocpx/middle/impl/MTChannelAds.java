package huihuang.proxy.ocpx.middle.impl;

import huihuang.proxy.ocpx.ads.meituan.MeiTuanParamEnum;
import huihuang.proxy.ocpx.channel.toutiao.ToutiaoParamEnum;
import huihuang.proxy.ocpx.middle.BaseSupport;
import huihuang.proxy.ocpx.middle.IChannelAds;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-20 22:42
 **/
@Component
public class MTChannelAds extends BaseSupport implements IChannelAds {


    @Override
    public String findMonitorAddress() {

        //1.查找meituan必要的参数
        List<MeiTuanParamEnum> necessaryEnums = MeiTuanParamEnum.findParamEnumsByNece();

        StringBuilder macro = new StringBuilder();
        //2.遍历查找头条对应的宏参数
        for (MeiTuanParamEnum paramEnum : necessaryEnums) {
            if (paramEnum.equals(MeiTuanParamEnum.APP_TYPE)) {
                macro.append(ToutiaoParamEnum.OS.getParam()).append("=").append(ToutiaoParamEnum.OS.getMacro()).append("&");
            } else if (paramEnum.equals(MeiTuanParamEnum.MD5_IDFA)) {
                macro.append(ToutiaoParamEnum.IDFA_MD5.getParam()).append("=").append(ToutiaoParamEnum.IDFA_MD5.getMacro()).append("&");
            } else if (paramEnum.equals(MeiTuanParamEnum.MD5_IMEI)) {
                macro.append(ToutiaoParamEnum.IMEI.getParam()).append("=").append(ToutiaoParamEnum.IMEI.getMacro()).append("&");
            } else if (paramEnum.equals(MeiTuanParamEnum.OAID)) {
                macro.append(ToutiaoParamEnum.OAID.getParam()).append("=").append(ToutiaoParamEnum.OAID.getMacro()).append("&");
            } else if (paramEnum.equals(MeiTuanParamEnum.MD5_OAID)) {
                macro.append(ToutiaoParamEnum.OAID_MD5.getParam()).append("=").append(ToutiaoParamEnum.OAID_MD5.getMacro()).append("&");
            } else if (paramEnum.equals(MeiTuanParamEnum.FEEDBACK_URL)) {
                macro.append(ToutiaoParamEnum.CALLBACK_URL.getParam()).append("=").append(ToutiaoParamEnum.CALLBACK_URL.getMacro()).append("&");
            } else if (paramEnum.equals(MeiTuanParamEnum.ACTION_TIME)) {
                macro.append(ToutiaoParamEnum.TS.getParam()).append("=").append(ToutiaoParamEnum.TS.getMacro()).append("&");
            }
        }
        String macroStr = macro.toString();
        if (macroStr.endsWith("&")) {
            macroStr = macroStr.substring(0, macroStr.length() - 1);
        }

        //3.config中查找服务地址
        String serverPath = queryServerPath();

        //4.拼接监测地址
        return serverPath + "?" + macroStr;
    }
}
