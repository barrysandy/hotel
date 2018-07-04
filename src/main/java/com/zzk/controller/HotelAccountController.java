package com.zzk.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zzk.util.HotelResult;
import com.zzk.util.DateUtils;
import com.zzk.util.UuidUtil;
import com.zzk.entity.Dict;
import com.zzk.entity.HotelAccount;
import com.zzk.service.CacheService;
import com.zzk.service.DictService;
import com.zzk.service.HotelAccountService;


@Controller
@RequestMapping(value="/account")
public class HotelAccountController extends BaseController {
	@Autowired
	private HotelAccountService hotelAccountService;
 	@Autowired
	private DictService dictService;
	private static List<Dict> dictList;

	/**
	 * 新增和修改账户信息
	 * @param account
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveOrUpdate")
	public String saveOrUpdate(HotelAccount account,BindingResult bindingResult){
		//支付方式 101对公账户，102个人账户，103支付宝，104微信
		HotelResult<Object> result  =new HotelResult<>();
			String id = account.getId();
			String shopId = account.getShopId();
			Integer[] code = new Integer[]{101,102,103,104};
			Integer accountType=account.getPayMode();
			if(accountType==null){
				accountType=0;
			}
			int i= Arrays.binarySearch(code, accountType);
			if(accountType==null || i==-1){
				result.setData(0);
				result.setMsg("error");
				result.setMessage("无效的支付类型");
				return JSON.toJSONString(result);
			}
			try{
			if(StringUtils.isBlank(id)){
				if(StringUtils.isBlank(shopId)){
					result.setData(0);
					result.setMsg("error");
					result.setMessage("酒店ID不能为空");
					return JSON.toJSONString(result);
				}
				account.setCreateDate(DateUtils.getDate());
				account.setStatus(1);
				account.setType(1);
				account.setId(UuidUtil.uuidStr());
				hotelAccountService.insert(account);
			}else{
				HotelAccount ha= hotelAccountService.selectByPrimaryKey(id);
				ha.setUpdateDate(DateUtils.getDate());
				ha.setAccountName(account.getAccountName());
				ha.setAccountNum(account.getAccountNum());
				ha.setBankCode(account.getBankCode());
				ha.setDepositBank(account.getDepositBank());
				ha.setSubBank(account.getSubBank());
				hotelAccountService.updateByPrimaryKey(ha);
			}
			}catch(Exception e){
				result.setState(0);
				result.setMsg("error");
				return JSON.toJSONString(result);
			}
			result.setState(1);
			result.setMsg("success");
		return JSON.toJSONString(result); 
	}
	/**
	 * 获取酒店的对公账户
	 * @param shopId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findAccount")
	public String findAccount(String shopId){
		HotelResult<HotelAccount> result  =new HotelResult<>();
		if(StringUtils.isBlank(shopId)){
			result.setState(0);
			result.setMsg("error");
			result.setMessage("酒店ID不能为空");
			return JSON.toJSONString(result);
		}
		int payMode=101;
		HotelAccount account = null;
		try{
			account = hotelAccountService.selectByHotelPayMode(shopId, payMode);
			if(account==null){
				account=new HotelAccount();
			}
			Integer depositBank = account.getDepositBank();
			String depositBankStr = null;
			if(depositBank!=null){
				depositBankStr =	getDictName("depositBank",account.getDepositBank().toString());
			}
			account.setDepositBankStr(depositBankStr);
			result.setData(account);
			result.setMsg("success");
			result.setState(1);
			return JSON.toJSONString(result,SerializerFeature.WriteMapNullValue);
		}catch(Exception e){
			result.setMsg("error");
			result.setState(0);
			result.setMessage(e.getMessage());
			return JSON.toJSONString(result);
		}
	}
	public String getDictName(String type,String code){
		dictList = CacheService.getAllDict();
		for(Dict dict:dictList){
			if(type.equals(dict.getType())&&code.equals(dict.getValue())){
				return dict.getLabel();
			}
		}
		return "";
	}
	/**
	 * 获取银行列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/bankData")
	public String bankData(){
		HotelResult<Object> result = new HotelResult<Object>();
//从JSON文件中取		
//		 String path = getClass().getClassLoader().getResource("bankData.json").toString();
//		        path = path.replace("\\", "/");
//		          if (path.contains(":")) {
//		              //path = path.substring(6);// 1
//		              path = path.replace("file:/","");// 2
//		          }
//		          try {
//		              String input = FileUtils.readFileToString(new File(path), "UTF-8");
//		              JSONArray json = JSONArray.parseArray(input);
//		              result.setState(1);
//		              result.setData(json);
//		              result.setTotalNum(json.size());
//		              result.setMsg("success");
//		             return JSON.toJSONString(result);
//		         } catch (Exception e) {
//		             e.printStackTrace();
//		             result.setState(0);
//		             result.setMsg("error");
//		             return JSON.toJSONString(result);
//		         }

			// 从字典中取数据
	     try{
			List<Map<String,String>> list = new ArrayList<>();
			Iterator<Dict> iterator = dictList.iterator();
			while(iterator.hasNext()){
				Dict dict =iterator.next();
				if("depositBank".equals(dict.getType())){
					Map<String,String> bankData = new HashMap<>();
					bankData.put("bankName", dict.getLabel());
					bankData.put("bin", dict.getValue());
					list.add(bankData);
				}
			}
		
	        result.setState(1);
	        result.setData(list);
	        result.setMsg("success");
	       return JSON.toJSONString(result);
	     }catch(Exception e){
	         result.setState(0);
	         result.setMsg("error");
	        return JSON.toJSONString(result);
	     }
	}

}
