package com.zzk.service;

import java.util.*;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.zzk.dao.SellerChooseMessageRemindingMapper;
import com.zzk.entity.SellerChooseMessageReminding;

/**
 * 商家选择消息提醒表
 *
 * @author: Kun
 * @date: 2018-04-03 14:50
 */
@Service("sellerChooseMessageRemindingService")
public class SellerChooseMessageRemindingServiceImpl implements SellerChooseMessageRemindingService {

    @Resource
    private SellerChooseMessageRemindingMapper sellerChooseMessageRemindingMapper;

    /**
     * 分页查询
     */
    @Override
    public List<SellerChooseMessageReminding> selectByPage(String sellerId) {
        return sellerChooseMessageRemindingMapper.selectByPage(sellerId);
    }

    /**
     * 查询总条数
     */
    @Override
    public int selectCount(Map record) {
        return sellerChooseMessageRemindingMapper.selectCount(record);
    }

    /**
     * 主键查询
     */
    @Override
    public SellerChooseMessageReminding selectByPrimaryKey(String id) {
        return sellerChooseMessageRemindingMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     */
    @Override
    public int update(SellerChooseMessageReminding bean) {
        return sellerChooseMessageRemindingMapper.updateByPrimaryKeySelective(bean);
    }

    /**
     * 新增
     */
    @Override
    public int insert(SellerChooseMessageReminding bean) {
        return sellerChooseMessageRemindingMapper.insertSelective(bean);
    }

    /**
     * 逻辑删除
     */
    @Override
    public int delete(String id) {
        SellerChooseMessageReminding bean = sellerChooseMessageRemindingMapper.selectByPrimaryKey(id);
        bean.setStatus(-1);
        return sellerChooseMessageRemindingMapper.updateByPrimaryKey(bean);
    }

    @Override
    public List<Map<String, Object>> listChooseMessageBySellerId(String sellerId) {
        return sellerChooseMessageRemindingMapper.listChooseMessageBySellerId(sellerId);
    }

}
