package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.bupet.BupetAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IBupetBiliAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(BupetAdsDTO bupetAdsDTO);

    /**
     * 更新监测记录
     */
    int update(BupetAdsDTO bupetAdsDTO);

    /**
     * 根据id查询
     */
    BupetAdsDTO queryBupetBiliAdsById(Integer id);

}
