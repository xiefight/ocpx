package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IDouyinhuoshanAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(LiangdamaoAdsDTO tianmaoAdsDTO);

    /**
     * 更新监测记录
     */
    int update(LiangdamaoAdsDTO tianmaoAdsDTO);

    /**
     * 根据id查询
     */
    LiangdamaoAdsDTO queryDouyinhuoshanAdsById(Integer id);

}
