package huihuang.proxy.ocpx.bussiness.dao.channel;

import huihuang.proxy.ocpx.channel.wifi.WifiCallbackDTO;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiCallbackDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-21 19:26
 **/
@Mapper
public interface IWifiCallbackDao {

    /**
     * 新增一条回调记录
     */
    int insert(WifiCallbackDTO wifiCallbackDTO);

    /**
     * 根据id查询
     */
//    MeiTuanAdsDTO queryMeiTuanAdsById(Integer id);

}
