package huihuang.proxy.ocpx.bussiness.dao.channel;

import huihuang.proxy.ocpx.channel.honor.HonorCallbackDTO;
import huihuang.proxy.ocpx.channel.huawei.HuaweiCallbackDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IHonorCallbackDao {

    /**
     * 新增一条回调记录
     */
    int insert(HonorCallbackDTO honorCallbackDTO);

    /**
     * 根据id查询
     */
//    MeiTuanAdsDTO queryMeiTuanAdsById(Integer id);

}
