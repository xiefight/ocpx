package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.qidu.QiduAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IQiduAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(QiduAdsDTO qiduAdsDTO);

    /**
     * 更新监测记录
     */
    int update(QiduAdsDTO qiduAdsDTO);

    /**
     * 根据id查询
     */
    QiduAdsDTO queryQiduAdsById(Integer id);

}
