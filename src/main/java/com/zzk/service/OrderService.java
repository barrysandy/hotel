package com.zzk.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zzk.util.Page;
import com.zzk.util.PageView;
import com.zzk.util.Result;
import com.zzk.vo.OrderBaseInfoVo;
import com.zzk.entity.Order;
import com.zzk.entity.OrderBaseInfo;
import com.zzk.entity.OrderCustom;
import com.zzk.entity.OrderCustomDo;
/**
 *
 * 订单信息
 * @author：sty
 * @date：2017-11-02 10:39
 */
public interface OrderService {
    
    /**
     * 分页条件查询
     * @param map
     * @return
     */
    PageView<OrderCustomDo> selectOrderByPage(Map map);
    /**
     *
     * 详情
     * @author：sty
     * @date：2017-11-02 10:39
     */
    Order selectByPrimaryKey(String orderId);
    /**
     *
     * 描述方法作用
     * @return
     * @author：sty
     * @date：2017-11-02 10:39
     */
    int selectCount(Map record);

    /**
     * 更新数据
     * @param bean
     * @return
     * @author：sty
     * @date：2017-11-02 10:39
     */
    public int update(Order bean);

    /**
     *
     * 新增
     * @param bean
     * @return
     * @author：sty
     * @date：2017-11-02 10:39
     */
    public int insert(Order bean);
    /**
     *
     * 删除
     * @param id
     * @return
     * @return
     * @author lishiqiang
     * @date 2017-3-16
     * modify history
     */
    public int delete(String id);
    /**
     *
     * 查询在住日期在comeTime-leaveTime内的订单
     * @param comeTime
     * @param leaveTime
     * @return
     * @author huashuwen
     * @date 2017-11-14
     */
    public List<Order> selectOrderInRange(Order bean);
    /**
     *
     * 分页查询买家的订单
     * @param map
     * @return
     * @author huashuwen
     * @date 2017-11-14
     */
    public List<Order> selectOrderByBuyerId(Map map);
    /**
     *
     * 分页查询买家的订单
     * @param map
     * @return
     * @author huashuwen
     * @date 2017-11-14
     */
    public PageView<OrderBaseInfo> selectOrderByBuyerIdNew(Map map,int pageNum,int pageSize);
    /**
     *
     * 卖家分页查询订单
     * @param map
     * @return
     * @author huashuwen
     * @date 2017-11-14
     */
    public List<Order> selectOrderByShopId(Map map);
    /**
     *
     * 卖家分页查询总条数
     * @param map
     * @return
     * @author huashuwen
     * @date 2017-11-14
     */
    public int selectCountByShopId(Map map);

    /**
     *
     * 订单号查询订单
     * @param
     * @return
     * @author huashuwen
     * @date 2017-11-16
     */
    public Order  selectByOrderNum(String orderNum);

    /**
     *
     * 查询某酒店时间范围内全部订单
     * @param bean
     * @return
     * @author huashuwen
     * @date 2017-11-16
     */
    public List<HashMap<String,Object>> selectByHotelId(Order bean);
    /**
     *
     * 更新成未付款超时
     * @param
     * @return
     * @author huashuwen
     * @date 2017-11-16
     */
    public void updateOrderNotPay();
    /**
     *
     * 更新成默认结束
     * @param
     * @return
     * @author huashuwen
     * @date 2017-11-16
     */
    public void updateOrderDefaultEnd();

    /**
     * 查询最新的一条ORDER
     * @param userId
     * @return
     */
    public Order selectRecendOrderByUserId(String shopId);

    /**
     * 买家取消订单（线路列表调用）
     * @param orderId
     * @return
     */
    public Result<Object> buyerCancel(String orderNo,HttpServletRequest request,String userId);
    
    public OrderCustom selectOrderDetail(String orderId);
    /**
     * 查询待处理订单
     * @param shopId
     * @return
     * @author John
     */
    public List<OrderCustom> selectSuspOrder(String shopId);
    /**
     * 每日订单详情
     * @param shopId
     * @return
     * @author John
     */
    public Map<String,String> selectDailyByshopId(String shopId);

    /**
     * 根据房型集合查询所有订单
     * @param shopId
     * @return
     * @author huashuwen
     */
    public List<HashMap<String,Object>> selectByRoomtypeList(List<String> roomtypeList,Date beginDate,Date endDate);
    /**
     * 查询酒店下每个房型历史销售总数
     * @param shopId
     * @return
     * @author huashuwen
     */
    public List<HashMap<String,Object>> selectSaleNumMap(String hotelId);

    /**
     * 通过账单号查询订单
     * @param billNo
     * @return
     */
    public PageView<OrderBaseInfo> selectByBillId(String billId,int pageNum,int pageSize);

    public void updateOrderCheckIn();

    public List<Map<String,Object>> selectDailyCountByShopId(Map map);


    public List<Order> selectAll();

    public Order orderBaseInfo2Order(OrderBaseInfo a);
    public OrderBaseInfo order2orderBaseInfo(Order a);
    public OrderCustom orderCustomDo2orderCustom(OrderCustomDo a);
    
    public PageView<OrderBaseInfoVo> selectByBuyerId(String userId, String orderState, Page pager);
    public void dealNotConfirm();
    public int ableCancel(Order order);
    /**
     * 退款申请
     */
    public Result<Object> refundApply(String orderNo, String userId, String reason);
}
