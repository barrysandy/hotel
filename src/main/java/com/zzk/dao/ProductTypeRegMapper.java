package com.zzk.dao;

import java.util.*;

import com.zzk.entity.ProductTypeReg;

public interface ProductTypeRegMapper {

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author Kun
     * @date 2018-03-07 15:04
     */
    int deleteByPrimaryKey(String id);

    /**
     * 新增
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-07 15:04
     */
    int insert(ProductTypeReg record);

    /**
     * 新增(只写入不为NULL的字段)
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-07 15:04
     */
    int insertSelective(ProductTypeReg record);

    /**
     * 通过主键ID查询
     *
     * @param id 实体类
     * @return ProductTypeReg 实体类
     * @author: Kun
     * @date: 2018-03-07 15:04
     */
    ProductTypeReg selectByPrimaryKey(String id);

    /**
     * 更新数据(只更新不为NULL的字段)
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-07 15:04
     */
    int updateByPrimaryKeySelective(ProductTypeReg record);

    /**
     * 更新数据
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-07 15:04
     */
    int updateByPrimaryKey(ProductTypeReg record);

    /**
     * 分页查询
     *
     * @param map 参数MAP
     * @return List
     * @author: Kun
     * @date: 2018-03-07 15:04
     */
    List<ProductTypeReg> selectByPage(Map map);

    /**
     * 查询总条数
     *
     * @return int 总条数
     * @author: Kun
     * @date: 2018-03-07 15:04
     */
    int selectCount(Map record);

    /**
     * 通过parentId查询商品分类
     * @param parentId 父类Id
     * @return list
     * @author kun
     * @date 11:20 2018/3/19
     */
    List<Map<String, Object>> listProductTypeByParentId(String parentId);

    /**
     * 查询线路商品的分类名,存放在list(String)中
     * @return list(String)
     * @author kun
     * @date 11:21 2018/3/19
     */
    List<Map<String,Object>> getLineTypeList();
}