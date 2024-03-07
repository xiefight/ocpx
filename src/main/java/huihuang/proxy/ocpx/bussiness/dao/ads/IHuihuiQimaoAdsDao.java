package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.huihui.HuihuiAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IHuihuiQimaoAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(HuihuiAdsDTO qimaoyoudaoAdsDTO);

    /**
     * 更新监测记录
     */
    int update(HuihuiAdsDTO qimaoyoudaoAdsDTO);

    /**
     * 根据id查询
     */
    HuihuiAdsDTO queryQimaoYoudaoAdsById(Integer id);

}
