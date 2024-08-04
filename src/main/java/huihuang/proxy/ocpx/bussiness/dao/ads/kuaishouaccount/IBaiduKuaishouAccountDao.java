package huihuang.proxy.ocpx.bussiness.dao.ads.kuaishouaccount;

import huihuang.proxy.ocpx.ads.kuaishou.KuaishouAdsDTO;
import huihuang.proxy.ocpx.bussiness.dao.ads.IKuaishouAdsDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    /**
     * 查询数据库最大id
     *
     * explain select max(id) from kuaishou_ads_baidu_bdksjs07;
     *      Select tables optimized away    没扫描表，没走索引，已经优化到不能再优化了
     * explain select id from kuaishou_ads_baidu_bdksjs07 order by id desc limit 1;
     *      Backward index scan; Using index   使用索引扫描。索引本身就是有序的，所以不需要再次进行排序
     */
    @Select("select max(id) from ${tableName}")
    Integer queryMaxId(@Param("tableName") String tableName);

}
