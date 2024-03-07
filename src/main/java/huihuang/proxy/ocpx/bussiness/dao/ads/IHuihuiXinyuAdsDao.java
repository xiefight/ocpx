package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.huihui.HuihuiAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: xietao
 * @Date: 2023/8/2 17:46
 */
@Mapper
public interface IHuihuiXinyuAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(HuihuiAdsDTO xinyuyoudaoAdsDTO);

    /**
     * 更新监测记录
     */
    int update(HuihuiAdsDTO xinyuyoudaoAdsDTO);

    /**
     * 根据id查询
     */
    HuihuiAdsDTO queryXinyuYoudaoAdsById(Integer id);

}
