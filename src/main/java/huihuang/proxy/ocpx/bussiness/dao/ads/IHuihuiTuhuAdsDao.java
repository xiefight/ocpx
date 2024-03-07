package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.huihui.HuihuiAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 * @Author: xietao
 * @Date: 2023/8/16 10:22
 */
@Mapper
public interface IHuihuiTuhuAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(HuihuiAdsDTO tuhuAdsDTO);

    /**
     * 更新监测记录
     */
    int update(HuihuiAdsDTO tuhuAdsDTO);

    /**
     * 根据id查询
     */
    HuihuiAdsDTO queryTuhuAdsById(Integer id);

}
