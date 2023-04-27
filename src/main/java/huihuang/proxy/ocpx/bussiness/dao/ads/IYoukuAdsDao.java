package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.youku.YoukuAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-21 19:26
 **/
@Mapper
public interface IYoukuAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(YoukuAdsDTO youkuAdsDTO);

    /**
     * 更新监测记录
     */
    int update(YoukuAdsDTO youkuAdsDTO);

    /**
     * 根据id查询
     */
    YoukuAdsDTO queryYoukuAdsById(Integer id);

}
