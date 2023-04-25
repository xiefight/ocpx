package huihuang.proxy.ocpx.bussiness.dao.common;

import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: 动态配置
 * @Author: xietao
 * @Date: 2023-04-21 10:30
 **/
@Mapper
public interface IConfigDao {

    String queryConfig();

}
