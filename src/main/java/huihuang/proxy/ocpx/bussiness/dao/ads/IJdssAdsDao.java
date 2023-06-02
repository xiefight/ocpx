package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: xietao
 * @Date: 2023/6/2 19:05
 */
@Mapper
public interface IJdssAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(LiangdamaoAdsDTO jdssAdsDTO);

    /**
     * 更新监测记录
     */
    int update(LiangdamaoAdsDTO jdssAdsDTO);

    /**
     * 根据id查询
     */
    LiangdamaoAdsDTO queryJdssAdsById(Integer id);

}
