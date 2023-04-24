package huihuang.proxy.ocpx.bussiness.dao;

import huihuang.proxy.ocpx.ads.litianjingdong.LTJDAdsDTO;
import huihuang.proxy.ocpx.ads.meituan.MeiTuanAdsDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-21 19:26
 **/
@Mapper
public interface ILtjdAdsDao {

    /**
     * 新增一条监测记录
     */
    int insert(LTJDAdsDTO ltjdAdsDTO);

    /**
     * 更新监测记录
     */
    int update(LTJDAdsDTO ltjdAdsDTO);

    /**
     * 根据id查询
     */
    LTJDAdsDTO queryLtjdAdsById(Integer id);

}
