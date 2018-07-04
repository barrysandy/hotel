package com.zzk.dao;

import java.util.*;

import com.zzk.entity.MessageRemindingConfig;

public interface MessageRemindingConfigMapper {

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author Kun
     * @date 2018-04-03 14:45
     */
    int deleteByPrimaryKey(String id);

    /**
     * 新增
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-04-03 14:45
     */
    int insert(MessageRemindingConfig record);

    /**
     * 新增(只写入不为NULL的字段)
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-04-03 14:45
     */
    int insertSelective(MessageRemindingConfig record);

    /**
     * 通过主键ID查询
     *
     * @param id 实体类
     * @return MessageRemindingConfig 实体类
     * @author: Kun
     * @date: 2018-04-03 14:45
     */
    MessageRemindingConfig selectByPrimaryKey(String id);

    /**
     * 更新数据(只更新不为NULL的字段)
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-04-03 14:45
     */
    int updateByPrimaryKeySelective(MessageRemindingConfig record);

    /**
     * 更新数据
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-04-03 14:45
     */
    int updateByPrimaryKey(MessageRemindingConfig record);

    /**
     * 分页查询
     *
     * @param map 参数MAP
     * @return List
     * @author: Kun
     * @date: 2018-04-03 14:45
     */
    List<MessageRemindingConfig> selectByPage(Map map);

    /**
     * 查询总条数
     *
     * @return int 总条数
     * @author: Kun
     * @date: 2018-04-03 14:45
     */
    int selectCount(Map record);

    /**
     * 查询消息提醒平台配置表
     * @return list
     * @author kun
     * @date 16:20 2018/4/3
     */
    List<Map<String,Object>> listMessageRemindConfig();
}