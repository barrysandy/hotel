package com.zzk.service;

import java.util.*;

import com.zzk.entity.GoodsSkuInfo;
import com.zzk.entity.ProductStockInfo;

/**
 * 单品信息表
 *
 * @author: Kun
 * @date: 2018-03-06 15:16
 */
public interface GoodsSkuInfoService {

    /**
     * 分页查询
     *
     * @param map 查询条件
     * @return List
     * @author: Kun
     * @date: 2018-03-06 15:16
     */
    List<GoodsSkuInfo> selectByPage(Map map);

    /**
     * 通过主键ID查询
     *
     * @param id 主键ID
     * @return 单品信息表实体类
     * @author: Kun
     * @date: 2018-03-06 15:16
     */
    GoodsSkuInfo selectByPrimaryKey(String id);

    /**
     * 查询总条数
     *
     * @param record 查询条件
     * @return 总条数
     * @author: Kun
     * @date: 2018-03-06 15:16
     */
    int selectCount(Map record);

    /**
     * 更新数据
     *
     * @param bean 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 15:16
     */
    int update(GoodsSkuInfo bean);

    /**
     * 新增
     *
     * @param bean 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 15:16
     */
    int insert(GoodsSkuInfo bean);

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author Kun
     * @date 2018-03-06 15:16
     */
    int delete(String id);

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
     * 新增单品信息
     *
     * @param skuId     单品ID
     * @param productId 关联商品ID
     * @param skuName   单品名
     * @param list      stock对象的List
     * @author kun
     * @date 11:44 2018/3/10
     */
    void insertSkuInfo(String skuId, String productId, String skuName, List<ProductStockInfo> list);

    /**
     * 修改单品信息
     *
     * @param skuId   单品ID
     * @param skuName 单品名
     * @param list    stock对象的List
     * @author kun
     * @date 11:47 2018/3/10
     */
    void updateSkuInfo(String skuId, String skuName, List<ProductStockInfo> list);

    /**
     * 查询发团列表
     *
     * @param map 商品ID
     * @return list
     * @author kun
     * @date 16:43 2018/3/10
     */
    List<Map<String, Object>> listLineGroupInfo(Map map);

    /**
     * 通过productId查询单品(出行方案)
     * @param productId
     * @return List
     * @author kun
     * @date 15:44 2018/3/20
     */
    List<Map<String,Object>> listSku(String productId);

    /**
     * 通过skuId查询日历信息
     * @param skuId
     * @return List
     * @author kun
     * @date 15:44 2018/3/20
     */
    List<Map<String,Object>> getSkuStockInfo(String skuId);

    /**
     * 修改价格信息 - 查询sku基本信息,以及日历中的发团信息
     * @param skuId 单品ID
     * @return map
     * @author kun
     * @date 9:44 2018/3/28
     */
    Map<String,Object> toSkuUpdatePage(String skuId);
    
    /**
     * 商品发布 - 价格信息 - 保存及修改
     * @param data
     * @return 
     * @author kun
     * @date 10:21 2018/3/28
     */
    int saveOrUpdateSkuInfo(String data);

    /**
     * 商品发布 - 价格信息 - 状态修改接口
     * @param skuId 单品Id
     * @param state 状态 1=启用 2=不启用
     * @return int
     * @author kun
     * @date 19:36 2018/3/28
     */
    int updateSkuState(String skuId ,Integer state);

}
