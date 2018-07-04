package com.zzk.service;

import java.util.*;

import com.zzk.entity.SellerChooseAttributeInfo;

/**
 * 商家选择的扩展属性信息表
 *
 * @author: Kun
 * @date: 2018-03-07 16:05
 */
public interface SellerChooseAttributeInfoService {

    /**
     * 分页查询
     *
     * @param map 查询条件
     * @return List
     * @author: Kun
     * @date: 2018-03-07 16:05
     */
    List<SellerChooseAttributeInfo> selectByPage(Map map);

    /**
     * 通过主键ID查询
     *
     * @param id 主键ID
     * @return 商家选择的扩展属性信息表实体类
     * @author: Kun
     * @date: 2018-03-07 16:05
     */
    SellerChooseAttributeInfo selectByPrimaryKey(String id);

    /**
     * 查询总条数
     *
     * @param record 查询条件
     * @return 总条数
     * @author: Kun
     * @date: 2018-03-07 16:05
     */
    int selectCount(Map record);

    /**
     * 更新数据
     *
     * @param bean 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-07 16:05
     */
    int update(SellerChooseAttributeInfo bean);

    /**
     * 新增
     *
     * @param bean 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-07 16:05
     */
    int insert(SellerChooseAttributeInfo bean);

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author Kun
     * @date 2018-03-07 16:05
     */
    int delete(String id);

    /**
     * 通过商品ID，查询该商品选择的商品扩展属性
     *
     * @param productId 商品ID
     * @return SellerChooseAttributeInfo bean
     * @author kun
     * @date 17:17 2018/3/7
     */
    List<Map<String, Object>> listChooseProductAttrByProductId(String productId);

    /**
     * 商品发布-基本信息-扩展属性保存
     * @param productId 商品Id
     * @param baseInfo json参数
     * @author kun
     * @date 10:36 2018/3/12
     */
    void saveProductPublishSellerAttr(String baseInfo, String productId);

    /**
     * 商品发布-基本信息-扩展属性修改
     *
     * @param baseInfo json参数
     * @return
     * @author kun
     * @date 11:00 2018/3/12
     */
    void updateProductPublishSellerAttr(String baseInfo);
}
