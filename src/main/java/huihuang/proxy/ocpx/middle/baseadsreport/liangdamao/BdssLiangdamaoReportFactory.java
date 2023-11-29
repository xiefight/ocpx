package huihuang.proxy.ocpx.middle.baseadsreport.liangdamao;

import huihuang.proxy.ocpx.channel.bdss.BdssPath;

/**
 * @Description: 上报客户的逻辑再抽象
 * 比如：百度和京东、优酷、番茄的对接，本质上是百度和粮大猫的对接，抽离出百度和粮大猫的公共部分，将京东、优酷、番茄的不同参数传入
 * @Author: xietao
 * @Date: 2023-05-23 17:39
 **/
public abstract class BdssLiangdamaoReportFactory extends BaiduLiangdamaoReportFactory {

    @Override
    protected String channelName() {
        return BdssPath.BDSS_CHANNEL_NAME;
    }


}
