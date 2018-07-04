package com.zzk.service;

import java.util.*;
import javax.annotation.Resource;

import com.zzk.dao.AuditInfoMapper;
import com.zzk.entity.AuditInfo;
import com.zzk.util.StringUtils;
import org.springframework.stereotype.Service;


/**
 * 审核信息表
 *
 * @author: Kun
 * @date: 2018-03-06 10:14
 */
@Service("auditInfoService")
public class AuditInfoServiceImpl implements AuditInfoService {

    @Resource
    private AuditInfoMapper auditInfoMapper;

    /**
     * 分页查询
     */
    @Override
    public List<AuditInfo> selectByPage(Map map) {
        return auditInfoMapper.selectByPage(map);
    }

    /**
     * 查询总条数
     */
    @Override
    public int selectCount(Map record) {
        return auditInfoMapper.selectCount(record);
    }

    /**
     * 主键查询
     */
    @Override
    public AuditInfo selectByPrimaryKey(String id) {
        return auditInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新
     */
    @Override
    public int update(AuditInfo bean) {
        return auditInfoMapper.updateByPrimaryKeySelective(bean);
    }

    /**
     * 新增
     */
    @Override
    public int insert(AuditInfo bean) {
        return auditInfoMapper.insertSelective(bean);
    }

    /**
     * 逻辑删除
     */
    @Override
    public int delete(String id) {
        AuditInfo bean = auditInfoMapper.selectByPrimaryKey(id);
        bean.setStatus(-1);
        return auditInfoMapper.updateByPrimaryKey(bean);
    }

    @Override
    public void saveProductPublishAuditInfo(String productId) {

        AuditInfo auditInfo = new AuditInfo();
        auditInfo.setId(StringUtils.getUUID());
        auditInfo.setObjectId(productId);
        auditInfo.setObjectType(1);
        /*0=待审核 1=审核通过 2=审核不通过 3=系统自动审核通过*/
        auditInfo.setAuditState(0);
        auditInfo.setStatus(1);
        auditInfo.setCreateTime(new Date());
        auditInfoMapper.insertSelective(auditInfo);
    }

    @Override
    public void updateProductPublishAuditInfo(String productId) {
        AuditInfo auditInfo = auditInfoMapper.getByObjectId(productId);
        /*0=待审核 1=审核通过 2=审核不通过 3=系统自动审核通过*/
        if (auditInfo != null) {
            auditInfo.setAuditState(0);
            auditInfo.setUpdateTime(new Date());
            auditInfoMapper.updateByPrimaryKeySelective(auditInfo);
        }
    }

}
