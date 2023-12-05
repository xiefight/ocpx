package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.huihuangmingtian.HuihuangmingtianAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IHuihuangDouyinhuoshanAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(HuihuangmingtianAdsDTO huihuangmingtianAdsDTO);

    /**
     * 更新监测记录
     */
    int update(HuihuangmingtianAdsDTO huihuangmingtianAdsDTO);

    /**
     * 根据id查询
     */
    HuihuangmingtianAdsDTO queryHuihuangDouyinhuoshanAdsById(Integer id);

}
