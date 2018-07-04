package com.zzk.service;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.zzk.aop.LogAspect;
import com.zzk.dao.OperateLogMapper;
import com.zzk.entity.OperateLog;
import com.zzk.entity.User;
import com.zzk.util.DateUtils;
import com.zzk.util.RedisUtil;
import com.zzk.util.StringUtils;

/**
 * 操作日志
* @author: huashuwen
* @date: 2018-04-02 16:31
 */
@Service("operateLogService")
public class OperateLogServiceImpl implements OperateLogService {

	@Resource
	private OperateLogMapper operateLogMapper;
	@Resource
	RedisUtil redisUtil;
	
	/**
	 * 分页查询
	 */
	@Override
	public List<OperateLog> selectByPage(Map map) {
		List<OperateLog> list = operateLogMapper.selectByPage(map);
		String dateStr = "";
		String user = "";
		for(OperateLog bean:list){
			dateStr = DateUtils.changeDateToStr(bean.getDate(), DateUtils.DEFAULT_DATETIME_FORMAT_YMDHMS);
			bean.setDateStr(dateStr);
			user = bean.getUser();
			if(StringUtils.isInteger(user)&&user.length()==11){
				user = user.substring(0, 3)+"****"+user.substring(7, 11);
				bean.setUser(user);
			}
		}
		return list;
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return operateLogMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public OperateLog selectByPrimaryKey(String id) {
		return operateLogMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(OperateLog bean) {
		return operateLogMapper.updateByPrimaryKeySelective(bean);
	}
	
	/**
	 * 新增
	 */
	@Override
	public int insert(OperateLog bean) {
		return operateLogMapper.insertSelective(bean);
	}
	
	
	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id) {
		OperateLog bean = operateLogMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		return operateLogMapper.updateByPrimaryKey(bean);
	}


	/**
	 * 新增操作日志
	 */
	@Override
	public int recordLog(String content,String id,int type){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String token = request.getHeader("Authorization");
		String user ="";
		String shopId = "";
		String jsonString = redisUtil.getValue(token);
		if(StringUtils.isNotBlank(jsonString)){
			JSONObject jo = JSONObject.parseObject(jsonString);
			User u = (User)JSONObject.toJavaObject(jo, User.class);
			shopId = u.getMerchatId();
			if(StringUtils.isNotBlank(u.getNickname())){
				user = u.getNickname();
			}else{
				user = u.getPhoneNum();
			}
		}
		String ip =LogAspect.getIp(request);
		OperateLog bean=new OperateLog();
		bean.setContent(content);
		bean.setObjId(id);
		bean.setDate(new Date());
		bean.setId(StringUtils.getUUID());
		bean.setStatus(1);
		bean.setType(type);
		bean.setUser(user);
		bean.setIp(ip);
		bean.setShopId(shopId);
		return insert(bean);
	}
}
