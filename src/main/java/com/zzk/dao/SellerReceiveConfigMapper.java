package com.zzk.dao;

import java.util.*;

import com.zzk.entity.SellerReceiveConfig;

public interface SellerReceiveConfigMapper {

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author Kun
     * @date 2018-04-03 15:12
     */
    int deleteByPrimaryKey(String id);

    /**
     * 新增
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-04-03 15:12
     */
    int insert(SellerReceiveConfig record);

    /**
     * 新增(只写入不为NULL的字段)
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-04-03 15:12
     */
    int insertSelective(SellerReceiveConfig record);

    /**
     * 通过主键ID查询
     *
     * @param id 实体类
     * @return SellerReceiveConfig 实体类
     * @author: Kun
     * @date: 2018-04-03 15:12
     */
    SellerReceiveConfig selectByPrimaryKey(String id);

    /**
     * 更新数据(只更新不为NULL的字段)
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-04-03 15:12
     */
    int updateByPrimaryKeySelective(SellerReceiveConfig record);

    /**
     * 更新数据
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-04-03 15:12
     */
    int updateByPrimaryKey(SellerReceiveConfig record);

    /**
     * 分页查询
     *
     * @param map 参数MAP
     * @return List
     * @author: Kun
     * @date: 2018-04-03 15:12
     */
    List<SellerReceiveConfig> selectByPage(Map map);

    /**
     * 查询总条数
     *
     * @return int 总条数
     * @author: Kun
     * @date: 2018-04-03 15:12
     */
    int selectCount(Map record);

    /**
     * 通过sellerId查询商家消息接受设置
     * @param sellerId 商家ID
     * @return map
     * @author kun
     * @date 15:31 2018/4/3
     */
    Map<String,Object> selectBySellerId(String sellerId);

    /**
     * 通过sellerId查询商家消息接受设置
     * @param sellerId 商家ID
     * @return bean
     * @author kun
     * @date 15:31 2018/4/3
     */
    SellerReceiveConfig getBySellerId(String sellerId);
}