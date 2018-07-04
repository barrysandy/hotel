package com.zzk.common;


public class OrderConstact {
	public enum OrderPayStatusEnum{
		// 1未支付2 已支付3 退款成功 4退款失败
				UNPAID(1,"待支付"),
				ALREADYPAID(2,"已支付"),
				REFUNDSUCCESS(3,"退款成功"),
				REFUNDFAILD(4,"退款失败");
				private int code;
				private String value;
				OrderPayStatusEnum(int code,String value){
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
				public static OrderPayStatusEnum codeOf(int code){
		            for(OrderPayStatusEnum rderPayStatusEnum : values()){
		                if(rderPayStatusEnum.getCode() == code){
		                    return rderPayStatusEnum;
		                }
		            }
		            throw new RuntimeException("没有找到对应的枚举");
		        }
	}
	public enum OrderRefund{
		// 1全额退款 2 部分退款  3 拒绝退款
				ALLREFUND(1,"全额退款"),
				REBATES(2,"部分退款"),
				REFUNDSUCCESS(3,"拒绝退款");
				private int code;
				private String value;
				OrderRefund(int code,String value){
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
	}
	public enum OrderStatusEnum {
		// 1 待付款 2 待确认 3待消费 4 待评价 5 已完成 6 退款申请 
		//7 退款中 8 退款成功 9 已取消（商家） 10 已取消（用户） 11 已取消（系统）
		WAITPAY(1,"待支付"),
		WAITCONFIRM(2,"待确认"),
		WAITCONSUME(3,"待消费"),
		WAITEVALUATE(4,"待评论"),
		COMPLETED(5,"已完成"),
		REFUNDAPPLY(6,"退款申请"),
		REFUNDING(7,"退款中"),
		REFUNDSUCCESS(8,"退款成功"),
		SELLERCANCELED(9,"已取消"),
		USERCANCELED(10,"已取消"),
		SYSTEMCANCELED(11,"已取消"),
		COMMENTED(12,"已评论");
		private int code;
		private String value;
		OrderStatusEnum(int code,String value){
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
		public static OrderStatusEnum codeOf(int code){
            for(OrderStatusEnum orderStatusEnum : values()){
                if(orderStatusEnum.getCode() == code){
                    return orderStatusEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
	}
}
