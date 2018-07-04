package com.zzk.common;

public class WithDrawConstact {
	public enum BillStateEnum{
		// 1未支付2 已支付3 退款成功 4退款失败
				BEGINGAUDITED(1,"审核中"),
				BEGINGPAYMENT(2,"支付中"),
				PAYSUCCESS(3,"支付成功"),
				PAYFAILD(4,"支付失败");
				private int code;
				private String value;
				BillStateEnum(int code,String value){
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
				public static BillStateEnum codeOf(int code){
		            for(BillStateEnum billStateEnum : values()){
		                if(billStateEnum.getCode() == code){
		                    return billStateEnum;
		                }
		            }
		            throw new RuntimeException("没有找到对应的枚举");
		        }
	}
	public enum PayMode{
		// 1微信 2 支付宝 3对公账户 4私人账户
				WECHAT(1,"微信"),
				IPAY(2,"支付宝"),
				PUBLICACOUNT(3,"对公账户"),
				PRIVATEACCOUNT(4,"私人账户");
				private int code;
				private String value;
				PayMode(int code,String value){
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
				public static PayMode codeOf(int code){
		            for(PayMode paymentTypeEnum : values()){
		                if(paymentTypeEnum.getCode() == code){
		                    return paymentTypeEnum;
		                }
		            }
		            throw new RuntimeException("没有找到对应的枚举");
		        }
	}
}
