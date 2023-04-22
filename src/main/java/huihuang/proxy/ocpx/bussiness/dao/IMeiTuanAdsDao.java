package huihuang.proxy.ocpx.bussiness.dao;

import huihuang.proxy.ocpx.ads.meituan.MeiTuanAdsDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-21 19:26
 **/
@Mapper
public interface IMeiTuanAdsDao {

    /**
     * 新增一条监测记录
     */
    void insert(MeiTuanAdsDTO meiTuanAdsDTO);

    /**
     * 更新监测记录
     */
    int update(MeiTuanAdsDTO meiTuanAdsDTO);

}
