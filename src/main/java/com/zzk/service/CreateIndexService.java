/**   
* @Description:  
* @author sty   
* @date 2017年3月20日 下午7:33:51 
* @version V1.0   
*/
package com.zzk.service;

import java.net.UnknownHostException;

public interface CreateIndexService {
	/**
	 * <p>description:导入所有酒店数据</p>
	 * @param 
	 * @return int
	 * @date 2017-7-6上午11:14:54
	 */
	public int createAllHotelIndex() throws UnknownHostException, Exception;
	/**
	 * <p>description:保存一条酒店数据</p>
	 * @param 
	 * @return int
	 * @date 2017-7-6上午11:14:54
	 */
	public int createIndexByHotelId(String hotelId) throws Exception;
	/*
	*//**
	 * <p>description:保存一条商品数据</p>
	 * @param 
	 * @return int
	 * @date 2017-7-6上午11:47:08
	 *//*
	public int createIndexByProductId(String productId) throws UnknownHostException;
	
	*//**
	 * <p>description:初始化搜索服务数据结构，创建索引</p>
	 * @param 
	 * @return void
	 * @date 2017-7-6上午10:56:03
	 */
	void initSearchEngine() throws Exception;
	/**
	 * <p>description:创建一条内容索引</p>
	 * @param 内容ID
	 * @return boolean
	 * @date 2017-7-6下午2:08:59
	 *//*
	public boolean createContentIndexById(String id) throws UnknownHostException;
	*//**
	 * <p>description:创建所有内容索引</p>
	 * @param 
	 * @return boolean
	 * @date 2017-7-6下午2:09:09
	 *//*
	public boolean createAllContentIndex() throws UnknownHostException, Exception;

	*//**
	 * @Description:  创建活动全部索引
	 * @author sty
	 *//*
	boolean createAllActivityIndex() throws Exception;

	*//**
	 * @Description:  创建一条活动数据
	 * @author sty
	 *//*
	boolean createActivityIndexById(String id) throws UnknownHostException;
	*//**
	 * <p>description:创建所有目的地数据</p>
	 * @param 
	 * @return void
	 * @author Wen Yugang
	 * @throws UnknownHostException 
	 * @date 2017-8-1上午10:07:58
	 *//*
	public boolean createAllDestinationIndex() throws UnknownHostException;
	*//**
	 * <p>description:创建商品数据通过商家ID，主要是用于开店关店时，统一处理数据状态，避免关店情况下商品被搜索的情况</p>
	 * @param 
	 * @return void
	 * @author Wen Yugang
	 * @throws UnknownHostException 
	 * @date 2017-8-2下午4:21:26
	 *//*
	public int createOrUpdateProductByShopId(String shopId) throws UnknownHostException;*/
	
	
}
