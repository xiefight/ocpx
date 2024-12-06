package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.jiyue.JiyueAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IJiyueYingkeAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(JiyueAdsDTO jiyueAdsDTO);

    /**
     * 更新监测记录
     */
    int update(JiyueAdsDTO jiyueAdsDTO);

    /**
     * 根据id查询
     */
    JiyueAdsDTO queryJiyueYingkeAdsById(Integer id);

}
