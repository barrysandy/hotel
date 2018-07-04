package com.zzk.dao;

import java.util.*;

import com.zzk.entity.TripElement;

public interface TripElementMapper {

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author huashuwen
     * @date 2018-03-10 10:57
     */
    int deleteByPrimaryKey(String id);

    /**
     * 新增
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: huashuwen
     * @date: 2018-03-10 10:57
     */
    int insert(TripElement record);

    /**
     * 新增(只写入不为NULL的字段)
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: huashuwen
     * @date: 2018-03-10 10:57
     */
    int insertSelective(TripElement record);

    /**
     * 通过主键ID查询
     *
     * @param id 实体类
     * @return TripElement 实体类
     * @author: huashuwen
     * @date: 2018-03-10 10:57
     */
    TripElement selectByPrimaryKey(String id);

    /**
     * 更新数据(只更新不为NULL的字段)
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: huashuwen
     * @date: 2018-03-10 10:57
     */
    int updateByPrimaryKeySelective(TripElement record);

    /**
     * 更新数据
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: huashuwen
     * @date: 2018-03-10 10:57
     */
    int updateByPrimaryKey(TripElement record);

    /**
     * 分页查询
     *
     * @param map 参数MAP
     * @return List
     * @author: huashuwen
     * @date: 2018-03-10 10:57
     */
    List<TripElement> selectByPage(Map map);

    /**
     * 查询总条数
     *
     * @return int 总条数
     * @author: huashuwen
     * @date: 2018-03-10 10:57
     */
    int selectCount(Map record);

    /**
     * 查询全部名称
     *
     * @return list
     * @author: huashuwen
     * @date: 2018-03-10 10:57
     */
    List<String> selectAll();

    /**
     * 根据名称查询bean
     *
     * @return bean
     * @author: huashuwen
     * @date: 2018-03-10 10:57
     */
    TripElement selectByName(String name);

    /**
     * 根据名称查询要素集合
     *
     * @return list
     * @author: huashuwen
     * @date: 2018-03-10 10:57
     */
    List<Map<String, Object>> selectByList(List<String> list);


    /**
     * 通过名字查询要素
     * @param name name
     * @return map
     * @author kun
     * @date 15:14 2018/4/12
     */
    Map<String,Object> selectOneByName(String name);

}