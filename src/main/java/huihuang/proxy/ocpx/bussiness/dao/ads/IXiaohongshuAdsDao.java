package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 * @Author: xietao
 * @Date: 2023/6/4 10:16
 */
@Mapper
public interface IXiaohongshuAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(LiangdamaoAdsDTO xiaohongshuAdsDTO);

    /**
     * 更新监测记录
     */
    int update(LiangdamaoAdsDTO xiaohongshuAdsDTO);

    /**
     * 根据id查询
     */
    LiangdamaoAdsDTO queryXiaohongshuAdsById(Integer id);

}
