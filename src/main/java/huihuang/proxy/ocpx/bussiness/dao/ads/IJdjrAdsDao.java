package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: xietao
 * @Date: 2023/5/30 21:01
 */
@Mapper
public interface IJdjrAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(LiangdamaoAdsDTO jdjrAdsDTO);

    /**
     * 更新监测记录
     */
    int update(LiangdamaoAdsDTO jdjrAdsDTO);

    /**
     * 根据id查询
     */
    LiangdamaoAdsDTO queryJdjrAdsById(Integer id);

}
