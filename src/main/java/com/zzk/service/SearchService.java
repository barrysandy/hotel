/**   
* @Description:  
* @author sty   
* @date 2017年3月21日 下午2:13:34 
* @version V1.0   
*/
package com.zzk.service;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.Map;

import com.zzk.util.Page;


public interface SearchService {
	

	/**
	 * @Description: 产品搜索方法
	 * @author sty
	 * @param type 产品类型
	 * @param categoryId 产品类型ID
	 * @param parameters 参数值
	 * @param attributes 属性
	 * @param start 起始条目
	 * @param size 返回数量
	 * @param pager 分页对象
	 * @param sortFiled 排序字段
	 * @param sortType 排序类型
	 * @throws UnknownHostException 
	 */
	public int search(String type,Map<String,Object> parameters,String starLevel,Double lat,Double lon,int start,int size, Page pager,Integer miniPrice,Integer maxPrice,String checkinDate,String leaveDate) throws UnknownHostException;
	/**
	 * @Description: 产品搜索方法
	 * @author huashuwen
	 * @param parameters 参数值
	 * @param attributes 属性
	 * @param start 起始条目
	 * @param size 返回数量
	 * @param pager 分页对象
	 * @param sortFiled 排序字段
	 * @param sortType 排序类型
	 * @throws UnknownHostException 
	 */
	public int search1(Map<String,Object> parameters,String starLevel,Double lat,Double lon,int start,int size, Page pager,Integer miniPrice,Integer maxPrice,Date checkinDate,Date leaveDate)throws Exception;
}
