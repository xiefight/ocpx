package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.ningzhi.NingzhiAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface INingzhiSoulAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(NingzhiAdsDTO ningzhiAdsDTO);

    /**
     * 更新监测记录
     */
    int update(NingzhiAdsDTO ningzhiAdsDTO);

    /**
     * 根据id查询
     */
    NingzhiAdsDTO queryNingzhiSoulAdsById(Integer id);

}
