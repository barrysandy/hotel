package com.zzk.controller;

import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zzk.entity.AdvertisementPositionInfo;
import com.zzk.service.AdvertisementPositionInfoService;
import com.zzk.util.Result;
import com.zzk.util.StringUtils;

/**
 * <p>description：广告位信息表</p>
 * @name：AdvertisementPositionInfoController
 * @author：huashuwen
 * @date：2018-03-09 15:30
 */
@Controller
@RequestMapping(value = "/advertisementPositionInfo")
public class AdvertisementPositionInfoController extends BaseController {

	@Autowired
	private AdvertisementPositionInfoService advertisementPositionInfoService;
	
	/**
	 * 广告位信息表保存操作
	* @param bean
	* @return
	* @author huashuwen
	* @date 2018-03-09 15:30
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public Result<AdvertisementPositionInfo> saveOrUpdate(AdvertisementPositionInfo bean){
		try{
			if(bean!=null){
				String id = bean.getId();
				if(StringUtils.isNotBlank(id)){
					AdvertisementPositionInfo rule = new AdvertisementPositionInfo();
					if(StringUtils.isNotBlank(id)){
						rule = advertisementPositionInfoService.selectByPrimaryKey(id);
					}
					bean.setCreateTime(rule.getCreateTime());
					bean.setUpdateTime(new Date());
					bean.setStatus(1);
					advertisementPositionInfoService.update(bean);
				}else{
					UUID u = UUID.randomUUID();
					bean.setStatus(1);
					bean.setId(u.toString());
					bean.setCreateTime(new Date());
					advertisementPositionInfoService.insert(bean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return new Result<AdvertisementPositionInfo>(0,"error",e.getMessage());
		}
		return new Result<AdvertisementPositionInfo>(1,"success","success");
	}
	/**
	 * 广告位信息表删除
	* @param id
	* @return
	* @return
	* @author huashuwen
	* @date 2018-03-09 15:30
	 */
	@RequestMapping("/del")
	@ResponseBody
	public Result<AdvertisementPositionInfo> del(String id){
		try{
			advertisementPositionInfoService.delete(id);
		}catch(Exception e){
			e.printStackTrace();
			return new Result<AdvertisementPositionInfo>(0,"error",e.getMessage());
		}
		return new Result<AdvertisementPositionInfo>(1,"success","success");
	}
	
}
