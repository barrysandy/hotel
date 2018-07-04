package com.zzk.service;

import com.zzk.entity.OrderBaseInfo;
import com.zzk.util.Page;
import com.zzk.util.PageView;
import com.zzk.util.Result;
import com.zzk.vo.OrderBaseInfoVo;

import java.util.*;

import javax.persistence.criteria.Order;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 订单信息表
 *
 * @author: Kun
 * @date: 2018-03-06 10:45
 */
public interface OrderBaseInfoService {

    /**
     * 分页查询
     *
     * @param map 查询条件
     * @return List
     * @author: Kun
     * @date: 2018-03-06 10:45
     */
    List<OrderBaseInfo> selectByPage(Map map);

    /**
     * 通过主键ID查询
     *
     * @param id 主键ID
     * @return 订单信息表实体类
     * @author: Kun
     * @date: 2018-03-06 10:45
     */
    OrderBaseInfo selectByPrimaryKey(String id);

    /**
     * 查询总条数
     *
     * @param record 查询条件
     * @return 总条数
     * @author: Kun
     * @date: 2018-03-06 10:45
     */
    int selectCount(Map record);

    /**
     * 更新数据
     *
     * @param bean 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-03-06 10:45
     */
    int update(OrderBaseInfo bean);

    /**
     * 新增
     *
     * @param bean 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-03-06 10:45
     */
    int insert(OrderBaseInfo bean);

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author Kun
     * @date 2018-03-06 10:45
     */
    int delete(String id);

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
     * @return
     * @author John
     * @date： 2018年3月13日 下午1:04:11
     */
    String selectOrderDetail(String orderNo);

    /**
     * 生成订单
     *
     * @param order
     * @param skuId
     * @param adult
     * @param child
     * @return
     * @author John
     * @date： 2018年3月13日 下午4:55:55
     */
    String createOrder(OrderBaseInfo order, String stockGoodsId, Integer adult, Integer child);

    /**
     * 通过发团ID查询参团人员信息
     *
     * @param groupId     发团ID
     * @param sort     排序
     * @return list 参团人员list
     * @author kun
     * @date 15:34 2018/3/13
     */
    List<Map<String, Object>> listLineOrderUserInfo(String groupId, String sort);

    /**
     * 买家通过订单号来取消订单
     *
     * @param orderNo
     * @param openId
     * @param request
     * @author John
     * @date： 2018年3月14日 下午2:23:22
     */
    Result<Object> buyerCancel(String orderNo, HttpServletRequest request, String userId);

    /**
     * 退款申请
     *
     * @param orderNo
     * @param request
     * @param userId
     * @param reason
     * @return
     * @author John
     * @date： 2018年3月14日 下午2:43:28
     */
    Result<Object> refundApply(String orderNo, String userId, String reason, HttpServletRequest request);

    /**
     * 商家取消订单
     *
     * @param orderNo
     * @return
     * @author John
     * @date： 2018年3月14日 下午2:58:58
     * @param remarks 
     * @param feekback 
     */
    Result<Object> sellerCancel(String orderNo, String feedback, String remarks, HttpServletRequest request);

    /**
     * 商家确认订单
     *
     * @param orderNo
     * @return
     * @author John
     * @date： 2018年3月14日 下午4:16:02
     */
    Result<Object> sellerConfirm(String orderNo);

    /**
     * 支付
     *
     * @param orderNo
     * @param extra
     * @param request
     * @param redirect
     * @param model
     * @return
     * @author John
     * @date： 2018年3月15日 上午11:04:14
     */
    Result<Object> wxpay(String orderNo, HttpServletRequest request,String channelId);

    /**
     * 支付回调函数
     *
     * @param request
     * @param response
     * @author John
     * @date： 2018年3月16日 下午12:10:17
     */
    void payCallBack(HttpServletRequest request, HttpServletResponse response);

    /**
     * 退款回调函数
     *
     * @param request
     * @param response
     * @author John
     * @date： 2018年3月16日 下午3:04:36
     */
    void refundCallBack(HttpServletRequest request, HttpServletResponse response);

    /**
     * 通过买家ID分页查询
     *
     * @param userId
     * @param pager
     * @param orderState
     * @return
     * @author John
     * @date： 2018年3月20日 上午9:38:20
     */
    Result<Object> selectByBuyerId(String userId, String orderState, Page pager);

    /**
     * 通过订单号查询订单详情 C端 userId作为防止越权
     *
     * @param userId
     * @param orderNo
     * @return
     * @author John
     * @date： 2018年3月20日 上午11:54:21
     */
    Result<Object> selectOrderItemByOrderNo(String userId, String orderNo);

    /**
     * B端同意退款
     *
     * @param request
     * @param feekBack
     * @param amount
     * @param remark
     * @return
     * @author John
     * @date： 2018年3月22日 下午4:35:54
     */
    Result<Object> agreeRefund(HttpServletRequest request, String orderNo, String feekBack, String amount, String remark);

    /**
     * B端拒绝退款
     *
     * @param orderNo
     * @param feekBack
     * @param remark
     * @return
     * @author John
     * @date： 2018年3月22日 下午5:22:30
     */
    Result<Object> refusedRefund(String orderNo, String feedback, String remarks);

    /**
     * 商家获取订单列表
     *
     * @param paraMap
     * @param pageSize
     * @param pageNumber
     * @return
     * @author John
     * @date： 2018年3月27日 下午5:50:06
     */
    PageView<OrderBaseInfoVo> listOrder(Map<String, Object> paraMap, Integer pageNumber, Integer pageSize);

    /**
     * 商户端获取订单详情接口
     *
     * @param orderNo
     * @return
     * @author John
     * @date： 2018年3月28日 上午10:00:43
     */
    Result<Object> selectOrderDetailByOrderNo(String orderNo);

    /**
     * 通过账单号获取对应的订单信息
     *
     * @param billNo
     * @return
     * @author John
     * @date： 2018年4月3日 下午2:53:02
     */
    Result<Object> selectByBillNo(String billId, Page page);

    /**
     * 通过商家id获取首页统计信息
     *
     * @param sellerId
     * @return
     * @author John
     * @date： 2018年4月3日 下午2:53:02
     */
    Map<String, Object> countHomePageData(String sellerId);

    /**
     * 通过商家id获取进7日订单量
     *
     * @param map
     * @return
     * @author John
     * @date： 2018年4月3日 下午2:53:02
     */
    List<Double> selectDailyCountByShopId(Map map, List numList) throws Exception;

    /**
     * 取消订单时恢复原有库存
     *
     * @param orderNo
     * @return
     * @author John
     * @date： 2018年4月17日 下午2:55:08
     */
    int cancelOrderAddStock(String orderNo);

    /**
     * desc每隔5分钟对已开团的订单进行扫描改变状态
     *
     * @author John
     * @date： 2018年4月20日 下午4:29:27
     */
    void orderToStart();

    /**
     * 通过订单编号查询订单
     * @param orderNo
     * @return
     * @author kun
     * @date 11:20 2018/4/23
     */
    OrderBaseInfo getOrderByOrderNo(String orderNo);
    /**
     * 催单
     * @param orderNo
     * @return
     * @author John
     * @date： 2018年5月3日 上午11:33:02
     */
	Result<Object> urgeOrder(String orderNo);
}
