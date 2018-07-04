package com.zzk.service;

import java.util.List;
import java.util.Map;

import com.zzk.util.PageView;
import com.zzk.entity.Evaluate;
import com.zzk.entity.EvaluateCustom;
import com.zzk.entity.ProductEvaluate;
 
public interface EvaluateService {
	/**
	 * 查询所有的评论及回复 根据 userID
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> selectAllEvaluate(Map<String,Object> map);
	/**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(String id);
    /**
     * 新增数据
     * @param record
     * @return
     */
    int insert(ProductEvaluate record);
   
     /**
      * 根据主键查找Evaluate
      * @param id
      * @return
      */
    Evaluate selectByPrimaryKey(String id);
    /**
     * 条件查询所有的评论
     * @param map
     * @return
     */
    List<Map<String,Object>> selectByPage(Map map);
    /**
     * 查询所有的回复
     * @param map
     * @return
     */
    List<EvaluateCustom> selectByPageReply(Map map);
     /**
      * 根据主键更新非空
      * @param record
      * @return
      */
    int updateByPrimaryKeySelective(Evaluate record);
      /**
       * 根据主键更新包含BLOB
       * @param record
       * @return
       */
    int updateByPrimaryKeyWithBLOBs(Evaluate record);
        /**
         * 更新不包含BLOB
         * @param record
         * @return
         */
    int updateByPrimaryKey(Evaluate record);
    /**
     * 将评或回复志为删除
     * @param parentId
     * @return
     */
    int delete(String parentId);
    /**
     * 修改评论根据ID
     * @param map
     * @return
     */
    int updateEvaluate(Evaluate evaluate);
    List<EvaluateCustom> selectScore(Map map);
    /**
     * 增加回复
     * @param record
     * @return
     */
	int insertReplyEvaluate(Evaluate record);
	
    /**
     * 增加评论
     * @param record
     * @return
     */
	int insertEvaluate(Evaluate record);
	/**
	 * 取得各项数据综合
	 * @param shopId
	 * @return
	 */
	EvaluateCustom selectCountByshopId(String shopId);
	
    
    
    
	
}
