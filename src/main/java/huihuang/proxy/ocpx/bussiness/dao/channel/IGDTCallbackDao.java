package huihuang.proxy.ocpx.bussiness.dao.channel;

import huihuang.proxy.ocpx.channel.guangdiantong.GuangdiantongCallbackDTO;
import huihuang.proxy.ocpx.channel.iqiyi.IQiyiCallbackDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IGDTCallbackDao {

    /**
     * 新增一条回调记录
     */
    int insert(GuangdiantongCallbackDTO gdtCallbackDTO);

    /**
     * 根据id查询
     */
//    MeiTuanAdsDTO queryMeiTuanAdsById(Integer id);

}
