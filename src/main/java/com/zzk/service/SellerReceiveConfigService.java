package com.zzk.service;

import java.util.*;

import com.zzk.entity.OrderBaseInfo;
import com.zzk.entity.RefundInfo;
import com.zzk.entity.SellerReceiveConfig;

/**
 * 商家消息接收设置表
 *
 * @author: Kun
 * @date: 2018-04-03 15:12
 */
public interface SellerReceiveConfigService {

    /**
     * 分页查询
     *
     * @param map 查询条件
     * @return List
     * @author: Kun
     * @date: 2018-04-03 15:12
     */
    List<SellerReceiveConfig> selectByPage(Map map);

    /**
     * 通过主键ID查询
     *
     * @param id 主键ID
     * @return 商家消息接收设置表实体类
     * @author: Kun
     * @date: 2018-04-03 15:12
     */
    SellerReceiveConfig selectByPrimaryKey(String id);

    /**
     * 查询总条数
     *
     * @param record 查询条件
     * @return 总条数
     * @author: Kun
     * @date: 2018-04-03 15:12
     */
    int selectCount(Map record);

    /**
     * 更新数据
     *
     * @param bean 实体类
     * @return int 更新生效行数
     * @author: Kun
     * @date: 2018-04-03 15:12
     */
    int update(SellerReceiveConfig bean);

    /**
     * 新增
     *
     * @param bean 实体类
     * @return int 新增个数
     * @author: Kun
     * @date: 2018-04-03 15:12
     */
    int insert(SellerReceiveConfig bean);

    /**
     * 删除
     *
     * @param id 主键ID
     * @return int 修改行数
     * @author Kun
     * @date 2018-04-03 15:12
     */
    int delete(String id);

    /**
     * 通过sellerId查询商家消息接受设置
     * @param sellerId 商家ID
     * @return map
     * @author kun
     * @date 15:31 2018/4/3
     */
    Map<String,Object> selectBySellerId(String sellerId);
    
    /**
     * 设置 - 消息提醒 - 保存接口
     * @param data
     * @return int
     * @author kun
     * @date 14:47 2018/4/8
     */
    int saveReceiveConfig(String data);
    /**
     * 发送提醒消息
     * @param sellerId 商户ID
     * @param type 1 新订单  2 退款订单  3取消订单 4 差评提醒 5 财务提醒 6 满团提醒
     * @return
     * @author John
     * @date： 2018年4月30日 下午10:32:36
     */
    int sendMessageToSeller(String sellerId,int type);
    /**
     * 发送给买家的信息接口
     * @param order
     * @param type类型 1预订（非立即确认的预订）2，确认 3，预订（立即确认的预订）4，商家拒单5，系统拒单6，退款申请
     * @return
     * @author John
     * @date： 2018年5月2日 上午11:18:33
     */
    int sendMessageToBuyer(OrderBaseInfo order,int type);

    /**
     * 发送给买家的信息接口
     * @param order
     * @param type类型 1预订（非立即确认的预订）2，确认 3，预订（立即确认的预订）4，商家拒单5，系统拒单6，退款申请
     * @return
     * @author hua
     * @date： 2018年5月2日 上午11:18:33
     */
    int sendHotelMessageToBuyer(OrderBaseInfo order,int type);

    /**
     * 发送信息给指定用户
     * @param type
     * @author John
     * @date： 2018年5月11日 下午4:52:17
     */
    void sendMessageToAgent(int type,OrderBaseInfo order,RefundInfo refund);

}
