package com.zzk.service;

import java.util.*;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.zzk.dao.MessageRemindingConfigMapper;
import com.zzk.entity.MessageRemindingConfig;

/**
 * 消息提醒配置表
 *
 * @author: Kun
 * @date: 2018-04-03 14:45
 */
@Service("messageRemindingConfigService")
public class MessageRemindingConfigServiceImpl implements MessageRemindingConfigService {

    @Resource
    private MessageRemindingConfigMapper messageRemindingConfigMapper;

    /**
     * 分页查询
     */
    @Override
    public List<MessageRemindingConfig> selectByPage(Map map) {
        return messageRemindingConfigMapper.selectByPage(map);
    }

    /**
     * 查询总条数
     */
    @Override
    public int selectCount(Map record) {
        return messageRemindingConfigMapper.selectCount(record);
    }

    /**
     * 主键查询
     */
    @Override
    public MessageRemindingConfig selectByPrimaryKey(String id) {
        return messageRemindingConfigMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     */
    @Override
    public int update(MessageRemindingConfig bean) {
        return messageRemindingConfigMapper.updateByPrimaryKeySelective(bean);
    }

    /**
     * 新增
     */
    @Override
    public int insert(MessageRemindingConfig bean) {
        return messageRemindingConfigMapper.insertSelective(bean);
    }

    /**
     * 逻辑删除
     */
    @Override
    public int delete(String id) {
        MessageRemindingConfig bean = messageRemindingConfigMapper.selectByPrimaryKey(id);
        bean.setStatus(-1);
        return messageRemindingConfigMapper.updateByPrimaryKey(bean);
    }

    @Override
    public List<Map<String, Object>> listMessageRemindConfig() {
        return messageRemindingConfigMapper.listMessageRemindConfig();
    }

}
