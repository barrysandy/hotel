package com.zzk.dao;

import java.util.*;

import com.zzk.entity.GoodsSkuInfo;

public interface GoodsSkuInfoMapper {

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author Kun
     * @date 2018-03-06 15:16
     */
    int deleteByPrimaryKey(String id);

    /**
     * 新增
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 15:16
     */
    int insert(GoodsSkuInfo record);

    /**
     * 新增(只写入不为NULL的字段)
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 15:16
     */
    int insertSelective(GoodsSkuInfo record);

    /**
     * 通过主键ID查询
     *
     * @param id 实体类
     * @return GoodsSkuInfo 实体类
     * @author: Kun
     * @date: 2018-03-06 15:16
     */
    GoodsSkuInfo selectByPrimaryKey(String id);

    /**
     * 更新数据(只更新不为NULL的字段)
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 15:16
     */
    int updateByPrimaryKeySelective(GoodsSkuInfo record);

    /**
     * 更新数据
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 15:16
     */
    int updateByPrimaryKey(GoodsSkuInfo record);

    /**
     * 分页查询
     *
     * @param map 参数MAP
     * @return List
     * @author: Kun
     * @date: 2018-03-06 15:16
     */
    List<GoodsSkuInfo> selectByPage(Map map);

    /**
     * 查询总条数
     *
     * @return int 总条数
     * @author: Kun
     * @date: 2018-03-06 15:16
     */
    int selectCount(Map record);

    /**
     * 通过商品Id 查询商品下的所有单品
     *
     * @param productId
     * @return 单品名称, 最小原价, 最小销售价, 最后修改时间, 是否启用, 单品ID
     * @author kun
     * @date 16:25 2018/3/9
     */
    List<Map<String, Object>> listGoodsListByProductId(String productId);

    /**
     * 通过productId查询单品(出行方案)
     *
     * @param productId
     * @return List
     * @author kun
     * @date 15:44 2018/3/20
     */
    List<Map<String, Object>> listSku(String productId);

    /**
     * 查询商品的限定时间
     * @param skuId
     * @return
     * @author kun
     * @date 11:24 2018/5/9
     */
    Map<String,Object> getLimitTimeBySkuId(String skuId);
}