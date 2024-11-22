package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.quannenghudong.QuannengHudongAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IQuannengZhipuqingyanAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(QuannengHudongAdsDTO quannengAdsDTO);

    /**
     * 更新监测记录
     */
    int update(QuannengHudongAdsDTO quannengAdsDTO);

    /**
     * 根据id查询
     */
    QuannengHudongAdsDTO queryQuannengZhipuqingyanAdsById(Integer id);

}
