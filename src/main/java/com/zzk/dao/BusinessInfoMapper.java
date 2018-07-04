package com.zzk.dao;

import java.util.*;

import com.zzk.entity.BusinessInfo;
import com.zzk.vo.BusinessAllInfoVo;
public interface BusinessInfoMapper {
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author wangpeng
	 * @date  2018-03-12 18:16
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-03-12 18:16
	 */
    int insert(BusinessInfo record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-03-12 18:16
	 */
    int insertSelective(BusinessInfo record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return BusinessInfo 实体类
     * @author: wangpeng
     * @date: 2018-03-12 18:16
	 */
    BusinessInfo selectByPrimaryKey(String id);
    
    /**
 	 * 通过主键ID查询商户全部信息
 	 * @param id 实体类
 	 * @return BusinessAllInfoVo
     * @author: wangpeng
     * @date: 2018-03-12 18:16
	 */
    BusinessAllInfoVo selectAllInfoByPrimaryKey(String id);


	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-03-12 18:16
	 */
    int updateByPrimaryKeySelective(BusinessInfo record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-03-12 18:16
	 */
    int updateByPrimaryKey(BusinessInfo record);
    /**
     * 查询出所有商户ID
     * @return
     * @author John
     * @date： 2018年3月29日 下午8:05:38
     */
    List<String> selectAllBusinessId();
    
    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: wangpeng
     * @date: 2018-03-12 18:16
	 */
    List<BusinessInfo> selectByPage(Map map);
	
	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: wangpeng
     * @date: 2018-03-12 18:16
	 */
    int selectCount(Map record);
    
    /**
     * 省市区相关查询
     * @return
     */
    List<Map<String,Object>> getProvinceList();
    
    List<Map<String,Object>> getCityList(String provinceId);
    
    List<Map<String,Object>> getAreaList(String cityId);
    
    String selectCity(String cityId);
    
    String selectProvince(String provinceId);
    
    String selectArea(String areaId);
}