package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.keep.KeepAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IKeepAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(KeepAdsDTO keepAdsDTO);

    /**
     * 更新监测记录
     */
    int update(KeepAdsDTO keepAdsDTO);

    /**
     * 根据id查询
     */
    KeepAdsDTO queryKeepAdsById(Integer id);

}
