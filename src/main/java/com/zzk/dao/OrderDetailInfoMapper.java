package com.zzk.dao;

import com.zzk.entity.OrderDetailInfo;

import java.util.*;

public interface OrderDetailInfoMapper {

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author Kun
     * @date 2018-03-06 11:01
     */
    int deleteByPrimaryKey(String id);

    /**
     * 新增
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 11:01
     */
    int insert(OrderDetailInfo record);

    /**
     * 新增(只写入不为NULL的字段)
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 11:01
     */
    int insertSelective(OrderDetailInfo record);

    /**
     * 通过主键ID查询
     *
     * @param id 实体类
     * @return OrderDetailInfo 实体类
     * @author: Kun
     * @date: 2018-03-06 11:01
     */
    OrderDetailInfo selectByPrimaryKey(String id);

    /**
     * 更新数据(只更新不为NULL的字段)
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 11:01
     */
    int updateByPrimaryKeySelective(OrderDetailInfo record);

    /**
     * 更新数据
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 11:01
     */
    int updateByPrimaryKey(OrderDetailInfo record);

    /**
     * 分页查询
     *
     * @param map 参数MAP
     * @return List
     * @author: Kun
     * @date: 2018-03-06 11:01
     */
    List<OrderDetailInfo> selectByPage(Map map);

    /**
     * 通过订单号查询
     *
     * @param orderNo
     * @return
     * @author John
     * @date： 2018年3月20日 下午1:19:11
     */
    OrderDetailInfo selectByOrderNo(String orderNo);

    /**
     * 查询总条数
     *
     * @return int 总条数
     * @author: Kun
     * @date: 2018-03-06 11:01
     */
    int selectCount(Map record);

    /**
     * 通过订单编号 查询订单确认后的短信返回内容参数 (哪里出发,共计天,导游电话)
     * @param orderNo
     * @return map
     * @author kun
     * @date 11:08 2018/4/28
     */
    Map<String,Object> getOrderConfirmResponse(String orderNo);

    /**
     * 通过订单号查询商品ID
     * @param orderNo 订单号
     * @return
     * @author kun
     * @date 14:19 2018/5/14
     */
    String getProductIdByOrderNo(String orderNo);
}