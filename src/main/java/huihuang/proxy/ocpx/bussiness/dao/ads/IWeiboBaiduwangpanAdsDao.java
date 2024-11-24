package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.weibo.WeiboAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IWeiboBaiduwangpanAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(WeiboAdsDTO weiboAdsDTO);

    /**
     * 更新监测记录
     */
    int update(WeiboAdsDTO weiboAdsDTO);

    /**
     * 根据id查询
     */
    WeiboAdsDTO queryWeiboBaiduwangpanAdsById(Integer id);

}
