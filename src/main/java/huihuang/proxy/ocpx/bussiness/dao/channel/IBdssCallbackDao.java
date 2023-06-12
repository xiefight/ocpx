package huihuang.proxy.ocpx.bussiness.dao.channel;

import huihuang.proxy.ocpx.channel.baidu.BaiduCallbackDTO;
import huihuang.proxy.ocpx.channel.bdss.BdssCallbackDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 * @Author: xietao
 * @Date: 2023/5/11 15:59
 */
@Mapper
public interface IBdssCallbackDao {

    /**
     * 新增一条回调记录
     */
    int insert(BdssCallbackDTO bdssCallbackDTO);

    /**
     * 根据id查询
     */
//    MeiTuanAdsDTO queryMeiTuanAdsById(Integer id);

}
