package com.zzk.dao;

import java.util.*;

import com.zzk.entity.ProductStockInfo;

public interface ProductStockInfoMapper {

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author Kun
     * @date 2018-03-06 15:34
     */
    int deleteByPrimaryKey(String id);

    /**
     * 新增
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 15:34
     */
    int insert(ProductStockInfo record);

    /**
     * 新增(只写入不为NULL的字段)
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 15:34
     */
    int insertSelective(ProductStockInfo record);

    /**
     * 通过主键ID查询
     *
     * @param id 实体类
     * @return ProductStockInfo 实体类
     * @author: Kun
     * @date: 2018-03-06 15:34
     */
    ProductStockInfo selectByPrimaryKey(String id);

    /**
     * 更新数据(只更新不为NULL的字段)
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 15:34
     */
    int updateByPrimaryKeySelective(ProductStockInfo record);

    /**
     * 更新数据
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 15:34
     */
    int updateByPrimaryKey(ProductStockInfo record);

    /**
     * 分页查询
     *
     * @param map 参数MAP
     * @return List
     * @author: Kun
     * @date: 2018-03-06 15:34
     */
    List<ProductStockInfo> selectByPage(Map map);

    /**
     * 查询总条数
     *
     * @return int 总条数
     * @author: Kun
     * @date: 2018-03-06 15:34
     */
    int selectCount(Map record);

    /**
     * 批量新增
     *
     * @param list
     * @author kun
     * @date 12:28 2018/3/10
     */
    int insertBatch(List<ProductStockInfo> list);

    /**
     * 通过skuId删除库存信息表
     *
     * @param skuId 单品ID
     * @return int
     * @author kun
     * @date 12:52 2018/3/10
     */
    int deleteOldInfoBySkuId(String skuId);

    /**
     * 查询发团列表
     *
     * @param map
     * @return list
     * @author kun
     * @date 16:43 2018/3/10
     */
    List<Map<String, Object>> listLineGroupInfo(Map map);

    /**
     * 通过skuId查询日历信息
     * @param map
     * @return List
     * @author kun
     * @date 15:44 2018/3/20
     */
    List<Map<String,Object>> getSkuStockInfo(Map map);

    /**
     * 通过skuId查询日历信息 这个方法没有限制 提前预定时间
     * @param skuId
     * @return List
     * @author kun
     * @date 15:44 2018/3/20
     */
    List<Map<String,Object>> getSkuStockInfoOld(String skuId);
    
    /**
     * 启动项目时把预占库存初始为0
     * @return List
     * @author hua
     * @date 15:44 2018/3/20
     */
    int updateInit();

}