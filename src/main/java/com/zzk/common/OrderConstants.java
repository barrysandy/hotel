package com.zzk.common;

public class OrderConstants {

	public static final int WAIT_TO_PAY = 101;//待付款
	
	public static final int NOT_PAY_CANCEL_BY_BUYER  = 407;//买家取消未付款
	
	public static final int WAIT_TO_CONFIRM = 201;//待确认(已付款)
	
	public static final int CONFIRM = 202;//已确认（待消费）
	
	public static final int OVERTIME_CANCEL = 405;//超时取消
	
	public static final int NOT_PAY_CANCEL_BY_SELLER = 406;//未付款商户取消
	
	public static final int CANCEL_BY_SELLER = 305;//已付款商户取消
	
	public static final int CANCEL_BY_BUYER = 302;//买家取消（限时内）
	
	public static final int CANCEL_BY_BUYER_ANYTIME = 303;//买家取消（随时取消）
	
	public static final int CANCEL_BY_BUYER_OVERTIME = 304;//买家取消（超时）
	
	public static final int REFUND_SUCCESS = 403;//退款成功

	public static final int REFUND_FAIL = 404;//退款失败
	
	public static final int HAVE_CHECK_IN = 301;//已入住
	
	public static final int HAVE_EVALUATE = 401;//已评价
	
	public static final int DEFAULT_END = 402;//默认结束
	
	public static final int REFUND_APLLY = 6;//默认结束
	/**
	 * 支付状态
	 */
	public static final int PAY_NOT = 1; //未支付
	public static final int PAY_PAY = 2; //支付
	public static final int PAY_REFUND = 3; //退款成功
	//public static final int PAY_REFUNDING = 1002; //退款中
	public static final int PAY_REFUND_FAIL = 4; //退款失败
	public static final int PAY_REFUNDING= 1002; //退款中
}

