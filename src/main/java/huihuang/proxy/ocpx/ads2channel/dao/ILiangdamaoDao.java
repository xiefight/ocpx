package huihuang.proxy.ocpx.ads2channel.dao;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-06-27 10:02
 **/
@Mapper
public interface ILiangdamaoDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(LiangdamaoAdsDTO ldmAdsDTO);

    /**
     * 更新监测记录
     */
    int update(LiangdamaoAdsDTO ldmAdsDTO);

    /**
     * 根据id查询
     */
    LiangdamaoAdsDTO queryLdmAdsById(Integer id);

}
