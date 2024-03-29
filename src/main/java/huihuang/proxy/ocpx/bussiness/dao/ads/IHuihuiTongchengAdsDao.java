package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.huihui.HuihuiAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: xietao
 * @Date: 2023/6/9 8:36
 */
@Mapper
public interface IHuihuiTongchengAdsDao extends IMarkDao {

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
    HuihuiAdsDTO queryHuihuiTongchengAdsById(Integer id);

}
