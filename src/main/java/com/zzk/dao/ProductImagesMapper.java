package com.zzk.dao;

import java.util.*;

import com.zzk.entity.ProductImages;

public interface ProductImagesMapper {

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author Kun
     * @date 2018-03-06 14:26
     */
    int deleteByPrimaryKey(String id);

    /**
     * 新增
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 14:26
     */
    int insert(ProductImages record);

    /**
     * 新增(只写入不为NULL的字段)
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 14:26
     */
    int insertSelective(ProductImages record);

    /**
     * 通过主键ID查询
     *
     * @param id 实体类
     * @return ProductImages 实体类
     * @author: Kun
     * @date: 2018-03-06 14:26
     */
    ProductImages selectByPrimaryKey(String id);

    /**
     * 更新数据(只更新不为NULL的字段)
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 14:26
     */
    int updateByPrimaryKeySelective(ProductImages record);

    /**
     * 更新数据
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 14:26
     */
    int updateByPrimaryKey(ProductImages record);

    /**
     * 分页查询
     *
     * @param map 参数MAP
     * @return List
     * @author: Kun
     * @date: 2018-03-06 14:26
     */
    List<ProductImages> selectByPage(Map map);

    /**
     * 查询总条数
     *
     * @return int 总条数
     * @author: Kun
     * @date: 2018-03-06 14:26
     */
    int selectCount(Map record);

    /**
     * 批量新增
     *
     * @param list
     * @return int
     * @author kun
     * @date 14:29 2018/3/8
     */
    int insertBatch(List<ProductImages> list);

    /**
     * 通过商品ID删除以前发布的图片
     *
     * @param productId 商品ID
     * @return
     * @author kun
     * @date 15:22 2018/3/8
     */
    int deleteOldProductPublishImg(String productId);

    /**
     * 通过商品ID查询商品图片List
     *
     * @param productId 商品ID
     * @return list(String)
     * @author kun
     * @date 19:55 2018/3/14
     */
    List<String> listImageUrlByProductId(String productId);

    /**
     * 通过商品ID查询最新的一张封面图片URL
     *
     * @param productId
     * @return
     * @author John
     * @date： 2018年3月21日 上午10:26:30
     */
    String selectCoverImageUrlByProductId(String productId);

    /**
     * 商品发布 - 通过商品ID查询商品图片
     * @param productId
     * @return productList
     * @author kun
     * @date 11:08 2018/3/29
     */
    List<ProductImages> listImageByProductId(String productId);
}