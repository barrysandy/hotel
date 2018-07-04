package com.zzk.dao;

import com.zzk.entity.OrderBaseInfo;
import com.zzk.entity.OrderBaseInfoCustom;
import com.zzk.vo.OrderBaseInfoVo;

import java.math.BigDecimal;
import java.util.*;

import org.apache.ibatis.annotations.Param;

public interface OrderBaseInfoMapper {

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author Kun
     * @date 2018-03-06 10:45
     */
    int deleteByPrimaryKey(String id);

    /**
     * 新增
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 10:45
     */
    int insert(OrderBaseInfo record);

    /**
     * 新增(只写入不为NULL的字段)
     *
     * @param record 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 10:45
     */
    int insertSelective(OrderBaseInfo record);

    /**
     * 通过主键ID查询
     *
     * @param id 实体类
     * @return OrderBaseInfo 实体类
     * @author: Kun
     * @date: 2018-03-06 10:45
     */
    OrderBaseInfo selectByPrimaryKey(String id);

    /**
     * 更新数据(只更新不为NULL的字段)
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 10:45
     */
    int updateByPrimaryKeySelective(OrderBaseInfo record);

    /**
     * 更新数据
     *
     * @param record 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 10:45
     */
    int updateByPrimaryKey(OrderBaseInfo record);

    /**
     * 分页查询
     *
     * @param map 参数MAP
     * @return List
     * @author: Kun
     * @date: 2018-03-06 10:45
     */
    List<OrderBaseInfo> selectByPage(Map map);

    /**
     * 查询总条数
     *
     * @return int 总条数
     * @author: Kun
     * @date: 2018-03-06 10:45
     */
    int selectCount(Map record);

    /**
     * 获取商家订单列表接口
     *
     * @param map
     * @return list List<Map>
     * @author kun
     * @date 19:06 2018/3/12
     */
    List<Map<String, Object>> listLineOrderInfo(Map map);

    /**
     * 获取订单详情
     *
     * @param orderNo
     * @return Map
     * @author John
     * @date： 2018年3月13日 下午1:47:47
     */
    Map<String, Object> selectOrderDetail(String orderNo);

    /**
     * 通过发团ID查询参团人员信息
     *
     * @param map 单品ID
     * @return list 参团人员list
     * @author kun
     * @date 15:34 2018/3/13
     */
    List<Map<String, Object>> listLineOrderUserInfo(Map map);

    /**
     * 根据订单号查询订单
     *
     * @param orderNo
     * @return
     * @author John
     * @date： 2018年3月14日 下午2:20:51
     */
    OrderBaseInfo selectByOrderNo(String orderNo);

    /**
     * 根据买家ID查询订单列表信息
     *
     * @param userId
     * @param orderState
     * @return
     * @author John
     * @date： 2018年3月20日 上午9:42:13
     */
    List<OrderBaseInfoCustom> selectByBuyerId(Map<String, Object> map);

    /**
     * 根据订单号查询订单详情
     *
     * @param orderNo
     * @return
     * @author John
     * @date： 2018年3月21日 下午2:49:37
     */
    OrderBaseInfoCustom selectOrderItemMobileByOrderNo(String orderNo);

    /**
     * 商家根据条件获取订单列表
     *
     * @param map
     * @return
     * @author John
     * @date： 2018年3月27日 下午5:28:11
     */
    List<OrderBaseInfoCustom> sellerFetchOrderList(Map map);

    /**
     * 根据订单状态查订单
     *
     * @param orderState
     * @return
     * @author hua
     * @date： 2018年3月27日 下午5:28:11
     */
    List<OrderBaseInfo> selectByOrderState(int orderState);

    /**
     * 根据订单号修改订单状态
     *
     * @param orderState
     * @return
     * @author hua
     * @date： 2018年3月27日 下午5:28:11
     */
    int updateOrderStateByOrderNo(@Param("orderNo") String OrderNo, @Param("orderState") int orderState);

    /**
     * 通过账单号查询
     *
     * @param billNo
     * @return
     * @author John
     * @date： 2018年3月29日 下午4:37:20
     */
    List<OrderBaseInfoCustom> selectByBillNo(String billNo);

    /**
     * 查询时间段内订单量
     *
     * @param sellerId
     * @param startTime
     * @param endTime
     * @return
     * @author John
     * @date： 2018年3月29日 下午4:37:20
     */
    int selectOrderTotal(@Param("sellerId") String sellerId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 查询时间段内销售额
     *
     * @param sellerId
     * @param startTime
     * @param endTime
     * @return
     * @author John
     * @date： 2018年3月29日 下午4:37:20
     */
    BigDecimal selectTotalMoney(@Param("sellerId") String sellerId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 统计进7日交易订单
     *
     * @param map
     * @return
     * @author hua
     * @date： 2018年3月29日 下午4:37:20
     */
    List<Map<String, Object>> selectDailyCountByShopId(Map map);

    /**
     * 定时当开团时间到后将订单状态改变为待评价
     *
     * @param orderNos
     * @return
     * @author John
     * @date： 2018年4月20日 下午4:13:09
     */
    int orderToStart(List<String> orderNos);

    /**
     * 获取开团时间过的人员订单号
     *
     * @return
     * @author John
     * @date： 2018年4月20日 下午4:45:13
     */
    List<String> selectWaitEvaluteOrderNo();
    /**
     * 查询出已经评价了且已开团的订单
     * @return
     * @author John
     * @date： 2018年4月23日 下午3:53:09
     */
    List<String> selectCommentedOrderNo();

    /**
     * 通过订单编号查询订单
     * @param orderNo
     * @return
     * @author kun
     * @date 11:20 2018/4/23
     */
    OrderBaseInfo getOrderByOrderNo(String orderNo);
    /**
     * 将已评论的订单改为已完成
     * @param orderNos
     * @return
     * @author John
     * @date： 2018年4月23日 下午5:21:43
     */
	int orderToFinished(List<String> orderNos);
    
}