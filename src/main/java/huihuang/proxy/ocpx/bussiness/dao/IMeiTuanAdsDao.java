package huihuang.proxy.ocpx.bussiness.dao;

import huihuang.proxy.ocpx.ads.meituan.MeiTuanParamField;
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
    void insert(MeiTuanParamField meiTuanParamField);

}
