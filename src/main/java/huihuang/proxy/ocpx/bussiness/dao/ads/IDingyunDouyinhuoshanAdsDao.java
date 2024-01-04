package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.dingyun.DingyunAdsDTO;
import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IDingyunDouyinhuoshanAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(DingyunAdsDTO huihuangmingtianAdsDTO);

    /**
     * 更新监测记录
     */
    int update(DingyunAdsDTO huihuangmingtianAdsDTO);

    /**
     * 根据id查询
     */
    DingyunAdsDTO queryDingyunDouyinhuoshanAdsById(Integer id);

}
