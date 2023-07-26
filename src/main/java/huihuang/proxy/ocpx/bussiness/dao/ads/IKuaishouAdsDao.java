package huihuang.proxy.ocpx.bussiness.dao.ads;

import huihuang.proxy.ocpx.ads.kuaishou.KuaishouAdsDTO;
import huihuang.proxy.ocpx.marketinterface.IMarkDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Primary;

/**
 * @Author: xietao
 * @Date: 2023/5/14 15:49
 */
@Mapper
@Primary
public interface IKuaishouAdsDao extends IMarkDao {

    /**
     * 新增一条监测记录
     */
    int insert(KuaishouAdsDTO kuaishouAdsDTO);

    /**
     * 更新监测记录
     */
    int update(KuaishouAdsDTO kuaishouAdsDTO);

    /**
     * 根据id查询
     */
    KuaishouAdsDTO queryKuaishouAdsById(Integer id);

}
