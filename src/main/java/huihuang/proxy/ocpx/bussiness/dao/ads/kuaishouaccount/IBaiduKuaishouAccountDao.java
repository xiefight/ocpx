package huihuang.proxy.ocpx.bussiness.dao.ads.kuaishouaccount;

import huihuang.proxy.ocpx.ads.kuaishou.KuaishouAdsDTO;
import huihuang.proxy.ocpx.bussiness.dao.ads.IKuaishouAdsDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 快手-百度根据账户id分表
 * @Author: xietao
 * @Date: 2024-07-23 10:29
 **/
@Mapper
public interface IBaiduKuaishouAccountDao extends IKuaishouAdsDao {

    /**
     * 判断表是否存在 存在则直接返回true 不存在返回false
     */
    int isTableExist(@Param("tableName") String tableName);

    /**
     * 创建表
     */
//    void createTable(@Param("sql") String sql);
    void createTable(@Param("tableName") String tableName, @Param("startId") Integer startId);

    /**
     * 根据id查询
     */
    KuaishouAdsDTO queryKuaishouAdsById(@Param("id") Integer id, @Param("tableName") String tableName);

}
