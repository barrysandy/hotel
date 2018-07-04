package com.zzk.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zzk.util.HotelResult;
import com.zzk.util.PageView;
import com.zzk.entity.Finance;
import com.zzk.entity.FinanceInfo;
import com.zzk.entity.Order;
import com.zzk.entity.OrderBaseInfo;
import com.zzk.service.FinanceService;
import com.zzk.service.OrderService;

@Controller
@RequestMapping(value="/finance")
public class FinanceController extends BaseController{

	@Autowired
	private FinanceService financeService;
	@Autowired
	private OrderService orderService;
	/**
	 * 获取财务列表
	 * @param finance
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/search")
	public String searchBill(FinanceInfo finance,@RequestParam(defaultValue="1")int pageNum,@RequestParam(defaultValue="3")int pageSize){
		HotelResult<Map> result = new HotelResult<>();
		Map<String,Object> map = new HashMap<>();
		PageView<FinanceInfo> view =	financeService.selectByPage(finance,pageNum,pageSize);
		List<FinanceInfo> tableData = view.getList();
		for (FinanceInfo fin : tableData) {
			if(fin.getActualMoney()==null){
				fin.setActualMoney(new BigDecimal(0.0));
			}
			if(fin.getOrderMoney()==null){
				fin.setOrderMoney(new BigDecimal(0.0));
			}
		}
		Map bigDecMap = financeService.selectAbleCashTotal(finance.getSellerId());
		if(bigDecMap == null){
			bigDecMap = new HashMap<>();
		}
		map.put("list", tableData);
		map.put("total", bigDecMap.get("ACTUALMONEY"));
		result.setData(map);
		result.setTotalNum((int)view.getTotal());
		result.setPageSize(view.getPageSize());
		result.setPageIndex(view.getPageNum());
		result.setPageCount(view.getPages());
		result.setState(1);
		result.setMsg("success");
		
		return JSON.toJSONString(result);
	}

	@ResponseBody
	@RequestMapping(value="/billDetail")
	public String findBillDetail(String id, int pageNum,@RequestParam(defaultValue="3") int pageSize){
		HotelResult<Map<String,Object>> result = new HotelResult<>();
		FinanceInfo finance = financeService.selectByPrimaryKey(id);
		if(finance ==null){
			result.setMsg("error");
			result.setState(0);
			result.setMessage("账单信息不存在");
			return JSON.toJSONString(result);
		}
		PageView<OrderBaseInfo> view = financeService.billDetail(id,pageNum,pageSize);
		Map<String,Object> dataMap = new HashMap<>();
		List<OrderBaseInfo> baseInfoList = view.getList();
		
		
		List<Order> list = new ArrayList<Order>();
		for(OrderBaseInfo orderBaseInfo :baseInfoList){
			Order o = orderService.orderBaseInfo2Order(orderBaseInfo);
			list.add(o);
		}
		for (Order order : list) {
			if(order.getActualMoney()==null){
				order.setActualMoney(new BigDecimal(0.00));
			}
			if(order.getOrderMoney()==null){
				order.setOrderMoney(new BigDecimal(0.00));
			}
			if(order.getBasicMoney()==null){
				order.setBasicMoney(new BigDecimal(0.00));
			}
			String orderObj = order.getOrderObject();
			JSONObject json = JSONObject.parseObject(orderObj);
			order.setRoomTypeName(json.getString("roomTypeName"));
		}
		dataMap.put("finance", finance);
		dataMap.put("order", list);
		result.setData(dataMap);
		result.setTotalNum((int)view.getTotal());
		result.setPageSize(view.getPageSize());
		result.setPageIndex(view.getPageNum());
		result.setPageCount(view.getPages());
		result.setState(1);
		result.setMsg("success");
		return JSON.toJSONString(result,SerializerFeature.WriteMapNullValue);
	}
	@ResponseBody
	@RequestMapping(value="/gen")
	public String  generatedBill(){
		HotelResult<Integer> result = new HotelResult<>();
		int status =financeService.generatedBills();
		result.setData(status);
		result.setState(1);
		result.setMsg("success");
		return JSON.toJSONString(result);
	}
}
