package com.zzk.common;

/**
 * Created by cby on 2016/9/3.
 */
public class CommonConstants {
    //系统缩写
    public final static String SYSTEM_NAME="trip_mall_";

    //批量insert 最大数量
    public static final int BATCH_INSERT_MAX_NUM=1000;
    //分页 每页最大记录数
    public static final int PAGE_MAX_NUMBER=50;
    
    //用户和用户类型之间的分隔符
    public static final String USER_AND_TYPE_SPLIT="_";
    
    
    /**
     * 交易类型：   1 打赏
     */
    public static final String PAY_TYPE_REWARD = "1";
    /**
     * 交易类型： 2 转账 
     */
    public static final String PAY_TYPE_TRANSFER_ACCOUNTS = "2";
    /**
     * 交易类型：3 订单 
     */
    public static final String PAY_TYPE_ORDER = "3";
    /**
     * 交易类型：4 提现 
     */
    public static final String PAY_TYPE_WITHDRAWALS = "4";
    /**
     * 交易类型：5 充值
     */
    public static final String PAY_TYPE_RECHARGE = "5";
    /**
     * 交易类型：6 退款
     */
    public static final String PAY_TYPE_REFUND = "6";
    
    
    /**
     * 支付类型：1 支付宝支付
     */
    public static final String PAY_SERVICE_ALIPAY="1";
    /**
     * 支付类型：2 微信支付
     */
    public static final String PAY_SERVICE_WEIXIN="2";
    /**
     * 支付类型：4 余额支付
     */
    public static final String PAY_SERVICE_BALANCE="4";
    /**
     * 秒支付:PAY_CHANNEL -支付宝ALI_WEB
     */
    public static final String BEECLOUD_PAY_CHANNEL_ALI="ALI_WEB";
    /**
     * 秒支付:PAY_CHANNEL -微信支付WX_NATIVE
     */
    public static final String BEECLOUD_PAY_CHANNEL_WX="WX_NATIVE";
    
  //粗实密码
    public static final String CHUSHIMIMA = "d35bb804b879047c5bd31318824c8c91";
    
    public static final String imgUrl="http://114.135.62.67:975/trip-mall/resources/image/tjb_default.jpg";

    
    //内部错误  
    public static final int SUCCESS = 1; 
    //内部错误  
    public static final int INTERNAL_ERROR = 500; 
    //注册成功
    public static final int REGISTER_SUCCESS = 10; 
    //注册名称、邮件、电话不能同时为空 
    public static final int REGISTER_INFO_ERROR = 11;
     //注册密码不能为空  
    public static final int REGISTER_PASSWORD_NULL = 12; 
     //注册用户名称已存在  
    public static final int REGISTER_NAME_EXISTED = 13; 
     //注册电子邮件已存在  
    public static final int REGISTER_EMAIL_EXISTED = 14; 
     //注册电话号码已存在  
    public static final int REGISTER_PHONE_EXISTED = 15; 
     //登陆成功  
    public static final int LOGIN_SUCCESS = 20; 
     //登陆手机号码为空
    public static final int LOGIN_PHONE_NULL = 21; 
     //登陆密码为空  
    public static final int LOGIN_PASSWORD_NULL = 22; 
     //登陆账号或者密码错误  
    public static final int LOGIN_PASSWORD_ERROR = 23; 
     //验证成功
    public static final int VALIDATE_SUCCESS = 30; 
     //验证失败
    public static final int VALIDATE_FAILED = 31;

}
