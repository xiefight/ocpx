package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.huihuang.HuihuangAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: xietao
 * @Date: 2023/7/6 16:26
 */
@Mapper
public interface IHuihuangAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(HuihuangAdsDTO huihuangAdsDTO);

    /**
     * 更新监测记录
     */
    int update(HuihuangAdsDTO huihuangAdsDTO);

    /**
     * 根据id查询
     */
    HuihuangAdsDTO queryHuihuangAdsById(Integer id);

}
