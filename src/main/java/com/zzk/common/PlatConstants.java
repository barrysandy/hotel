package com.zzk.common;

/**
 * Created by JP on 2017/3/16.
 */
public class PlatConstants {
    /**
     * 1支付宝 2微信 3银联
     */
    public static final int PAY_ZHIFUBAO = 1;
    public static final int PAY_WEIXIN = 2;
    public static final int PAY_YINLIAN = 3;
    /**
     * 流水分类
     */
    public static final int STREAM_DASHANG = 1;
    public static final int STREAM_ZHUANZHANG = 2;
    public static final int STREAM_DINGDAN = 3;
    public static final int STREAM_TIXIAN = 4;
    public static final int STREAM_CHONGZHI = 5;
    public static final int STREAM_TUIKUAN = 6;
    /**
     * 0 目的地 1 出发地 2 站点
     */
    public static final int DEST_MIDIDI = 0;
    public static final int DEST_CHUFADI = 1;
    public static final int DEST_ZHANDIAN = 2;
    /**
     * plat_log log_type, come_from
     */
    public static final int LOG_TYPE_ERROR = 1;
    public static final int LOG_TYPE_INTERFACE_ERROR = 2;

    public static final int COME_FROM_PLAT = 1;
    public static final int COME_FROM_SELLER = 2;
    public static final int COME_FROM_MALL = 3;
    public static final int COME_FROM_MEMBER = 4;
    /**
     * 逻辑删除：1.未删除；-1.删除
     * reture "1"
     */
    public static final int isNotDelete = 1;

    /**
     * 逻辑删除：1.未删除；-1.删除
     * reture "-1"
     */
    public static final int isDelete = -1;

    /**
     * 是否显示：1.显示；-1.不显示
     * reture "1"
     */
    public static final int isShow = 1;
    /**
     * 是否显示：1.显示；-1.不显示
     * reture "-1"
     */
    public static final int isNotShow = -1;

    /**
     * 根节点ID
     */
    public static final String ROOT = "ROOT";

    /**
     * 节点层级
     */
    public static final String level1 = "1";
    public static final String level2 = "2";
    public static final String level3 = "3";
    
    /**
     * t_mp_order.CHECK_TYPE
     * 对账记录类型 0默认 1对账数据
     */
    public static final int DEFAULT_TYPE = 0;
    public static final int ACCOUNT_TYPE = 1;

    /**
     * seller_order_info.CHECK_STATE
     * 对账状态  0 待对账订单,1 已对账，-3异常订单， -4 退款订单
     */
    public static final int WAIT_CONFIRME = 0;
    public static final int CONFIRMED = 1;
    public static final int CONFIRMED_AUTO = 2;
    public static final int EXCEPTION_ORD = -3;
    public static final int REFUND_ORD = -4;
    /**
     * seller_order_info.PAY_STATE
     * 支付状态 0 未支付 1 已支付  3退款成功 1002退款中 4退款失败
     */
    public static final int PAY_NO = 1;
    public static final int PAY_YES = 2;
    public static final int PAY_REFUND_SUCCESS = 3;
    public static final int	PAY_REFUNDING = 1002;
    public static final int PAY_REFUND_FALSE = 4;

    /**
     * plat_withdraw_apply.AUDIT_STATE
     * 0待审核 1审核通过 2待财务支付 3提现成功 -1驳回
     */
    public static final int AUDIT_PENDING_APPROVAL = 0;
    public static final int AUDIT_FINANCE_PAY = 1;
    public static final int AUDIT_SUCCESS = 2;
    public static final int AUDIT_REJECT = -1;


    /**
     * 线路分类
     */
    public static final String lineLType = "lineLType";

    /**
     * 平台通用分类type管理

     新增第一级父分类parentid为 ROOT，
     平台商品分类的分类type为 1   //platformproduct
     //商家商品分类的分类type为商家id
     线路目的地的分类type为2   //linedestination
     线路出发地的分类type为3   //line_departure
     线路价格的分类type为4   //line_price
     线路分类的分类type为5  //line_type
     景点目的地的分类type为6  //scenic_destination
     景点价格的分类type为7  //scenic_price
     景点分类的分类type为8  //scenic_type

     酒店星级的分类type为10  //hotel_start
     酒店分类的分类type为11  //hotel_type
     酒店价格分类type为12  //hotel_

     */
    //公共分类配置表 : plat_type_management 下的type字段
    public static final int platformproduct = 1;
    public static final int linedestination = 2;
    public static final int linedeparture = 3;
//    public static final int lineprice = 4;
    public static final int linetype = 5;
    public static final int scenicdestination = 6;
//    public static final int scenicprice = 7;
    public static final int scenictype = 8;
    public static final int carType = 9;
    public static final int hotel_star = 10;
    public static final int hotel_type = 11;
//    public static final int hotalprice = 12;
    public static final int lineDayNum =13;
    public static final int specialtyType =14;
    public static final int restaurantType =15;
    public static final int combinationType =16;
    public static final int entertainmentType =17;

    /**
     * 审核类型 audit_type
     * 审核类型 1：商家入驻审核；2：活动审核；3：游记审核；4：攻略审核；5：商品审核
     */
    public static final int auditBusinessSettled = 1;
    public static final int auditActivity = 2;
    public static final int auditTravels = 3;
    public static final int auditRaiders = 4;
    public static final int auditCommodity = 5;
    
    
    /**
     * 审核状态 atdit_status
     * 审核状态 1：草稿；2 ：待审核；3：系统审核审核通过；4 ：人工审核通过；5 ：失败；6：系统取消；7：用户取消；
     */
    public static final int auditToDraft = 1;
    public static final int auditToPending = 2;
    public static final int auditToSystem = 3;
    public static final int auditToManual  = 4;
    public static final int auditToFailure = 5;
    public static final int auditToSystemCancel = 6;
    public static final int auditToUserCancel = 7;



    /**
     * 审核方式 isAudit
     * 审核方式  1.人工审核；2.系统审核
     */
    public static final int auditByManual = 1;
    public static final int auditBySystem = 2;
    
    /**
     * 审核页面显示数据条数
     */
    public static final int toTopNum = 5;




    //商品价格区间配置表 : plat_price_management
    public static final int hotalprice = 1;
    public static final int lineprice = 2;
    public static final int scenicprice = 3;
    public static final int entertainmentprice = 4;

    //商品基本信息配置表 : plat_product_base_info 下的type字段
    public static final int hotalBaseInfo = 1;
    public static final int specialBaseInfo = 2;
    public static final int scenicBaseInfo = 3;
    public static final int lineBaseInfo = 4 ;
    public static final int foodBaseInfo = 5;
    public static final int groupBaseInfo = 6;
    public static final int carBaseInfo = 7;
    public static final int entertainmentInfo = 8;

    //商品评价项配置  1 酒店；2特产；3 景点门票；4，线路，5餐饮，6组合，7租车

    public static final int hotalEvaluate = 1 ;
    public static final int specialEvaluate = 2 ;
    public static final int scenicEvaluate = 3 ;
    public static final int lineEvaluate = 4 ;
    public static final int foodEvaluate = 5 ;
    public static final int groupEvaluate = 6 ;
    public static final int carEvaluate = 7 ;
	public static final int entertainmentEvaluate = 8;


    //平台基础配置 - code 编码1,商品审核标准2,咨询评价审核标准
    public static final int PRODUCT_STAND = 1;
    public static final int COMMENT_STAND = 2;





    //脚板类型 : 1 赤脚版 ; 2 铁脚板 ; 3 铜脚板 ; 4 银脚板 ; 5 金脚板 ; 6 钻脚板
    public static  final int foot = 1;
    public static  final int ironfoot = 2;
    public static  final int copperfoot = 3;
    public static  final int silverfoot = 4;
    public static  final int goldfoot = 5;
    public static  final int diamondfoot = 6;

    //类目ID 暂时放着
    public static final String hotalCategory = "c27b31db-c778-42bb-8f9d-a24a9700dcd0";
    public static final String scenicCategory = "f360b899-fb5d-4714-b173-04e33d3afbea";
    public static final String specialCategory = "5db5550a-178e-4517-ab2e-a4a6c12a9775";
    public static final String lineCategory = "ee5b6a35-78de-4ebb-8224-d6554d3c2cea";
    public static final String foodCategory = "7bb02251-5ca7-41a6-8df5-50737c2bde93";
    public static final String groupCategory = "9cf65753-9b6c-4a09-8e73-98fba67391f9";
    public static final String carCategory = "644c068c-45bc-48bd-a1ee-d2022791ff86";
    public static final String entertainmentCategory = "d15c2d42-1858-405b-b884-34c8da0d2e9f";
    
    //活动状态 1草稿，2 活动报名中，3，报名结束，4 活动进行中，5  活动结束 6 待审核 7 审核不通过
    public static final int ACTIVE_CAOGAO = 1;
    public static final int ACTIVE_BAOMINGZHONG = 2;
    public static final int ACTIVE_BAOMINGJIESHU =3;
    public static final int ACTIVE_JINXINGZHONG =4;
    public static final int ACTIVE_HUODONGJIESHU =5;
    public static final int ACTIVE_WAITAUDIT =6;
    public static final int ACTIVE_AUDITFAIL =7;
    //酒店状态
    //1正常营业 2关店 3暂停营业 4审核中 5审核不通过
    public static final int HOTEL_NORMAL = 1;
    public static final int HOTEL_CLOSE = 2;
    public static final int HOTEL_PAUSE =3;
    public static final int HOTEL_REVIEW =4;
    public static final int HOTEL_NOT_PASS =5;
    //房型状态
    //1审核通过 2关闭 3审核中 4审核不通过
    public static final int ROOMTYPE_NORMAL = 1;
    public static final int ROOMTYPE_CLOSE = 2;
    public static final int ROOMTYPE_REVIEW =3;
    public static final int ROOMTYPE_NOT_PASS =4;
  //商品状态
    //1上架 2下架 
    public static final int HOTELGOODS_SALE = 1;
    public static final int HOTELGOODS_CLOSE = 2;
    
   
    
    //预订取消类型
    //0不可取消 1随时取消 2限时取消
    public static final int CANCEL_NOT = 0;
    public static final int CANCEL_ANYTIME = 1;
    public static final int CANCEL_LIMIT =2;
    //入住确认类型
    //0付款自动确认     1. 30分钟内确认    2 限定时间内确认
    public static final int CONFIRM_AUTO = 1;
    public static final int CONFIRM_30_MINUTES =2;
    public static final int CONFIRM_TIME =0;
    
  //房型合作方式  0佣金     1佣金率
    public static final int COOPERATE_BROKERAGE = 0;
    public static final int COOPERATE_PROPORTION =1;
    
}
