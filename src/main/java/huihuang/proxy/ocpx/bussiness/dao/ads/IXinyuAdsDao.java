package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 * @Author: xietao
 * @Date: 2023/6/8 19:37
 */
@Mapper
public interface IXinyuAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(LiangdamaoAdsDTO xinyuAdsDTO);

    /**
     * 更新监测记录
     */
    int update(LiangdamaoAdsDTO xinyuAdsDTO);

    /**
     * 根据id查询
     */
    LiangdamaoAdsDTO queryXinyuAdsById(Integer id);

}
