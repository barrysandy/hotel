package com.zzk.common;

public class FinanceConstact {
	public enum StatusEnum{
		//财务状态0全部 1未出账单 2待提现 3审核中 4支付中 5支付成功 6支付失败 
				NO_BILL(1,"未出账单"),
				WAIT_CASH(2,"待提现"),
				AUDITING(3,"审核中"),
				PAYING(4,"支付中"),
				PAY_SUCCESS(5,"支付成功"),
				PAY_FAILD(6,"支付失败");
				private int code;
				private String value;
				StatusEnum(int code,String value){
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
}
