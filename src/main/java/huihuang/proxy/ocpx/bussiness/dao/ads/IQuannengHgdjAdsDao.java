package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 红果短剧
 */

@Mapper
public interface IQuannengHgdjAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(QuannengHudongAdsDTO quannengXiguaVideoAdsDTO);

    /**
     * 更新监测记录
     */
    int update(QuannengHudongAdsDTO quannengXiguaVideoAdsDTO);

    /**
     * 根据id查询
     */
    QuannengHudongAdsDTO queryQuannengHgdjAdsById(Integer id);

}
