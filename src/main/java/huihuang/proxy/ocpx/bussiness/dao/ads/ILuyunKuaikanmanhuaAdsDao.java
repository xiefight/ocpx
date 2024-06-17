package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.luyun.LuyunAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ILuyunKuaikanmanhuaAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(LuyunAdsDTO keepAdsDTO);

    /**
     * 更新监测记录
     */
    int update(LuyunAdsDTO keepAdsDTO);

    /**
     * 根据id查询
     */
    LuyunAdsDTO queryLuyunKuaikanmanhuaAdsById(Integer id);

}
