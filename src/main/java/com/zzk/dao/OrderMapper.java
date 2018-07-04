package com.zzk.dao;

import com.zzk.entity.Order;
import com.zzk.entity.OrderBaseInfo;
import com.zzk.entity.OrderBaseInfoCustom;
import com.zzk.entity.OrderCustomDo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
	//
    int deleteByPrimaryKey(String orderId);
    //
    int insert(OrderBaseInfo record);
    //
    OrderBaseInfo selectByPrimaryKey(String orderId);

    //
    int updateByPrimaryKey(OrderBaseInfo record);
    
    //
    List<OrderCustomDo> selectOrderByPage(Map map);
    
    List<OrderCustomDo> selectByOrderState(int OrderState);
    
	//
    int selectCount(Map record);
    //
    List<OrderBaseInfo> selectOrderInRange(OrderBaseInfo bean);
    /**
     * 通过买家Id查询  暂时废弃
     */
    List<Order> selectOrderByBuyerId(Map map);
    //
    List<OrderBaseInfo> selectOrderByBuyerIdNew(Map map);
    //
    List<OrderBaseInfo> selectOrderByShopId(Map map);
    //
    List<OrderBaseInfo> selectByBillId(String billId);
    //    
    int selectCountByShopId(Map map);
    //
    List<HashMap<String,Object>> selectOrderByHotelId(OrderBaseInfo bean);
    //
    List<HashMap<String,Object>> selectByRoomtypeList(Map map);
    //
    int updateOrderNotPay(Date date);
    //
    int updateOrderDefaultEnd(Date date);
    //
    int updateOrderCheckIn();
    //
    OrderBaseInfo selectByOrderNum(String orderNum);
    //
    OrderBaseInfo selectRecentOrderByUserId(String shopId);
    //
    OrderCustomDo selectOrderDetail(String orderId);
    //
    List<OrderCustomDo> selectSuspOrder(String shopId);
    //
    Map<String,String> selectDailyByshopId(Map map);

    //
    List<HashMap<String, Object>> selectSaleNumMap(String hotelId);

    //
    List<OrderBaseInfo> selectAll();
    //
    List<Map<String, Object>> selectDailyCountByShopId(Map map);

    OrderBaseInfo selectByPayNum(String payNum);
}