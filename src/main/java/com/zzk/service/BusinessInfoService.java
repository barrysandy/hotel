package com.zzk.service;

import java.util.*;

import com.zzk.entity.BusinessInfo;
/**
 * 用户表
 * @author: wangpeng
 * @date: 2018-03-12 18:16
 */
public interface BusinessInfoService {

	/**
	 * 分页查询
 	 * @param map 查询条件
	 * @return List
     * @author: wangpeng
     * @date: 2018-03-12 18:16
	 */
	List<BusinessInfo> selectByPage(Map map);
	
	/**
 	 * 通过主键ID查询
 	 * @param id 主键ID
	 * @return 商户表实体类
     * @author: wangpeng
     * @date: 2018-03-12 18:16
	 */
	BusinessInfo selectByPrimaryKey(String id);
	
	/**
	 * 查询总条数
	 * @param record 查询条件
	 * @return 总条数
     * @author: wangpeng
     * @date: 2018-03-12 18:16
	 */
	int selectCount(Map record);
	
	/**
	 * 更新数据
	 * @param bean 实体类
	 * @return int 更新生效行数
     * @author: wangpeng
     * @date: 2018-03-12 18:16
	 */
	 int update(BusinessInfo bean);
	
	/**
 	 * 新增
	 * @param bean 实体类
	 * @return int 新增个数
     * @author: wangpeng
     * @date: 2018-03-12 18:16
	 */
	 int insert(BusinessInfo bean);
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author wangpeng
	 * @date  2018-03-12 18:16
	 */
	 int delete(String id);
	/**
	 * 通过ID查询商户详情
	 * @param id
	 * @return
	 * @author John
	 * @date： 2018年3月12日 下午6:59:42
	 */
	 String selectByPrimaryKeyInfo(String id);
	 /**
	  * 修改商户信息
	  * @param business
	  * @return
	  * @author John
	  * @date： 2018年3月12日 下午7:28:14
	  */
	 String updateBusinessInfo(BusinessInfo business);
	 /**
		 * 
		* 获取省份列表
		* @return
		* @author huashuwen
		* @date 2017-11-03
		 */
		public List<Map<String,Object>> getProvinceList();
		/**
		 * 
		* 根据省获取城市列表
		* @param provinceId
		* @return
		* @author huashuwen
		* @date 2017-11-03
		 */
		public List<Map<String,Object>> getCityList(String provinceId);
		
		/**
		 * 
		* 根据城市获取地区列表
		* @param cityId
		* @return
		* @author huashuwen
		* @date 2017-11-03
		 */
		public List<Map<String,Object>> getAreaList(String cityId);
		
		/**
		 * 
		* 查询城市
		* @param cityId
		* @return
		* @author huashuwen
		* @date 2017-11-03
		 */
		public String selecCity(String cityId);
		/**
		 * 
		* 查询省份
		* @param provinceId
		* @return
		* @author huashuwen
		* @date 2017-11-03
		 */
		public String selecProvince(String provinceId);
		/**
		 * 
		* 查询地区
		* @param areaId
		* @return
		* @author huashuwen
		* @date 2017-11-03
		 */
		public String selectArea(String areaId);
		
	 
}
