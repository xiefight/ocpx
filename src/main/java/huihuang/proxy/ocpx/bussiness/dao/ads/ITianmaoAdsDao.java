package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoAdsDTO;
import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoParamField;
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
    int insert(LiangdamaoAdsDTO tianmaoAdsDTO);

    /**
     * 更新监测记录
     */
    int update(LiangdamaoAdsDTO tianmaoAdsDTO);

    /**
     * 根据id查询
     */
    LiangdamaoAdsDTO queryTianmaoAdsById(Integer id);

}
