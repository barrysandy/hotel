package com.zzk.dao;

import java.util.*;
import com.zzk.entity.QualificationInfo;
public interface QualificationInfoMapper {
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author wangpeng
	 * @date  2018-03-12 18:22
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-03-12 18:22
	 */
    int insert(QualificationInfo record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-03-12 18:22
	 */
    int insertSelective(QualificationInfo record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return QualificationInfo 实体类
     * @author: wangpeng
     * @date: 2018-03-12 18:22
	 */
    QualificationInfo selectByPrimaryKey(String id);
    
    /**
 	 * 通过外键ID查询
 	 * @param id 实体类
 	 * @return QualificationInfo 实体类
     * @author: hua
     * @date: 2018-03-12 18:22
	 */
    QualificationInfo selectByMerchantId(String merchantId);
    
	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-03-12 18:22
	 */
    int updateByPrimaryKeySelective(QualificationInfo record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-03-12 18:22
	 */
    int updateByPrimaryKey(QualificationInfo record);
    
    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: wangpeng
     * @date: 2018-03-12 18:22
	 */
    List<QualificationInfo> selectByPage(Map map);
	
	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: wangpeng
     * @date: 2018-03-12 18:22
	 */
    int selectCount(Map record);
}