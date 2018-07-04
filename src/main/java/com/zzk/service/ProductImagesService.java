package com.zzk.service;

import java.util.*;

import com.zzk.entity.ProductImages;

/**
 * 商品图片表
 *
 * @author: Kun
 * @date: 2018-03-06 14:26
 */
public interface ProductImagesService {

    /**
     * 分页查询
     *
     * @param map 查询条件
     * @return List
     * @author: Kun
     * @date: 2018-03-06 14:26
     */
    List<ProductImages> selectByPage(Map map);

    /**
     * 通过主键ID查询
     *
     * @param id 主键ID
     * @return 商品图片表实体类
     * @author: Kun
     * @date: 2018-03-06 14:26
     */
    ProductImages selectByPrimaryKey(String id);

    /**
     * 查询总条数
     *
     * @param record 查询条件
     * @return 总条数
     * @author: Kun
     * @date: 2018-03-06 14:26
     */
    int selectCount(Map record);

    /**
     * 更新数据
     *
     * @param bean 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 14:26
     */
    int update(ProductImages bean);

    /**
     * 新增
     *
     * @param bean 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 14:26
     */
    int insert(ProductImages bean);

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author Kun
     * @date 2018-03-06 14:26
     */
    int delete(String id);

    /**
     * 商品发布-基本信息-图片保存
     * @param productId 商品ID
     * @param baseInfo json参数
     * @author kun
     * @date 11:48 2018/3/8
     */
    void saveProductPublishProductImg(String baseInfo, String productId);

    /**
     * 商品发布-基本信息-图片保存
     *
     * @param baseInfo json参数
     * @author kun
     * @date 15:20 2018/3/8
     */
    void updateProductPublishProductImg(String baseInfo);

    /**
     * 商品发布 - 通过商品ID查询商品图片
     * @param productId
     * @return productList
     * @author kun
     * @date 11:08 2018/3/29
     */
    List<ProductImages> listImageByProductId(String productId);
}
