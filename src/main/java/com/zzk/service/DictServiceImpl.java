package com.zzk.service;

import java.util.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzk.dao.DictMapper;
import com.zzk.entity.Dict;
import com.zzk.util.Result;

/**
 * 字典信息表的实体类
* @author: wangpeng
* @date: 2018-04-10 11:46
 */
@Service("dictService")
public class DictServiceImpl implements DictService {

	@Resource
	private DictMapper dictMapper;
	
	
	/**
	 * 分页查询
	 */
	@Override
	public List<Dict> selectByPage(Map map) {
		return dictMapper.selectByPage(map);
	}
	
	/**
	 * 查询总条数
	 */
	@Override
	public int selectCount(Map record) {
		return dictMapper.selectCount(record);
	}
	
	/**
	 * 主键查询
	 */
	@Override
	public Dict selectByPrimaryKey(String id) {
		return dictMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(Dict bean) {
		int result = dictMapper.updateByPrimaryKeySelective(bean);
		CacheService.update();
		return result;
	}
	
	/**
	 * 新增
	 */
	@Override
	public int insert(Dict bean) {
		int result = dictMapper.insertSelective(bean);
		CacheService.update();
		return result; 
	}
	
	/**
	 * 逻辑删除
	 */
	@Override
	public int delete(String id) {
		Dict bean = dictMapper.selectByPrimaryKey(id);
		bean.setStatus(-1);
		int result = dictMapper.updateByPrimaryKeySelective(bean);
		CacheService.update();
		return result;
	}

	@Override
	public Result<Object> selectList(Dict dict) {
		try{
			List<Dict> list= dictMapper.findList(dict);
			return new Result<Object>(1,"success","请求成功",list);
		}catch(Exception e){
			return new Result<Object>(0,"error","数据异常",e);
		}
	}
	
	
	
}
