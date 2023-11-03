package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IQuannengBaidujisuAdsDao extends IMarkDao {

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
    QuannengHudongAdsDTO queryQuannengBaidujisuAdsById(Integer id);

}
