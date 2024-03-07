package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.huihui.HuihuiAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;


/**
 * @Author: xietao
 * @Date: 2023/8/8 11:43
 */
@Mapper
public interface IHuihuiXianyuAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(HuihuiAdsDTO tantanAdsDTO);

    /**
     * 更新监测记录
     */
    int update(HuihuiAdsDTO tantanAdsDTO);

    /**
     * 根据id查询
     */
    HuihuiAdsDTO queryXianyuAdsById(Integer id);

}
