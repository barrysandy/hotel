package com.zzk.dao;

import java.util.*;
import com.zzk.entity.SellerChooseAttributeInfo;
public interface SellerChooseAttributeInfoMapper {
	
	/**
	 * 删除
	 * @param id 主键ID
	 * @return int 修改行数
	 * @author Kun
	 * @date  2018-03-07 16:05
	 */
    int deleteByPrimaryKey(String id);

	/**
 	 * 新增
	 * @param record 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-07 16:05
	 */
    int insert(SellerChooseAttributeInfo record);

	/**
 	 * 新增(只写入不为NULL的字段)
	 * @param record 实体类
	 * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-07 16:05
	 */
    int insertSelective(SellerChooseAttributeInfo record);

	/**
 	 * 通过主键ID查询
 	 * @param id 实体类
 	 * @return SellerChooseAttributeInfo 实体类
     * @author: Kun
     * @date: 2018-03-07 16:05
	 */
    SellerChooseAttributeInfo selectByPrimaryKey(String id);

	/**
	 * 更新数据(只更新不为NULL的字段)
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-07 16:05
	 */
    int updateByPrimaryKeySelective(SellerChooseAttributeInfo record);

	/**
	 * 更新数据
	 * @param record 实体类
	 * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-07 16:05
	 */
    int updateByPrimaryKey(SellerChooseAttributeInfo record);
    
    /**
	 * 分页查询
	 * @param map 参数MAP
	 * @return List
     * @author: Kun
     * @date: 2018-03-07 16:05
	 */
    List<SellerChooseAttributeInfo> selectByPage(Map map);
	
	/**
	 * 查询总条数
	 * @return int 总条数
     * @author: Kun
     * @date: 2018-03-07 16:05
	 */
    int selectCount(Map record);


    /**
     * 通过商品ID，查询该商品选择的商品扩展属性
     * @param productId 商品ID
     * @return SellerChooseAttributeInfo bean
     * @author kun
     * @date 17:17 2018/3/7
     */
	List<Map<String,Object>> listChooseProductAttrByProductId(String productId);

	/**
	 * 批量新增
	 * @param list 实体类list
	 * @return int
	 * @author kun
	 * @date 10:53 2018/3/12
	 */
	int insertBatch(List<SellerChooseAttributeInfo> list);

	/**
	 * 通过productId删除扩展属性
	 * @param productId 商品ID
	 * @return
	 * @author kun
	 * @date 11:05 2018/3/12
	 */
	int deleteByProductId(String productId);
	/**
	 * 通過productId查詢出改線路的資訊電話
	 * @param productId
	 * @return
	 * @author John
	 * @date： 2018年5月22日 下午2:13:27
	 */
	String selectPhoneNumberByProductId(String productId);
}