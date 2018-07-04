package com.zzk.common;

public class Constact {
	public static final String IS_AUTHENTICATION = "isAuthentication";
	/**
	 * 支付方式枚举类
	 * @Description:
	 * @author John
	 * @date： 2018年4月24日 下午2:58:02
	 */
	public enum PaymentTypeEnum{
		// 1微信 2 支付宝 3对公账户 4私人账户
				WECHAT(1,"微信"),
				ALIPAY(2,"支付宝"),
				PUBLICACOUNT(3,"对公账户"),
				PRIVATEACCOUNT(4,"私人账户");
				private int code;
				private String value;
				PaymentTypeEnum(int code,String value){
					this.code = code;
					this.value = value;
				}
				public int getCode() {
					return code;
				}
				public void setCode(int code) {
					this.code = code;
				}
				public String getValue() {
					return value;
				}
				public void setValue(String value) {
					this.value = value;
				}
				public static PaymentTypeEnum codeOf(int code){
		            for(PaymentTypeEnum paymentTypeEnum : values()){
		                if(paymentTypeEnum.getCode() == code){
		                    return paymentTypeEnum;
		                }
		            }
		            throw new RuntimeException("没有找到对应的枚举");
		        }
	}
	/**
	 * 平台枚举类
	 * @Description:
	 * @author John
	 * @date： 2018年4月24日 下午2:57:49
	 */
	public enum PlatformEnum{
		// 1酒店2 线路 ..
				HOTEL(1,"酒店"),
				LINE(2,"线路");
				private int code;
				private String value;
				PlatformEnum(int code,String value){
					this.code = code;
					this.value = value;
				}
				public int getCode() {
					return code;
				}
				public void setCode(int code) {
					this.code = code;
				}
				public String getValue() {
					return value;
				}
				public void setValue(String value) {
					this.value = value;
				}
				public static PlatformEnum codeOf(int code){
		            for(PlatformEnum platformEnum : values()){
		                if(platformEnum.getCode() == code){
		                    return platformEnum;
		                }
		            }
		            throw new RuntimeException("没有找到对应的枚举");
		        }
	}
}
