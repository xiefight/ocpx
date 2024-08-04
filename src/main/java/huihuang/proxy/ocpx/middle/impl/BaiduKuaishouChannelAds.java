package huihuang.proxy.ocpx.middle.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouAdsDTO;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouParamEnum;
import huihuang.proxy.ocpx.ads.kuaishou.KuaishouParamField;
import huihuang.proxy.ocpx.bussiness.dao.ads.IKuaishouAdsDao;
import huihuang.proxy.ocpx.bussiness.dao.ads.kuaishouaccount.IBaiduKuaishouAccountDao;
import huihuang.proxy.ocpx.bussiness.service.common.IConfigService;
import huihuang.proxy.ocpx.channel.baidu.BaiduParamEnum;
import huihuang.proxy.ocpx.channel.baidu.BaiduPath;
import huihuang.proxy.ocpx.common.Constants;
import huihuang.proxy.ocpx.middle.baseadsreport.KuaishouReportFactory;
import huihuang.proxy.ocpx.util.CommonUtil;
import huihuang.proxy.ocpx.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * baidu--kuaishou
 *
 * @Author: xietao
 * @Date: 2023/5/10 22:28
 */
@Component("bkChannelAds")
public class BaiduKuaishouChannelAds extends KuaishouReportFactory {

    String channelAdsKey = Constants.ChannelAdsKey.BAIDU_KUAISHOU;

    @Autowired
    private IConfigService configService;

    @Autowired
    @Qualifier("kuaishouAdsBaiduDao")
    private IKuaishouAdsDao kuaishouAdsBaiduDao;

    @Autowired
    private IBaiduKuaishouAccountDao baiduKuaishouAccountDao;

    @Override
    protected String channelAdsKey() {
        return channelAdsKey;
    }

    @Override
    protected String serverPathKey() {
        return Constants.ServerPath.BAIDU_KUAISHOU;
    }

    @Override
    protected String channelName() {
        return BaiduPath.BAIDU_CHANNEL_NAME;
    }

    @Override
    protected IKuaishouAdsDao adsDao() {
        return baiduKuaishouAccountDao;
    }

    /**
     * 生成监测链接
     */
    @Override
    public String findMonitorAddress() {
        StringBuilder macro = new StringBuilder();
        //1.遍历kuaishou查找baidu对应的宏参数
        Set<KuaishouParamEnum> kuaishouParamEnums = KuaishouParamEnum.kuaishouBaiduMap.keySet();
        for (KuaishouParamEnum kuaishou : kuaishouParamEnums) {
            BaiduParamEnum baidu = KuaishouParamEnum.kuaishouBaiduMap.get(kuaishou);
            if (Objects.isNull(baidu) || StrUtil.isEmpty(baidu.getMacro())) {
                continue;
            }
            macro.append(baidu.getParam()).append("=").append(baidu.getMacro()).append("&");
        }
        String macroStr = macro.toString();
        if (macroStr.endsWith("&")) {
            macroStr = macroStr.substring(0, macroStr.length() - 1);
        }
        //2.config中查找服务地址
        String serverPath = queryServerPath();
        //3.拼接监测地址
        return serverPath + Constants.ServerPath.BAIDU_KUAISHOU + Constants.ServerPath.CLICK_REPORT + "?" + macroStr;
    }

    @Override
    protected Object channelParamToAdsParam(Map<String, String[]> parameterMap) {
        KuaishouParamField kuaishouParamField = new KuaishouParamField();

        Set<Map.Entry<KuaishouParamEnum, BaiduParamEnum>> bkSet = KuaishouParamEnum.kuaishouBaiduMap.entrySet();
        bkSet.stream().filter(bk -> Objects.nonNull(bk.getValue())).forEach(tm -> {
            KuaishouParamEnum kuaishou = tm.getKey();
            BaiduParamEnum baidu = tm.getValue();
            //kuaishou的字段名
            String kuaishouField = kuaishou.getName();
            String baiduParam = baidu.getParam();
            String[] value = parameterMap.get(baiduParam);
            if (Objects.isNull(value) || value.length == 0) return;
            if ("null".equals(value[0]) || "NULL".equals(value[0])) return;
            if (value[0].startsWith("__") && value[0].endsWith("__")) return;
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(kuaishouField, kuaishouParamField.getClass());
                Method setMethod = descriptor.getWriteMethod();
                setMethod.invoke(kuaishouParamField, value[0]);
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        //特殊处理oaid
        String[] oaids = parameterMap.get(BaiduParamEnum.OAID.getParam());
        if (null != oaids && oaids.length > 0) {
            kuaishouParamField.setOaid(oaids[0]);
        } else {
            String[] oaidMd5s = parameterMap.get(BaiduParamEnum.OAID_MD5.getParam());
            if (null != oaidMd5s && oaidMd5s.length > 0) {
                kuaishouParamField.setOaid(oaidMd5s[0]);
            }
        }
        logger.info("clickReport {} 媒体侧请求的监测链接中的参数，转化成广告侧的参数对象 channelParamToAdsParam:{}", channelAdsKey, kuaishouParamField);
        return kuaishouParamField;
    }

    @Override
    public void convertParams(Object adsObj) {
        super.convertParams(adsObj);
        KuaishouParamField kuaishouParamField = (KuaishouParamField) adsObj;
        if (BaiduPath.BAIDU_KUAISHOU_ACCOUNT_05.equals(kuaishouParamField.getAccount_id())
                || BaiduPath.BAIDU_KUAISHOUJISU_ACCOUNT_05.equals(kuaishouParamField.getAccount_id())) {
            kuaishouParamField.setRta_id(RandomUtil.randomStamp());
        }
    }


    @Override
    protected Object saveOriginParamData(Object adsObj) {
        KuaishouParamField kuaishouParamField = (KuaishouParamField) adsObj;
        KuaishouAdsDTO kuaishouAdsDTO = new KuaishouAdsDTO();
        BeanUtil.copyProperties(kuaishouParamField, kuaishouAdsDTO);
        if (null != kuaishouParamField.getAccount_id()) {
            //暂用于百度-快手分表时，account_id作为分表字段
            kuaishouAdsDTO.setTableName(CommonUtil.PREFIX_BAIDU_KUAISHOU_TABLE_NAME + kuaishouParamField.getAccount_id());
        }
        kuaishouAdsDTO.setChannelName(channelName());
        //先判断表是否已经创建过
        String tableName = kuaishouAdsDTO.getTableName();
        //在配置表中配置新增的 账户:id,必须在生成数据之前
        Integer startId = CommonUtil.baiduKuaishouAccountMap.get(tableName);
        assert startId != null;
        if (!CommonUtil.kuaishouBaiduTables.contains(tableName)) {
            //表不存在,创建
            logger.info("clickReport {} 创建表，表名:{}", channelAdsKey(), tableName);
            //创建之前,需要查看配置,要创建哪个表,起始自增id是多少
//            Map<String, Integer> baiduKuaishouAccountMap = configService.queryBaiduKuaishouAccountMap();
//            assert baiduKuaishouAccountMap != null;
            //创建表
            synchronized (BaiduPath.class) {
                //加锁,避免重复建表
                if (baiduKuaishouAccountDao.isTableExist(tableName) == 0) {
                    baiduKuaishouAccountDao.createTable(tableName, startId);
                }
            }
            //将表名添加到集合中,避免重复创建
            CommonUtil.kuaishouBaiduTables.add(tableName);
            //保存id和表名的映射关系,方便回传更新时,根据id快速定位到表名
            // 假如配置项是: {"bdks01":1000,"bdks02":2000,"bdksjs01":3000,"bdksjs02":4000},
            // 在存储时,应该是{2000:"bdks01",3000:"bdks02",4000:"bdksjs01",5000:"bdksjs02"},这样在对比时,小于2000的，就是bdks01,小于3000的，就是bdks02
            // 步近 1000 0000
//            CommonUtil.kuaishouBaiduIdTableMap.put(startId + CommonUtil.STEP_NUM, tableName);
        }
        //查询当前数据库的最大id是否超过每张表的阈值
        Integer maxId = ((IBaiduKuaishouAccountDao) adsDao()).queryMaxId(tableName);
        if (maxId != null && maxId >= startId + CommonUtil.THRESHOLD) {
            //超过阈值,需要插入到新表中，提前创建好
            String newTableName = CommonUtil.NEW_ORIGIN_BAIDU_KUAISHOU_TABLE_NAME;
            logger.info("clickReport {} 当前表已经超过阈值，需要创建新表，原表名:{},新表名:{}", channelAdsKey(), tableName, newTableName);
            kuaishouAdsDTO.setTableName(newTableName);
        }
//        baseServiceInner.insertAdsObject(kuaishouAdsDTO, adsDao());
        adsDao().insert(kuaishouAdsDTO);
        logger.info("clickReport {} 将原始参数保存数据库，返回数据库对象 saveOriginParamData:{}", channelAdsKey(), kuaishouAdsDTO);
        return kuaishouAdsDTO;
    }


}
