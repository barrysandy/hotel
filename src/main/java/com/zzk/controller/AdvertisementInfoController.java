package com.zzk.controller;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zzk.entity.AdvertisementInfo;
import com.zzk.service.AdvertisementInfoService;
import com.zzk.util.Result;
import com.zzk.util.StringUtils;

/**
 * <p>description：广告信息表</p>
 * @name：AdvertisementInfoController
 * @author：huashuwen
 * @date：2018-03-09 15:29
 */
@Controller
@RequestMapping(value = "/advertisementInfo")
public class AdvertisementInfoController extends BaseController {

	@Autowired
	private AdvertisementInfoService advertisementInfoService;
	
	/**
	 * 广告信息表保存操作
	* @param bean
	* @return
	* @author huashuwen
	* @date 2018-03-09 15:29
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public Result saveOrUpdate(AdvertisementInfo bean){
		try{
			if(bean!=null){
				String id = bean.getId();
				if(StringUtils.isNotBlank(id)){
					AdvertisementInfo rule = new AdvertisementInfo();
					if(StringUtils.isNotBlank(id)){
						rule = advertisementInfoService.selectByPrimaryKey(id);
					}
					bean.setCreateTime(rule.getCreateTime());
					bean.setUpdateTime(new Date());
					bean.setStatus(1);
					advertisementInfoService.update(bean);
				}else{
					UUID u = UUID.randomUUID();
					bean.setStatus(1);
					bean.setId(u.toString());
					bean.setCreateTime(new Date());
					advertisementInfoService.insert(bean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();//统一的异常处理
			return new Result<AdvertisementInfo>(2,"error",e.getMessage());
		}
		return new Result<AdvertisementInfo>(1,"success","success");
	}
	/**
	 * 广告信息表删除
	* @param id
	* @return
	* @return
	* @author huashuwen
	* @date 2018-03-09 15:29
	 */
	@RequestMapping("/del")
	@ResponseBody
	public Result<AdvertisementInfo> del(String id){
		try{
			advertisementInfoService.delete(id);
		}catch(Exception e){
			e.printStackTrace();
			return new Result<AdvertisementInfo>(2,"error",e.getMessage());
		}
		return new Result<AdvertisementInfo>(1,"success","success");
	}
	
}
