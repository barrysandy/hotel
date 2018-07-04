package com.zzk.service;

import java.util.*;

import com.zzk.entity.TripElement;

/**
 * 旅游要素
 *
 * @author: huashuwen
 * @date: 2018-03-10 10:57
 */
public interface TripElementService {

    /**
     * 分页查询
     *
     * @param map 查询条件
     * @return List
     * @author: huashuwen
     * @date: 2018-03-10 10:57
     */
    List<TripElement> selectByPage(Map map);

    /**
     * 通过主键ID查询
     *
     * @param id 主键ID
     * @return 旅游要素实体类
     * @author: huashuwen
     * @date: 2018-03-10 10:57
     */
    TripElement selectByPrimaryKey(String id);

    /**
     * 查询总条数
     *
     * @param record 查询条件
     * @return 总条数
     * @author: huashuwen
     * @date: 2018-03-10 10:57
     */
    int selectCount(Map record);

    /**
     * 更新数据
     *
     * @param bean 实体类
     * @return int 更新生效行数
     * @author: huashuwen
     * @date: 2018-03-10 10:57
     */
    int update(TripElement bean);

    /**
     * 新增
     *
     * @param bean 实体类
     * @return int 新增个数
     * @author: huashuwen
     * @date: 2018-03-10 10:57
     */
    int insert(TripElement bean);

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author huashuwen
     * @date 2018-03-10 10:57
     */
    int delete(String id);

    /**
     * 查询旅游要素信息
     *
     * @param name 名称
     * @return tripElement
     * @author huashuwen
     * @date 2018-03-12 10:57
     */
    TripElement selectByName(String name);

    /**
     * 查询旅游要素信息集合
     *
     * @param list 要素名称集合
     * @return tripElementList 要素对象集合
     * @author huashuwen
     * @date 2018-03-12 10:57
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
