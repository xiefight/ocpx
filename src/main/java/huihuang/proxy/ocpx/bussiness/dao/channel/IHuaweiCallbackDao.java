package huihuang.proxy.ocpx.bussiness.dao.channel;

import huihuang.proxy.ocpx.channel.baidu.BaiduCallbackDTO;
import huihuang.proxy.ocpx.channel.huawei.HuaweiCallbackDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 * @Author: xietao
 * @Date: 2023/5/14 17:26
 */
@Mapper
public interface IHuaweiCallbackDao {

    /**
     * 新增一条回调记录
     */
    int insert(HuaweiCallbackDTO huaweiCallbackDTO);

    /**
     * 根据id查询
     */
//    MeiTuanAdsDTO queryMeiTuanAdsById(Integer id);

}
