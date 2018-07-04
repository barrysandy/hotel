package com.zzk.service;

import com.zzk.entity.ProductTypeInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 商品分类信息表
 *
 * @author: Kun
 * @date: 2018-03-06 14:35
 */
public interface ProductTypeInfoService {

    /**
     * 分页查询
     *
     * @param map 查询条件
     * @return List
     * @author: Kun
     * @date: 2018-03-06 14:35
     */
    List<ProductTypeInfo> selectByPage(Map map);

    /**
     * 通过主键ID查询
     *
     * @param id 主键ID
     * @return 商品分类信息表实体类
     * @author: Kun
     * @date: 2018-03-06 14:35
     */
    ProductTypeInfo selectByPrimaryKey(String id);

    /**
     * 查询总条数
     *
     * @param record 查询条件
     * @return 总条数
     * @author: Kun
     * @date: 2018-03-06 14:35
     */
    int selectCount(Map record);

    /**
     * 更新数据
     *
     * @param bean 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 14:35
     */
    int update(ProductTypeInfo bean);

    /**
     * 新增
     *
     * @param bean 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 14:35
     */
    int insert(ProductTypeInfo bean);

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author Kun
     * @date 2018-03-06 14:35
     */
    int delete(String id);

    /**
     * @param productId 商品ID
     * @return ProductTypeInfo bean
     * @author kun
     * @date 15:23 2018/3/7
     */
    ProductTypeInfo getByProductId(String productId);

    /**
     * 商品发布-基本信息-商品类型保存
     * @param baseInfo json参数
     * @param productId 商品ID
     * @author kun
     * @date 10:51 2018/3/8
     */
    void saveProductPublishSellerChooseType(String baseInfo, String productId);

    /**
     * 商品发布-基本信息-商品类型保存
     *
     * @param baseInfo json参数
     * @author kun
     * @date 10:51 2018/3/8
     */
    void updateProductPublishSellerChooseType(String baseInfo);
}
