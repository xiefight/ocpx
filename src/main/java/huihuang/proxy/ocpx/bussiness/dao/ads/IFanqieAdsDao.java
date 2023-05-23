package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.fanqie.FanqieAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: xietao
 * @Date: 2023/5/23 21:25
 */
@Mapper
public interface IFanqieAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(FanqieAdsDTO fanqieAdsDTO);

    /**
     * 更新监测记录
     */
    int update(FanqieAdsDTO fanqieAdsDTO);

    /**
     * 根据id查询
     */
    FanqieAdsDTO queryFanqieAdsById(Integer id);

}
