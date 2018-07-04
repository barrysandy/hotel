package com.zzk.service;

import java.util.*;

import com.zzk.entity.ProductStockInfo;

/**
 * 库存表
 *
 * @author: Kun
 * @date: 2018-03-06 15:34
 */
public interface ProductStockInfoService {

    /**
     * 分页查询
     *
     * @param map 查询条件
     * @return List
     * @author: Kun
     * @date: 2018-03-06 15:34
     */
    List<ProductStockInfo> selectByPage(Map map);

    /**
     * 通过主键ID查询
     *
     * @param id 主键ID
     * @return 库存表实体类
     * @author: Kun
     * @date: 2018-03-06 15:34
     */
    ProductStockInfo selectByPrimaryKey(String id);

    /**
     * 查询总条数
     *
     * @param record 查询条件
     * @return 总条数
     * @author: Kun
     * @date: 2018-03-06 15:34
     */
    int selectCount(Map record);

    /**
     * 更新数据
     *
     * @param bean 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 15:34
     */
    int update(ProductStockInfo bean);

    /**
     * 新增
     *
     * @param bean 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 15:34
     */
    int insert(ProductStockInfo bean);

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author Kun
     * @date 2018-03-06 15:34
     */
    int delete(String id);

}
