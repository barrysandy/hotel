package com.zzk.service;

import java.util.*;

import com.zzk.entity.ProductEvaluate;

/**
 * 商品评论表
 *
 * @author: Kun
 * @date: 2018-03-06 11:45
 */
public interface ProductEvaluateService {

    /**
     * 分页查询
     *
     * @param map 查询条件
     * @return List
     * @author: Kun
     * @date: 2018-03-06 11:45
     */
    List<ProductEvaluate> selectByPage(Map map);

    /**
     * 通过主键ID查询
     *
     * @param id 主键ID
     * @return 商品评论表实体类
     * @author: Kun
     * @date: 2018-03-06 11:45
     */
    ProductEvaluate selectByPrimaryKey(String id);

    /**
     * 查询总条数
     *
     * @param record 查询条件
     * @return 总条数
     * @author: Kun
     * @date: 2018-03-06 11:45
     */
    int selectCount(Map record);

    /**
     * 更新数据
     *
     * @param bean 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 11:45
     */
    int update(ProductEvaluate bean);

    /**
     * 新增
     *
     * @param bean 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 11:45
     */
    int insert(ProductEvaluate bean);

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author Kun
     * @date 2018-03-06 11:45
     */
    int delete(String id);

    /**
     * 获取商家各项评分信息
     * @param sellerId 商家Id
     * @return result
     * @author kun
     * @date 11:54 2018/3/15
     */
    Map<String,Object> getSellerAllScoreInfo(String sellerId);

    /**
     * 查询评论列表
     * @param map 各项参数查看controller
     * @return list
     * @author kun
     * @date 15:34 2018/3/15
     */
    List<Map<String,Object>> getEvaluateList(Map<String,Object> map);
    
    /**
     * 删除评论回复
     * @param evaluateId 评论ID
     * @return int
     * @author kun
     * @date 17:00 2018/3/28
     */
    int deleteReply(String evaluateId);

}
