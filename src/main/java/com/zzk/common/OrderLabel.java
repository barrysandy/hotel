package com.zzk.common;

import java.util.ArrayList;
import java.util.List;

public class OrderLabel {
	
	private String label;
	private String value;
	private static List<OrderLabel>  list =null;
	private static int[] state = null;
	
	static{
		 list =new ArrayList<>();
		    OrderLabel label1 = new OrderLabel("101", "待付款");
			OrderLabel label2 = new OrderLabel("201", "已付款");
			OrderLabel label3 = new OrderLabel("405", "未付款超时取消");
			OrderLabel label4 = new OrderLabel("406", "未付款商户取消");
			OrderLabel label5 = new OrderLabel("305", "已付款商户取消 ");
			OrderLabel label6 = new OrderLabel("302", "买家取消（限时内）");
			OrderLabel label7 = new OrderLabel("303", "买家取消（随时取消）");
			OrderLabel label8 = new OrderLabel("304", "买家取消（超时）");
			OrderLabel label11 = new OrderLabel("301", "已入住");
			OrderLabel label12 = new OrderLabel("401", "已评价");
			OrderLabel label13 = new OrderLabel("402", "未评价默认结束");
			OrderLabel label14 = new OrderLabel("306", "未付款买家取消");
			list.add(label1);
			list.add(label2);
			list.add(label3);
			list.add(label4);
			list.add(label5);
			list.add(label6);
			list.add(label7);
			list.add(label8);
			list.add(label11);
			list.add(label12);
			list.add(label13);
			list.add(label14);
			state = new int[]{101,201,301,302,303,304,305,306,401,402,405,406};
	}
	
	public OrderLabel(String label, String value) {
		super();
		this.label = label;
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public static List<OrderLabel>  getLabellist(){
		
		return list;
	}
	
	public static int[] getLableArray(){
		return state;
	}
}
