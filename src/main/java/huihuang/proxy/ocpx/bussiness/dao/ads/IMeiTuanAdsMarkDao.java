package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.meituan.MeiTuanAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-21 19:26
 **/
@Mapper
public interface IMeiTuanAdsMarkDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(MeiTuanAdsDTO meiTuanAdsDTO);

    /**
     * 更新监测记录
     */
    int update(MeiTuanAdsDTO meiTuanAdsDTO);

    /**
     * 根据id查询
     */
    MeiTuanAdsDTO queryMeiTuanAdsById(Integer id);

}
