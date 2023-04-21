package huihuang.proxy.ocpx.bussiness.service.impl;

import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.bussiness.dao.IConfigDao;
import huihuang.proxy.ocpx.bussiness.service.ITMService;
import huihuang.proxy.ocpx.common.BasicResult;
import huihuang.proxy.ocpx.common.Response;
import huihuang.proxy.ocpx.middle.IChannelAds;
import huihuang.proxy.ocpx.middle.factory.ChannelAdsFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-20 21:33
 **/
@Service
public class TMServiceImpl implements ITMService {

    @Autowired
    private ChannelAdsFactory channelAdsFactory;
    @Autowired
    private IConfigDao configDao;

    @Override
    public Response monitorAddress(Map<String, Object> params) {

        String channel = (String) params.get("channel");
        String ads = (String) params.get("ads");

        assert StrUtil.isNotEmpty(channel);
        assert StrUtil.isNotEmpty(ads);

        String key = channel + "-" + ads;
        IChannelAds channelAds = channelAdsFactory.findChannelAds(key);
        if (Objects.isNull(channelAds)) {
            return BasicResult.getFailResponse("未找到对应渠道：" + channel + " 和广告商：" + ads + "的监测地址");
        }
        String monitorAddress = channelAds.findMonitorAddress();
        if (StrUtil.isEmpty(monitorAddress)) {
            return BasicResult.getFailResponse("生成监测地址失败，渠道：" + channel + " 和广告商：" + ads + "");
        }

        return BasicResult.getSuccessResponse(monitorAddress);
    }

    @Override
    public Response queryConfig() {
        String config = configDao.queryConfig();
        return BasicResult.getSuccessResponse(config);
    }
}
