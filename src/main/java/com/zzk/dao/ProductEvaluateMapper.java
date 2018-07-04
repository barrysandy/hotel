package com.zzk.dao;

import java.util.*;

import com.zzk.entity.ProductEvaluate;

public interface ProductEvaluateMapper {

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author Kun
     * @date 2018-03-06 11:45
     */
    int deleteByPrimaryKey(String id);

    /**
     * 新增
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 11:45
     */
    int insert(ProductEvaluate record);

    /**
     * 新增(只写入不为NULL的字段)
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 11:45
     */
    int insertSelective(ProductEvaluate record);

    /**
     * 通过主键ID查询
     *
     * @param id 实体类
     * @return ProductEvaluate 实体类
     * @author: Kun
     * @date: 2018-03-06 11:45
     */
    ProductEvaluate selectByPrimaryKey(String id);

    /**
     * 更新数据(只更新不为NULL的字段)
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 11:45
     */
    int updateByPrimaryKeySelective(ProductEvaluate record);

    /**
     * 更新数据
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 11:45
     */
    int updateByPrimaryKey(ProductEvaluate record);

    /**
     * 分页查询
     *
     * @param map 参数MAP
     * @return List
     * @author: Kun
     * @date: 2018-03-06 11:45
     */
    List<ProductEvaluate> selectByPage(Map map);

    /**
     * 查询总条数
     *
     * @return int 总条数
     * @author: Kun
     * @date: 2018-03-06 11:45
     */
    int selectCount(Map record);

    /**
     * 获取商家各项评分信息
     * @param sellerId 商家Id
     * @return result
     * @author kun
     * @date 11:54 2018/3/15
     */
    Map<String,Object> getSellerAllScoreInfo(String sellerId);

    /**
     * 查询评论列表
     * @param map 各项参数查看controller
     * @return list
     * @author kun
     * @date 15:34 2018/3/15
     */
    List<Map<String,Object>> getEvaluateList(Map<String,Object> map);

    /**
     * 通过商品ID查询该商品的总评分
     * @param productId
     * @return string
     * @author kun
     * @date 10:42 2018/3/20
     */
    String getOneProductScore(String productId);

    /**
     * 通过商品ID查询该商品的所有评论
     * @param productId
     * @return
     * @author kun
     * @date 10:51 2018/3/20
     */
    List<Map<String,Object>> listProductEvaluate(String productId);


}