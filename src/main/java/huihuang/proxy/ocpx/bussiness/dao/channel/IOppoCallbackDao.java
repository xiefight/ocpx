package huihuang.proxy.ocpx.bussiness.dao.channel;

import huihuang.proxy.ocpx.channel.oppo.OppoCallbackDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: xietao
 * @Date: 2023/6/4 16:23
 */
@Mapper
public interface IOppoCallbackDao {

    /**
     * 新增一条回调记录
     */
    int insert(OppoCallbackDTO oppoCallbackDTO);

}
