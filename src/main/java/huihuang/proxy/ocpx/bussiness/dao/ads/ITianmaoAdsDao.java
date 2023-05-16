package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.tianmao.TianmaoAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: xietao
 * @Date: 2023/5/16 20:30
 */
@Mapper
public interface ITianmaoAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(TianmaoAdsDTO tianmaoAdsDTO);

    /**
     * 更新监测记录
     */
    int update(TianmaoAdsDTO tianmaoAdsDTO);

    /**
     * 根据id查询
     */
    TianmaoAdsDTO queryTianmaoAdsById(Integer id);

}
