package com.zzk.service;

import java.util.*;

import com.zzk.entity.MessageRemindingConfig;

/**
 * 消息提醒配置表
 *
 * @author: Kun
 * @date: 2018-04-03 14:45
 */
public interface MessageRemindingConfigService {

    /**
     * 分页查询
     *
     * @param map 查询条件
     * @return List
     * @author: Kun
     * @date: 2018-04-03 14:45
     */
    List<MessageRemindingConfig> selectByPage(Map map);

    /**
     * 通过主键ID查询
     *
     * @param id 主键ID
     * @return 消息提醒配置表实体类
     * @author: Kun
     * @date: 2018-04-03 14:45
     */
    MessageRemindingConfig selectByPrimaryKey(String id);

    /**
     * 查询总条数
     *
     * @param record 查询条件
     * @return 总条数
     * @author: Kun
     * @date: 2018-04-03 14:45
     */
    int selectCount(Map record);

    /**
     * 更新数据
     *
     * @param bean 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-04-03 14:45
     */
    int update(MessageRemindingConfig bean);

    /**
     * 新增
     *
     * @param bean 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-04-03 14:45
     */
    int insert(MessageRemindingConfig bean);

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author Kun
     * @date 2018-04-03 14:45
     */
    int delete(String id);

    /**
     * 查询消息提醒平台配置表
     * @return list
     * @author kun
     * @date 16:20 2018/4/3
     */
    List<Map<String,Object>> listMessageRemindConfig();
}
