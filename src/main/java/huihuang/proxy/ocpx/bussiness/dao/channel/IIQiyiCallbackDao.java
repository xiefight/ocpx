package huihuang.proxy.ocpx.bussiness.dao.channel;

import huihuang.proxy.ocpx.channel.iqiyi.IQiyiCallbackDTO;
import huihuang.proxy.ocpx.channel.xiaomi.XiaomiCallbackDTO;
import org.apache.ibatis.annotations.Mapper;

/** 
 * 
 * @Author: xietao
 * @Date: 2023/6/29 22:27
 */ 
@Mapper
public interface IIQiyiCallbackDao {

    /**
     * 新增一条回调记录
     */
    int insert(IQiyiCallbackDTO iqiyiCallbackDTO);

    /**
     * 根据id查询
     */
//    MeiTuanAdsDTO queryMeiTuanAdsById(Integer id);

}
