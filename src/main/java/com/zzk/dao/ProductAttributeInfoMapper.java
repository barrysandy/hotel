package com.zzk.dao;

import java.util.*;

import com.zzk.entity.ProductAttributeInfo;

public interface ProductAttributeInfoMapper {

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author Kun
     * @date 2018-03-06 11:43
     */
    int deleteByPrimaryKey(String id);

    /**
     * 新增
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 11:43
     */
    int insert(ProductAttributeInfo record);

    /**
     * 新增(只写入不为NULL的字段)
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 11:43
     */
    int insertSelective(ProductAttributeInfo record);

    /**
     * 通过主键ID查询
     *
     * @param id 实体类
     * @return ProductAttributeInfo 实体类
     * @author: Kun
     * @date: 2018-03-06 11:43
     */
    ProductAttributeInfo selectByPrimaryKey(String id);

    /**
     * 更新数据(只更新不为NULL的字段)
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 11:43
     */
    int updateByPrimaryKeySelective(ProductAttributeInfo record);

    /**
     * 更新数据
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 11:43
     */
    int updateByPrimaryKey(ProductAttributeInfo record);

    /**
     * 分页查询
     *
     * @param map 参数MAP
     * @return List
     * @author: Kun
     * @date: 2018-03-06 11:43
     */
    List<ProductAttributeInfo> selectByPage(Map map);

    /**
     * 查询总条数
     *
     * @return int 总条数
     * @author: Kun
     * @date: 2018-03-06 11:43
     */
    int selectCount(Map record);

    /**
     * 通过商品类型ID查询平台配置的扩展属性
     *
     * @param categoryId 分类ID
     * @return
     * @author kun
     * @date 17:34 2018/3/7
     */
    List<Map<String, Object>> listProductAttrByCategoryId(String categoryId);

    /**
     * 查询名字为主题的扩展属性(只是一个测试方法,不建议使用)
     * @return string
     * @author kun
     * @date 10:50 2018/3/19
     */
    String getThemeAttribute();

    /**
     * 通过名字查询扩展属性
     * @param name
     * @return map
     * @author kun
     * @date 19:13 2018/3/22
     */
    Map<String,Object> getAttributeByName(String name);
}