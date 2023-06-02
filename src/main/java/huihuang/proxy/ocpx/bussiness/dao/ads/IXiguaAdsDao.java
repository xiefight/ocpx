package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.liangdamao.LiangdamaoAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: xietao
 * @Date: 2023/5/9 20:25
 */
@Mapper
public interface IXiguaAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(LiangdamaoAdsDTO xiguaAdsDTO);

    /**
     * 更新监测记录
     */
    int update(LiangdamaoAdsDTO xiguaAdsDTO);

    /**
     * 根据id查询
     */
    LiangdamaoAdsDTO queryXiguaAdsById(Integer id);

}
