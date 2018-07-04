package com.zzk.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;








import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import com.zzk.util.HotelResult;
import com.zzk.util.Exceptions;
import com.zzk.util.PageView;
import com.zzk.util.StringUtils;
import com.zzk.entity.Evaluate;
import com.zzk.entity.EvaluateCustom;
import com.zzk.entity.Order;
import com.zzk.entity.ProductEvaluate;
import com.zzk.service.EvaluateService;
import com.zzk.service.OrderService;
import com.zzk.service.ProductEvaluateService;
import com.zzk.controller.BaseController;

/**
 * @name：EvaluateController
 * @author：huashuwen
 * @date：2017-11-24 16:42
 */
@Controller
@RequestMapping(value = "/evaluate")
public class EvaluateController extends BaseController {

	@Autowired
	private EvaluateService evaluateService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductEvaluateService productEvaluateService;
	
	/**
	 * 添加评论
	 * @param evaluate
	 * @author John
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveOrUpdateEva")
    public String  addEvaluate(Evaluate evaluate){
		HotelResult<Object> result = new HotelResult<>();
		String id =  evaluate.getId();
		String orderId = evaluate.getOrderId();
		String userId = evaluate.getCreaterId();
		if(StringUtils.isBlank(id)){
			if(StringUtils.isBlank(orderId) || StringUtils.isBlank(userId)){
				result.setState(0);
				result.setMsg("error");
				result.setMessage("订单ID或用户ID不能为空");
				return JSON.toJSONString(result);
			}
			if(StringUtils.isBlank(evaluate.getImgs())){
				evaluate.setImgs(null);
			}
			int code =evaluateService.insertEvaluate(evaluate);
			if(code>0){
				result.setState(1);
				result.setMsg("success");
				result.setMessage("评论成功");
				return JSON.toJSONString(result);
			}else{
				result.setState(0);
				result.setMsg("error");
				return JSON.toJSONString(result);
			}
		}else{
			evaluateService.updateEvaluate(evaluate);
			result.setState(1);
			result.setMsg("success");
			result.setMessage("修改成功");
			return JSON.toJSONString(result);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/del")
	public HotelResult<Evaluate> del(String id){
		try{
			evaluateService.delete(id);
		}catch(Exception e){
			Exceptions.getStackTraceAsString(e);//统一的异常处理
			return new HotelResult<Evaluate>(2,"error");
		}
		return new HotelResult<Evaluate>(1,"success");
	}
	
	@ResponseBody
	@RequestMapping(value="/saveOrUpdateRely")
	public String replyEvaluate(Evaluate evaluate){
		HotelResult<Object> result = new HotelResult<>();
		String id = evaluate.getId();
		ProductEvaluate bean = productEvaluateService.selectByPrimaryKey(id);
		if(bean!=null){
			bean.setReplayContent(evaluate.getContent());
			bean.setReplayState(1);
			bean.setReplayTime(new Date());
			productEvaluateService.update(bean);
			result.setState(1);
			result.setMsg("success");
			result.setMessage("修改成功");
			return JSON.toJSONString(result);
		}else{
			result.setState(0);
			result.setMsg("error");
			result.setMessage("error");
			return JSON.toJSONString(result);
		}
	}

	@ResponseBody
	@RequestMapping(value="/searchEvaluate")
	@SuppressWarnings("unchecked")
	public String searchEvaluate(Evaluate evaluate,@RequestParam(defaultValue="1")String pageNum,@RequestParam(defaultValue="10")String pageSize){
		HotelResult<Object> result = new HotelResult<>();
		Map<String,Object> map = new HashMap<>();
		String evaluateType="";
		if(evaluate.getScore()!=null){
			if(evaluate.getScore()==0){
				evaluateType="好评";
			}
			if(evaluate.getScore()==1){
				evaluateType="中评";		
			}
			if(evaluate.getScore()==2){
				evaluateType="差评";		
			}
		}
		if(evaluate.getReplyStatus()!=null){
			if(evaluate.getReplyStatus()==0){
				evaluateType="noReply";		
			}
		}	
		if(StringUtils.isNotBlank(evaluate.getImgs())){
			evaluateType="haveImg";		
		}
		map.put("sellerId",evaluate.getShopId());
		map.put("evaluateType",evaluateType);
        PageHelper.startPage(Integer.valueOf(pageNum),Integer.valueOf(pageSize));
		
        List<Map<String,Object>> list = evaluateService.selectAllEvaluate(map);
        PageView pv=new PageView<>(list);
		result.setData(pv.getList());
		result.setTotalNum((int)pv.getTotal());
		result.setPageSize(pv.getPageSize());
		result.setMessage("查询成功");
		result.setPageCount(pv.getPages());
		result.setState(1);
		result.setMsg("success");
		return JSON.toJSONString(result,SerializerFeature.WriteMapNullValue);
		
	}
	
	@ResponseBody
	@RequestMapping(value="/evaCount")
	public String evaCount(String shopId){
		HotelResult<EvaluateCustom> result = new HotelResult<>();
		EvaluateCustom custom =	evaluateService.selectCountByshopId(shopId);
		if(custom==null){
			custom= new EvaluateCustom();
		}
		result.setState(1);
		result.setData(custom);
		result.setMsg("success");
		return JSON.toJSONString(result);
	}
	
	
}

