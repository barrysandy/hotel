package com.zzk.controller;

import java.io.FileNotFoundException;
import java.util.*;

import javax.annotation.Resource;

/*import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;*/
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
/*import com.zzk.util.HttpUtils;
import com.zzk.util.ExcelUtils;*/
import com.zzk.util.JsonUtils;
import com.zzk.util.Page;
import com.zzk.util.Result;
import com.zzk.util.StringUtils;
import com.zzk.entity.TripElement;
import com.zzk.service.TripElementService;

/**
 * 旅游要素
 *
 * @name: TripElementController
 * @author: huashuwen
 * @date: 2018-03-10 10:57
 */
@Controller
@RequestMapping(value = "/tripElement")
public class TripElementController extends BaseController {

    @Resource
    private TripElementService tripElementService;
	//@Resource
	//private ExcelUtils excelUtils;
	
	private static String host = "http://geo.market.alicloudapi.com";
	private static String path = "/v3/geocode/geo";
	private static String method = "GET";
	private static String appcode = "e85d2d16958643168e7628ff5618192a";
    /**
     * 旅游要素删除
     *
     * @param id 主键ID
     * @return String
     * @author huashuwen
     * @date 2018-03-10 10:57
     */
    @RequestMapping("/del")
    @ResponseBody
    public String del(String id) {
        List<String> list = new ArrayList<String>();
        list.add("雅安");
        list.add("瓦斯村");
        tripElementService.selectByName("雅安");
        tripElementService.selectByList(list);
        /*try{
			tripElementService.delete(id);
		}catch(Exception e){
			e.printStackTrace();
			return JsonUtils.turnJson(false,"error"+e.getMessage(),e);
		}*/
        return JsonUtils.turnJson(true, "success", null);
    }

    /**
     * 查询旅游要素信息
     *
     * @return list
     * @author hua
     */
    @RequestMapping("/getElementsList")
    @ResponseBody
    public Result getElementsList(String elementsStr) {
		try {
		    if (StringUtils.isBlank(elementsStr)){
		        return new Result(0,"fail","参数错误");
            } else {
                String[] elementsNameArray = elementsStr.split(",");
                List<String> elementsNameList = new ArrayList<>();
                for (String e : elementsNameArray) {
                    elementsNameList.add(e);
                }
                List<Map<String,Object>> resultList = tripElementService.selectByList(elementsNameList);
                return new Result<>(1, "success", "查询成功", resultList);
            }
        }catch (Exception e){
		    e.printStackTrace();
		    return new Result<>(0,"fail","内部异常",e);
        }
    }

	/**
	 * 查询旅游要素信息
	 * @param str 要素名称
	 * @return list
	 * @author hua
	 */
	/*@RequestMapping("/importData")
	@ResponseBody
	public Result importData(String path){
		
		try {
			List<TripElement> list=excelUtils.readExcel(path);
			
			for(TripElement t: list){
				t.setStatus(1);
				t.setCreateTime(new Date());
				t.setId(UUID.randomUUID().toString());
				t.setContent(t.getName());
				t.setTypeId("2");
				
				Map<String, String> headers = new HashMap<String, String>();
			    headers.put("Authorization", "APPCODE " + appcode);
			    Map<String, String> querys = new HashMap<String, String>();
			    Map<String, Double> resultMap = new HashMap<String, Double>();
			    querys.put("address", "address");
			    querys.put("batch", "false");
			    querys.put("city", "");
			    querys.put("output", "JSON");
				
				
				HttpResponse response;
				try {
					response = HttpUtils.doGet(host, path, method, headers, querys);
				
		    	if(response.getStatusLine().getStatusCode()==200){
		    		String jsonStr = EntityUtils.toString(response.getEntity());
		    		JSONObject jsonObject = JSONObject.parseObject(jsonStr);
		    		String count = (String)jsonObject.get("count");
		    		if(!"0".equals(count)){
		    			JSONArray geocodes = (JSONArray)jsonObject.get("geocodes");
			    		JSONObject a = (JSONObject)geocodes.get(0);
			    		String location = (String)a.get("location");
			    		String[] aLocation= location.split(",");
			    		double lon = Double.parseDouble(aLocation[0]);
			    		double lat = Double.parseDouble(aLocation[1]);
			    		resultMap.put("lat", lat);
			    		resultMap.put("lon", lon);
		    		}else{
		    		}
		    	}else{
		    		
		    	}
				tripElementService.insert(t);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FileFormatException e) {
			e.printStackTrace();
		}
		
		return new Result<>(1,"success","查询成功",null);
	}*/
	
	
}
